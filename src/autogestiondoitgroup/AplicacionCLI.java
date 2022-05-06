package autogestiondoitgroup;

import java.text.ParseException;
import javax.mail.MessagingException;
import logicadepresentacion.*;
import logicadenegocios.*;

public class AplicacionCLI {

  public static void main(String[] args) throws ParseException, MessagingException, Exception {
      
    InterfazComandos nuevaInterfaz = new InterfazComandos();
    nuevaInterfaz.ejecutarMenuPrincipal();
    
            
  }
}
