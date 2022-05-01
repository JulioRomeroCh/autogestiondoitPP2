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
    
    public boolean insertarCliente(String pCodigoCliente, String pNumeroTelefonico, String pCorreo){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarCliente(?,?,?,?,?,?)}");
        insertar.setString(1, pCodigoCliente);
        insertar.setString(2, pNumeroTelefonico);
        insertar.setString(3, pCorreo);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
}
