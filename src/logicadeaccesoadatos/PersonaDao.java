package logicadeaccesoadatos;


import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeaccesoadatos.CuentaDao;
import logicadeintegracion.*;
import logicadenegocios.Persona;

public class PersonaDao {
    
       
  public static boolean insertarPersona(String pIdentificacion, String pNombre, String pPrimerApellido,
          String pSegundoApellido, Date pFechaNacimiento, String pRol){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarPersona(?,?,?,?,?,?)}");
        insertar.setString(1, pIdentificacion);
        insertar.setString(2, pNombre);
        insertar.setString(3, pPrimerApellido);
        insertar.setString(4, pSegundoApellido);
        
        insertar.setDate(5, new java.sql.Date(pFechaNacimiento.getTime()));
        insertar.setString(6, pRol);
        insertar.execute();
    }
    catch (Exception error){
        //error.printStackTrace();
        salida = false;
    }
     
     return salida; 
  }
  
    public static boolean insertarClienteEsPersona(String pIdentificacion, String pCodigoCliente){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarPersonaCliente(?,?)}");
        insertar.setString(1, pCodigoCliente);
        insertar.setString(2, pIdentificacion);
        insertar.execute();
    }
    catch (Exception error){
      error.printStackTrace();
      salida = false;
    }
     
     return salida; 
  }
    
   public static boolean insertarPersonaTieneCuenta(String pIdentificacion, String pNumeroCuenta){
    String identificadorCuenta = CuentaDao.recorrerReferenciaNumeroCuenta(pNumeroCuenta);
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarPersonaTieneCuenta(?,?)}");
        insertar.setInt(1, Integer.parseInt(identificadorCuenta));
        insertar.setString(2, pIdentificacion);
        insertar.execute();
    }
    catch (Exception error){
        error.printStackTrace();
      salida = false;
    }
     
     return salida; 
  }
   
   
  //-----------CARGAR BASE DATOS
   
  private static ResultSet cargarPersona(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL cargarPersona()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al consultar la tabla persona");
      }
      return resultado;
    }
    
      public static void recorrerCargarPersona(){
     
      try{
      ResultSet resultado = cargarPersona();
      while (resultado.next()){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); 
        
      String pIdentificacion = String.valueOf(resultado.getObject(1));
      String pNombre = String.valueOf(resultado.getObject(2));
      String pPrimerApellido = String.valueOf(resultado.getObject(3));   
      String pSegundoApellido = String.valueOf(resultado.getObject(4));
      String pFechaNacimiento =  String.valueOf(resultado.getObject(5));
      
      Date fechaConvertida = new SimpleDateFormat("yyyy-MM-dd").parse(pFechaNacimiento);
      

       
      Persona nuevaPersona = new Persona(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, fechaConvertida);
      ControladorPersona.personas.add(nuevaPersona);
       }
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de personas");
      }
    }
   
      
    private static ResultSet cargarPersonaTieneCuenta(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL cargarPersonaTieneCuenta()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al consultar la tabla persona_tiene_cuenta");
      }
      return resultado;
    }
    
      public static void recorrerCargarPersonaTieneCuenta(){
     
      try{
      ResultSet resultado = cargarPersonaTieneCuenta();
      while (resultado.next()){
        
      String pNumeroCuenta = String.valueOf(resultado.getObject(1));  
      String pIdentificacionPersona = String.valueOf(resultado.getObject(2));
  
      Persona nuevaPersona = ControladorPersona.buscarPersona(pIdentificacionPersona);
      nuevaPersona.cuentas.add(ControladorCuenta.buscarCuenta(pNumeroCuenta));

       }
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de persona_tiene_cuenta");
      }
    }  
   
   
}
