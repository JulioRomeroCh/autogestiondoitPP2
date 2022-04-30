package logicadenegocios;

import java.util.Date;

public class Usuario extends Persona {
    
    
  public Usuario(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    
  }
  
  public String consultarDatosCliente(Persona pCliente){
    return "";    
  }
  
  public String toString(){
   String mensaje = "";
    
   mensaje= super.toString() + "\n";
    
   return mensaje;   
  }
  
  /*public boolean comparar(Comparable pUsuario){
      
  }*/
    
}
