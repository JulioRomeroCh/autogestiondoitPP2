/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;


import java.util.Date;

import java.sql.*;

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
        insertar.setDate(5, (java.sql.Date) pFechaNacimiento);
        insertar.setString(6, pRol);
        insertar.execute();
    }
    catch (Exception error){
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
      salida = false;
    }
     
     return salida; 
  }
    
      public boolean insertarPersonaTieneCuenta(String pIdentificacion, String pNumeroCuenta){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarClienteTieneCuenta(?,?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.setString(2, pIdentificacion);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
}
