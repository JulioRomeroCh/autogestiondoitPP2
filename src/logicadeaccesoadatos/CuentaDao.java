package logicadeaccesoadatos;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeintegracion.ControladorCuenta;
import logicadenegocios.Cuenta;
import webservice.TipoCambio;

public class CuentaDao {
 
  /**
   * <p> Método que crea una nueva cuenta en la base de datos.
   * @param pNumeroCuenta: atributo de tipo String.
   * @param pFechaCreacion: atributo de tipo Date.
   * @param pSaldo: atributo de tipo double.
   * @param pPin: atributo de tipo String.
   * @param pEstatus: atributo de tipo String.
   * @return booleano que representa el ézito o fracaso de la inserción.
   */  
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
  
  /**
   * <p> Método que actualizae el Pin de una cuenta, en la base de datos.
   * @param pNumeroCuenta: String que representa la cuenta que actualizará el Pin.
   * @param pPin: String que representa el nuevo Pin.
   * @return booleano que representa el ézito o fracaso de la actualización.
   */  
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
  
  /**
   * <p> Método que actualizae el saldo de una cuenta, en la base de datos.
   * @param pNumeroCuenta: String que representa la cuenta que actualizará el Pin.
   * @param pSaldo: String que representa el saldo actualizado de una cuenta.
   * @return booleano que representa el ézito o fracaso de la actualización.
   */ 
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
  
  /**
   * <p> Método encargado de bloquear una cuenta, a nivel de base de datos.
   * @param pNumeroCuenta: String que representa la cuenta que se desea inactivar.
   * @return booleano que representa el ézito o fracaso de la inactivación.
   */
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
   
  /**
   * <p> Método que consulta si el pin ingresado es el correcto para la cuenta indicada.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pPin: String que se compara con el Pin actual de la cuenta indicada.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**
   * <p> Método que recorre el resultado del método consultarExistenciaPin y lo retorna.
   * @param pNumeroCuenta :String que representa la cuenta que se desea consultar.
   * @param pPin :String que se compara con el Pin actual de la cuenta indicada.
   * @return String que contiene un 1 en caso de que el pin ingresado coincida con el pin de la cuenta, o 0
   * en caso contrario.
   */
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
  
  /**
   * <p> Método que retorna un booleano, indicando si el pin ingresado coincide con el pin de la cuenta.
   * @param pNumeroCuenta :String que representa la cuenta que se desea consultar.
   * @param pPin :String que se compara con el Pin actual de la cuenta indicada.
   * @return booleno que indica si el pin ingresado coincide con el pin de la cuenta.
   */
  public static boolean verificarCorrectitudPin(String pNumeroCuenta, String pPin){    
    String valorVerdad = recorrerResultadoPin(pNumeroCuenta, pPin);    
    if (valorVerdad.equals("1")){
      return true;
    }
    else{
      return false;
    } 
  }
  
  /**
   * <p> Método que consulta los datos del cliente asociado a una cuenta.
   * @param pNumeroCuenta:String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: identificación,
   * nombre, apellido1 y apellido2.
   */
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

