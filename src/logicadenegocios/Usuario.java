package logicadenegocios;

import java.util.Date;

public class Usuario extends Persona {
    
    
  public Usuario(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    
  }
  
 
  
  public String toString(){
   String mensaje = "";
    
   mensaje= super.toString() + "\n";
    
   return mensaje;   
  }
    
}
