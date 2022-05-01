package logicadenegocios;

import java.util.Date;


public class Cliente extends Persona{
   
    
  private String codigo;
  private static int cantidadClientes = 0;
  public String numeroTelefonico;
  public String correoElectronico;
  
  public Cliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    this.cantidadClientes += 1;
    this.codigo = "CIF_" + cantidadClientes;        
    this.numeroTelefonico = pNumeroTelefonico;
    this.correoElectronico = pCorreoElectronico;
  }
  
  public String toString(){
  
    String mensaje = "";
    
    mensaje = "Código: " + this.codigo + "\n";
    mensaje+= "Número telefónico: " + this.numeroTelefonico + "\n";
    mensaje+= "Correo electrónico: " + this.correoElectronico + "\n";
    mensaje+= super.toString() + "\n";
    
    return mensaje;
  }    
    
  public String getCorreoElectronico(){
    return this.correoElectronico;
  }
}
