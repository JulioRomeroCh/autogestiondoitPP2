/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;

public class OperacionDao {

 public static boolean insertarOperacion(Date pFechaOperacion, String pTipo,
      int pMonto, boolean pCargoComision, double pMontoComision){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarOperacion(?,?,?,?,?)}");
        insertar.setDate(1, (java.sql.Date) pFechaOperacion);
        insertar.setString(2,  pTipo);
        insertar.setInt(3, pMonto);
        insertar.setBoolean(4, pCargoComision);
        insertar.setDouble(5, pMontoComision);
        insertar.execute();
    }
    catch (Exception error){
        System.out.println("Excepción de insertar operación: " + "\n");
        error.printStackTrace();
      salida = false;
    }
     return salida; 
  }    
}
