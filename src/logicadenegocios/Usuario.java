package logicadenegocios;

import java.util.Date;

public class Usuario extends Persona {
    
  /**
   * <p> Constructor de la clase Usuario.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento : atributo de tipo Date
   */  
  public Usuario(String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    
  }
  
  public Usuario(){
  }
  
 
  /**
   * <p> Método que retorna la información asociada al usuario.
   * @return String que contiene: apellidos, nombre e identificación del usuario.
   */  
  public String toString(){
    String mensaje = "";  
    mensaje= super.toString() + "\n";   
    return mensaje;
  }
  
   
}
