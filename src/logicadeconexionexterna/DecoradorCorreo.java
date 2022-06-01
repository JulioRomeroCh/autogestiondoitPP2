/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeconexionexterna;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 *
 * @author Jose Blanco
 */
public abstract class DecoradorCorreo implements ICorreoElectronico{
    
  protected ICorreoElectronico correoElectronico;
  
  public DecoradorCorreo(ICorreoElectronico pCorreoElectronico){
    correoElectronico = pCorreoElectronico;
  }
 
  @Override
  public abstract void generarCorreoElectronico(String pCorreo, String pMotivo) throws AddressException, MessagingException;
    
}
