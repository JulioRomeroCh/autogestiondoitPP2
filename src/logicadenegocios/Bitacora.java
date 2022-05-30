package logicadenegocios;

import java.util.Date;


public abstract class Bitacora {
    
  protected Date fechaConHora;
  protected String accion;
  protected String vistaAccedida;
  
  protected Cuenta cuenta;
  
  public Bitacora(){
    this.fechaConHora = new Date();
  }
  
  public abstract void agregarAccion(String pAccion, String pVista);
  
  public abstract void crearBitacora(String pCondicion);
  
  public abstract void abrirArchivo();
  
  public void setAccion(String pAccion){
    this.accion = pAccion;
  }
  
  public String getAccion(){
    return this.accion;
  }
  
  public void setVista(String pVista){
    this.vistaAccedida = pVista;
  }
  
  public String getVista(){
    return this.vistaAccedida;
  }
  
  public Date obtenerFechaConHora(){
    return this.fechaConHora;
  }
  
}
