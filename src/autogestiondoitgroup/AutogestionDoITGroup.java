package autogestiondoitgroup;

import java.util.Date;

import logicadeconexionexterna.*;
import logicadeaccesoadatos.*;
import logicadenegocios.*;
import logicadevalidacion.ExpresionRegular;

public class AutogestionDoITGroup {

    public static void main(String[] args) {
      Conexion conexion = new Conexion();
      conexion.conectar();
        System.out.println(ExpresionRegular.validarFormatoNumeroTelefonico("85184388"));
    }
    
}
