/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package logicadeconexionexterna;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 *
 * @author Jose Blanco
 */
public interface ICorreoElectronico {
    
  public abstract void generarCorreoElectronico(String pCorreo, String pMotivo) throws AddressException, MessagingException;
}
