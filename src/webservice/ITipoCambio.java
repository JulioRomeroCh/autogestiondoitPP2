package webservice;

import webservice.ConversorXML;


public interface ITipoCambio {
    
  public abstract double consultarCompraDolar();
  public abstract double consultarVentaDolar();
  public abstract String obtenerValores();
  public abstract void establecerDireccionWeb();
  public abstract ConversorXML establecerXml(String pDatos) throws Exception;
    
}