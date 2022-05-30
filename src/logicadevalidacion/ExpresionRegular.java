
package logicadevalidacion;

public class ExpresionRegular {
  /**
   * <p> Método que valida el formato de correo electrónico, usando expresiones Regulares.
   * @param pCorreoElectronico: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */ 
  public static boolean validarFormatoCorreoElectronico(String pCorreoElectronico){
  
    return pCorreoElectronico.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
  }
  
  /**
   * <p> Método que valida el formato del número telefónico costarricense, usando expresiones Regulares.
   * @param pTelefono: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */
  public static boolean validarFormatoNumeroTelefonico(String pTelefono){
  
    return pTelefono.matches("^[0-9]{8}$");
  }
  /**
   * Método que valida el formato del pin, usando expresiones Regulares. El pin debe contener: 
   * seis caracteres, una letra mayúscula, una letra minúscula y un carácter especial.
   * @param pPin: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */
  public static boolean validarFormatoPin(String pPin){
   
    return pPin.matches("^(?=.{6}$)(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
  }
  /**
   * <p> Método que valida el formato de fecha. El formato que debe cumplir es: DD/MM/AAAA
   * @param pFecha: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */
  public static boolean validarFormatoFecha(String pFecha){
  
    return pFecha.matches("([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})");    
  }
  /**
   * <p> Método que valida si una cadena se compone únicamente de números enteros positivos,
   * @param pTexto: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */
  public static boolean validarNumerosEnterosPositivos(String pTexto){
  
    return pTexto.matches("^[0-9]+$");
  }
  /**
   * <p> Método que valida si una cadena se compone sólo de letras.
   * @param pTexto: String el cual se valida el formato.
   * @return boolean que indica si el parámetro cumple el formato solicitado.
   */
  public static boolean validarStringConSoloLetras(String pTexto){
    return pTexto.matches("^[A-Za-z]+$");
  }
}
