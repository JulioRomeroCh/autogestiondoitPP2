package logicadeconexionexterna;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;



public class CorreoIngles extends DecoradorCorreo{
    
  public CorreoIngles (ICorreoElectronico pCorreoElectronico){
   super(pCorreoElectronico);
  }
  
  @Override
  public void generarCorreoElectronico(String pCorreo, String pMotivo) throws AddressException, MessagingException{
   
    String nuevoMotivo = crearTextoIngles(pMotivo);
    super.correoElectronico.generarCorreoElectronico(pCorreo, nuevoMotivo);
  }
  
  private String crearTextoIngles(String pMotivo){
    
    try{  
    String textoFinal = pMotivo + " " + "\n" + 
            "The cause of the inactivation of your account is due to: ";
    
    String motivoIngles = Traductor.traducirEspanolIngles(pMotivo);
    
    textoFinal += motivoIngles;
    return textoFinal;
            
  }
    catch (Exception error){
        System.out.println("Error al traducir texto en correoIngles");
        return "";
    }
  }
  
  

  
  
  
}
