/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeaccesoadatos;

import java.sql.*;
import java.text.SimpleDateFormat;

public class CuentaDao {
     
  public boolean insertarCuenta(String pNumeroCuenta, Date pFechaCreacion, double pSaldo,
      String pPin, String pEstatus){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarCuenta(?,?,?,?,?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.setDate(2, pFechaCreacion);
        insertar.setDouble(3, pSaldo);
        insertar.setString(4, pPin);
        insertar.setString(5, pEstatus);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
  
   public boolean insertarCuentaTieneOperacion (String pNumeroCuenta, int pIdentificadorOperacion){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarCuentaTieneOperacion(?,?)}");
        insertar.setInt(1, pIdentificadorOperacion);
        insertar.setString(2, pNumeroCuenta);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
   
  
   public boolean actualizarPin (String pNumeroCuenta,String pPin){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL updatePinCuenta(?,?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.setString(2, pPin);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
   
   public boolean actualizarSaldo (String pNumeroCuenta, double pSaldo){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL updateSaldoCuenta(?,?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.setDouble(2, pSaldo);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
   
    public boolean inactivarCuentaBaseDeDatos (String pNumeroCuenta){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL updateEstatus(?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.execute();
    }
    catch (Exception error){
      salida = false;
    }
     
     return salida; 
  }
    
    private ResultSet consultarExistenciaPin (String pNumeroCuenta, String pPin){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarCorrectitudPin(?,?)}");
        consulta.setString(1, pPin);
        consulta.setString(2, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar correctitud de pin");
      }
      return resultado;
    }
    
    private String recorrerResultadoPin(String pNumeroCuenta, String pPin){
     
      try{
         String valorVerdad = "";
         ResultSet resultado = consultarExistenciaPin(pNumeroCuenta, pPin);
       while (resultado.next()){
          valorVerdad = resultado.getObject(1).toString();
       }
       return valorVerdad;
      }
      catch(Exception error){
          System.out.println("Error al recorrer resultado de la cuenta");
          return "0";
      }
    }
    
    public boolean verificarCorrectitudPin(String pNumeroCuenta, String pPin){
      
        String valorVerdad = recorrerResultadoPin(pNumeroCuenta, pPin);
        
        if (valorVerdad.equals("1")){
          return true;
        }
        else{
            return false;
        }
     
    }
    
    private ResultSet consultarClienteCuenta (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarClienteCuenta(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar datos de la cuenta");
      }
      return resultado;
    }

    public String recorrerconsultarClienteCuenta(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarClienteCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += resultado.getObject(1).toString() + "\n";
          datosCliente += resultado.getObject(2).toString() + "\n";
          datosCliente += resultado.getObject(3).toString() + "\n";
          datosCliente += resultado.getObject(4).toString() + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer resultado del cliente");
          return "0";
      }
    }    
    
    private ResultSet consultarExistenciaNumeroCuenta (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarExistenciaNumeroCuenta(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar existencia de cuenta");
      }
      return resultado;
    }
    
    private String recorrerResultadoNumeroCuenta(String pNumeroCuenta){
     
      try{
         String valorVerdad = "";
         ResultSet resultado = consultarExistenciaNumeroCuenta(pNumeroCuenta);
       while (resultado.next()){
          valorVerdad = resultado.getObject(1).toString();
       }
       return valorVerdad;
      }
      catch(Exception error){
          System.out.println("Error al recorrer resultado de la cuenta");
          return "0";
      }
    }
    
      public boolean verificarExistenciaCuenta(String pNumeroCuenta){
      
        String valorVerdad = recorrerResultadoNumeroCuenta(pNumeroCuenta);
        
        if (valorVerdad.equals("1")){
          return true;
        }
        else{
            return false;
        }
     
    }
      
      private ResultSet consultarSaldo (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL selectSaldo(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al consultar saldo");
      }
      return resultado;
    }
      
    private double recorrerResultadoSaldo(String pNumeroCuenta){
     
      try{
         double saldo = 0.0;
         ResultSet resultado = consultarSaldo(pNumeroCuenta);
       while (resultado.next()){
          saldo = Integer.parseInt(resultado.getObject(1).toString());
       }
       return saldo;
      }
      catch(Exception error){
          System.out.println("Error al recorrer el saldo");
          return 0.0;
      }
    }
    
      public boolean verificarSuficienciaDeFondos(String pNumeroCuenta, double pMonto){
      
        double saldo = recorrerResultadoSaldo(pNumeroCuenta);
        
        if (saldo >= pMonto){
          return true;
        }
        else{
            return false;
        }
     
    }
    
      
      
      
    private ResultSet referenciarIdentificadorCuenta (String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL referenciarCuenta(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al referenciar la cuenta");
      }
      return resultado;
    }

    public String recorrerReferenciaNumeroCuenta(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = referenciarIdentificadorCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += resultado.getObject(1).toString();
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer la referencia del número de cuenta");
          return "0";
      }
    }  
      
    private ResultSet consultarCorreoClientePorCuenta (String pNumeroCuenta){
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

    public String recorrerConsultarCorreoClientePorCuenta (String pNumeroCuenta){
     
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
   
   
}