  /**
   * <p> Método que retorna los datos del cliente dueño de una cuenta.
   * @param pNumeroCuenta:String que representa la cuenta que se desea consultar.
   * @return String que contiene los datos de un cliente: identificación,
   * nombre, apellido1 y apellido2. 
   */
  public static String recorrerConsultarClienteCuenta(String pNumeroCuenta){   
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
  
  /**
   * <p> Método que consulta a la base de datos si la cuenta ingresada se encuentra registrada.
   * @param pNumeroCuenta:String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**
   * Método que recorre el resultado del método consultarExistenciaNumeroCuenta y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene un 1 en caso de que la cuenta indicada esté registrada en el sistema, o
   * 0 en caso contrario.
   */
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
  /**
   * <p> Método que retorna un booleano, indicando si la cuenta ingresada está registrada.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return boolean que indica si la cuenta ingresada está registrada.
   */  
  public static boolean verificarExistenciaCuenta(String pNumeroCuenta){    
    String valorVerdad = recorrerResultadoNumeroCuenta(pNumeroCuenta);    
      if (valorVerdad.equals("1")){
        return true;
      }
      else{
        return false;
      }
  }
  /**
   * <p> Método que consulta el saldo de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */    
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
  
  /**
   * <p> Método que recorre el resultado del método consultarSaldo y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return double que representa el saldo de la cuenta indicada.
   */    
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
  
  /**
   * <p> Método encargado de verificar si la cuenta indicada tiene suficiencia de fondos para realizar
   * una operación.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pMonto: double que se compara con el saldo de la cuenta.
   * @return boolean que indica si existe sufiencia de fondos para afrontar una operación.
   */  
  public static boolean verificarSuficienciaDeFondos(String pNumeroCuenta, double pMonto){
    double saldo = recorrerResultadoSaldo(pNumeroCuenta);
    if (saldo >= pMonto){
      return true;
    }
    else{
      return false;
    } 
  }
  
  /**
   * <p> Método encargado de referenciar una cuenta, usando el número de cuenta
   * @param pNumeroCuenta: String que representa la cuenta que se desea referenciar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**<p> Método que recorre el resultado del método referenciarIdentificadorCuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea referenciar.
   * @return String que representa el identificador de la cuenta seleccionada.
   */
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
  
  /**
   * <p> Método encargado de consultar los datos de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: número de cuenta,
   * estatus, saldo; además de datos del dueño como: nombre, apellido1, apellido2 y el pin de la cuenta
   * (encriptado).
   */    
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
  
  /**
   * <p> Método encargado de recorrer el resultado del método consultarDatosCuenta y retornar el resultado.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene un mensaje con los datos asociados a la cuenta (los datos son mostrados 
   * en colones).
   */
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
  
  /**
   * <p> Método encargado de recorrer el resultado del método consultarDatosCuenta y retornar el resultado en dólares.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene un mensaje con los datos asociados a la cuenta (los datos son mostrados
   * en dolares).
   */ 
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
  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en depósitos,
   * para todas las cuentas.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */      
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
  
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en depósitos,
   * para todas las cuentas.
   */  
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
  
  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en retiros,
   * para todas las cuentas.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */ 
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
 
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesRetirosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros,
   * para todas las cuentas.
   */    
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

  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en retiros y depósitos,
   * para todas las cuentas.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */   
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
  
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosYRetirosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros
   * y depósitos, para todas las cuentas.
   */   
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

  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en depósitos,
   * para una cuenta en específico.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */       
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
  
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosCuentaUnica
   * y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en depósitos,
   * para una cuenta en específico.
   */   
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
  
  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en retiros,
   * para una cuenta en específico.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */    
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
  
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesRetirosCuentaUnica
   * y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros,
   * para una cuenta en específico.
   */   
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
  
  /**
   * <p> Método que consulta las ganancias totales relacionadas al cobro de comisiones en retiros y depósitos,
   * para una cuenta en específico.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */    
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
  
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosYRetirosCuentaUnica
   * y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros y depósitos,
   * para una cuenta en específico.
   */     
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
  
  /**
   * <p> Método encargado de consultar el estatus de una cuenta específica.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**
   * <p> Método que recorre el resultado del método ConsultaEstatusCuenta y lo retorna.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene el estatus de una cuenta (activa o inactiva). 
   */
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

  /**
   * <p> Método que consulta las cuentas creadas y la información de los dueños de estas.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: número de cuenta, nombre
   * y apellido1 del dueño.
   */    
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
  
  /**
   * Método que consulta los datos específicos de todas las cuentas, además de la información de los dueños.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: número de cuenta, estatus,
   * saldo, identificación del dueño, nombre, apellido1 y apellido2.
   */
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
  /**
   * <p> Método que consulta todos los datos de una cuenta, desde la base de datos.
   * @return ResultSet: El resultado de la consulta a la base de datos. Contiene: numero de cuenta, fecha
   * de creación, saldo, pin y estatus de todas las cuentas.
   */
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
  /**
   * Método que consulta las cuentas de la base de datos, crea los objetos de tipo cuenta y las agrega
   * al ArrayList cuentas.
   */  
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
        Cuenta nuevaCuenta;
        nuevaCuenta = logicacreacional.SimpleCuentaFactory.crearCuenta();
        nuevaCuenta.setNumeroCuenta(pNumeroCuenta);
        nuevaCuenta.setFecha(pFechaCreacion);
        nuevaCuenta.setSaldo(pSaldo);
        nuevaCuenta.setPin(pPin);
        nuevaCuenta.setEstatus(pEstatus);
        ControladorCuenta.cuentas.add(nuevaCuenta);
      }
    }
    catch(Exception error){
      error.printStackTrace();
      System.out.println("Error al recorrer la consulta de cuentas o al instanciar cuenta en el dao de cuentas");
    }
  }
}

