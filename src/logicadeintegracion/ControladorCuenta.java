package logicadeintegracion;

import java.util.ArrayList;
import javax.mail.MessagingException;
import logicadeaccesoadatos.CuentaDao;
import logicadenegocios.*;
import logicadeintegracion.*;
import logicadepresentacion.InterfazComandos;
import logicadevalidacion.ExpresionRegular;
import logicadevalidacion.ValidacionIntentos;
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
 
 public static String llamarBloquearCuenta(String pNumeroCuenta) throws MessagingException{
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   return nuevaCuenta.bloquearCuenta("Dos intentos consecutivos fallidos de PIN", CuentaDao.recorrerConsultarCorreoClientePorCuenta(pNumeroCuenta));
 
 }
 
 public static String llamarConsultarCuentaParticular(String pNumeroCuenta){
   Cuenta nuevaCuenta = buscarCuenta(pNumeroCuenta);
   String mensaje = nuevaCuenta.getNumeroCuenta()+ "\n";
   mensaje += nuevaCuenta.getEstatus() + "\n";
   mensaje += nuevaCuenta.getSaldo() + "\n";
   mensaje += CuentaDao.recorrerconsultarClienteCuenta(pNumeroCuenta) + "\n";
   
   return mensaje;
 }
 
 public static String llamarDepositarColones(String pNumeroCuenta, String pMonto){
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
    
}
