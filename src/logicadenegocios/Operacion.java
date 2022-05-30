package logicadenegocios;

import java.util.ArrayList;
import java.util.Date;
import webservice.ITipoCambio;

public class Operacion {

  private Date fechaOperacion;
  private TipoOperacion tipo;
  private boolean cargoComision;
  private double monto;
  private double montoComision;
  
 
  
  /**
   * <p> Constructor de la clase Operación.
   * @param pTipo: Atributo de tipo TipoOperacion.
   * @param pCargoComision: Atributo de tipo boolean.
   * @param pMonto: Atributo de tipo int.
   * @param pMontoComision: : Atributo de tipo double.
   */
  
  public Operacion(){
     this.fechaOperacion = new Date();

  }
  
  public Operacion(TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){
    this.fechaOperacion = new Date();
    this.tipo = pTipo;
    this.cargoComision = pCargoComision;
    this.monto = pMonto;
    this.montoComision = pMontoComision;    

  }
  
  /**
   * <p> Constructor sobrecargado de la clase Operación.
   * @param pFecha: Atributo de tipo Date.
   * @param pTipo: Atributo de tipo TipoOperacion.
   * @param pCargoComision: Atributo de tipo boolean.
   * @param pMonto: Atributo de tipo int.
   * @param pMontoComision: Atributo de tipo double.
   */
  public Operacion(Date pFecha, TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){ 
    this.fechaOperacion = pFecha;
    this.tipo = pTipo;
    this.cargoComision = pCargoComision;
    this.monto = pMonto;
    this.montoComision = pMontoComision;     

  }
  
  /**
   * <p> Método que retorna la información asociada a la operación.
   * @return String que contiene: fecha de la operación, tipo, cargo de comisión, monto de operación
   * y monto cobrado por concepto de comisión.
   */  
  public String toString(){
   
    String mensaje = "";

    mensaje = "Fecha operación: " + this.getFechaOperacion() + "\n";
    mensaje+= "Tipo: " + this.getTipo() + "\n";
    mensaje+= "Cargo comisión: " + this.cargoComision + "\n";
    mensaje+= "Monto operación: " + this.getMonto() + "\n";
    mensaje+= "Monto comisión: " + this.getMontoComision() + "\n"+"\n"+"\n";
    
    return mensaje; 
      
  }    
  
  /**
   * <p> Método que retorna la información asociada a la operación, con los montos representados en dólares.
   * @param pCompra: Objeto de tipo ITipoCambio, se usa para obtener los indicadores económicos.
   * @return String que contiene: fecha de la operación, tipo, cargo de comisión, monto de operación (en dólares)
   * y monto cobrado por concepto de comisión (en dólares).
   */    
  public String dolarizar(ITipoCambio pCompra){  
    String mensaje = "";
    mensaje = "Fecha operación: " + this.getFechaOperacion() + "\n";
    mensaje+= "Tipo: " + this.getTipo() + "\n";
    mensaje+= "Cargo comisión: " + this.cargoComision + "\n";
    mensaje+= "Monto operación: " + (this.getMonto() / pCompra.consultarCompraDolar()) + "\n";
    mensaje+= "Monto comisión: " + (this.getMontoComision() / pCompra.consultarCompraDolar()) + "\n"+"\n";   
    return mensaje;      
  }
  
  /**
   * Método accesor del atributo fechaOperacion.
   * @return Date que indica la fecha de la operación.
   */  
  public Date getFechaOperacion() {
    return fechaOperacion;
  }

  /**
   * Método accesor del atributo fechaOperacion.
   * @param pFechaOperacion que indica la fecha de la operación.
   */     
  public void setFechaOperacion(Date pFechaOperacion) {
    this.fechaOperacion = pFechaOperacion;
  }

  /**
   * Método accesor del atributo tipo.
   * @return TipoOperacion que indica el tipo de la operación.
   */    
  public TipoOperacion getTipo() {
    return tipo;
  }

  /**
   * Método accesor del atributo tipo.
   * @param pTipo que indica el tipo de la operación.
   */   
  public void setTipo(TipoOperacion pTipo) {
    this.tipo = pTipo;
  }

  /**
   * Método accesor del atributo monto.
   * @return double que indica el monto de la operación.
   */    
  public double getMonto() {
    return monto;
  }

   /**
   * Método accesor del atributo monto.
   * @param pMonto: double que indica el monto de la operación.
   */  
  public void setMonto(double pMonto) {
    this.monto = pMonto;
  }

  /**
   * Método accesor del atributo montoComision.
   * @return double que indica el monto cobrado por concepto de comisión de la operación.
   */    
  public double getMontoComision() {
    return montoComision;
  }

   /**
   * Método accesor del atributo montoComision.
   * @param pMontoComision: double que indica el monto cobrado por concepto de comisión de la operación.
   */ 
  public void setMontoComision(double pMontoComision) {
    this.montoComision = pMontoComision;
  }
  
  public void setCargoComision(boolean pCargo){
    this.cargoComision = pCargo;
  }

}
