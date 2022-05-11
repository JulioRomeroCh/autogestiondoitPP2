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
import logicadeintegracion.ControladorCuenta;
import logicadenegocios.Cuenta;
import webservice.TipoCambio;

public class CuentaDao {
 
    
  public static boolean insertarCuenta(String pNumeroCuenta, java.util.Date pFechaCreacion, double pSaldo,
      String pPin, String pEstatus){
      
    boolean salida = true;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();
    try{        
        CallableStatement insertar = conectar.prepareCall("{CALL insertarCuenta(?,?,?,?,?)}");
        insertar.setString(1, pNumeroCuenta);
        insertar.setDate(2, new java.sql.Date(pFechaCreacion.getTime()));
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
  
  
   public static boolean actualizarPin (String pNumeroCuenta,String pPin){
      
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
   
   public static boolean actualizarSaldo (String pNumeroCuenta, double pSaldo){
      
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
   
    public static boolean inactivarCuentaBaseDeDatos (String pNumeroCuenta){
      
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
    
    private static ResultSet consultarExistenciaPin (String pNumeroCuenta, String pPin){
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
    
    private static String recorrerResultadoPin(String pNumeroCuenta, String pPin){
     
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
    
    public static boolean verificarCorrectitudPin(String pNumeroCuenta, String pPin){
      
        String valorVerdad = recorrerResultadoPin(pNumeroCuenta, pPin);
        
        if (valorVerdad.equals("1")){
          return true;
        }
        else{
            return false;
        }
     
    }
    
    private static ResultSet consultarClienteCuenta (String pNumeroCuenta){
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

    public static String recorrerconsultarClienteCuenta(String pNumeroCuenta){
     
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
    
    private static ResultSet consultarExistenciaNumeroCuenta (String pNumeroCuenta){
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
    
    private static String recorrerResultadoNumeroCuenta(String pNumeroCuenta){
     
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
    
      public static boolean verificarExistenciaCuenta(String pNumeroCuenta){
      
        String valorVerdad = recorrerResultadoNumeroCuenta(pNumeroCuenta);
        
        if (valorVerdad.equals("1")){
          return true;
        }
        else{
            return false;
        }
     
    }
      
      private static ResultSet consultarSaldo (String pNumeroCuenta){
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
          error.printStackTrace();
          System.out.println("Error al consultar saldo");
      }
      return resultado;
    }
      
    private static double recorrerResultadoSaldo(String pNumeroCuenta){
     
      try{
         double saldo = 0.0;
         ResultSet resultado = consultarSaldo(pNumeroCuenta);
       while (resultado.next()){
          saldo = Double.parseDouble(resultado.getObject(1).toString());
       }
       return saldo;
      }
      catch(Exception error){
          System.out.println("Error al recorrer el saldo");
          return 0.0;
      }
    }
    
      public static boolean verificarSuficienciaDeFondos(String pNumeroCuenta, double pMonto){
        double saldo = recorrerResultadoSaldo(pNumeroCuenta);
        
        if (saldo >= pMonto){
          return true;
        }
        else{
            return false;
        }
     
    }
    
      
      
      
    private static ResultSet referenciarIdentificadorCuenta (String pNumeroCuenta){
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

    public static String recorrerReferenciaNumeroCuenta(String pNumeroCuenta){
     
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
      
      private static ResultSet consultarDatosCuenta(String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarDatosCuenta(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          System.out.println("Error al referenciar la los datos de una cuenta");
      }
      return resultado;
    }

    public static String recorrerConsultaDatosCuenta(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarDatosCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += "\n" + "Número de cuenta (encriptado por seguridad): " +resultado.getObject(1).toString() + "\n";
          datosCliente += "PIN (encriptado por seguridad)" + resultado.getObject(7).toString() + "\n";
          datosCliente += "Estatus de la cuenta: " + resultado.getObject(2).toString() + "\n";
          datosCliente += "Saldo: " + resultado.getObject(3).toString() + "\n";
          datosCliente += "Nombre completo del dueño: " + resultado.getObject(4).toString() + " ";
          datosCliente += resultado.getObject(5).toString() + " ";
          datosCliente += resultado.getObject(6).toString() + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de datos de una cuenta");
          return "0";
      }
    }  
    
        public static String recorrerConsultaDatosCuentaDolares(String pNumeroCuenta){
     
      try{
          double compraDolar = new TipoCambio().consultarCompraDolar();
         String datosCliente = "";
         ResultSet resultado = consultarDatosCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += "\n" + "Número de cuenta (encriptado por seguridad): " +resultado.getObject(1).toString() + "\n";
          datosCliente += "PIN (encriptado por seguridad)" + resultado.getObject(7).toString() + "\n";
          datosCliente += "Estatus de la cuenta: " + resultado.getObject(2).toString() + "\n";
          datosCliente += "Saldo (en dólares): " + (Double.parseDouble(resultado.getObject(3).toString()) / compraDolar) + "\n";
          datosCliente += "Nombre completo del dueño: " + resultado.getObject(4).toString() + " ";
          datosCliente += resultado.getObject(5).toString() + " ";
          datosCliente += resultado.getObject(6).toString() + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          System.out.println("Error al recorrer la consulta de datos de una cuenta");
          return "0";
      }
    } 
       //---------------------------- Comisiones Universo Cuentas ------------------------
        
       private static ResultSet consultarTotalComisionesDepositosUniversoCuentas(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesDepositosUniversoCuentas()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          //System.out.println("Error al consultar el total de comisiones por depósitos del UC");
      }
      return resultado;
    }
    
        public static String recorrerConsultaTotalComisionesDepositosUniversoCuentas(){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesDepositosUniversoCuentas();
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de depósitos para todo el universo de cuentas: " +resultado.getObject(1).toString() + " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por depósito de UC");
          return "Total de comisiones por concepto de depósitos para todo el universo de cuentas: 0 colones";
      }
    } 
      
       private static ResultSet consultarTotalComisionesRetirosUniversoCuentas(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesRetirosUniversoCuentas()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          //System.out.println("Error al consultar el total de comisiones por retiros del UC");
      }
      return resultado;
    }
    
        public static String recorrerConsultaTotalComisionesRetirosUniversoCuentas(){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesRetirosUniversoCuentas();
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de retiros para todo el universo de cuentas: " +resultado.getObject(1).toString() + " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por retiros de UC");
          return "Total de comisiones por concepto de retiros para todo el universo de cuentas: 0 colones";
      }
    }
        
      private static ResultSet consultarTotalComisionesDepositosYRetirosUniversoCuentas(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesDepositosYRetirosUniversoCuentas()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          //System.out.println("Error al consultar el total de comisiones por depósitos y retiros del UC");
      }
      return resultado;
    }
    
      public static String recorrerConsultaTotalComisionesDepositosYRetirosUniversoCuentas(){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesDepositosYRetirosUniversoCuentas();
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de depósitos y retiros para todo el universo de cuentas: " +resultado.getObject(1).toString() + " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por depósitos y retiros de UC");
         return "Total de comisiones por concepto de depósitos y retiros para todo el universo de cuentas: 0 colones";
      }
    }
   
    //-----------------------------------------Comisiones cuenta en particular---------------------

           private static ResultSet consultarTotalComisionesDepositosCuentaUnica(String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesDepositosCuentaUnica(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          //System.out.println("Error al consultar el total de comisiones por depósitos de única cuenta");
      }
      return resultado;
    }
    
      public static String recorrerConsultaTotalComisionesDepositosCuentaUnica(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesDepositosCuentaUnica(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de depósitos para la cuenta " + pNumeroCuenta + " es: "  + resultado.getObject(1).toString() +  " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por depósito de una cuenta en particular");
          return "Total de comisiones por concepto de depósitos para la cuenta indicada es de: 0 colones ";
      }
    } 
      
       private static ResultSet consultarTotalComisionesRetirosCuentaUnica(String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesRetirosCuentaUnica(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          //System.out.println("Error al consultar el total de comisiones por retiros de cuenta única");
      }
      return resultado;
    }
    
      public static String recorrerConsultaTotalComisionesRetirosUnicaCuenta(String pNumeroCuenta){
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesRetirosCuentaUnica(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de retiros para la cuenta " + pNumeroCuenta + " es: " + resultado.getObject(1).toString() + " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por retiros para una única cuenta");
          return "Total de comisiones por concepto de retiros para la cuenta indicada es de: 0 colones ";
      }
    }
        
      private static ResultSet consultarTotalComisionesDepositosYRetirosCuentaUnica(String pNumeroCuenta){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL consultarTotalComisionesDepositosYRetirosCuentaUnica(?)}");
        consulta.setString(1, pNumeroCuenta);
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          //System.out.println("Error al consultar el total de comisiones por depósitos y retiros de única cuenta");
      }
      return resultado;
    }
    
      public static String recorrerConsultaTotalComisionesDepositosYRetirosCuentaUnica(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = consultarTotalComisionesDepositosYRetirosCuentaUnica(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += "\n" + "Total de comisiones por concepto de depósitos y retiros para la cuenta " + pNumeroCuenta + " es: " +resultado.getObject(1).toString()  + " colones" + "\n";
       }
       return datosCliente;
      }
      catch(Exception error){
          //System.out.println("Error al recorrer la consulta del total de comisiones por depósitos y retiros de cuenta única");
          return "Total de comisiones por concepto de depósitos y retiros para la cuenta indicada es de: 0 colones";
      }
    }
      
   public static ResultSet ConsultaEstatusCuenta(String pNumeroCuenta){
    ResultSet resultado = null;
    PreparedStatement consulta;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();   
    
    try{ 
      consulta = conectar.prepareStatement("{CALL consultarEstatus(?)}");
      consulta.setString(1, pNumeroCuenta);
      resultado = consulta.executeQuery();    
    }
    catch(Exception error){
      error.printStackTrace();    
    }
    return resultado;
   }
   
   public static String recorrerConsultaEstatusCuenta(String pNumeroCuenta){
     
      try{
         String datosCliente = "";
         ResultSet resultado = ConsultaEstatusCuenta(pNumeroCuenta);
       while (resultado.next()){
          datosCliente += resultado.getObject(1).toString();
       }
       return datosCliente;
      }
      catch(Exception error){
        error.printStackTrace();
        return "";
      }
    }
      
      
   //------------------------Métodos GUI

      
   public static ResultSet ConsultaListarCuentas(){
    ResultSet resultado = null;
    PreparedStatement consulta;
    Conexion nuevaConexion = new Conexion();
    Connection conectar = nuevaConexion.conectar();   
    
    try{ 
      consulta = conectar.prepareStatement("SELECT DISTINCT CAST(aes_decrypt(cuenta.numeroCuenta, 'pass45') AS char(100)), persona.nombre, persona.apellido1 FROM cuenta JOIN persona_tiene_cuenta ON cuenta.identificadorCuenta = persona_tiene_cuenta.identificadorCuenta JOIN persona ON persona_tiene_cuenta.identificacionPersona = persona.identificacionPersona ORDER BY aes_decrypt(cuenta.saldo, 'pass45') + 0 DESC");
      resultado = consulta.executeQuery();    
    }
    catch(Exception error){
      error.printStackTrace();    
    }
    return resultado;
   }
   
     public static ResultSet ConsultaListarCuentasTabla(){
      
      
      PreparedStatement consulta;
      ResultSet resultado = null;
          
      try{
      
        Conexion nuevaConexion = new Conexion();
        Connection conectar = nuevaConexion.conectar();   
        consulta = conectar.prepareStatement("SELECT DISTINCT CAST(aes_decrypt(cuenta.numeroCuenta, 'pass45') AS char(100)), cuenta.estatus, CAST(aes_decrypt(cuenta.saldo, 'pass45') AS char(100)), persona.identificacionPersona, persona.nombre, persona.apellido1, persona.apellido2 FROM cuenta JOIN persona_tiene_cuenta ON cuenta.identificadorCuenta = persona_tiene_cuenta.identificadorCuenta JOIN persona ON persona_tiene_cuenta.identificacionPersona = persona.identificacionPersona ORDER BY aes_decrypt(cuenta.saldo, 'pass45') + 0 DESC");      
        resultado = consulta.executeQuery();       
          
      }
      catch(Exception error){
        error.printStackTrace();    
      }
      return resultado;
  }
   

  
   //----------------------MÉTODOS CARGAR BASE 
  
  private static ResultSet cargarCuenta(){
       Conexion nuevaConexion = new Conexion();
       Connection conectar = nuevaConexion.conectar();
       ResultSet resultado = null;
       PreparedStatement consulta;
       
      try{
        consulta = conectar.prepareCall("{CALL cargarCuenta()}");
        resultado = consulta.executeQuery();  
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al consultar la tabla cuenta");
      }
      return resultado;
    }
    
      public static void recorrerCargarCuenta(){
     
      try{
      ResultSet resultado = cargarCuenta();
      while (resultado.next()){
      
      String pNumeroCuenta = String.valueOf(resultado.getObject(1));
      String fechaEnString = String.valueOf(resultado.getObject(2));
      java.util.Date pFechaCreacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaEnString);
      double pSaldo =  (Double.parseDouble(String.valueOf(resultado.getObject(3))));
      String pPin = String.valueOf(resultado.getObject(4));
      String pEstatus = String.valueOf(resultado.getObject(5));
        
      Cuenta nuevaCuenta = new Cuenta(pNumeroCuenta, pFechaCreacion, pSaldo, pPin, pEstatus);
      ControladorCuenta.cuentas.add(nuevaCuenta);
       }
      }
      catch(Exception error){
          error.printStackTrace();
          System.out.println("Error al recorrer la consulta de cuentas");
      }
    }
  

  
  
  
  
  
}

