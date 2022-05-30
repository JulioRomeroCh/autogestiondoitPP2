package logicadeintegracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import logicadenegocios.*;
import logicadevalidacion.*;
import logicadeaccesoadatos.ClienteDao;


public class ControladorCliente {
  
  public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
  
  /**
   * <p> Método que busca un cliente en el arreglo "clientes" y lo retorna.
   * @param pIdentificacion: Identificación del cliente que se desea buscar.
   * @return Objeto de tipo Cliente.
   */
  public static Cliente buscarCliente(String pIdentificacion){
    Cliente cliente;
    try{
      cliente = (Cliente) logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Cliente");
    for(int contador = 0; contador != clientes.size(); contador++){
      if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         cliente = clientes.get(contador);
      }  
    }
      return cliente;
  }
  catch (Exception error){
    System.out.println("Error al instanciar un cliente en Controlador Cliente");
    return null;
  }
}
  
  

  
  /**
   * <p> Método que registra una cuenta y la asocia a una persona.
   * @param pMonto: String que representa el monto inicial de depósito.
   * @param pPin: String que representa el pin de la nueva cuenta.
   * @param pIdentificacion: Identificación del cliente dueño de la cuenta.
   * @return String que indica los datos del nuevo registro.
   */
  public static String llamarMetodoRegistrarCuentaPersonaCLI(String pMonto, String pPin, String pIdentificacion){
   if(ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true){
     Cliente nuevoCliente = buscarCliente(pIdentificacion);
     String mensaje = nuevoCliente.registrarCuenta(Integer.parseInt(pMonto), pPin, pIdentificacion);
     ControladorCuenta.cuentas.add(nuevoCliente.cuentas.get(nuevoCliente.cuentas.size()-1));       
     return mensaje;         
   }
   else{
     return "El monto no es un número entero";    
   }
 }
  
  /**
   * Método que registra un cliente
   * @param pIdentificacion: Atributo de tipo String.
   * @param pNombre: Atributo de tipo String.
   * @param pPrimerApellido: Atributo de tipo String.
   * @param pSegundoApellido: Atributo de tipo String.
   * @param pFechaNacimiento: Atributo de tipo String.
   * @param pNumeroTelefonico: Atributo de tipo String.
   * @param pCorreoElectronico: Atributo de tipo String.
   * @return String que indica los datos del nuevo registro.
   * @throws ParseException: Excepción en caso de que los datos enviados no puedan ser convertidos a un
   * tipo de dato en específico.
   */
  public static String llamarMetodoRegistrarClienteCLI(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      String pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico) throws ParseException{
    try{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true){
       
       Cliente nuevoCliente;
      nuevoCliente = (Cliente) logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Cliente");
      nuevoCliente.setIdentificador(pIdentificacion);
      nuevoCliente.setNombre(pNombre);
      nuevoCliente.setPrimerApellido(pPrimerApellido);
      nuevoCliente.setSegundoApellido(pSegundoApellido);
      nuevoCliente.setFechaNacimiento(convertirTextoAFecha(pFechaNacimiento));
      nuevoCliente.correoElectronico = pCorreoElectronico;
      nuevoCliente.numeroTelefonico = pNumeroTelefonico;
      
      clientes.add(nuevoCliente);
      return nuevoCliente.registrarPersona();   
    }
    else{
      return "Error en el formato de la fecha";
    }
   }
    
    catch (Exception error){
        System.out.println("Error al instanciar cliente en el Controlador Cliente, método registrarClienteCLI");
        return null;
    }
  
  }
  /**
   * Método que convierte una cadena de texto en un tipó Date.
   * @param pFechaEnTexto: La cadena que se desea convertir.
   * @return Date.
   * @throws ParseException: Excepción en caso de que los datos enviados no puedan ser convertidos a un
   * tipo de dato en específico.
   */
  private static Date convertirTextoAFecha(String pFechaEnTexto) throws ParseException{
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      return formatoFecha.parse(pFechaEnTexto);
      
  }
  /**
   * <p> Método encargado de listar la información de todos los clientes de forma descendente.
   * @return mensaje que contiene: primer apellido, segundo apellido, nombre e identificador
   * de todos los clientes.
   */
  public static String listarClientes(){
   String mensaje = "";
   Ordenacion ordenClientes = new Ordenacion();
   Cliente[] arregloClientes = convertirListaClientesEnArreglo();
   ordenClientes.ordenamientoInsercion(arregloClientes);
   for (int contador = 0; contador != arregloClientes.length; contador++){
     mensaje += "\n" + arregloClientes[contador].getPrimerApellido() + " ";
     mensaje += arregloClientes[contador].getSegundoApellido() + " ";
     mensaje += arregloClientes[contador].getNombre() + "\n";
     mensaje += arregloClientes[contador].getIdentificador() + "\n";
   }
   return mensaje;
  }
  /**
   * <p> Método encargado de convertir un ArrayList en Arreglo. 
   * @return Arreglo de tipo Cliente.
   */
  public static Cliente[] convertirListaClientesEnArreglo(){
    Cliente [] arregloClientes = new Cliente[clientes.size()];
    for(int contador = 0; contador != clientes.size(); contador++){
      arregloClientes[contador] = clientes.get(contador);
    }
    return arregloClientes;
  }
  
  /**
   * <p> Método que consulta la información de un cliente.
   * @param pIdentificacion: String que representa el cliente que se desea consultar.
   * @return String que contiene la información del cliente deseado.
   */
  public static String consultarDatosCliente(String pIdentificacion){
      
    for(int contador = 0; contador != clientes.size(); contador++){
       if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         return clientes.get(contador).toString();
       }
    }
    return "La identificación ingresada no coincide con ninguno de los clientes registrados en el sistema.";    
  }
  
  /**
   * 
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que contiene el número del cliente asociado a la cuenta.
   */
  public static String consultarNumeroClientePorCuenta(String pNumeroCuenta){
    return ClienteDao.recorrerConsultarNumeroClientePorCuenta(pNumeroCuenta);
  }
  
 
  
 
}

