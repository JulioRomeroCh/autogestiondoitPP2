package logicadeintegracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import logicadenegocios.*;
import logicadevalidacion.*;

public class ControladorPersona {
  
  public static ArrayList<Persona> personas = new ArrayList<Persona>();   
  

  /**
   * <p> Método encargado de buscar una persona en el ArrayList "personas" y lo retorna.
   * @param pIdentificador: String que corresponde a la persona que se desea buscar.
   * @return Objeto de tipo Persona.
   */
  public static Persona buscarPersona(String pIdentificador){
    try{
    Persona persona;
    persona = logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Persona");
    for(int contador = 0; contador != personas.size(); contador++){
      if(personas.get(contador).getIdentificador().equalsIgnoreCase(pIdentificador)){
        persona = personas.get(contador);
      }  
    }
      return persona;
  }
    catch (Exception error){
        System.out.println("Error al instanciar una persona en controladorPersona, buscarPersona");  
        return null;
    }
  }
  


  /**
   * <p> Método que registra un usuario en el sistema.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento: atributo de tipo String.
   * @return mensaje de confirmación de la inserción.
   * @throws ParseException: Excepción en caso de que la fecha enviada no pueda ser convertida a un
   * tipo Date.
   */
  public static String llamarMetodoRegistrarUsuarioCLI(String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido, String pFechaNacimiento) throws ParseException{
      try{
      if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true){  
      Usuario nuevoUsuario;
      nuevoUsuario = (Usuario) logicacreacional.SimplePersonaFactory.crearPersona("logicadenegocios.Usuario");
      nuevoUsuario.setIdentificador(pIdentificacion);
      nuevoUsuario.setNombre(pNombre);
      nuevoUsuario.setPrimerApellido(pPrimerApellido);
      nuevoUsuario.setSegundoApellido(pSegundoApellido);
      nuevoUsuario.setFechaNacimiento(convertirTextoAFecha(pFechaNacimiento));
      ControladorPersona.personas.add(nuevoUsuario);   
      return nuevoUsuario.registrarPersona(); 
  }
    else{
      return "Error en el formato de la fecha";
    }
    }
    catch (Exception error){
        System.out.println("Error al instanciar un usuario en controladorPersona, método registrarUsuarioCLI");  
        error.printStackTrace();
        return null;
    }
  }
  

  /**
   * Método que convierte una cadena de texto en un tipó Date.
   * @param pFechaEnTexto: La cadena que se desea convertir.
   * @return Date.
   * @throws ParseException: Excepción en caso de que la fecha enviada no pueda ser convertida a un
   * tipo Date.
   */  
    private static Date convertirTextoAFecha(String pFechaEnTexto) throws ParseException{
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
      return formatoFecha.parse(pFechaEnTexto);
      
  }
    
  
}
