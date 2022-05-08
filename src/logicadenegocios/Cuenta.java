package logicadenegocios;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.MessagingException;
import logicadeaccesoadatos.*;
import logicadeconexionexterna.*;
import webservice.TipoCambio;

public class Cuenta implements Comparable {
 
  private String numeroCuenta;
  private Date fechaCreacion;
  private double saldo;
  private String estatus = "activa";
  private static final String MONEDA = "colones";
  private String pin;
  private final double COMISION = 0.02;
  private int transaccionesRealizadas = 0;
  
  public ArrayList<Operacion> operaciones;
  
  public Cuenta(double pMonto, String pPin){
      
    this.saldo = pMonto;
    this.pin = pPin;
    
    operaciones = new ArrayList<Operacion>();
    
    this.numeroCuenta = "CR"   + generarNumeroCuenta();
    this.fechaCreacion = new Date();
  }  
  
  public Cuenta(String pNumeroCuenta, Date pFechaCreacion, double pSaldo, String pPin, String pEstatus){
    operaciones = new ArrayList<Operacion>();
    this.numeroCuenta = pNumeroCuenta;
    this.fechaCreacion = pFechaCreacion;
    this.saldo = pSaldo;
    this.pin = pPin;
    this.estatus = pEstatus;

  }
  
  public Cuenta(){
      
  }
  
  public void registrarOperacion(TipoOperacion pTipo, String pNumeroCuenta, boolean pCargoComision, int pMonto, double pMontoComision){
    
      Operacion nuevaOperacion = new Operacion(pTipo, pCargoComision, pMonto, pMontoComision);
      operaciones.add(nuevaOperacion); 
      OperacionDao.insertarOperacion(pTipo.name(), pNumeroCuenta, pMonto, pCargoComision, pMontoComision);

  }
  
  public double getSaldo(){
    return this.saldo;
  }
  
  public String getEstatus(){
    return this.estatus;
  }
  
  public String bloquearCuenta(String pMotivo, String pCorreo) throws MessagingException{
    this.estatus = "inactiva";  
    CuentaDao.inactivarCuentaBaseDeDatos(getNumeroCuenta());
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
    mensaje+= "Número de depósitos y/o retiros: " + this.getTransaccionesRealizadas() + "\n"; 
    
    return mensaje;
  }
  
  public String cambiarPin(String pNumeroCuenta, String pPinActual, String pNuevoPin){ 
      
      this.pin = pNuevoPin;
      registrarOperacion(TipoOperacion.CAMBIO_PIN, pNumeroCuenta, false, 0, 0.0);
      CuentaDao.actualizarPin(pNumeroCuenta, pNuevoPin);
     
      return "Estimado usuario, se ha cambiado satisfacotiramente el PIN de su cuenta " + pNumeroCuenta + "\n";
      
  }

