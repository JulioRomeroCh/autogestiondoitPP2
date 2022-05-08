package logicadeintegracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static logicadeintegracion.ControladorCuenta.cuentas;
import static logicadeintegracion.ControladorPersona.buscarPersona;
import logicadenegocios.*;
import logicadevalidacion.*;
import logicadeaccesoadatos.ClienteDao;


public class ControladorCliente {
  
  public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
     
  public void iniciar(){
      
  }  
  
  public static Cliente buscarCliente(String pIdentificacion){
    Cliente cliente = new Cliente();
    for(int contador = 0; contador != clientes.size(); contador++){
      if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         cliente = clientes.get(contador);
      }  
    }
      return cliente;
  }
  
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
  
  public static String llamarMetodoRegistrarClienteCLI(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      String pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true){
      Cliente nuevoCliente = new Cliente(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, convertirTextoAFecha(pFechaNacimiento), pNumeroTelefonico, pCorreoElectronico);
      clientes.add(nuevoCliente);
      return nuevoCliente.registrarPersona();   
    }
    else{
      return "Error en el formato de la fecha";
    }
   
  
  }
  
  public static Date convertirTextoAFecha(String pFechaEnTexto) throws ParseException{
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      return formatoFecha.parse(pFechaEnTexto);
      
  }
  
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
  
  public static Cliente[] convertirListaClientesEnArreglo(){
    Cliente [] arregloClientes = new Cliente[clientes.size()];
    for(int contador = 0; contador != clientes.size(); contador++){
      arregloClientes[contador] = clientes.get(contador);
    }
    return arregloClientes;
  }
  
  public static String consultarDatosCliente(String pIdentificacion){
      
    for(int contador = 0; contador != clientes.size(); contador++){
       if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         return clientes.get(contador).toString();
       }
    }
    return "La identificación ingresada no coincide con ninguno de los clientes registrados en el sistema.";    
  }
  
  public static String consultarNumeroClientePorCuenta(String pNumeroCuenta){
    return ClienteDao.recorrerConsultarNumeroClientePorCuenta(pNumeroCuenta);
  }
  
 
  
 
}

