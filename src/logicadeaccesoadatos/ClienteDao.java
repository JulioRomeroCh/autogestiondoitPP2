/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;
/**
 *
 * @author Jose Blanco
 */
public class ClienteDao {
    
    public static boolean insertarCliente(String pCodigoCliente, String pNumeroTelefonico, String pCorreo){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarCliente(?,?,?)}");
        insertar.setString(1, pCodigoCliente);
        insertar.setString(2, pNumeroTelefonico);
        insertar.setString(3, pCorreo);
        insertar.execute();
    }
    catch (Exception error){
      error.printStackTrace();
      salida = false;
    }
     
     return salida; 
  }
    
    private static ResultSet consultarCorreoClientePorCuenta (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarCorreoCliente(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar el correo del cliente");
      }
      return resultado;
    }

    public static String recorrerConsultarCorreoClientePorCuenta (String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarCorreoClientePorCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += resultado.getObject(1).toString();
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer resultado de correo del cliente");
          return "0";
      }
    }  
   
    
        private static ResultSet consultarNumeroClientePorCuenta (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarNumeroCliente(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar el número del cliente");
      }
      return resultado;
    }

    public static String recorrerConsultarNumeroClientePorCuenta (String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarNumeroClientePorCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += resultado.getObject(1).toString();
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer resultado de número del cliente");
          return "0";
      }
    }  
   
}
