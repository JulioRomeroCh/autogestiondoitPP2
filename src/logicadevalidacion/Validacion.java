package logicadevalidacion;

import logicadenegocios.TipoOperacion;
import logicadeintegracion.ControladorCuenta;
import logicadenegocios.Cuenta;


public class Validacion {
    
    public static boolean convertirNumeroABooleano(int pNumero){
      if(pNumero == 0){
        return false;    
      }   
      else{
        return true;    
      }
    }
    
    public static boolean esTransaccionConDinero(TipoOperacion pTipo){
      
        if(pTipo.name().equals("DEPOSITO") || pTipo.name().equals("RETIRO") || pTipo.name().equals("TRANSFERENCIA")){
          return true;    
        }
        else{
          return false;    
        }
    }
    
    public static void llamarEsTransaccionConDinero(TipoOperacion pTipo, String pNumeroCuenta){
      if(esTransaccionConDinero(pTipo)){
        Cuenta nuevaCuenta = ControladorCuenta.buscarCuenta(pNumeroCuenta);
        int numeroActualTransacciones = nuevaCuenta.getTransaccionesRealizadas();
        nuevaCuenta.modificarTransaccionesRealizadas(numeroActualTransacciones + 1);
      }  
    }
    
}
