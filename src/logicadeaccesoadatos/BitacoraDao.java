package logicadeaccesoadatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import logicadeaccesoadatos.Conexion;

public class BitacoraDao {
    
     private static ResultSet consultarBitacoraSegunVista(String PVista){
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    ResultSet resultado = null;
    PreparedStatement consulta;
    try{
      consulta = conectar.prepareCall("{CALL consultarBitacoraSegunVista(?)}");
      consulta.setString(1, PVista);
      resultado = consulta.executeQuery();  
    }
    catch(Exception error){
      System.out.println("Error al consultar una bitácora según la vista");
    }
    return resultado;
  }


  
  public static ArrayList<ArrayList<String>> recorrerConsultaBitacoraSegunVista(String pVista){
      ArrayList<ArrayList<String>> listaTotal= new ArrayList<ArrayList<String>>();
      try{
      ArrayList<String> listaDatos=new ArrayList<String>();
      ResultSet resultado = consultarBitacoraSegunVista(pVista);
      while (resultado.next()){
        for (int indice=1;indice!=4;indice++){
          listaDatos.add(resultado.getObject(indice).toString());
        }
        listaTotal.add(listaDatos);
        listaDatos=new ArrayList<String>();
        //listaDatos.clear();
      }
      System.out.println("Largo de la grande "+listaTotal.size());
      System.out.println("Largo de la pequeña "+listaTotal.get(0).size());
      return listaTotal;
    }
    catch(Exception error){
      System.out.println("Error al recorrer la consulta de una bitácora según la vista");
      return listaTotal;
    }
  }
  
  //-----------------------------------------------
  
       private static ResultSet consultarBitacoraTodaVista(){
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    ResultSet resultado = null;
    PreparedStatement consulta;
    try{
      consulta = conectar.prepareCall("{CALL consultarBitacoraTodaVista()}");
      resultado = consulta.executeQuery();  
    }
    catch(Exception error){
      System.out.println("Error al consultar una bitácora con todas las vistas");
      error.printStackTrace();
    }
    return resultado;
  }


       
  public static ArrayList<ArrayList<String>> recorrerConsultaBitacoraTodaVista(){
      ArrayList<ArrayList<String>> listaTotal= new ArrayList<ArrayList<String>>();
      try{
      ArrayList<String> listaDatos=new ArrayList<String>();
      ResultSet resultado = consultarBitacoraTodaVista();
      while (resultado.next()){
        for (int indice=1;indice!=4;indice++){
          listaDatos.add(resultado.getObject(indice).toString());
        }
        listaTotal.add(listaDatos);
        listaDatos=new ArrayList<String>();
      }
      return listaTotal;
    }
    catch(Exception error){
      System.out.println("Error al recorrer la consulta de una bitácora Toda vista Csv y Xml");
      return listaTotal;
    }
  }
  //--------------------------------------
  
    
   private static ResultSet consultarBitacoraHoy(){
    java.util.Date fechaHoy = new Date();
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    ResultSet resultado = null;
    PreparedStatement consulta;
    try{
      consulta = conectar.prepareCall("{CALL consultarBitacoraHoy(?)}");
      consulta.setDate(1, new java.sql.Date(fechaHoy.getTime()));
      resultado = consulta.executeQuery();  
    }
    catch(Exception error){
      System.out.println("Error al consultar una bitácora de hoy");
    }
    return resultado;
  }

   
  public static ArrayList<ArrayList<String>> recorrerConsultaBitacoraHoy(){
      ArrayList<ArrayList<String>> listaTotal= new ArrayList<ArrayList<String>>();
      try{
      ArrayList<String> listaDatos=new ArrayList<String>();
      ResultSet resultado = consultarBitacoraHoy();
      while (resultado.next()){
        for (int indice=1;indice!=4;indice++){
          listaDatos.add(resultado.getObject(indice).toString());
        }
        listaTotal.add(listaDatos);
        listaDatos=new ArrayList<String>();
      }
      return listaTotal;
    }
    catch(Exception error){
      System.out.println("Error al recorrer la consulta de una bitácora hoy Csv y Xml");
      return listaTotal;
    }
  } 
  
  
  //Verificar credenciales
  
   private static ResultSet consultarCredencialesAdministrador(String pUsuario, String pContrasena){
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    ResultSet resultado = null;
    PreparedStatement consulta;
    try{
      consulta = conectar.prepareCall("{CALL verificarCredenciales(?,?)}");
      consulta.setString(1, pUsuario);
      consulta.setString(2, pContrasena);
      resultado = consulta.executeQuery();  
    }
    catch(Exception error){
      System.out.println("Error al consultar las credenciales del administrador");
    }
    return resultado;
  }


  private static String recorrerConsultaCredencialesAdministrador(String pUsuario, String pContrasena){
    try{
      String valorDeVerdad = "";
      ResultSet resultado = consultarCredencialesAdministrador(pUsuario, pContrasena);
      while (resultado.next()){
        valorDeVerdad += resultado.getObject(1).toString();

      }
      return valorDeVerdad;
    }
    catch(Exception error){
      System.out.println("Error al recorrer la consulta de credenciales");
      return "0";
    }
  }
  
  public static boolean verificarCredencialesAdministrador(String pUsuario, String pContrasena){
    if (recorrerConsultaCredencialesAdministrador(pUsuario, pContrasena).equals("1")){
      return true;
    }
    else{
      return false;
    }
  }
  
}