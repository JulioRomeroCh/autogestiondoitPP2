package logicadevalidacion;

import logicadeaccesoadatos.CuentaDao;
import logicadeconexionexterna.MensajeTexto;

public class ValidacionIntentos {
   

    
  public static boolean validarCantidadIntentosPin(String pPin, String pNumeroCuenta){

      if (CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) == false){
        return false; 
      }
      else{
          return true;
      }
    
    
  }
  
  public static boolean validarCantidadIntentosPalabraSecreta (String pPalabra, MensajeTexto pMensaje){
   if (pMensaje.getPalabraSecreta().equals(pPalabra)){
     return true;
   }
   else{
     return false;
  }
  }
  
    
}
