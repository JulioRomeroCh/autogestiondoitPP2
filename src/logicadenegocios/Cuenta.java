package logicadenegocios;

import java.util.ArrayList;
import java.util.Date;

public class Cuenta implements Comision {
 
  private String numeroCuenta;
  private Date fechaCreacion;
  private double saldo;
  private String estatus = "activa";
  private static final String MONEDA = "colones";
  private String pin;
  private final double COMISION = 0.02;
  private int transaccionesRealizadas = 0;
  
  private ArrayList<Operacion> operaciones;
  
  public Cuenta(double pMonto, String pPin){
      
    this.saldo = pMonto;
    this.pin = pPin;
    
    operaciones = new ArrayList<Operacion>();
    
    this.numeroCuenta = "CR"   + generarNumeroCuenta();
    this.fechaCreacion = new Date();
  }  
  
  public void registrarOperacion(TipoOperacion pTipo, boolean pCargoComision, int pMonto,
      double pMontoComision){
    
      Operacion nuevaOperacion = new Operacion(pTipo, pCargoComision, pMonto, pMontoComision);
      operaciones.add(nuevaOperacion);
      
  }
  
  public double getSaldo(){
    return this.saldo;
  }
  
  public void bloquearCuenta(){
    this.estatus = "inactiva";   
  }
  
  public String toString(){
   
    String mensaje = "";

    mensaje = "Número de cuenta: " + this.numeroCuenta + "\n";
    mensaje+= "Fecha de creación: " + this.fechaCreacion + "\n";
    mensaje+= "Saldo: " + this.saldo + "\n";
    mensaje+= "Estatus: " + this.estatus + "\n";
    mensaje+= "Moneda: " + this.MONEDA + "\n";
    mensaje+= "Pin: " + this.pin + "\n";
    mensaje+= "Comisión: " + this.COMISION + "\n";
    mensaje+= "Número de depósitos y/o retiros: " + this.transaccionesRealizadas + "\n"; 
    
    return mensaje;
  }
  
  public String cambiarPin(String pNumeroCuenta, String pPinActual, String pNuevoPin){
   return "";     
  }
  
  public String depositarColones(String pNumeroCuenta, int pMonto){
    return "";    
  }
  
  /*
  public String depositarDolares(TipoCambio pCompra, String pNumeroCuenta, int pMonto){
    return "";    
  }
  
  public String retirarColones(String pNumeroCuenta, String pPin, MensajeTexto pMensaje,
      int pMonto){
    return ""; 
  }
  
  public String retirarDolares(TipoCambio pVenta, String pNumeroCuenta, String pPin,
      MensajeTexto pMensaje, int pMonto){
    return "";    
  }
  
  public String transferirFondos (String pCuentaOrigen, String pPin, MensajeTexto pMensaje,
      int pMonto, String pCuentaDestino){
    return "";    
  }
  */
  @Override
  public double calcularComisionesDepositos(){
    return 0.0;    
  }
  
  @Override
  public double calcularComisionesRetiros(){
    return 0.0;    
  }
    
  @Override
  public double calcularComisionesTotales(){
    return 0.0;    
  }
  
  public void cobrarComision(){
      
  }
  
  public String consultarDatosCuenta(Cuenta pCuenta){
    return "";    
  }
  
  public String consultarSaldoColones(String pNumeroCuenta, String pPin){
    return "";    
  }
  /*
  public String consultarSaldoDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
    return "";    
  }
  */
  public String consultarEstatus(String pNumeroCuenta){
    return "";    
  }
  
  public String generarEstadoCuentaColones(String pNumeroCuenta, String pPin){
    return "";    
  }
  /*
  public String generarEstadoCuentaDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
    return "";    
  }
  */
  private int generarNumeroCuenta(){
  
    int numeroAleatorio;

    numeroAleatorio = (int)(Math.random()*99999+10000);
    
    return numeroAleatorio;
      
  }
  
  public boolean comparar (Comparable pCuenta){
      return this.getSaldo() > ((Cuenta) pCuenta).getSaldo();
  }
      
    
}
