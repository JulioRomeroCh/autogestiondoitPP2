package logicadenegocios;

import java.util.Date;

public class Operacion {
 
  private Date fechaOperacion;
  private TipoOperacion tipo;
  private boolean cargoComision;
  private int monto;
  private double montoComision;
  
  public Operacion(TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){
   
    this.fechaOperacion = new Date();
    this.tipo = pTipo;
    this.cargoComision = pCargoComision;
    this.monto = pMonto;
    this.montoComision = pMontoComision;
      
  }
  
  public String toString(){
   
    String mensaje = "";

    mensaje = "Fecha operación: " + this.fechaOperacion + "\n";
    mensaje+= "Tipo: " + this.tipo + "\n";
    mensaje+= "Cargo comisión: " + this.cargoComision + "\n";
    mensaje+= "Monto operación: " + this.monto + "\n";
    mensaje+= "Monto comisión: " + this.montoComision + "\n";
    
    return mensaje; 
      
  }    
    
    
}
