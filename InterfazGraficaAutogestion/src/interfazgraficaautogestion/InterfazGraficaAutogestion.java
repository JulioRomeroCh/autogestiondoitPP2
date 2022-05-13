package interfazgraficaautogestion;

import java.text.ParseException;
import logicadeaccesoadatos.ClienteDao;
import logicadeaccesoadatos.CuentaDao;
import logicadeaccesoadatos.OperacionDao;
import logicadeaccesoadatos.PersonaDao;
import logicadepresentacion.*;

public class InterfazGraficaAutogestion {
    
  public static void main(String[] args) throws Exception{
    cargarBaseDatos();
    InterfazUsuario nuevaInterfazGUI = new InterfazUsuario();
    logicadeintegracionGUI.ControladorInterfazUsuario nuevoControlador = new logicadeintegracionGUI.ControladorInterfazUsuario(nuevaInterfazGUI);
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