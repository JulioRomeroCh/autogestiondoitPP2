package logicadenegocios;

import java.util.Date;


public class Cliente  extends Persona{
   
    
  private String codigo;
  private int cantidadCliente = 0;
  private String numeroTelefonico;
  private String correoElectronico;
  
  public Cliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
            
    this.numeroTelefonico = pNumeroTelefonico;
    this.correoElectronico = pCorreoElectronico;
  }
  
  /*public boolean comparar(Comparable pPersonas){
      
  }*/
  
  public String toString(){
  
    String mensaje = "";
    
    mensaje = "Código: " + this.codigo + "\n";
    mensaje+= "Número telefónico: " + this.numeroTelefonico + "\n";
    mensaje+= "Correo electrónico: " + this.correoElectronico + "\n";
    mensaje+= super.toString() + "\n";
    
    return mensaje;
  }    
    
}
