package logicadevalidacion;

import logicadenegocios.TipoOperacion;
import logicadeintegracion.ControladorCuenta;
import logicadenegocios.Cuenta;


public class Validacion {
  /**
   * Método que convierte una representación binaria en booleano.
   * @param pNumero: int que se compone de 1 o 0.
   * @return boolean: retorna true en caso de que pNumero sea 1, false en caso contrario.
   */  
  public static boolean convertirNumeroABooleano(int pNumero){
    if(pNumero == 0){
      return false;    
    }   
    else{
      return true;    
    }
  }
  
  /** <p> Método que verifia si una operación requiere un movimiento de dinero.
   * @param pTipo: TipoOperacion.
   * @return boolean que indica si una operación requiere un movimiento de dinero.
   */  
  public static boolean esTransaccionConDinero(TipoOperacion pTipo){  
    if(pTipo.name().equals("DEPOSITO") || pTipo.name().equals("RETIRO") || pTipo.name().equals("TRANSFERENCIA")){
      return true;    
    }
    else{
      return false;    
    }
  }
  
  /**
   * <p> Método que agrega una transacción realizada a una cuenta, en caso de que la operación requiera
   * un movimiento de dinero.
   * @param pTipo: TipoOperacion.
   * @param pNumeroCuenta: String que representa la cuenta que representa la operación.
   */  
  public static void llamarEsTransaccionConDinero(TipoOperacion pTipo, String pNumeroCuenta){
    if(esTransaccionConDinero(pTipo)){
      Cuenta nuevaCuenta = ControladorCuenta.buscarCuenta(pNumeroCuenta);
      int numeroActualTransacciones = nuevaCuenta.getTransaccionesRealizadas();
      nuevaCuenta.modificarTransaccionesRealizadas(numeroActualTransacciones + 1);
    }  
  }  
}
