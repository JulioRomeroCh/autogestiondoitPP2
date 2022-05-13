package webservice;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;

public class ConversorXML {
   
  private String xml;
  private Element elementoBase;
  
  public ConversorXML(String pDatos) throws SAXException, IOException, ParserConfigurationException{
    //Reemplaza caracteres especiales
    pDatos =  remplazarCaracter(pDatos);
    this.xml = pDatos;
    
    //Creador de Builder/Parser XML
    DocumentBuilderFactory constructorDocumento = DocumentBuilderFactory.newInstance();
    DocumentBuilder constructor = constructorDocumento.newDocumentBuilder();
    Document documento = constructor.parse(new InputSource(new StringReader(this.xml)));
    this.elementoBase = documento.getDocumentElement();
  }
  
  /**
   * Obtiene el valor de una etiqueta en un documento XML
   * @param tag La etiqueta del cual extraer el valor
   * @return <code>String</code> con el valor de la etiqueta enviada por parámetro
   */
  
  public String obtenerValor(String pEtiqueta){
    try {
      NodeList lista = this.elementoBase.getElementsByTagName(pEtiqueta);
      NodeList subLista = lista.item(0).getChildNodes();
      return subLista.item(0).getNodeValue();     
    } 
    catch (Exception error) {
      return "0";
    }
  }

  private String remplazarCaracter(String pMensaje){
    pMensaje = pMensaje.replace("&lt;", "<");
    pMensaje = pMensaje.replace("&gt;", ">");
    return pMensaje;
  }   
}