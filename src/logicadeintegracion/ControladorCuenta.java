package logicadeintegracion;

import java.util.ArrayList;
import javax.mail.MessagingException;
import logicadeaccesoadatos.CuentaDao;
import logicadenegocios.*;
import logicadevalidacion.ExpresionRegular;
import logicadeaccesoadatos.ClienteDao;
import webservice.TipoCambio;

public class ControladorCuenta {
  
    
  public static ArrayList<Cuenta> cuentas = new ArrayList();
  
  
  /**
   * Método que busca una cuenta en el ArrayList "cuentas" y lo retorna.
   * @param pNumeroCuenta: La cuenta que se desea buscar.
   * @return Objeto de tipo Cuenta.
   */ 
  public static Cuenta buscarCuenta(String pNumeroCuenta){
    Cuenta cuenta;
    try{
    cuenta = logicacreacional.SimpleCuentaFactory.crearCuenta();
    for(int contador = 0; contador != cuentas.size(); contador++){
      if(cuentas.get(contador).getNumeroCuenta().equals(pNumeroCuenta)){
         cuenta = cuentas.get(contador);
      }  
    }
      return cuenta;
  }
  catch (Exception error){
      System.out.println("Error al instanciar una cuenta en el controlador cuenta");
      return null;
   }
  
  }
  /**
   * <p> Método que retorna los datos del cliente dueño de una cuenta.
   * @param pNumeroCuenta:String que representa la cuenta que se desea consultar.
   * @return String que contiene los datos de un cliente: identificación,
   * nombre, apellido1 y apellido2. 
   */
 public static String consultarDatosCuentaCliente(String pNumeroCuenta){
   return CuentaDao.recorrerConsultarClienteCuenta(pNumeroCuenta);
 }
 
