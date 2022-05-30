
package logicadeconexionexterna;

//Imports fundamentales
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CorreoElectronico implements ICorreoElectronico{

  //Credenciales del correo
  private static final String USUARIO = "doitgroupjjk@gmail.com";
  private static final String CONTRASENA = "grupodoit123";
  
  //Cambiar el tipo de dato por Persona
  /**
   * <p> Método encargado de enviar el correo electrónico al destinatario indicado.
   * @param pCorreo: String que representa la dirección de correo electrónico de destino.
   * @param pMotivo: String que representa el contenido del correo electrónico.
   * @throws AddressException: Excepción lanzada en caso de que la dirección del destinatario sea incorrecta.
   * @throws MessagingException: Excepción lanzada en caso de error en el momento de enviar un correo electrónico.
   */
  public static void generarCorreoElectronico(String pCorreo, String pMotivo) throws AddressException, MessagingException {
      
   //Se establecen las propiedades del correo
   
   Properties propiedades = new Properties();
   
   propiedades.put("mail.smtp.host", "smtp.gmail.com");
   propiedades.setProperty("mail.smtp.port", "587");
   propiedades.setProperty("mail.smtp.auth", "true");
   propiedades.setProperty("mail.smtp.starttls.enable", "true");
   
   //Autenticar usuario y contraseña
   
   propiedades.setProperty("mail.smtp.user", USUARIO);
   propiedades.setProperty("mail.smtp.clave", CONTRASENA);
   
   //Generar sesión y vínculo con Email
   
   Session sesion = Session.getDefaultInstance(propiedades);
   
   //Redacción del mensaje
    MimeMessage mensaje = new MimeMessage(sesion);
      
      //Envía el mensaje
    mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(pCorreo));
    
    //Escribe el encabezado
    mensaje.setSubject("Inactivación de cuenta / Account blocked");
    
    //Escribe el cuerpo del correo
    
   ICorreoElectronico correo = new CorreoElectronico();
   correo = new CorreoIngles(correo);
    mensaje.setText("El motivo de inactivación de su cuenta se debe a: " + pMotivo + "\n" + 
            "The cause of the inactivation of your account is due to: " + correo.crearTextoCorreo(pMotivo));
    
    //Servidor de envío del correo
    Transport mensajero = sesion.getTransport("smtp");
    mensajero.connect(USUARIO, CONTRASENA);
    mensajero.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
    mensajero.close();
    System.out.println("Correo enviado exitosamente");
  }
  
  @Override
  public String crearTextoCorreo(String pMotivo){
    return pMotivo;
  }
  
  
}
