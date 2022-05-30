package logicadeaccesoadatos;


import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;
import logicadeaccesoadatos.CuentaDao;
import logicadeintegracion.*;
import logicadenegocios.Persona;

public class PersonaDao {
    
  /**
  * <p> Método que registra una persona en la base de datos.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento: atributo de tipo Date.
   * @param pMontoComision: atributo de tipo double.
   * @return booleano que representa el ézito o fracaso del registro.
   */     
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
  
  /**
   * Método encargado de asociar un cliente y una persona, mediante la tabla intermedia.
   * @param pIdentificacion: String que representa la identificación de la persona.
   * @param pCodigoCliente: String que representa el código del cliente.
   * @return booleano que representa el ézito o fracaso del registro.
   */
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
  
  /**
   * Método encargado de asociar una persona y una cuenta, mediante la tabla intermedia.
   * @param pIdentificacion: String que representa la identificación de la persona.
   * @param pNumeroCuenta: String que representa la cuenta a asociar.
   * @return booleano que representa el ézito o fracaso del registro.
   */
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
  /**
   * Método que consulta la información de las personas registradas en la base de datos.
   * @return ResultSet: el resultado de la consulta. Contiene: identificación, nombre, apellido1,
   * apellido2 y fecha de nacimiento.
   */ 
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
  /**
   * Método que consulta las personas registradas en la base de datos, crea los objetos de tipo Persona
   * y las añade al ArrayList "personas".
   */  
  public static void recorrerCargarPersona(){
    try{
      ResultSet resultado = cargarPersona();
      while (resultado.next()){
        String pIdentificacion = String.valueOf(resultado.getObject(1));
        String pNombre = String.valueOf(resultado.getObject(2));
        String pPrimerApellido = String.valueOf(resultado.getObject(3));   
        String pSegundoApellido = String.valueOf(resultado.getObject(4));
        String pFechaNacimiento =  String.valueOf(resultado.getObject(5));
        Date fechaConvertida = new SimpleDateFormat("yyyy-MM-dd").parse(pFechaNacimiento);
      
        Persona nuevaPersona;
        nuevaPersona = logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Persona");
        nuevaPersona.setIdentificador(pIdentificacion);
        nuevaPersona.setNombre(pNombre);
        nuevaPersona.setPrimerApellido(pPrimerApellido);
        nuevaPersona.setSegundoApellido(pSegundoApellido);
        nuevaPersona.setFechaNacimiento(fechaConvertida);
        
        ControladorPersona.personas.add(nuevaPersona);
      }
    }
    catch(Exception error){
      System.out.println("Error al recorrer la consulta de personas o al instanciar una persona en daoPersona");
    }
  }
  /**
   * <p> Método que consulta las cuentas registradas y las personas dueñas.
   * @return ResultSet: el resultado de la consulta.
   */     
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
  
  /**
   * Método que asocia las cuentas con las personas registradas en la base de datos.
   */  
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
