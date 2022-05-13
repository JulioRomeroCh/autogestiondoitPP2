package logicadeaccesoadatos;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeaccesoadatos.*;
import logicadeintegracion.*;
import logicadenegocios.Cuenta;
import logicadenegocios.Operacion;
import logicadenegocios.TipoOperacion;
import logicadevalidacion.Validacion;
import webservice.TipoCambio;

public class OperacionDao {

 public static boolean insertarOperacion(String pTipo, String pNumeroCuenta,
      int pMonto, boolean pCargoComision, double pMontoComision){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    java.util.Date fechaActual = new java.util.Date();
    try{    
        CallableStatement insertar = conectar.prepareCall("{CALL insertarOperacion(?,?,?,?,?,?)}");
        
        insertar.setInt(1, Integer.parseInt(CuentaDao.recorrerReferenciaNumeroCuenta(pNumeroCuenta)));
        insertar.setDate(2, new java.sql.Date(fechaActual.getTime()));
        insertar.setString(3,  pTipo);
        insertar.setInt(4, pMonto);
        insertar.setBoolean(5, pCargoComision);
        insertar.setDouble(6, pMontoComision);
        insertar.execute();
    }
    catch (Exception error){
        System.out.println("Excepción de insertar operación: " + "\n");
        error.printStackTrace();
      salida = false;
    }
     return salida; 
  }
 
       private static ResultSet consultarOperacionesCuenta(String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarOperacionesCuenta(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al referenciar las operaciones de una cuenta");
      }
      return resultado;
    }

    public static String recorrerConsultaOperacionesCuenta(String pNumeroCuenta){
     
      try{
         String datosCuenta = "Operaciones de la cuenta" + "\n";
         ResultSet resultado = consultarOperacionesCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCuenta += "\n" + "Fecha: " +resultado.getObject(1).toString() + "\n";
          datosCuenta += "Tipo de operación: " + resultado.getObject(2).toString() + "\n";
          datosCuenta += "Monto de la operación: " + resultado.getObject(3).toString() + "\n";
          datosCuenta += "Monto de comisión cobrada: " + resultado.getObject(4).toString() + "\n";
       }
       return datosCuenta;
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de operaciones de una cuenta");
          return "0";
      }
    }
    
        public static String recorrerConsultaOperacionesCuentaDolares(String pNumeroCuenta){
     
      try{
         double compraDolar = new TipoCambio().consultarCompraDolar();
         String datosCuenta = "Operaciones de la cuenta" + "\n";
         ResultSet resultado = consultarOperacionesCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCuenta += "\n" + "Fecha: " +resultado.getObject(1).toString() + "\n";
          datosCuenta += "Tipo de operación: " + resultado.getObject(2).toString() + "\n";
          datosCuenta += "Monto de la operación (en dólares): " + (Double.parseDouble(resultado.getObject(3).toString()) / compraDolar)  + "\n";
          datosCuenta += "Monto de comisión cobrada (en dólares): " + (Double.parseDouble(resultado.getObject(4).toString()) / compraDolar)  + "\n";
       }
       return datosCuenta;
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de operaciones de una cuenta");
          return "0";
      }
    }
        
        
    //---------------------------CARGAR BASE DE DATOS    
    private static ResultSet cargarOperacion(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL cargarOperacion()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al consultar la tabla operacion");
      }
      return resultado;
    }
    
      public static void recorrerCargarOperacion(){
     
      try{
      ResultSet resultado = cargarOperacion();
      while (resultado.next()){

      String pNumeroCuenta = String.valueOf(resultado.getObject(1));
      String fechaEnString = String.valueOf(resultado.getObject(2));
      java.util.Date pFechaOperacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnString);
      TipoOperacion pTipoOperacion =  TipoOperacion.valueOf(String.valueOf(resultado.getObject(3)));
      int pMontoOperacion = (Integer.parseInt(String.valueOf(resultado.getObject(4))));
      boolean pCargoComision = Validacion.convertirNumeroABooleano((Integer.parseInt(String.valueOf(resultado.getObject(5)))));
      double pMontoComision = Double.parseDouble(String.valueOf(resultado.getObject(6)));
      
      Operacion nuevaOperacion = new Operacion(pFechaOperacion, pTipoOperacion, pCargoComision, pMontoOperacion, pMontoComision);
      Cuenta nuevaCuenta = ControladorCuenta.buscarCuenta(pNumeroCuenta);
      nuevaCuenta.operaciones.add(nuevaOperacion);
      Validacion.llamarEsTransaccionConDinero(pTipoOperacion, pNumeroCuenta);
      
 
      }
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al recorrer la consulta de operación");
      }
    }
        
        
}