  public String depositarColones(String pNumeroCuenta, int pMonto){
   
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion=0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, true, pMonto, pMonto*COMISION);
        totalOperacion = cobrarComision(pMonto);
        montoComision = pMonto * COMISION;
        
    }
    else{
        registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    transaccionesRealizadas++;
    saldo = saldo + totalOperacion;
    CuentaDao.actualizarSaldo(pNumeroCuenta, saldo);
    
    String mensaje = "Estimado usuario, se han depositado correctamente " + pMonto + ".00 colones" + "\n";
    mensaje+="[El monto real depositado a su cuenta " + pNumeroCuenta + " es de " + formatoDosDecimales.format(totalOperacion) + " colones]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoComision) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje;    
  }
  
   
  
  public String depositarDolares(TipoCambio pCompra, String pNumeroCuenta, int pMonto){
      
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
   
    double montoEnColones = pMonto * pCompra.consultarCompraDolar();
    
    double totalOperacion = 0.0;
    double montoComision = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, true, (int) Math.round(montoEnColones), montoEnColones*COMISION);
        totalOperacion = cobrarComision((int) Math.round(montoEnColones));
        montoComision = montoEnColones * COMISION;
    }
    else{
        registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, false, (int) Math.round(montoEnColones), 0.0);
        totalOperacion = montoEnColones;
    }
     transaccionesRealizadas++;
    saldo = saldo + totalOperacion;
    CuentaDao.actualizarSaldo(pNumeroCuenta, saldo);
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("MM/dd/yyyy");
    String fechaActual = formatoFecha.format(new Date());
    DecimalFormat formatoDecimal = new DecimalFormat("#.000");

    
    String mensaje = "Estimado usuario, se han recibido correctamente " + pMonto + ".00 dólares" + "\n";
    mensaje+="[Según el BCCR, el tipo de cambio de compra del dólar de " + fechaActual + " es: " + formatoDosDecimales.format(pCompra.consultarCompraDolar()) + "]" + "\n";
    mensaje+="[El monto equivalente en colones es " +  formatoDecimal.format(montoEnColones) + "]" + "\n";
    mensaje+="[El monto real depositado a su cuenta " + pNumeroCuenta + " es de " + formatoDosDecimales.format(totalOperacion) + " colones]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoComision) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje;    
  }
  
  
  public String retirarColones(String pNumeroCuenta, String pPin, int pMonto){
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, true, pMonto, pMonto*COMISION);
        totalOperacion = cobrarComision(pMonto);
        montoComision = pMonto * COMISION;
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    transaccionesRealizadas++;
    saldo = saldo - totalOperacion;
    CuentaDao.actualizarSaldo(pNumeroCuenta, saldo);
    
    String mensaje = "Estimado usuario, el monto de este retiro es " + formatoDosDecimales.format(totalOperacion) + " colones." + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoComision) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje; 
  }
  
  public String retirarDolares(TipoCambio pVenta, String pNumeroCuenta, String pPin, int pMonto){
        
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
   
    double montoEnColones = pMonto * pVenta.consultarVentaDolar();
    double montoComision = 0.0;
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, true, (int) Math.round(montoEnColones), montoEnColones * COMISION);
        totalOperacion = cobrarComision((int) Math.round(montoEnColones));
        montoComision = montoEnColones * COMISION;
        
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, false, (int) Math.round(montoEnColones), 0.0);
        totalOperacion = montoEnColones;
    }
    transaccionesRealizadas++;
    saldo = saldo - totalOperacion;
    CuentaDao.actualizarSaldo(pNumeroCuenta, saldo);
    

    DecimalFormat formatoDecimal = new DecimalFormat("#.000");

    
    String mensaje = "Estimado usuario, el monto de este retiro es" + formatoDosDecimales.format(pMonto) + "\n";
    mensaje+="[Según el BCCR, el tipo de cambio de venta del dólar de hoy es: " + formatoDosDecimales.format(pVenta.consultarVentaDolar())+ "]"  + "\n";
    mensaje+="[El monto equivalente de su retiro es " +  formatoDecimal.format(totalOperacion) + "]" + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoComision) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    return mensaje;    
    
  }
  
  public String transferirFondos (String pCuentaOrigen, String pPin, int pMonto, Cuenta pCuentaDestino){
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaOrigen, true, pMonto, pMonto * COMISION);
        totalOperacion = cobrarComision(pMonto);
        montoComision = pMonto * COMISION;
    }
    else{
        registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaOrigen, false, pMonto, 0.0);
        totalOperacion = pMonto;
    }
    transaccionesRealizadas++;;
    saldo = saldo - totalOperacion;
    pCuentaDestino.saldo+= pMonto;
    pCuentaDestino.registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaDestino.getNumeroCuenta(), false, pMonto, 0);
    CuentaDao.actualizarSaldo(pCuentaOrigen, saldo);
    CuentaDao.actualizarSaldo(pCuentaDestino.getNumeroCuenta(), pCuentaDestino.saldo);
    
    String mensaje = "Estimado usuario, la transferencia de fondo se ejecutó satisfactoriamente." + "\n";
    mensaje+= "El monto retirado de la cuenta origen y depositado en la cuenta destino es " + pMonto + ".00 colones." + "\n";
    mensaje+= "[El monto cobrado por concepto de comisión a la cuenta origen fue de " + formatoDosDecimales.format(montoComision) + "colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    return mensaje;   
  }
  
  
  
  public String calcularComisionesDepositosCuentaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesDepositosCuentaUnica(pNumeroCuenta);
  }
  

  public String calcularComisionesRetirosCuentaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesRetirosUnicaCuenta(pNumeroCuenta);
  }
    

  public String calcularComisionesTotalesCuantaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesDepositosYRetirosCuentaUnica(pNumeroCuenta);
  }
  
  
  private boolean verificarCobroComision(){
    if( this.getTransaccionesRealizadas()>=3){  
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
      
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false, 0, 0.0);
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");  
      
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " + formatoDosDecimales.format(this.saldo) + " colones." + "\n";
    
    return mensaje;
      
  }
  
  public String consultarSaldoDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
      
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false,  0, 0.0);  
      
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");  
      
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " + formatoDosDecimales.format(this.saldo / pCompra.consultarCompraDolar()) + " dólares." + "\n";
    mensaje+= "Para esta conversión se utilizó el tipo de cambio del dólar, precio de compra." + "\n";
    mensaje+= "[Según el BCCR, el tipo de cambio de compra del dólar de hoy es: " + formatoDosDecimales.format(pCompra.consultarCompraDolar()) + "]" + "\n";
    
    return mensaje;  
    
  }
  
  public String consultarEstatus(String pNumeroCuenta){
      
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false, 0, 0.0);
    
    String mensaje = "La cuenta número " + pNumeroCuenta + " tiene estatus de " + this.estatus + "\n";
    
    return mensaje;
    
  }
  
  
  public String generarEstadoCuentaColones(String pNumeroCuenta, String pPin){

    String mensaje = "";
      mensaje+= CuentaDao.recorrerConsultaDatosCuenta(pNumeroCuenta);
      mensaje += OperacionDao.recorrerConsultaOperacionesCuenta(pNumeroCuenta);
    
    return mensaje;
  }
  
  public String generarEstadoCuentaDolares(TipoCambio pCompra, String pNumeroCuenta, String pPin){
    String mensaje = "";
      mensaje+= CuentaDao.recorrerConsultaDatosCuentaDolares(pNumeroCuenta);
      mensaje += OperacionDao.recorrerConsultaOperacionesCuentaDolares(pNumeroCuenta);
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


  public int getTransaccionesRealizadas() {
    return transaccionesRealizadas;
  }

  public void modificarTransaccionesRealizadas(int pTransaccionesRealizadas) {
    this.transaccionesRealizadas += pTransaccionesRealizadas;
  }
      
    
}
