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
  /**
   * <p> Método que retorna la palabra secreta.
   * @return String que representa la palabra secreta del objeto de tipo MensajeTexto. 
   */
  public String getPalabraSecreta() {
    return palabraSecreta;
  }
  /**
   * Método que genera una palabra secreta aleatoria, de máximo cinco caracteres.
   * @return String que representa la palabra secreta generada.
   */  
  public String generarPalabraSecreta(){
    int tamano = (int)(Math.random()*4+2);
    String palabra = UUID.randomUUID().toString().toUpperCase().substring(0, tamano);
    palabraSecreta = palabra;
    return palabra;
  }
  /**
   * <p> Método que verifica que la palabra secreta ingresada por el usuario coincida con el atributo.
   * @param pPalabra: String que representa la palabra secreta ingresada por el usuario.
   * @param pNumeroReceptor: String que representa el número telefónico del cliente.
   * @return boolean que indica si la palabra coincide con la cadena ingresada por el cliente.
   * @throws Exception: Excepción lanzada de forma genérica, en caso de error en el envío del mensaje.
   */
  public boolean corroborarPalabraSecreta(String pPalabra, String pNumeroReceptor) throws Exception{
    if (pPalabra.equals(palabraSecreta)){
      return true;
    }
    else{
        enviarPalabraSecreta(pNumeroReceptor);
      return false;
    }
  }
  
  /**
   * <p> Método que genera una palabra secreta y posteriormente la envía al método generarMensaje.
   * @param pNumeroReceptor: String que representa el número telefónico del cliente.
   * @throws Exception: Excepción lanzada de forma genérica, en caso de error en el envío del mensaje.
   */
  public void enviarPalabraSecreta(String pNumeroReceptor) throws Exception{
      generarMensaje(pNumeroReceptor, generarPalabraSecreta());
  }
     
  /**
   * Método generarMensaje: Método que envía un mensaje de texto al cliente y/o usuario con la palabra secreta necesaria para
   *     llevar a cabo operaciones que impliquen el retiro de dinero de una cuenta. Para ello, se realiza la autenticación por
   *     medio de credenciales y se adjunta un mensaje con la información respectiva.
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
