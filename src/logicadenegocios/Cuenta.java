package logicadenegocios;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.MessagingException;
import logicadeaccesoadatos.*;
import logicadeconexionexterna.*;
import webservice.TipoCambio;

public class Cuenta implements Comision, Comparable {
 
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
  
  public Cuenta(){
      
  }
  
  public void registrarOperacion(TipoOperacion pTipo, boolean pCargoComision, int pMonto, double pMontoComision){
    
      Operacion nuevaOperacion = new Operacion(pTipo, pCargoComision, pMonto, pMontoComision);
      operaciones.add(nuevaOperacion); 
      
      CuentaDao nuevoDaoCuenta = new CuentaDao();
      nuevoDaoCuenta.insertarCuentaTieneOperacion(getNumeroCuenta(), pMonto);
      
  }
  
  public double getSaldo(){
    return this.saldo;
  }
  
  public String getEstatus(){
    return this.estatus;
  }
  
  public String bloquearCuenta(String pMotivo, String pCorreo) throws MessagingException{
    this.estatus = "inactiva";  
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    nuevoDaoCuenta.inactivarCuentaBaseDeDatos(getNumeroCuenta());
    String mensaje = "Su cuenta ha sido inactivada por el siguiente motivo: " + pMotivo;
    CorreoElectronico.generarCorreoElectronico(pCorreo, pMotivo);
    return mensaje;
  }
  
  public String toString(){
   
    String mensaje = "";

    mensaje = "Número de cuenta: " + this.getNumeroCuenta() + "\n";
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
      
      this.pin = pNuevoPin;
      CuentaDao nuevoDaoCuenta = new CuentaDao();
      registrarOperacion(TipoOperacion.CAMBIO_PIN, false, 0, 0.0);
      nuevoDaoCuenta.actualizarPin(pNumeroCuenta, pNuevoPin);
     
      return "Estimado usuario, se ha cambiado satisfacotiramente el PIN de su cuenta " + pNumeroCuenta + "\n";
      
  }

