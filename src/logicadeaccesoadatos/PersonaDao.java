/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;


import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeaccesoadatos.CuentaDao;

public class PersonaDao {
    
    
    
  public boolean insertarPersona(String pIdentificacion, String pNombre, String pPrimerApellido,
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
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaTexto = formatoFecha.format(pFechaNacimiento);
        Date fechaDate = formatoFecha.parse(fechaTexto);
        java.sql.Date fechaSQL = new java.sql.Date(fechaDate.getDate());
        
        insertar.setDate(5, fechaSQL);
        insertar.setString(6, pRol);
        insertar.execute();
    }
    catch (Exception error){
        error.printStackTrace();
        salida = false;
    }
     
     return salida; 
  }
  
    public boolean insertarClienteEsPersona(String pIdentificacion, String pCodigoCliente){
      
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
    
      public boolean insertarPersonaTieneCuenta(String pIdentificacion, String pNumeroCuenta){
    CuentaDao accesoACuenta = new CuentaDao();
    String identificadorCuenta = accesoACuenta.recorrerReferenciaNumeroCuenta(pNumeroCuenta);
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
}
