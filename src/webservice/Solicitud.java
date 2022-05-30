package webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Solicitud {

  /**
   * Devuelve un documento HTML a partir de una solicitud <code>HTTP GET</code>
   * @param pEnlace: con el URL a donde enviar la solicitud.
   * @return String
   * @throws Exception: Excepción lanzada de forma genérica al consultar el Web Service.
   */    
  protected static String obtenerHTML(String pEnlace) throws Exception {
    StringBuilder resultado = new StringBuilder();
    URL direccionWeb = new URL(pEnlace);
    HttpURLConnection conexion = (HttpURLConnection) direccionWeb.openConnection();
    conexion.setRequestMethod("GET");
    
    BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
    String linea;
    
    while ((linea = lector.readLine()) != null) {
        
      resultado.append(linea);
      
    }
    
    lector.close();
    
    return resultado.toString();
  }        
    
}