  public String depositarColones(String pNumeroCuenta, int pMonto){
    
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    
    double totalOperacion=0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.DEPOSITO, true, pMonto, pMonto*COMISION);
        totalOperacion = cobrarComision(pMonto);
        
    }
    else{
        registrarOperacion(TipoOperacion.DEPOSITO, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    
    saldo = saldo + totalOperacion;
    nuevoDaoCuenta.actualizarSaldo(pNumeroCuenta, saldo);
    
    String mensaje = "Estimado usuario, se han depositado correctamente " + pMonto + ".00 colones" + "\n";
    mensaje+="[El monto real depositado a su cuenta " + pNumeroCuenta + " es de " + formatoDosDecimales.format(totalOperacion) + " colones]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(pMonto * COMISION) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje;    
  }
  
   
  
  public String depositarDolares(TipoCambio pCompra, String pNumeroCuenta, int pMonto){
      
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
   
    double montoEnColones = pMonto * pCompra.consultarCompraDolar();
    
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.DEPOSITO, true, (int) Math.round(montoEnColones), montoEnColones*COMISION);
        totalOperacion = cobrarComision((int) Math.round(montoEnColones));
        
    }
    else{
        registrarOperacion(TipoOperacion.DEPOSITO, false, (int) Math.round(montoEnColones), 0.0);
        totalOperacion = montoEnColones;
    }
    
    saldo = saldo + totalOperacion;
    nuevoDaoCuenta.actualizarSaldo(pNumeroCuenta, saldo);
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("MM/dd/yyyy");
    String fechaActual = formatoFecha.format(new Date());
    DecimalFormat formatoDecimal = new DecimalFormat("#.000");

    
    String mensaje = "Estimado usuario, se han recibido correctamente " + pMonto + ".00 dólares" + "\n";
    mensaje+="[Según el BCCR, el tipo de cambio de compra del dólar de " + fechaActual + " es: " + formatoDosDecimales.format(pCompra.consultarCompraDolar()) + "]" + "\n";
    mensaje+="[El monto equivalente en colones es " +  formatoDecimal.format(montoEnColones) + "]" + "\n";
    mensaje+="[El monto real depositado a su cuenta " + pNumeroCuenta + " es de " + formatoDosDecimales.format(totalOperacion) + " colones]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoEnColones * COMISION) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje;    
  }
  
  
  public String retirarColones(String pNumeroCuenta, String pPin, MensajeTexto pMensaje, int pMonto){
    
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, true, pMonto, pMonto*COMISION);
        totalOperacion = cobrarComision(pMonto);
        
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    
    saldo = saldo - totalOperacion;
    nuevoDaoCuenta.actualizarSaldo(pNumeroCuenta, saldo);
    
    String mensaje = "Estimado usuario, el monto de este retiro es" + formatoDosDecimales.format(totalOperacion) + "colones." + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(pMonto * COMISION) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje; 
  }
  
  public String retirarDolares(TipoCambio pVenta, String pNumeroCuenta, String pPin, MensajeTexto pMensaje, int pMonto){
        
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
   
    double montoEnColones = pMonto * pVenta.consultarVentaDolar();
    
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, true, (int) Math.round(montoEnColones), montoEnColones * COMISION);
        totalOperacion = cobrarComision((int) Math.round(montoEnColones));
        
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, false, (int) Math.round(montoEnColones), 0.0);
        totalOperacion = montoEnColones;
    }
    
    saldo = saldo - totalOperacion;
    nuevoDaoCuenta.actualizarSaldo(pNumeroCuenta, saldo);
    

    DecimalFormat formatoDecimal = new DecimalFormat("#.000");

    
    String mensaje = "Estimado usuario, el monto de este retiro es" + formatoDosDecimales.format(pMonto) + "\n";
    mensaje+="[Según el BCCR, el tipo de cambio de venta del dólar de hoy es: " + formatoDosDecimales.format(pVenta.consultarVentaDolar())+ "]"  + "\n";
    mensaje+="[El monto equivalente de su retiro es " +  formatoDecimal.format(totalOperacion) + "]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoEnColones * COMISION) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    return mensaje;    
    
  }
  
  public String transferirFondos (String pCuentaOrigen, String pPin, MensajeTexto pMensaje, int pMonto, Cuenta pCuentaDestino){
    
    CuentaDao nuevoDaoCuenta = new CuentaDao();
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.TRANSFERENCIA, true, pMonto, pMonto * COMISION);
        totalOperacion = cobrarComision(pMonto);
        
    }
    else{
        registrarOperacion(TipoOperacion.TRANSFERENCIA, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    
    saldo = saldo - totalOperacion;
    pCuentaDestino.saldo+= pMonto;
    nuevoDaoCuenta.actualizarSaldo(pCuentaOrigen, saldo);
    nuevoDaoCuenta.actualizarSaldo(pCuentaDestino.getNumeroCuenta(), pCuentaDestino.saldo);
    
    String mensaje = "Estimado usuario, la transferencia de fondo se ejecutó satisfactoriamente." + "\n";
    mensaje+= "El monto retirado de la cuenta origen y depositado en la cuenta destino es " + pMonto + ".00 colones." + "\n";
    mensaje+= "[El monto cobrado por concepto de comisión a la cuenta origen fue de " + formatoDosDecimales.format(pMonto * COMISION) + "colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    return mensaje;   
  }
  
  
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
  
  
  private boolean verificarCobroComision(){
    if(this.transaccionesRealizadas>=3){  
      return true;    
    }   
    else{
      return false;    
    }
  }
  
  private double cobrarComision(int pMonto){
      
    double montoTotal = 0.0;
    montoTotal = pMonto - (pMonto*this.COMISION);
    return montoTotal;
    
  }
  
  public String consultarSaldoColones(String pNumeroCuenta, String pPin){
      
    registrarOperacion(TipoOperacion.CONSULTA, false, 0, 0.0);
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");  
      
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " + formatoDosDecimales.format(this.saldo) + " colones." + "\n";
    
    return mensaje;
      
  }
  
  public String consultarSaldoDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
      
    registrarOperacion(TipoOperacion.CONSULTA, false, 0, 0.0);  
      
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");  
      
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " + formatoDosDecimales.format(this.saldo / pCompra.consultarCompraDolar()) + " dólares." + "\n";
    mensaje+= "Para esta conversión se utilizó el tipo de cambio del dólar, precio de compra." + "\n";
    mensaje+= "[Según el BCCR, el tipo de cambio de compra del dólar de hoy es: " + formatoDosDecimales.format(pCompra.consultarCompraDolar()) + "]" + "\n";
    
    return mensaje;  
    
  }
  
  public String consultarEstatus(String pNumeroCuenta){
      
    registrarOperacion(TipoOperacion.CONSULTA, false, 0, 0.0);
    
    String mensaje = "La cuenta número " + pNumeroCuenta + " tiene estatus de " + this.estatus + "\n";
    
    return mensaje;
    
  }
  
  
  public String generarEstadoCuentaColones(String pNumeroCuenta, String pPin){

    
    String mensaje = "";
    for (int contador = 0; contador!=operaciones.size(); contador++){
      mensaje+= operaciones.get(contador).toString();
    }
    return mensaje;
  }
  
  public String generarEstadoCuentaDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
    String mensaje = "";
    for (int contador = 0; contador!=operaciones.size(); contador++){
      mensaje+= operaciones.get(contador).dolarizar(pCompra);
    }
    return mensaje;    
  }
  
  
  private int generarNumeroCuenta(){
  
    int numeroAleatorio;

    numeroAleatorio = (int)(Math.random()*99999+10000);
    
    return numeroAleatorio;
      
  }
  
  public boolean comparar (Comparable pCuenta){
      return this.getSaldo() > ((Cuenta) pCuenta).getSaldo();
  }

  public String getNumeroCuenta() {
    return numeroCuenta;
  }
      
    
}
