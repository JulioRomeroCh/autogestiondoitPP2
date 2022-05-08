package logicadenegocios;

import java.util.Date;
import logicadeaccesoadatos.*;
import webservice.TipoCambio;

public class Operacion {

  
 
  private Date fechaOperacion;
  private TipoOperacion tipo;
  private boolean cargoComision;
  private double monto;
  private double montoComision;
  
  public Operacion(TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){
   
    this.fechaOperacion = new Date();
    this.tipo = pTipo;
    this.cargoComision = pCargoComision;
    this.monto = pMonto;
    this.montoComision = pMontoComision;      
  }
  
   public Operacion(Date pFecha, TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){
   
    this.fechaOperacion = pFecha;
    this.tipo = pTipo;
    this.cargoComision = pCargoComision;
    this.monto = pMonto;
    this.montoComision = pMontoComision;      
  }
  
  public String toString(){
   
    String mensaje = "";

    mensaje = "Fecha operación: " + this.getFechaOperacion() + "\n";
    mensaje+= "Tipo: " + this.getTipo() + "\n";
    mensaje+= "Cargo comisión: " + this.cargoComision + "\n";
    mensaje+= "Monto operación: " + this.getMonto() + "\n";
    mensaje+= "Monto comisión: " + this.getMontoComision() + "\n"+"\n"+"\n";
    
    return mensaje; 
      
  }    
  
  public String dolarizar(TipoCambio pCompra){
   
    String mensaje = "";

    mensaje = "Fecha operación: " + this.getFechaOperacion() + "\n";
    mensaje+= "Tipo: " + this.getTipo() + "\n";
    mensaje+= "Cargo comisión: " + this.cargoComision + "\n";
    mensaje+= "Monto operación: " + (this.getMonto() / pCompra.consultarCompraDolar()) + "\n";
    mensaje+= "Monto comisión: " + (this.getMontoComision() / pCompra.consultarCompraDolar()) + "\n"+"\n"+"\n";
    
    return mensaje; 
      
  }
  
  
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

   
    public void setFechaOperacion(Date pFechaOperacion) {
        this.fechaOperacion = pFechaOperacion;
    }

   
    public TipoOperacion getTipo() {
        return tipo;
    }

  
    public void setTipo(TipoOperacion pTipo) {
        this.tipo = pTipo;
    }


    public double getMonto() {
        return monto;
    }

 
    public void setMonto(double pMonto) {
        this.monto = pMonto;
    }

  
    public double getMontoComision() {
        return montoComision;
    }

 
    public void setMontoComision(double pMontoComision) {
        this.montoComision = pMontoComision;
    }
    
    
}
