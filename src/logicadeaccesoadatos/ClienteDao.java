/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
    
    
    
    //--------------------MÉTODOS GUI
    
  public static void ConsultaListarClientes(JComboBox pComboBox){
    ResultSet resultado;
    PreparedStatement consulta;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    pComboBox.removeAllItems();
    
    try{
    
      consulta = conectar.prepareStatement("SELECT identificacionPersona, nombre, apellido1 FROM Persona WHERE rol = 'Cliente'");
      resultado = consulta.executeQuery();
      
      while(resultado.next()){
        String mensaje = String.valueOf(resultado.getObject(1)) + " - " + String.valueOf(resultado.getObject(2)) + " " +String.valueOf(resultado.getObject(3));
        pComboBox.addItem(mensaje);
      }
      
    }
    catch(Exception error){
      error.printStackTrace();    
    }

    
  }
  
  
      
  public static void ConsultaListarClientesTabla(JTable pTabla){
      DefaultTableModel modelo = (DefaultTableModel) pTabla.getModel();
      modelo.setRowCount(0);
      PreparedStatement consulta;
      ResultSet resultado;
      ResultSetMetaData datosResultado;
      int cantidadColumnas = 0;
      
      try{
      
        Conexion nuevaConexion = new Conexion();
        Connection conectar = nuevaConexion.conectar();   
        consulta = conectar.prepareStatement("SELECT apellido1, apellido2, nombre, identificacionPersona FROM Persona WHERE rol = 'Cliente'");      
        resultado = consulta.executeQuery();
        datosResultado = resultado.getMetaData();
        cantidadColumnas = datosResultado.getColumnCount();
        
        while(resultado.next()){
          Object [] fila = new Object[cantidadColumnas];
          for(int indice = 0; indice<cantidadColumnas; indice++){
            fila[indice] = resultado.getObject(indice + 1);
          }
          modelo.addRow(fila);
        }
  
          
      }
      catch(Exception error){
        error.printStackTrace();    
      }
      
  }
  
   
}
