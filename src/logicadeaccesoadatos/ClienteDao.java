package logicadeaccesoadatos;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeintegracion.*;
import logicadenegocios.Cliente;
/**
 *
 * @author Jose Blanco
 */
public class ClienteDao {
    
  /**
  * <p> Método que crea un nuevo cliente en la base de datos.
  * @param pCodigoCliente: atributo de tipo String.
  * @param pNumeroTelefonico: atributo de tipo String.
  * @param pCorreo: atributo de tipo String.
  * @return booleano que representa el ézito o fracaso de la inserción.
  */
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
  /**
   * <p> Método encargado de consultar el correo electrónico de un cliente, usando el número de cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer el correo del dueño.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */  
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

  /**
   * <p> Método que retorna el correo del dueño de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer el correo del dueño.
   * @return String que contiene el correo electrónico del dueño de la cuenta.
   */
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
  
  /**
   * Método encargado de consultar el número de un cliente, usando el número de cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer el número de cliente del dueño.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */  
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

  /**
   * Método que retorna el número de cliente del dueño de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer el número de cliente del dueño.
   * @return String que contiene el número del cliente del dueño de la cuenta.
   */
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
  
  /**
   * <p> Método que verifica si una identificación está asociada con un cliente.
   * @param pIdentificacion: String que representa la identificación que se desea consultar.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**
   * <p> Método que retorna el valor de verdad, indicando si la identificación está registrada en el sistema.
   * @param pIdentificacion: String que representa la identificación que se desea consultar.
   * @return String que contiene un 1 en caso de que la identificación esté asociada a un cliente, o 0
   * en caso contrario.
   */   
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
  
  /**
   * <p> Método que retorna un booleano, indicando si la identificación está asociada a una persona en el sistema.
   * @param pIdentificacion: String que representa la identificación que se desea consultar.
   * @return boolean que indica si la identificación está asociada a un cliente.
   */
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
  /**
   * <p> Método que consulta las personas que se encuentran registradas como clientes.
   * @return ResultSet: El resultado de la consulta a la base de datos, contiene la identificación,
   * nombre y apellido1 de los clientes.
   */
  public static ResultSet ConsultaListarClientes(){
  ResultSet resultado = null;
  PreparedStatement consulta;
  Conexion nuevaConexion = new Conexion();
  Connection conectar = nuevaConexion.conectar();     
  try{ 
    consulta = conectar.prepareStatement("SELECT DISTINCT identificacionPersona, nombre, apellido1 FROM Persona WHERE rol = 'Cliente' ORDER BY apellido1 ASC");
    resultado = consulta.executeQuery();    
  }
  catch(Exception error){
    error.printStackTrace();    
  }
  return resultado;
  }  
    
  /**
   * <p> Método que consulta las personas que se encuentran registradas como clientes, para cargarlas a una tabla.
   * @return ResultSet: El resultado de la consulta a la base de datos, contiene el
   * nombre, identificación, apellido1 y apellido2 de los clientes.
   */  
  public static ResultSet ConsultaListarClientesTabla(){
    PreparedStatement consulta;
    ResultSet resultado = null;      
    try{  
      Conexion nuevaConexion = new Conexion();
      Connection conectar = nuevaConexion.conectar();   
      consulta = conectar.prepareStatement("SELECT DISTINCT apellido1, apellido2, nombre, identificacionPersona FROM Persona WHERE rol = 'Cliente' ORDER BY apellido1 ASC");      
      resultado = consulta.executeQuery();           
    }
    catch(Exception error){
      error.printStackTrace();    
    }
    return resultado;
  }
 
  /**
   * <p> Método encargado de consultar la identificación de un cliente, usando el número de cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer la identificación del dueño.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
  private static ResultSet consultarIdentificacionClientePorCuenta (String pNumeroCuenta){ 
    Conexion nuevaConexion = new Conexion(); 
    Connection conectar = nuevaConexion.conectar(); 
    ResultSet resultado = null; 
    PreparedStatement consulta;     
    try{ 
      consulta = conectar.prepareCall("{CALL consultarIdentificacionCliente(?)}"); 
      consulta.setString(1, pNumeroCuenta); 
      resultado = consulta.executeQuery();   
    } 
    catch(Exception error){ 
      System.out.println("Error al consultar la identificacion del cliente por cuenta"); 
    } 
    return resultado; 
  } 
 
  /**
   * <p> Método que retorna la identificación del dueño de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea conocer la identificación del dueño.
   * @return String que contiene la identificación del dueño de la cuenta.
   */
  public static String recorrerConsultarIdentificacionClientePorCuenta (String pNumeroCuenta){     
    try{ 
      String datosCliente = ""; 
      ResultSet resultado = consultarIdentificacionClientePorCuenta(pNumeroCuenta); 
      while (resultado.next()){ 
        datosCliente += resultado.getObject(1).toString(); 
      } 
      return datosCliente; 
    } 
    catch(Exception error){ 
      System.out.println("Error al recorrer resultado de identificación del cliente"); 
      return "0"; 
    } 
  }
  
  //---------------------------------CARGARBASEDATOS----------------
  /**
   * Metódo que consulta los datos de los clientes registrados en la base de datos.
   * @return ResultSet: El resultado de la consulta a la base de datos.
   */
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
  
  /**
   * <p> Método que convierte los clientes registrados en la base de datos en objetos, posteriormente los 
   * agrega a los ArrayList del controlador Cliente y Persona.
   */
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
        Cliente nuevoCliente;
        nuevoCliente = (Cliente) logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Cliente");
        nuevoCliente.setIdentificador(pIdentificacion);
        nuevoCliente.setNombre(pNombre);
        nuevoCliente.setPrimerApellido(pPrimerApellido);
        nuevoCliente.setSegundoApellido(pSegundoApellido);
        nuevoCliente.setFechaNacimiento(pFechaNacimiento);
        nuevoCliente.codigo = pCodigo;
        nuevoCliente.numeroTelefonico = pNumero;
        nuevoCliente.correoElectronico = pCorreo;
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
