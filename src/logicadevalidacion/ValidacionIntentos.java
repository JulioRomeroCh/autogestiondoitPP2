package logicadevalidacion;

import logicadeaccesoadatos.CuentaDao;

public class ValidacionIntentos {
   

    
  public static boolean validarCantidadIntentosPin(String pPin, String pNumeroCuenta){

    CuentaDao nuevoDaoCuenta = new CuentaDao();
      if (nuevoDaoCuenta.verificarCorrectitudPin(pNumeroCuenta, pPin) == false){
        return false; 
      }
      else{
          return true;
      }
    
    
  }   
    
}
