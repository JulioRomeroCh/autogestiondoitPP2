/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logicadeintegracion.*;
import logicadenegocios.Cliente;
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
    
    private static ResultSet consultarExistenciaCliente (String pIdentificacion){ 
       Conexion nuevaConexion = new Conexion(); 
       Connection conectar = nuevaConexion.conectar(); 
       ResultSet resultado = null; 
       PreparedStatement consulta; 
        
      try{ 
        consulta = conectar.prepareCall("{CALL consultarExistenciaCliente(?)}"); 
        consulta.setString(1, pIdentificacion); 
        resultado = consulta.executeQuery();   
      } 
      catch(Exception error){ 
          System.out.println("Error al consultar existencia del cliente"); 
      } 
      return resultado; 
    } 
     
    private static String recorrerResultadoExistenciaCliente(String pIdentificacion){ 
      
      try{ 
         String valorVerdad = ""; 
         ResultSet resultado = consultarExistenciaCliente(pIdentificacion); 
       while (resultado.next()){ 
          valorVerdad = resultado.getObject(1).toString(); 
       } 
       return valorVerdad; 
      } 
      catch(Exception error){ 
          System.out.println("Error al recorrer resultado de la existencia del cliente"); 
          return "0"; 
      } 
    } 
     
    public static boolean verificarExistenciaCliente(String pIdentificacion){ 
       
        String valorVerdad = recorrerResultadoExistenciaCliente(pIdentificacion); 
         
        if (valorVerdad.equals("1")){ 
          return true; 
        } 
        else{ 
            return false; 
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
  
  //---------------------------------CARGARBASEDATOS----------------
  
  private static ResultSet cargarCliente(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL cargarCliente()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al consultar la tabla cliente");
      }
      return resultado;
    }
    
      public static void recorrerCargarCliente(){
     
      try{
      ResultSet resultado = cargarCliente();
      while (resultado.next()){
   
      String pIdentificacion = String.valueOf(resultado.getObject(1));
      String pNombre = String.valueOf(resultado.getObject(2));
      String pPrimerApellido = String.valueOf(resultado.getObject(3));   
      String pSegundoApellido = String.valueOf(resultado.getObject(4));
      String fechaEnString = String.valueOf(resultado.getObject(5));
      java.util.Date pFechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnString);
      String pCodigo = String.valueOf(resultado.getObject(6));
      String pNumero = String.valueOf(resultado.getObject(7));
      String pCorreo = String.valueOf(resultado.getObject(8));
        
      Cliente nuevoCliente = new Cliente(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento, pCodigo, pNumero, pCorreo);
      ControladorCliente.clientes.add(nuevoCliente);
      ControladorPersona.personas.add(nuevoCliente);
       }
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al recorrer la consulta de clientes");
      }
    }
      

  
   
}
