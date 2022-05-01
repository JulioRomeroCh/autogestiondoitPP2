
package logicadeconexionexterna;

//Imports fundamentales
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import logicadenegocios.Cliente;


public class CorreoElectronico {

  //Credenciales del correo
  private static final String USUARIO = "doitgroupjjk@gmail.com";
  private static final String CONTRASENA = "grupodoit123";
  
  //Cambiar el tipo de dato por Persona
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
    mensaje.setSubject("Inactivación de cuenta");
    
    //Escribe el cuerpo del correo
    
    mensaje.setText("El motivo de inactivación de su cuenta se debe a: " + pMotivo);
    
    //Servidor de envío del correo
    Transport mensajero = sesion.getTransport("smtp");
    mensajero.connect(USUARIO, CONTRASENA);
    mensajero.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
    mensajero.close();
    System.out.println("Correo enviado exitosamente");
  }
  
}
