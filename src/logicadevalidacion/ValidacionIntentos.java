package logicadevalidacion;

import logicadeaccesoadatos.CuentaDao;
import logicadeconexionexterna.MensajeTexto;

public class ValidacionIntentos {
   

  /**
   * Método que controla la cantidad de intentos de pin incorrecto.
   * @param pPin: String que ingresa el usuario.
   * @param pNumeroCuenta: String que representa la cuenta a la cual pertenece el Pin.
   * @return boolean: indica si el pin ingresado pór el usuario corresponde al pin de la cuenta.
   */  
  public static boolean validarCantidadIntentosPin(String pPin, String pNumeroCuenta){

      if (CuentaDao.verificarCorrectitudPin(pNumeroCuenta, pPin) == false){
        return false; 
      }
      else{
          return true;
      }
    
    
  }
  
  /**
   * Método que controla la cantidad de intentos de palabra secreta incorrecta.
   * @param pPalabra: String que ingresa el usuario.
   * @param pMensaje: Objeto de tipo MensajeTexto.
   * @return boolean: indica si la palabra secreta ingresada pór el usuario corresponde a
   * la palabra secreta almacenada en pMensaje.
   */   
  public static boolean validarCantidadIntentosPalabraSecreta (String pPalabra, MensajeTexto pMensaje){
   if (pMensaje.getPalabraSecreta().equals(pPalabra)){
     return true;
   }
   else{
     return false;
  }
  }
  
    
}
