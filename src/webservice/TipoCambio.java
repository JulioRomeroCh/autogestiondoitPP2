package webservice;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TipoCambio implements ITipoCambio{

  private int indicador = 0;
  private String fechaInicio;
  private String fechaFinal;
  private final String NOMBRE = "KRS";
  private final String SUBNIVELES = "N";
  private final String HOST = "https://gee.bccr.fi.cr/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML";
  private String enlace;
  private final String VALOR_ETIQUETA = "NUM_VALOR";
  
  public TipoCambio(){
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    this.fechaInicio = sdf.format(date);
    this.fechaFinal = fechaInicio;
  }

  
  @Override
  public double consultarCompraDolar(){
    this.indicador = 317;
    double valor = Double.parseDouble(obtenerValores());
    return valor;
  }
  
  @Override
  public double consultarVentaDolar(){
    this.indicador = 318;   
    double valor = Double.parseDouble(obtenerValores());
    return valor;
  }
  
  @Override
  public String obtenerValores(){
    try{
      establecerDireccionWeb();    
      String data = Solicitud.obtenerHTML(enlace);
      ConversorXML xml = establecerXml(data);

      return xml.obtenerValor(VALOR_ETIQUETA);
    } 
    catch (Exception error) {
      return "No se pudo obtener los datos";
    }
  }
  
  @Override
  public void establecerDireccionWeb(){
    String parametros = "Indicador=" + indicador + "&FechaInicio=" + fechaInicio + "&FechaFinal=" + fechaFinal + "&Nombre=" + NOMBRE + "&SubNiveles=" + SUBNIVELES + "&CorreoElectronico=KevRjs172@gmail.com&Token=VR50MO22J4";
    this.enlace = HOST + "?" + parametros;
  }
  
  @Override
  public ConversorXML establecerXml(String pDatos) throws Exception{
      establecerDireccionWeb();     
      pDatos = Solicitud.obtenerHTML(enlace);
      ConversorXML xml = new ConversorXML(pDatos);   
      return xml;
  }
  
}