  /**
   * <p> Método que actualiza el Pin de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el cambio de pin.
   * @param pPinActual: String que indica el pin actual de la cuenta.
   * @param pNuevoPin: String que indica el nuevo pin de la cuenta.
   * @return String que contiene un mensaje acerca del cambio de pin.
   */
 public static String llamarCambiarPinCLI(String pNumeroCuenta, String pPinActual, String pNuevoPin, String pVista){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if(CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPinActual) && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
    return nuevaCuenta.cambiarPin(pNumeroCuenta, pPinActual, pNuevoPin, pVista);

   }
   return "Error al cambiar PIN y/o la cuenta está inactiva";
 }
 
  /**
   * <p> Método encargada de bloquear una cuenta.
   * @param pMotivo: String que representa el motivo de bloqueo (intentos fallidos de pin o error en 
   * la escritura de la palabra secreta)
   * @param pNumeroCuenta: String que la cuenta a bloquear.
   * @return String que contiene la información del bloqueo de la cuenta.
   * @throws MessagingException : Excepción lanzada en caso de error en el momento de enviar un correo electrónico.
   */ 
 public static String llamarBloquearCuenta(String pMotivo, String pNumeroCuenta) throws MessagingException{
   if(CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.bloquearCuenta(pMotivo, ClienteDao.recorrerConsultarCorreoClientePorCuenta(pNumeroCuenta));
   }
   else{
     return "La cuenta indicada no está registrada en el sistema";    
   }
 }
 
 /**
  * <p> Método que consulta una cuenta en particular.
  * @param pNumeroCuenta: Cuenta  a consultar.
  * @return String con la información de la cuenta.
  */
 public static String llamarConsultarCuentaParticular(String pNumeroCuenta){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   String mensaje = "Número de cuenta: " + nuevaCuenta.getNumeroCuenta()+ "\n";
   mensaje += "Estatus: " + nuevaCuenta.getEstatus() + "\n";
   mensaje += "Saldo: " + nuevaCuenta.getSaldo() + "\n";
   mensaje += CuentaDao.recorrerConsultarClienteCuenta(pNumeroCuenta) + "\n";
   
   return mensaje;
 }
 
  /**
   * <p> Método encargado de aplicar un depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el depósito.
   * @param pMonto: int que representa la cantidad a depositar (en colones).
   * @return String que contiene un mensaje acerca del depósito.
   */ 
 public static String llamarDepositarColones(String pNumeroCuenta, String pMonto, String pVista){
   if (ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) == true && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.depositarColones(pNumeroCuenta, Integer.parseInt(pMonto), pVista);
   }
   else{
     return "El monto no es un número entero y/o la cuenta no existe y/o la cuenta está inactiva";
   }
 }
 
  /**
   * <p> Método encargado de aplicar un depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el depósito.
   * @param pMonto: int que representa la cantidad a depositar (en dólares).
   * @return String que contiene un mensaje acerca del depósito.
   */  
 public static String llamarDepositarDolares(String pNumeroCuenta, String pMonto, String pVista){
   if (ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) == true && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.depositarDolares(new TipoCambio(), pNumeroCuenta, Integer.parseInt(pMonto), pVista);
   }
   else{
     return "El monto no es un número entero y/o la cuenta no existe y/o la cuenta está inactiva";
   }
 }

  /**
   * <p> Método encargado de aplicar un retiro a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el retiro.
   * @param pMonto: int que representa la cantidad a retirar (en colones).
   * @param pPin: String que representa el pin actual de una cuenta, el cual se debe verificar para proceder
   * con el retiro.
   * @return String que contiene un mensaje acerca del retiro.
   */ 
 public static String llamarRetirarColones(String pNumeroCuenta,String pPin, String pMonto, String pVista){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarSuficienciaDeFondos(pNumeroCuenta, Double.parseDouble(pMonto)) && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
     return nuevaCuenta.retirarColones(pNumeroCuenta, pPin, Integer.parseInt(pMonto), pVista);
   }
   else{
     return "Error al retirar dinero en colones y/o la cuenta está inactiva";
   }
 }
 

  /**
   * <p> Método encargado de aplicar un retiro a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta a la cual se le aplicará el retiro.
   * @param pMonto: int que representa la cantidad a retirar (en dólares).
   * @param pPin: String que representa el pin actual de una cuenta, el cual se debe verificar para proceder
   * con el retiro.
   * @return String que contiene un mensaje acerca del retiro.
   */  
 public static String llamarRetirarDolares(String pNumeroCuenta,String pPin, String pMonto, String pVista){
   TipoCambio venta = new TipoCambio();
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarSuficienciaDeFondos(pNumeroCuenta, Double.parseDouble(pMonto)*venta.consultarVentaDolar())&& CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
     return nuevaCuenta.retirarDolares(new TipoCambio(), pNumeroCuenta, pPin, Integer.parseInt(pMonto), pVista);
   }
   else{
     return "Error al retirar dinero en dólares y/o la cuenta está inactiva";
   }
 }
 
  /**
   * <p> Método encargado de aplicar una transferencia entre dos cuenta (Cuenta Origen y Cuenta Destino).
   * @param pNumeroCuentaOrigen: String que representa la cuenta de la cual se realiza la transferencia (sufre una
   * disminución del saldo).
   * @param pPin: String que representa el pin actual de la cuenta de origen, el cual se debe verificar 
   * para proceder con la transferencia.
   * @param pMonto: int que representa el monto a transferir.
   * @param pNumeroCuentaDestino: Objeto de tipo Cuenta que representa la cuenta a la cual se realiza la 
   * transferencia (sufre una aumento del saldo).
   * @return String que contiene un mensaje acerca de la transferencia.
   */ 
 public static String llamarTransferirFondos(String pNumeroCuentaOrigen,String pPin, String pMonto, String pNumeroCuentaDestino, String pVista){
   Cuenta cuentaOrigen = buscarCuenta(pNumeroCuentaOrigen);
   Cuenta cuentaDestino = buscarCuenta(pNumeroCuentaDestino);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuentaOrigen) && CuentaDao.verificarCorrectitudPin(pNumeroCuentaOrigen, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarExistenciaCuenta(pNumeroCuentaDestino)&& CuentaDao.verificarSuficienciaDeFondos(pNumeroCuentaOrigen, Double.parseDouble(pMonto))&& CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuentaOrigen).equalsIgnoreCase("Activa")){
         
     return cuentaOrigen.transferirFondos(pNumeroCuentaOrigen, pPin, Integer.parseInt(pMonto), cuentaDestino, pVista);
   }
   else{
     return "Error al transferir fondos y/o la cuenta está inactiva";
   }
 }
 
 
 /**
  * <p> Método que muestra la información de las cuentas registradas.
  * @return String que contiene la información de todas las cuentas.
  */ 
 public static String listarCuentas(){
   String mensaje = "";
   Ordenacion ordenCuentas = new Ordenacion();
   Cuenta[] arregloCuentas = convertirListaCuentasEnArreglo();
   ordenCuentas.ordenamientoInsercion(arregloCuentas);
   for (int contador = 0; contador != arregloCuentas.length; contador++){ 
     mensaje += arregloCuentas[contador].getNumeroCuenta() + "\n";
     mensaje += arregloCuentas[contador].getEstatus() + "\n";
     mensaje += arregloCuentas[contador].getSaldo() + "\n";
     mensaje += CuentaDao.recorrerConsultarClienteCuenta(arregloCuentas[contador].getNumeroCuenta()) + "\n";
   }
   return mensaje;
  }
  
  /**
   * <p> Método encargado de convertir un ArrayList en Arreglo. 
   * @return Arreglo de tipo Cuenta.
   */ 
  public static Cuenta[] convertirListaCuentasEnArreglo(){
    Cuenta [] arregloCuentas = new Cuenta[cuentas.size()];
    for(int contador = 0; contador != cuentas.size(); contador++){
      arregloCuentas[contador] = cuentas.get(contador);
    }
    return arregloCuentas;
  }

  /**
   * <p> Método que consulta el tipo de cambio de la compra para hoy
   * @return double que corresponde al tipo de cambio de la compra para hoy.
   */  
  public static String consultarCompraDolar(){
    TipoCambio compra = new TipoCambio();
    return String.valueOf(compra.consultarCompraDolar());
  }

  /**
   * <p> Método que consulta el tipo de cambio de la venta para hoy
   * @return double que corresponde al tipo de cambio de la venta para hoy.
   */  
  public static String consultarVentaDolar(){
    TipoCambio venta = new TipoCambio();
    return String.valueOf(venta.consultarVentaDolar());
  }
  
  /**
   * <p> Método que consulta el saldo en colones de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el saldo.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene el saldo de la cuenta indicada.
   */  
  public static String llamarConsultarSaldoColones(String pNumeroCuenta, String pPin, String pVista){
    if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      return nuevaCuenta.consultarSaldoColones(pNumeroCuenta, pPin, pVista);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin y/o la cuenta está inactiva";
    }
  }
  
  /**
   * <p> Método que consulta el saldo en dólares de una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el saldo.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene el saldo en dólares de la cuenta indicada.
   */
   public static String llamarConsultarSaldoDolares(String pNumeroCuenta, String pPin, String pVista){
    if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)&& CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      return nuevaCuenta.consultarSaldoDolares(new TipoCambio(), pNumeroCuenta, pPin,pVista);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin y/o la cuenta está inactiva";
    }
  }
   
  /**
   * <p> Método que genera el estado de cuenta en colones.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene la información del estado de cuenta en colones.
   */   
   public static String llamarGenerarEstadoCuentaColones (String pNumeroCuenta, String pPin){
     
       if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)&& CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
         Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
         return nuevaCuenta.generarEstadoCuentaColones(pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin y/o la cuenta está inactiva";
    }
   }
    
  /**
   * <p> Método que genera el estado de cuenta en dólares.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @param pPin: String que representa el pin actual de la cuenta, el cual se debe verificar 
   * para proceder con la consulta.
   * @return String que contiene la información del estado de cuenta en dólares.
   */     
    public static String llamarGenerarEstadoCuentaDolares (String pNumeroCuenta, String pPin){
     
       if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)&& CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
         Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
         return nuevaCuenta.generarEstadoCuentaDolares(new TipoCambio(), pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin y/o la cuenta está inactiva";
    }
   }
    
  /**
   * <p> Método que consulta el estatus de una cuenta. Además registra la operación de consulta.
   * @param pNumeroCuenta: String que representa la cuenta de la cual se desea consultar el estatus.
   * @return String que contiene la información relacionada al estatus de una cuenta.
   */    
   public static String llamarConsultarEstatus(String pNumeroCuenta, String pVista){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        return nuevaCuenta.consultarEstatus(pNumeroCuenta,pVista);
      }
      else{
        return "La cuenta no está registrada en el sistema";
      }
    }
    
  /**
   * <p> Método que consulta las comisiones cobradas por concepto de depósito a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de depósito a una cuenta. 
   */    
   public static String llamarCalcularComisionesDepositosCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) ){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesDepositosCuentaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }
    
  /**
   * <p> Método que consulta las comisiones cobradas por concepto de retiros a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de retiros a una cuenta. 
   */    
   public static String llamarCalcularComisionesRetirosCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesRetirosCuentaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }

  /**
   * <p> Método que consulta las comisiones cobradas por concepto de depósito y retiros a una cuenta.
   * @param pNumeroCuenta: String que representa la cuenta que se desea consultar.
   * @return String que representa el monto de comisiones cobradas por concepto de depósito y retiros 
   * a una cuenta. 
   */     
   public static String llamarcalcularComisionesTotalesCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesTotalesCuentaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }
   
    /**
     * <p> Método que concatena las consultas sobre el cálculo de comisiones y lo retorna.
     * @param pNumeroCuenta: Número de cuenta que se desea consultar.
     * @return String que contiene el desglose del cobro de comisiones para una cuenta,
     */ 
    public static String calcularTodasLasComisionesCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.recorrerConsultaEstatusCuenta(pNumeroCuenta).equalsIgnoreCase("Activa")){
        String comisiones = "";
        comisiones += llamarCalcularComisionesDepositosCuentaUnica(pNumeroCuenta)+ "\n";
        comisiones += llamarCalcularComisionesRetirosCuentaUnica(pNumeroCuenta) + "\n";
        comisiones += llamarcalcularComisionesTotalesCuentaUnica(pNumeroCuenta) + "\n";
        return comisiones;
      }
      else{
        return "La cuenta indicada no está registrada en el sistema y/o la cuenta está inactiva";
      }
    }
     //------------------------------- Métoodos comisiones universo cuentas------------------
    
    
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en depósitos,
   * para todas las cuentas.
   */      
   public static String llamarCalcularComisionesDepositosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesDepositosUniversoCuentas();
    }
   
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesRetirosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros,
   * para todas las cuentas.
   */    
   public static String llamarCalcularComisionesRetirosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesRetirosUniversoCuentas();
    }
        
  /**
   * <p> Método que recorre el resultado del método consultarTotalComisionesDepositosYRetirosUniversoCuentas
   * y lo retorna.
   * @return String que contiene el mensaje relacionado al total de ganancias producto de comisiones en retiros
   * y depósitos, para todas las cuentas.
   */    
   public static String llamarCalcularComisionesDepositosYRetirosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesDepositosYRetirosUniversoCuentas();
    }
       
   /**
   * <p> Método que concatena las consultas sobre el cálculo de comisiones para todas las cuentas
   * y lo retorna.
   * @return String que contiene el desglose del cobro de comisiones para todas las  cuentas,
   */    
   public static String calcularTodasLasComisionesUniversoCuentas(){
         String comisiones = "";
         comisiones += llamarCalcularComisionesDepositosUniversoCuentas() + "\n";
         comisiones += llamarCalcularComisionesRetirosUniversoCuentas() + "\n";
         comisiones += llamarCalcularComisionesDepositosYRetirosUniversoCuentas() + "\n";
         return comisiones;
       }
}
