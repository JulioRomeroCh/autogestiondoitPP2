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
  /**
  * <p> Método que registra una nueva operación en la base de datos.
   * @param pTipo: atributo de tipo String.
   * @param pNumeroCuenta: atributo de tipo String.
   * @param pMonto: atributo de tipo int.
   * @param pCargoComision: atributo de tipo boolean.
   * @param pMontoComision: atributo de tipo double.
  * @return booleano que representa el ézito o fracaso del registro.
   */
  public static boolean insertarOperacion(String pTipo, String pNumeroCuenta,
  int pMonto, boolean pCargoComision, double pMontoComision, String pVista){
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    java.util.Date fechaActual = new java.util.Date();
    try{    
      CallableStatement insertar = conectar.prepareCall("{CALL insertarOperacion(?,?,?,?,?,?,?,?)}");
      insertar.setInt(1, Integer.parseInt(CuentaDao.recorrerReferenciaNumeroCuenta(pNumeroCuenta)));
      insertar.setDate(2, new java.sql.Date(fechaActual.getTime()));
      insertar.setString(3,  pTipo);
      insertar.setInt(4, pMonto);
      insertar.setBoolean(5, pCargoComision);
      insertar.setDouble(6, pMontoComision);
      insertar.setDate(7, new java.sql.Date(fechaActual.getTime()));
      insertar.setString(8, pVista);
      
      insertar.execute();
    }
    catch (Exception error){
      System.out.println("Excepción de insertar operación: " + "\n");
      error.printStackTrace();
      salida = false;
    }
    return salida; 
  }
  /**
   *<p> Método que consulta las operaciones asociadas a una cuenta. 
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: fecha, tipo, 
   * monto total y monto de la comisión.
   */
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

  /**
   * Método que recorre el resultado del método consultarOperacionesCuenta y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene los datos de las operaciones relacionadas a una cuenta 
   * (los montos se muestran en colones).
   */
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
  
  /**
   * Método que recorre el resultado del método consultarOperacionesCuenta y lo retorna,
   * con los montos en dólares.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene los datos de las operaciones relacionadas a una cuenta 
   * (los montos se muestran en dólares).
   */  
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
  /**
   * <p> Método que consulta todas las operaciones registradas en la base de datos.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene:
   * numero de cuenta, fecha de la operaciónm, tipo, monto, cargo de comisión y monto de comisión.
   */
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
  /**
   * <p> Método que consulta las operaciones registradas, las convierte en objetos de tipo Operación y
   * las asigna a la cuenta respectiva.
   */  
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
        Operacion nuevaOperacion;
        nuevaOperacion = logicacreacional.SimpleOperacionFactory.crearOperacion();
        nuevaOperacion.setFechaOperacion(pFechaOperacion);
        nuevaOperacion.setTipo(pTipoOperacion);
        nuevaOperacion.setCargoComision(pCargoComision);
        nuevaOperacion.setMonto(pMontoOperacion);
        nuevaOperacion.setMontoComision(pMontoComision);
        Cuenta nuevaCuenta = ControladorCuenta.buscarCuenta(pNumeroCuenta);
        nuevaCuenta.operaciones.add(nuevaOperacion);
        Validacion.llamarEsTransaccionConDinero(pTipoOperacion, pNumeroCuenta);
      }
    }
    catch(Exception error){
      error.printStackTrace();
      System.out.println("Error al recorrer la consulta de operación o al instanciar una operación en el daoOperacion");
    }
  }
        
        
}
