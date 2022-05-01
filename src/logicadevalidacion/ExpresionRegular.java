
package logicadevalidacion;

public class ExpresionRegular {
 
  public static boolean validarFormatoCorreoElectronico(String pCorreoElectronico){
  
    return pCorreoElectronico.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
  }
  
  public static boolean validarFormatoNumeroTelefonico(String pTelefono){
  
    return pTelefono.matches("^[0-9]{8}$");
  }
  
  public static boolean validarFormatoPin(String pPin){
   
    return pPin.matches("^(?=.{6}$)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
  }
  
  public static boolean validarFormatoFecha(String pFecha){
  
    return pFecha.matches("([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})");    
  }
  
  public static boolean validarNumerosEnterosPositivos(String pTexto){
  
    return pTexto.matches("^[0-9]+$");
  }
  
  public static boolean validarStringConSoloLetras(String pTexto){
    return pTexto.matches("^[A-Za-z]+$");
    
  }
}
