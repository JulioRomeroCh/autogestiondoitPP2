/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;
import logicadeaccesoadatos.*;
import webservice.TipoCambio;

public class OperacionDao {

 public static boolean insertarOperacion(String pTipo, String pNumeroCuenta,
      int pMonto, boolean pCargoComision, double pMontoComision){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    java.sql.Date fechaSQL = new java.sql.Date((new java.util.Date()).getDate());
    try{    
        CallableStatement insertar = conectar.prepareCall("{CALL insertarOperacion(?,?,?,?,?,?)}");
        
        insertar.setInt(1, Integer.parseInt(CuentaDao.recorrerReferenciaNumeroCuenta(pNumeroCuenta)));
        insertar.setDate(2, fechaSQL);
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
}
