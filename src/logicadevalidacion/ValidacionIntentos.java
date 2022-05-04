package logicadevalidacion;

import logicadeaccesoadatos.CuentaDao;

public class ValidacionIntentos {
   

    
  public static boolean validarCantidadIntentosPin(String pPin, String pNumeroCuenta){

      if (CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) == false){
        return false; 
      }
      else{
          return true;
      }
    
    
  }   
    
}
