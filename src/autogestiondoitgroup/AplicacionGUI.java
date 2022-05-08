package autogestiondoitgroup;

import java.text.ParseException;
import logicadeaccesoadatos.ClienteDao;
import logicadeaccesoadatos.CuentaDao;
import logicadeaccesoadatos.OperacionDao;
import logicadeaccesoadatos.PersonaDao;
import logicadeintegracion.*;
import logicadepresentacion.*;

public class AplicacionGUI {
    
  public static void main(String[] args) throws Exception{
    cargarBaseDatos();
    InterfazUsuario nuevaInterfazGUI = new InterfazUsuario();
    ControladorInterfazUsuario nuevoControlador = new ControladorInterfazUsuario(nuevaInterfazGUI);
    nuevaInterfazGUI.setVisible(true);
    
  } 
  
  public static void cargarBaseDatos() throws ParseException{
      PersonaDao.recorrerCargarPersona();
      ClienteDao.recorrerCargarCliente();
      CuentaDao.recorrerCargarCuenta();       
      PersonaDao.recorrerCargarPersonaTieneCuenta();
      OperacionDao.recorrerCargarOperacion();
  }
    
}
