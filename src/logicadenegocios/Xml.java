package logicadenegocios;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logicadeaccesoadatos.BitacoraDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Xml extends Bitacora{
    
    private String ruta = "D:\\Escritorio\\DOCUMENTOXML.xml";
      
      
    public Xml (Cuenta pCuenta){
     super.cuenta = pCuenta;
     this.cuenta.anadirAccionBitacora(this);
    }
    
    @Override
    public void agregarAccion(String pAccion, String pVista){

      this.fechaConHora = new Date();
      this.accion = pAccion;
      this.vistaAccedida = pVista;
    }
    
    @Override
    public void crearBitacora(String pCondicion){
    
    try{
    DocumentBuilderFactory documentoFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder constructorDocumento = documentoFactory.newDocumentBuilder();
    Document documento = constructorDocumento.newDocument();
    
    //Elemento principal
    Element principal = documento.createElement("Bitácora");
    documento.appendChild(principal);
       
    ArrayList<ArrayList<String>> listaTotal = retornarListaDatos(pCondicion);
    for (int indice = 0; indice!=listaTotal.size(); indice++){

      Element registros = documento.createElement("Registro");
      principal.appendChild(registros);  
        
      Element primerElemento = documento.createElement("accion");
      primerElemento.setTextContent(listaTotal.get(indice).get(0));
      registros.appendChild(primerElemento);
    
      Element segundoElemento = documento.createElement("fechayhora");
      segundoElemento.setTextContent(listaTotal.get(indice).get(1));
      registros.appendChild(segundoElemento);
    
      Element tercerElemento = documento.createElement("vista");
      tercerElemento.setTextContent(listaTotal.get(indice).get(2));
      registros.appendChild(tercerElemento);
      
    }
    
    //Escritura de los elemento en un archivo XML
    TransformerFactory transformadorFactory = TransformerFactory.newInstance();
    Transformer transformador = transformadorFactory.newTransformer();
    DOMSource fuente = new DOMSource(documento);
    StreamResult resultado = new StreamResult(new File(ruta));
    transformador.transform(fuente, resultado);
    }
    
    catch (Exception error){
        System.out.println("Error al crear un archivo XML");
    }

        
  }
  
  private ArrayList<ArrayList<String>> retornarListaDatos(String pCondicion){
      
    ArrayList<ArrayList<String>> listaTotal = new ArrayList<ArrayList<String>>(); 
    
    if(pCondicion.equals("todos")){
      listaTotal = BitacoraDao.recorrerConsultaBitacoraTodaVista();
    }
    else if(pCondicion.equals("hoy")){
      listaTotal = BitacoraDao.recorrerConsultaBitacoraHoy();
    }
    else{
      listaTotal = BitacoraDao.recorrerConsultaBitacoraSegunVista(pCondicion);
    }  
    
    return listaTotal;
      
  }
  
      public void abrirArchivo(){
     try{
       File archivo = new File(ruta);
       
       Desktop escritorio = Desktop.getDesktop();
       if (archivo.exists()){
         escritorio.open(archivo);
           System.out.println("Archivo abierto");
       }
     }
     
     catch (Exception error){
         System.out.println("Error al abrir la bitácora, XML");
     }
    }
    
}
