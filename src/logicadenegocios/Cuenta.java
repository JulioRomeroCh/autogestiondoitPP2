package logicadenegocios;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.MessagingException;
import logicadeaccesoadatos.*;
import logicadeconexionexterna.*;
import webservice.ITipoCambio;

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
   public ArrayList<Bitacora> acciones;
  
  /**
   * <p> Constructor de la clase Cuenta.
   * @param pMonto: atributo de tipo double.
   * @param pPin: atributo de tipo String.
   */
  public Cuenta(double pMonto, String pPin){
      
    this.saldo = pMonto;
    this.pin = pPin;
    this.acciones = new ArrayList<Bitacora>();
    operaciones = new ArrayList<Operacion>();
    
    this.numeroCuenta = "CR"   + generarNumeroCuenta();
    this.fechaCreacion = new Date();
  }  
  /**
   * Constructor sobrecargado de la clase Cuenta.
   * @param pNumeroCuenta: atributo de tipo String.
   * @param pFechaCreacion: atributo de tipo Date.
   * @param pSaldo: atributo de tipo double.
   * @param pPin: atributo de tipo String.
   * @param pEstatus: atributo de tipo String.
   */
  public Cuenta(String pNumeroCuenta, Date pFechaCreacion, double pSaldo, String pPin, String pEstatus){
    this.acciones = new ArrayList<Bitacora>();
    operaciones = new ArrayList<Operacion>();
    this.numeroCuenta = pNumeroCuenta;
    this.fechaCreacion = pFechaCreacion;
    this.saldo = pSaldo;
    this.pin = pPin;
    this.estatus = pEstatus;
  }
  /**
   * <p> Constructor por defecto de  la clase Cuenta.
   */
  public Cuenta(){
    this.acciones = new ArrayList<Bitacora>();
    operaciones = new ArrayList<Operacion>();  
     this.numeroCuenta = "CR"   + generarNumeroCuenta();
  }
  /**
   * <p> Método que registra una operación asociada a una cuenta.
   * @param pTipo: Atributo de TipoOperacion (DEPOSITO, RETIRO, TRANSFERENCIA, CONSULTA o CAMBIO_PIN)
   * @param pNumeroCuenta: String que representa la cuenta asociada a la operación.
   * @param pCargoComision: boolean que representa si la operacipon aplica  un cobro de comisión.
   * @param pMonto: Int que representa eñ monto de la operación.
   * @param pMontoComision: double que representa la comisión cobrada a la operación.
   */
  public void registrarOperacion(TipoOperacion pTipo, String pNumeroCuenta, boolean pCargoComision, int pMonto, double pMontoComision, String pVista){
    try{
    Operacion nuevaOperacion;
    nuevaOperacion = logicacreacional.SimpleOperacionFactory.crearOperacion();
    nuevaOperacion.setTipo(pTipo);
    nuevaOperacion.setCargoComision(pCargoComision);
    nuevaOperacion.setMonto(pMonto);
    nuevaOperacion.setMontoComision(pMontoComision);
    operaciones.add(nuevaOperacion); 
    OperacionDao.insertarOperacion(pTipo.name(), pNumeroCuenta, pMonto, pCargoComision, pMontoComision, pVista);
    notificarSuscriptores(pTipo.name(), pVista);
    }
    catch (Exception error){
        System.out.println("Error al instanciar una operación en Cuenta, registrarOperacion");
        error.printStackTrace();
    }
  }

  
   
  /**
   * Método accesor del atributo saldo.
   * @return double que contiene el saldo de la cuenta.
   */
  public double getSaldo(){
    return this.saldo;
  }
  /**
   * Método accesor del atributo estatus.
   * @return String que contiene el estatus de la cuenta.
   */  
  public String getEstatus(){
    return this.estatus;
  }
  
  /**
   * <p> Método encargada de bloquear una cuenta.
   * @param pMotivo: String que representa el motivo de bloqueo (intentos fallidos de pin o error en 
   * la escritura de la palabra secreta)
   * @param pCorreo: String que representa el correo electrónico del dueño de la cuenta a bloquear.
   * @return String que contiene la información del bloqueo de la cuenta.
   * @throws MessagingException: Excepción lanzada en caso de error en el momento de enviar un correo electrónico.
   */
  public String bloquearCuenta(String pMotivo, String pCorreo) throws MessagingException{
    this.estatus = "inactiva";  
    CuentaDao.inactivarCuentaBaseDeDatos(getNumeroCuenta());
    String mensaje = "Su cuenta ha sido inactivada por el siguiente motivo: " + pMotivo;
    ICorreoElectronico correoElectronico = new CorreoElectronico();
    ICorreoElectronico correoIngles = new CorreoIngles(correoElectronico);
    correoIngles.generarCorreoElectronico(pCorreo, pMotivo);
    return mensaje;
  }
  /**
   * <p> Método que retorna la información asociada a la cuenta.
   * @return String que contiene: número de cuenta, fecha de creación, saldo, estatus, moneda,
   * pin, comisión y la cantidad de depósitos y retiros asociados.
   */
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
  /**
   * <p> Método que actualiza el Pin de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el cambio de pin.
   * @param pPinActual: String que indica el pin actual de la cuenta.
   * @param pNuevoPin: String que indica el nuevo pin de la cuenta.
   * @return String que contiene un mensaje acerca del cambio de pin.
   */
  public String cambiarPin(String pNumeroCuenta, String pPinActual, String pNuevoPin, String pVista){      
    this.pin = pNuevoPin;
    registrarOperacion(TipoOperacion.CAMBIO_PIN, pNumeroCuenta, false, 0, 0.0, pVista);
    CuentaDao.actualizarPin(pNumeroCuenta, pNuevoPin);    
    return "Estimado usuario, se ha cambiado satisfactoriamente el PIN de su cuenta " + pNumeroCuenta + "\n";     
  }
  
  /**
   * <p> Método encargado de aplicar un depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el depósito.
   * @param pMonto: int que representa la cantidad a depositar (en colones).
   * @return String que contiene un mensaje acerca del depósito.
   */
  public String depositarColones(String pNumeroCuenta, int pMonto, String pVista){  
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion=0.0;   
    if(verificarCobroComision() == true){
      registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, true, pMonto, pMonto*COMISION, pVista);
      totalOperacion = cobrarComision(pMonto);
      montoComision = pMonto * COMISION;       
    }
    else{
      registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, false, pMonto, 0.0, pVista);
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
  
   
  /**
   * <p> Método encargado de aplicar un depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el depósito.
   * @param pMonto: int que representa la cantidad a depositar (en dólares).
   * @return String que contiene un mensaje acerca del depósito.
   */  
  public String depositarDolares(ITipoCambio pCompra, String pNumeroCuenta, int pMonto, String pVista){     
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00"); 
    double montoEnColones = pMonto * pCompra.consultarCompraDolar();   
    double totalOperacion = 0.0;
    double montoComision = 0.0;   
    if(verificarCobroComision() == true){
      registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, true, (int) Math.round(montoEnColones), montoEnColones*COMISION, pVista);
      totalOperacion = cobrarComision((int) Math.round(montoEnColones));
      montoComision = montoEnColones * COMISION;
    }
    else{
      registrarOperacion(TipoOperacion.DEPOSITO, pNumeroCuenta, false, (int) Math.round(montoEnColones), 0.0, pVista);
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
  
  /**
   * <p> Método encargado de aplicar un retiro a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el retiro.
   * @param pMonto: int que representa la cantidad a retirar (en colones).
   * @param pPin: String que representa el pin actual de una cuenta, el cual se debe verificar para proceder
   * con el retiro.
   * @return String que contiene un mensaje acerca del retiro.
   */
  public String retirarColones(String pNumeroCuenta, String pPin, int pMonto, String pVista){
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion = 0.0;
    
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, true, pMonto, pMonto*COMISION, pVista);
        totalOperacion = cobrarComision(pMonto);
        montoComision = pMonto * COMISION;
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, false, pMonto, 0.0,pVista);
        totalOperacion = pMonto;
    }
    transaccionesRealizadas++;
    saldo = saldo - totalOperacion;
    CuentaDao.actualizarSaldo(pNumeroCuenta, saldo);
    
    String mensaje = "Estimado usuario, el monto de este retiro es " + formatoDosDecimales.format(totalOperacion) + " colones." + "\n";
    mensaje+="[El monto cobrado por concepto de comisión fue de " + formatoDosDecimales.format(montoComision) + " colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";
    
    
    return mensaje; 
  }

  /**
   * <p> Método encargado de aplicar un retiro a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el retiro.
   * @param pMonto: int que representa la cantidad a retirar (en dólares).
   * @param pPin: String que representa el pin actual de una cuenta, el cual se debe verificar para proceder
   * con el retiro.
   * @return String que contiene un mensaje acerca del retiro.
   */  
  public String retirarDolares(ITipoCambio pVenta, String pNumeroCuenta, String pPin, int pMonto, String pVista){      
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00"); 
    double montoEnColones = pMonto * pVenta.consultarVentaDolar();
    double montoComision = 0.0;
    double totalOperacion = 0.0;   
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, true, (int) Math.round(montoEnColones), montoEnColones * COMISION, pVista);
        totalOperacion = cobrarComision((int) Math.round(montoEnColones));
        montoComision = montoEnColones * COMISION;
        
    }
    else{
        registrarOperacion(TipoOperacion.RETIRO, pNumeroCuenta, false, (int) Math.round(montoEnColones), 0.0, pVista);
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
  
  /**
   * <p> Método encargado de aplicar una transferencia entre dos cuenta (Cuenta Origen y Cuenta Destino).
   * @param pCuentaOrigen: String que representa la cuenta de la cual se realiza la transferencia (sufre una
   * disminución del saldo).
   * @param pPin: String que representa el pin actual de la cuenta de origen, el cual se debe verificar 
   * para proceder con la transferencia.
   * @param pMonto: int que representa el monto a transferir.
   * @param pCuentaDestino: Objeto de tipo Cuenta que representa la cuenta a la cual se realiza la 
   * transferencia (sufre una aumento del saldo).
   * @return String que contiene un mensaje acerca de la transferencia.
   */
  public String transferirFondos (String pCuentaOrigen, String pPin, int pMonto, Cuenta pCuentaDestino, String pVista){  
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    double montoComision = 0.0;
    double totalOperacion = 0.0;   
    if(verificarCobroComision() == true){
        registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaOrigen, true, pMonto, pMonto * COMISION,pVista);
        totalOperacion = cobrarComision(pMonto);
        montoComision = pMonto * COMISION;
    }
    else{
        registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaOrigen, false, pMonto, 0.0,pVista);
        totalOperacion = pMonto;
    }
    transaccionesRealizadas++;;
    saldo = saldo - totalOperacion;
    pCuentaDestino.saldo+= pMonto;
    pCuentaDestino.registrarOperacion(TipoOperacion.TRANSFERENCIA, pCuentaDestino.getNumeroCuenta(), false, pMonto, 0, pVista);
    CuentaDao.actualizarSaldo(pCuentaOrigen, saldo);
    CuentaDao.actualizarSaldo(pCuentaDestino.getNumeroCuenta(), pCuentaDestino.saldo);   
    String mensaje = "Estimado usuario, la transferencia de fondo se ejecutó satisfactoriamente." + "\n";
    mensaje+= "El monto retirado de la cuenta origen y depositado en la cuenta destino es " + pMonto + ".00 colones." + "\n";
    mensaje+= "[El monto cobrado por concepto de comisión a la cuenta origen fue de " 
        + formatoDosDecimales.format(montoComision) + "colones, que fueron rebajados automáticamente de su saldo actual]" + "\n";  
    return mensaje;   
  }
  
  
  /**
   * <p> Método que consulta las comisiones cobradas por concepto de depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de depósito a una cuenta. 
   */
  public String calcularComisionesDepositosCuentaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesDepositosCuentaUnica(pNumeroCuenta);
  }
  
  /**
   * <p> Método que consulta las comisiones cobradas por concepto de retiros a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de retiros a una cuenta. 
   */
  public String calcularComisionesRetirosCuentaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesRetirosUnicaCuenta(pNumeroCuenta);
  }
    
  /**
   * <p> Método que consulta las comisiones cobradas por concepto de depósito y retiros a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de depósito y retiros 
   * a una cuenta. 
   */
  public String calcularComisionesTotalesCuentaUnica(String pNumeroCuenta){
    return CuentaDao.recorrerConsultaTotalComisionesDepositosYRetirosCuentaUnica(pNumeroCuenta);
  }
  
  /**
   * <p> Método encargado de verificar si se debe aplicar el cobro de comisión en la siguiente operación
   * con dinero.
   * @return boolean que indica si se debe aplicar el cobro de comisión en la siguiente operación
   * con dinero.
   */
  private boolean verificarCobroComision(){
    if( this.getTransaccionesRealizadas()>=3){  
      return true;    
    }   
    else{
      return false;    
    }
  }
  
  /**
   * <p> Método que aplica el cobro de comisión al monto de una operación.
   * @param pMonto: int que indica el monto al cual se debe aplicar la comisión.
   * @return double que indica el monto de la operación con el cobro de comisión aplicado.
   */
  private double cobrarComision(int pMonto){     
    double montoTotal = 0.0;
    montoTotal = pMonto - (pMonto*this.COMISION);
    return montoTotal;  
  }
  
  /**
   * <p> Método que consulta el saldo en colones de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el saldo.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene el saldo de la cuenta indicada.
   */
  public String consultarSaldoColones(String pNumeroCuenta, String pPin, String pVista){    
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false, 0, 0.0, pVista);  
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");       
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " + formatoDosDecimales.format(this.saldo) 
        + " colones." + "\n";   
    return mensaje;     
  }
  
  /**
   * <p> Método que consulta el saldo en dólares de una cuenta.
   * @param pCompra: Objeto de tipo ITipoCambio, se usa para obtener los indicadores económicos.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el saldo.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene el saldo en dólares de la cuenta indicada.
   */
  public String consultarSaldoDolares(ITipoCambio pCompra, String pNumeroCuenta, String pPin, String pVista){     
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false,  0, 0.0, pVista);       
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");      
    String mensaje = "Estimado usuario el saldo actual de su cuenta es " 
        + formatoDosDecimales.format(this.saldo / pCompra.consultarCompraDolar()) + " dólares." + "\n";
    mensaje+= "Para esta conversión se utilizó el tipo de cambio del dólar, precio de compra." + "\n";
    mensaje+= "[Según el BCCR, el tipo de cambio de compra del dólar de hoy es: " 
    + formatoDosDecimales.format(pCompra.consultarCompraDolar()) + "]" + "\n";   
    return mensaje;    
  }
  
  /**
   * <p> Método que consulta el estatus de una cuenta. Además registra la operación de consulta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el estatus.
   * @return String que contiene la información relacionada al estatus de una cuenta.
   */
  public String consultarEstatus(String pNumeroCuenta, String pVista){    
    registrarOperacion(TipoOperacion.CONSULTA, pNumeroCuenta, false, 0, 0.0,pVista);
    String mensaje = "La cuenta número " + pNumeroCuenta + " tiene estatus de " + this.estatus + "\n";   
    return mensaje;   
  }
  
  /**
   * <p> Método que genera el estado de cuenta en colones.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene la información del estado de cuenta en colones.
   */
  public String generarEstadoCuentaColones(String pNumeroCuenta, String pPin){
    String mensaje = "";
    mensaje+= CuentaDao.recorrerConsultaDatosCuenta(pNumeroCuenta);
    mensaje += OperacionDao.recorrerConsultaOperacionesCuenta(pNumeroCuenta);  
    return mensaje;
  }
  /**
   * <p> Método que genera el estado de cuenta en dólares.
   * @param pCompra: Objeto de tipo ITipoCambio, se usa para obtener los indicadores económicos.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene la información del estado de cuenta en dólares.
   */  
  public String generarEstadoCuentaDolares(ITipoCambio pCompra, String pNumeroCuenta, String pPin){
    String mensaje = "";
    mensaje+= CuentaDao.recorrerConsultaDatosCuentaDolares(pNumeroCuenta);
    mensaje += OperacionDao.recorrerConsultaOperacionesCuentaDolares(pNumeroCuenta);
    return mensaje;    
  }
  
  /**
   * <p> Método encargado de asignar un número de cuenta de forma aleatoria (de 5 dígitos).
   * @return int que representa el número de cuenta generado de forma aleatoria (de 5 dígitos).
   */
  private int generarNumeroCuenta(){ 
    int numeroAleatorio;
    numeroAleatorio = (int)(Math.random()*99999+10000);   
    return numeroAleatorio;     
  }
  
  /**
   * Método que indica si el saldo de la cuenta es mayor al saldo de la cuenta ingresada por parámetro.
   * @param pCuenta: Objeto de tipo Comparable, la cual se compara con la cuenta proveedora del método.
   * @return boolena que indica si el saldo de la cuenta es mayor al saldo de la cuenta ingresada por parámetro.
   */
  public boolean comparar (Comparable pCuenta){
    return this.getSaldo() > ((Cuenta) pCuenta).getSaldo();
  }

  /**
   * Método accesor del atributo numeroCuenta.
   * @return String que indica el número de cuenta.
   */
  public String getNumeroCuenta() {
    return numeroCuenta;
  }

  /**
   * Método accesor del atributo transaccionesRealizadas.
   * @return int que indica el número de transacciones realizadas.
   */
  public int getTransaccionesRealizadas() {
    return transaccionesRealizadas;
  }
  
  /**
   * <p> Método que modifica el número de transacciones realizadas.
   * @param pTransaccionesRealizadas: int que indica la cantidad de transacciones realizadas actualizadas.
   */
  public void modificarTransaccionesRealizadas(int pTransaccionesRealizadas) {
    this.transaccionesRealizadas += pTransaccionesRealizadas;
  }
  
  public void setNumeroCuenta(String pNumeroCuenta){
    this.numeroCuenta = pNumeroCuenta;
  }
  
  public void setFecha(Date pFecha){
    this.fechaCreacion = pFecha;
  }
  
  public void setSaldo (double pSaldo){
    this.saldo = pSaldo;
  }
  
  public void setPin(String pPin){
    this.pin = pPin;
  }
  
  public void setEstatus (String pEstatus){
    this.estatus = pEstatus;
  }
  
    
   public void anadirAccionBitacora (Bitacora pBitacora){
    acciones.add(pBitacora);
  }
  
  private void notificarSuscriptores(String pAccion, String pVista){
    for (int contador = 0; contador != acciones.size(); contador++){
      acciones.get(contador).agregarAccion(pAccion, pVista);
    }
  }
  
    


}
