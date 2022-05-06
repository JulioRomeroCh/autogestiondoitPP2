package autogestiondoitgroup;

import logicadeintegracion.*;
import logicadepresentacion.*;

public class AplicacionGUI {
    
  public static void main(String[] args) throws Exception{
    InterfazUsuario nuevaInterfazGUI = new InterfazUsuario();
    ControladorGUI nuevoControlador = new ControladorGUI(nuevaInterfazGUI);
    nuevaInterfazGUI.setVisible(true);
  }    
    
}
