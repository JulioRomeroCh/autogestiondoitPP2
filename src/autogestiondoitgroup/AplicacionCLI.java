package autogestiondoitgroup;

import java.text.ParseException;
import javax.mail.MessagingException;
import logicadeaccesoadatos.ClienteDao;
import logicadeaccesoadatos.CuentaDao;
import logicadeaccesoadatos.OperacionDao;
import logicadeaccesoadatos.PersonaDao;
import logicadepresentacion.*;

public class AplicacionCLI {
  /**
   * <p> Método principal, encargado de ejecutar la aplicación.
   * @param args: Arreglo de String, contiene los argumentos necesarios para ejecutar la aplicación.
   * @throws ParseException: Excepción en caso de que los datos enviados no puedan ser convertidos a un
   * tipo de dato en específico.
   * @throws MessagingException: Excepción lanzada en caso de error en el momento de enviar un correo electrónico.
   * @throws Exception: Excepción lanzada de forma genérica.
   */
  public static void main(String[] args) throws ParseException, MessagingException, Exception {
    cargarBaseDatos(); 
    InterfazComandos nuevaInterfaz = new InterfazComandos();
    nuevaInterfaz.ejecutarMenuPrincipal();               
  }
  /**
   * <p> Método encargado de cargar la base de datos y convertir la información en objetos.
   * @throws ParseException: Excepción en caso de que los datos enviados no puedan ser convertidos a un
   * tipo de dato en específico.
   */
  public static void cargarBaseDatos() throws ParseException{
    PersonaDao.recorrerCargarPersona();
    ClienteDao.recorrerCargarCliente();
    CuentaDao.recorrerCargarCuenta();       
    PersonaDao.recorrerCargarPersonaTieneCuenta();
    OperacionDao.recorrerCargarOperacion();
  }
  
}
