package logicadeintegracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import logicadenegocios.*;
import logicadevalidacion.*;


public class ControladorPersona {
  
  public static ArrayList<Persona> personas = new ArrayList<Persona>();   
  

  
  public static Persona buscarPersona(String pIdentificador){
    Persona persona = new Persona();
    for(int contador = 0; contador != personas.size(); contador++){
      if(personas.get(contador).getIdentificador().equalsIgnoreCase(pIdentificador)){
        persona = personas.get(contador);
      }  
    }
      return persona;
  }
  


  
  public static String llamarMetodoRegistrarUsuarioCLI(String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido, String pFechaNacimiento) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true){  
      Usuario nuevoUsuario = new Usuario(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, convertirTextoAFecha(pFechaNacimiento));
      ControladorPersona.personas.add(nuevoUsuario);   
      return nuevoUsuario.registrarPersona(); 
  }
    else{
      return "Error en el formato de la fecha";
    }
    }
  

  
    private static Date convertirTextoAFecha(String pFechaEnTexto) throws ParseException{
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      return formatoFecha.parse(pFechaEnTexto);
      
  }
    
  
}
