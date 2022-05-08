package logicadeconexionexterna;

//imports fundamentales
import java.net.*;
import java.util.Base64;
import java.io.*;
import java.util.UUID;

/**
 * Clase MensajeTexto: Clase encargada de enviar un mensaje con la palabra secreta.
 * 
 * @author Jose Ignacio Blanco Chaves
 * @author Kevin Rojas Salazar
 * @author Julio Romero Chacón
 * 
 */
public class MensajeTexto {
  
  private String palabraSecreta = "";
  
  public String getPalabraSecreta() {
    return palabraSecreta;
  }
  
    
  public String generarPalabraSecreta(){
    int tamano = (int)(Math.random()*4+2);
    String palabra = UUID.randomUUID().toString().toUpperCase().substring(0, tamano);
    palabraSecreta = palabra;
    return palabra;
  }
  
  public boolean corroborarPalabraSecreta(String pPalabra, String pNumeroReceptor) throws Exception{
    if (pPalabra.equals(palabraSecreta)){
      return true;
    }
    else{
        enviarPalabraSecreta(pNumeroReceptor);
      return false;
    }
  }
  
  public void enviarPalabraSecreta(String pNumeroReceptor) throws Exception{
      generarMensaje(pNumeroReceptor, generarPalabraSecreta());
  }
     
  /**
   * Método generarMensaje: Método que envía un mensaje de texto al cliente y/o usuario con la palabra secreta necesaria para
   *     llevar a cabo operaciones que impliquen el retiro de dinero de una cuenta. Para ello, se realiza la autenticación por
   *     medio de credenciales y se adjunta un mensaje con la información respectiva.
   * 
   * @param pNumeroReceptor: dato de tipo String que corresponde al número de teléfono al cual será enviada la información.
   * @param pMensaje: dato de tipo String que corresponde a la palabra secreta.
   * @throws Exception: exepción que se lanza cuando hay un problema de autenticación. 
   */
  public void generarMensaje(String pNumeroReceptor, String pMensaje) throws Exception{ //Inicio del método generarMensaje 
    
    String host = "https://api.bulksms.com/v1/messages";
    String usuario = ""+"grupodoit"+"";
    String contrasena = ""+"DoIT1234"+"";
    
    //Contenido del mensaje
    String datos = "{to: \""+("+506"+pNumeroReceptor)+"\", encoding: \"UNICODE\", body: \""+pMensaje+"\"}";
    
    URL enlace = new URL(host);
    HttpURLConnection solicitud = (HttpURLConnection) enlace.openConnection();
    solicitud.setDoOutput(true);
    
    String autenticacion = usuario + ":" + contrasena;
    String codificacion = Base64.getEncoder().encodeToString(autenticacion.getBytes());
    
    solicitud.setRequestProperty("Authorization", "Basic " + codificacion);
    solicitud.setRequestMethod("POST");
    solicitud.setRequestProperty( "Content-Type", "application/json");
    
    OutputStreamWriter salida = new OutputStreamWriter(solicitud.getOutputStream());
    salida.write(datos);
    salida.close();
    
    try{
    
      InputStream respuesta = solicitud.getInputStream();
      BufferedReader lector = new BufferedReader(new InputStreamReader(respuesta));  
      lector.close();
      //System.out.println("Mensaje enviado con éxito");
    }      
    
    catch(IOException error){
      System.out.println("Excepción al enviar mensaje: " + error);    
    } 
      
      
  }//Fin del método generarMensaje  
   
}
