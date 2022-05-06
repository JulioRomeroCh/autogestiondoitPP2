package logicadeintegracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logicadenegocios.*;
import logicadeaccesoadatos.*;
import static logicadeintegracion.ControladorCliente.buscarCliente;
import static logicadeintegracion.ControladorPersona.convertirTextoAFecha;
import logicadevalidacion.ExpresionRegular;


public class ControladorGUI implements ActionListener{

  //----------------------------ATRIBUTOS---------------------------------------  
  private static ArrayList<Persona> personas = new ArrayList<Persona>();
  private static ArrayList<Persona> clientes = new ArrayList<Persona>();
  private logicadepresentacion.InterfazUsuario vistaGUI;
    
  
  //-----------------------------CONTROLADOR---------------------------------------
  public ControladorGUI(logicadepresentacion.InterfazUsuario pVistaGUI){
    this.vistaGUI = pVistaGUI;
    //Botones
    this.vistaGUI.BotonRegistrarCliente.addActionListener(this);
    this.vistaGUI.BotonRegistrarUsuario.addActionListener(this);
    this.vistaGUI.BotonListarConsultarClientes.addActionListener(this);
    this.vistaGUI.BotonRegistrarCuenta.addActionListener(this);
    //Ítems del menú
    this.vistaGUI.ListarConsultarCliente.addActionListener(this);
    this.vistaGUI.MenuRegistrarCuenta.addActionListener(this);
  }  
   
