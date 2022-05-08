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
  
  public static void iniciar(){}
  

  public static Cuenta buscarCuenta(String pNumeroCuenta){
    Cuenta cuenta = new Cuenta();
    for(int contador = 0; contador != cuentas.size(); contador++){
      if(cuentas.get(contador).getNumeroCuenta().equals(pNumeroCuenta)){
         cuenta = cuentas.get(contador);
      }  
    }
      return cuenta;
  }

  
 public static String consultarDatosCuentaCliente(String pNumeroCuenta){
   return CuentaDao.recorrerconsultarClienteCuenta(pNumeroCuenta);
 }
 
 
 public static String llamarCambiarPinCLI(String pNumeroCuenta, String pPinActual, String pNuevoPin){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if(CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPinActual) == true){
    return nuevaCuenta.cambiarPin(pNumeroCuenta, pPinActual, pNuevoPin);

   }
   return "Error al cambiar PIN";
 }
 
 public static String llamarBloquearCuenta(String pMotivo, String pNumeroCuenta) throws MessagingException{
   if(CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.bloquearCuenta(pMotivo, ClienteDao.recorrerConsultarCorreoClientePorCuenta(pNumeroCuenta));
   }
   else{
     return "La cuenta indicada no está registrada en el sistema";    
   }
 }
 
 public static String llamarConsultarCuentaParticular(String pNumeroCuenta){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   String mensaje = "Número de cuenta: " + nuevaCuenta.getNumeroCuenta()+ "\n";
   mensaje += "Estatus: " + nuevaCuenta.getEstatus() + "\n";
   mensaje += "Saldo: " + nuevaCuenta.getSaldo() + "\n";
   mensaje += CuentaDao.recorrerconsultarClienteCuenta(pNumeroCuenta) + "\n";
   
   return mensaje;
 }
 
 public static String llamarDepositarColones(String pNumeroCuenta, String pMonto){
     System.out.println("Monto: " +  pMonto);
   if (ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) == true){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.depositarColones(pNumeroCuenta, Integer.parseInt(pMonto));
   }
   else{
     return "El monto no es un número entero y/o la cuenta no existe";
   }
 }
 
 public static String llamarDepositarDolares(String pNumeroCuenta, String pMonto){
   if (ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) == true){
     Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
     return nuevaCuenta.depositarDolares(new TipoCambio(), pNumeroCuenta, Integer.parseInt(pMonto));
   }
   else{
     return "El monto no es un número entero y/o la cuenta no existe";
   }
 }
 
 public static String llamarRetirarColones(String pNumeroCuenta,String pPin, String pMonto){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarSuficienciaDeFondos(pNumeroCuenta, Double.parseDouble(pMonto))){
     return nuevaCuenta.retirarColones(pNumeroCuenta, pPin, Integer.parseInt(pMonto));
   }
   else{
     return "Error al retirar dinero en colones";
   }
 }
 
 public static String llamarRetirarDolares(String pNumeroCuenta,String pPin, String pMonto){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarSuficienciaDeFondos(pNumeroCuenta, Double.parseDouble(pMonto))){
     return nuevaCuenta.retirarDolares(new TipoCambio(), pNumeroCuenta, pPin, Integer.parseInt(pMonto));
   }
   else{
     return "Error al retirar dinero en dólares";
   }
 }
 
 public static String llamarTransferirFondos(String pNumeroCuentaOrigen,String pPin, String pMonto, String pNumeroCuentaDestino){
   Cuenta cuentaOrigen = buscarCuenta(pNumeroCuentaOrigen);
   Cuenta cuentaDestino = buscarCuenta(pNumeroCuentaDestino);
   if (CuentaDao.verificarExistenciaCuenta(pNumeroCuentaOrigen) && CuentaDao.verificarCorrectitudPin(pNumeroCuentaOrigen, pPin) && ExpresionRegular.validarNumerosEnterosPositivos(pMonto) && CuentaDao.verificarExistenciaCuenta(pNumeroCuentaDestino)&& CuentaDao.verificarSuficienciaDeFondos(pNumeroCuentaOrigen, Double.parseDouble(pMonto))){
         
     return cuentaOrigen.transferirFondos(pNumeroCuentaOrigen, pPin, Integer.parseInt(pMonto), cuentaDestino);
   }
   else{
     return "Error al transferir fondos";
   }
 }
 
 
  
 public static String listarCuentas(){
   String mensaje = "";
   Ordenacion ordenCuentas = new Ordenacion();
   Cuenta[] arregloCuentas = convertirListaCuentasEnArreglo();
   ordenCuentas.ordenamientoInsercion(arregloCuentas);
   for (int contador = 0; contador != arregloCuentas.length; contador++){ 
     mensaje += arregloCuentas[contador].getNumeroCuenta() + "\n";
     mensaje += arregloCuentas[contador].getEstatus() + "\n";
     mensaje += arregloCuentas[contador].getSaldo() + "\n";
     mensaje += CuentaDao.recorrerconsultarClienteCuenta(arregloCuentas[contador].getNumeroCuenta()) + "\n";
   }
   return mensaje;
  }
  
  public static Cuenta[] convertirListaCuentasEnArreglo(){
    Cuenta [] arregloCuentas = new Cuenta[cuentas.size()];
    for(int contador = 0; contador != cuentas.size(); contador++){
      arregloCuentas[contador] = cuentas.get(contador);
    }
    return arregloCuentas;
  }
  
  public static String consultarCompraDolar(){
    TipoCambio compra = new TipoCambio();
    return String.valueOf(compra.consultarCompraDolar());
  }
  
  public static String consultarVentaDolar(){
    TipoCambio venta = new TipoCambio();
    return String.valueOf(venta.consultarVentaDolar());
  }
  
  public static String llamarConsultarSaldoColones(String pNumeroCuenta, String pPin){
    if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      return nuevaCuenta.consultarSaldoColones(pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin";
    }
  }
  
   public static String llamarConsultarSaldoDolares(String pNumeroCuenta, String pPin){
    if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      return nuevaCuenta.consultarSaldoDolares(new TipoCambio(), pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin";
    }
  }
   
   public static String llamarGenerarEstadoCuentaColones (String pNumeroCuenta, String pPin){
     
       if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)){
         Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
         return nuevaCuenta.generarEstadoCuentaColones(pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin";
    }
   }
    
    public static String llamarGenerarEstadoCuentaDolares (String pNumeroCuenta, String pPin){
     
       if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta) && CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin)){
         Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
         return nuevaCuenta.generarEstadoCuentaDolares(new TipoCambio(), pNumeroCuenta, pPin);
    }
    else{
      return "Error en el número de cuenta y/o en el número de pin";
    }
   }
    
    public static String llamarConsultarEstatus(String pNumeroCuenta){
      Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        return nuevaCuenta.consultarEstatus(pNumeroCuenta);
      }
      else{
        return "La cuenta no está registrada en el sistema";
      }
    }
    
    public static String llamarCalcularComisionesDepositosCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesDepositosCuentaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }
    
     public static String llamarCalcularComisionesRetirosCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesRetirosCuentaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }
     
     public static String llamarcalcularComisionesTotalesCuantaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
        return nuevaCuenta.calcularComisionesTotalesCuantaUnica(pNumeroCuenta);
      }
      return "La cuenta no está registrada en el sistema";
    }
     
    public static String calcularTodasLasComisionesCuentaUnica(String pNumeroCuenta){
      if (CuentaDao.verificarExistenciaCuenta(pNumeroCuenta)){
        String comisiones = "";
        comisiones += llamarCalcularComisionesDepositosCuentaUnica(pNumeroCuenta)+ "\n";
        comisiones += llamarCalcularComisionesRetirosCuentaUnica(pNumeroCuenta) + "\n";
        comisiones += llamarcalcularComisionesTotalesCuantaUnica(pNumeroCuenta) + "\n";
        return comisiones;
      }
      else{
        return "La cuenta indicada no está registrada en el sistema";
      }
    }
     //------------------------------- Métoodos comisiones universo cuentas------------------
      public static String llamarCalcularComisionesDepositosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesDepositosUniversoCuentas();
    }
      
        public static String llamarCalcularComisionesRetirosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesRetirosUniversoCuentas();
    }
        
       public static String llamarCalcularComisionesDepositosYRetirosUniversoCuentas(){
        return CuentaDao.recorrerConsultaTotalComisionesDepositosYRetirosUniversoCuentas();
    }
       
       public static String calcularTodasLasComisionesUniversoCuentas(){
         String comisiones = "";
         comisiones += llamarCalcularComisionesDepositosUniversoCuentas() + "\n";
         comisiones += llamarCalcularComisionesRetirosUniversoCuentas() + "\n";
         comisiones += llamarCalcularComisionesDepositosYRetirosUniversoCuentas() + "\n";
         return comisiones;
       }
}
