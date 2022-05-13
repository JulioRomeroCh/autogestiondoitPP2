package autogestiondoitgroup;

import java.text.ParseException;
import javax.mail.MessagingException;
import logicadeaccesoadatos.ClienteDao;
import logicadeaccesoadatos.CuentaDao;
import logicadeaccesoadatos.OperacionDao;
import logicadeaccesoadatos.PersonaDao;
import logicadepresentacion.*;

public class AplicacionCLI {

  public static void main(String[] args) throws ParseException, MessagingException, Exception {
    cargarBaseDatos(); 
    InterfazComandos nuevaInterfaz = new InterfazComandos();
    nuevaInterfaz.ejecutarMenuPrincipal();               
  }
  
  public static void cargarBaseDatos() throws ParseException{
    PersonaDao.recorrerCargarPersona();
    ClienteDao.recorrerCargarCliente();
    CuentaDao.recorrerCargarCuenta();       
    PersonaDao.recorrerCargarPersonaTieneCuenta();
    OperacionDao.recorrerCargarOperacion();
  }
  
}