  //-----------------------------LLAMADO DE MÉTODOS---------------------------------------
  public String llamarMetodoRegistrarClienteGUI(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
    String pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true && ExpresionRegular.validarFormatoCorreoElectronico(pCorreoElectronico) == true){
      Cliente nuevoCliente = new Cliente(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, logicadeintegracion.ControladorCliente.convertirTextoAFecha(pFechaNacimiento), pNumeroTelefonico, pCorreoElectronico);
      clientes.add(nuevoCliente);
      return nuevoCliente.registrarPersona();   
    }
    else{
      return "Error en el formato del correo y/o fecha";
    }
  }
  
  public static String llamarMetodoRegistrarUsuarioGUI(String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido, String pFechaNacimiento) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true){  
      Usuario nuevoUsuario = new Usuario(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, logicadeintegracion.ControladorPersona.convertirTextoAFecha(pFechaNacimiento));
      personas.add(nuevoUsuario);      
      return nuevoUsuario.registrarPersona(); 
    }
    else{
      return "Error en el formato de la fecha";
    }
  }
  
  public static String llamarMetodoRegistrarCuentaPersonaGUI(String pMonto, String pPin, String pIdentificacion){
   if(ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && ExpresionRegular.validarFormatoPin(pPin) == true){
     Cliente nuevoCliente = buscarCliente(pIdentificacion);
     String mensaje = nuevoCliente.registrarCuenta(Integer.parseInt(pMonto), pPin, pIdentificacion);
     ControladorCuenta.cuentas.add(nuevoCliente.cuentas.get(nuevoCliente.cuentas.size()-1));       
     return mensaje;         
   }
   else{
     return "El monto no es un número entero o el formato del PIN es erroneo";    
   }
 }
  
  public static Cliente buscarCliente(String pIdentificacion){
    Cliente cliente = new Cliente();
    for(int contador = 0; contador != clientes.size(); contador++){
      if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         cliente = (Cliente) clientes.get(contador);
      }  
    }
      return cliente;
  }
  
  public static String consultarDatosCliente(String pIdentificacion){
      
    for(int contador = 0; contador != clientes.size(); contador++){
       if(clientes.get(contador).getIdentificador().equals(pIdentificacion)){
         return clientes.get(contador).toString();
       }
    }
    return "La identificación ingresada no coincide con ninguno de los clientes registrados en el sistema.";    
  }

  
   //------------------------------ACCIÓN DE LOS BOTONES------------------------------------- 
    @Override
    public void actionPerformed(ActionEvent evento) {
        
      if(evento.getSource() == vistaGUI.BotonRegistrarCliente){
        try {
           if(ExpresionRegular.validarFormatoNumeroTelefonico(vistaGUI.RegistrarClienteNumero.getText()) == true){
             String fecha = String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getDate()) + "/0" + String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getDay()+1) + "/" + String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getYear()+1900); 
             JOptionPane.showMessageDialog(null,llamarMetodoRegistrarClienteGUI(vistaGUI.RegistrarClienteIdentificacion.getText(), vistaGUI.RegistrarClienteNombre.getText(), vistaGUI.RegistrarClientePrimerApellido.getText(), vistaGUI.RegistrarClienteSegundoApellido.getText(), fecha, vistaGUI.RegistrarClienteNumero.getText(), vistaGUI.RegistrarClienteCorreo.getText()));
             vistaGUI.RegistrarClienteIdentificacion.setText(""); vistaGUI.RegistrarClienteNombre.setText(""); vistaGUI.RegistrarClientePrimerApellido.setText(""); vistaGUI.RegistrarClienteSegundoApellido.setText(""); vistaGUI.RegistrarClienteCorreo.setText(""); vistaGUI.RegistrarClienteNumero.setText(""); vistaGUI.RegistrarClienteFechaNacimiento.setCalendar(null);
           }
           else{
             JOptionPane.showMessageDialog(null, "Por favor, ingrese un número telefónico de 8 dígitos", "Advertencia", JOptionPane.WARNING_MESSAGE);    
           }
        } 
        catch (ParseException ex) {
          Logger.getLogger(ControladorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      else if(evento.getSource() == vistaGUI.BotonRegistrarUsuario){
        try {
          String fecha = String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDate()) + "/0" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDay()+1) + "/" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getYear()+1900); 
          JOptionPane.showMessageDialog(null, llamarMetodoRegistrarUsuarioGUI(vistaGUI.RegistrarUsuarioIdentificacion.getText(), vistaGUI.RegistrarUsuarioNombre.getText(), vistaGUI.RegistrarUsuarioPrimerApellido.getText(), vistaGUI.RegistrarUsuarioSegundoApellido.getText(), fecha));
          vistaGUI.RegistrarUsuarioIdentificacion.setText(""); vistaGUI.RegistrarUsuarioNombre.setText(""); vistaGUI.RegistrarUsuarioPrimerApellido.setText(""); vistaGUI.RegistrarUsuarioSegundoApellido.setText(""); vistaGUI.RegistrarUsuarioFechaNacimiento.setCalendar(null);
        } 
        catch (ParseException ex) {
          Logger.getLogger(ControladorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      else if(evento.getSource() == vistaGUI.BotonListarConsultarClientes){
        String mensaje = vistaGUI.ComboListarConsultarClientes.getSelectedItem().toString();
        String [] partesMensaje = mensaje.split(" ");
        JOptionPane.showMessageDialog(null, consultarDatosCliente(partesMensaje[0]));
      } 
      
      else if(evento.getSource() == vistaGUI.BotonRegistrarCuenta){
        String mensaje = vistaGUI.ComboIdentificacionRegistrarCuenta.getSelectedItem().toString();
        String [] partesMensaje = mensaje.split(" ");
        System.out.println(vistaGUI.RegistrarCuentaMontoInicial.getText());
        System.out.println(vistaGUI.RegistrarCuentaPIN.getText());
        System.out.println((partesMensaje[0]));
        JOptionPane.showMessageDialog(null, llamarMetodoRegistrarCuentaPersonaGUI(vistaGUI.RegistrarCuentaMontoInicial.getText(), vistaGUI.RegistrarCuentaPIN.getText(), partesMensaje[0]));
        vistaGUI.RegistrarCuentaMontoInicial.setText(""); vistaGUI.RegistrarCuentaPIN.setText("");
      } 
      
      else if(evento.getSource() == vistaGUI.ListarConsultarCliente){
        ClienteDao.ConsultaListarClientesTabla(vistaGUI.TablaListarConsultarCliente);
        ClienteDao.ConsultaListarClientes(vistaGUI.ComboListarConsultarClientes);
      } 
      
      else if(evento.getSource() == vistaGUI.MenuRegistrarCuenta){
        ClienteDao.ConsultaListarClientes(vistaGUI.ComboIdentificacionRegistrarCuenta);
      } 
      

      

        
    }
    
    
    
}
