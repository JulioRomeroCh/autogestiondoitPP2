package webservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TipoCambio {

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

  public double consultarCompraDolar(){
    this.indicador = 317;
    double valor = Double.parseDouble(obtenerValores());
    return valor;
  }
  
  public double consultarVentaDolar(){
    this.indicador = 318;   
    double valor = Double.parseDouble(obtenerValores());
    return valor;
  }
  
  private String obtenerValores(){
    try{
      establecerDireccionWeb(); 
      
      String data = Solicitud.obtenerHTML(enlace);
      ConversorXML xml = new ConversorXML(data);

      return xml.obtenerValor(VALOR_ETIQUETA);
    } 
    catch (Exception error) {
      return "No se pudo obtener los datos";
    }
  }
  
  private void establecerDireccionWeb(){
    String parametros = "Indicador=" + indicador + "&FechaInicio=" + fechaInicio + "&FechaFinal=" + fechaFinal + "&Nombre=" + NOMBRE + "&SubNiveles=" + SUBNIVELES + "&CorreoElectronico=KevRjs172@gmail.com&Token=VR50MO22J4";
    this.enlace = HOST + "?" + parametros;
  }
  
}
