package logicadeintegracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.sql.*;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logicadenegocios.*;
import logicadeaccesoadatos.*;
import logicadeconexionexterna.MensajeTexto;
import logicadevalidacion.*;

public class ControladorInterfazUsuario implements ActionListener {
  
  //-------------------------ATRIBUTOS-------------------------
  private logicadepresentacion.InterfazUsuario vistaGUI;
  
  //-------------------------CONSTRUCTOR-------------------------
  public ControladorInterfazUsuario(logicadepresentacion.InterfazUsuario pVistaGUI){
    //Vista
    this.vistaGUI = pVistaGUI; 
    
    //Botones
    this.vistaGUI.BotonRegistrarCliente.addActionListener(this);
    this.vistaGUI.BotonRegistrarUsuario.addActionListener(this);
    this.vistaGUI.BotonListarConsultarClientes.addActionListener(this);
    this.vistaGUI.BotonRegistrarCuenta.addActionListener(this);
    this.vistaGUI.BotonDepositar.addActionListener(this);
    this.vistaGUI.BotonConsultarTipoCambio.addActionListener(this);
    this.vistaGUI.BotonListarConsultarCuentas.addActionListener(this);
    this.vistaGUI.BotonValidarPinConsultarSaldo.addActionListener(this);
    this.vistaGUI.BotonConsultarEstatus.addActionListener(this);
    this.vistaGUI.BotonGanancias.addActionListener(this);
    this.vistaGUI.BotonValidarEstadoCuenta.addActionListener(this);
    this.vistaGUI.BotonCambiarPINValidar.addActionListener(this);
    this.vistaGUI.BotonCambiarPINCambiar.addActionListener(this);
    this.vistaGUI.BotonRetiroValidarPIN.addActionListener(this);
    this.vistaGUI.BotonRetiroValidarPalabra.addActionListener(this);
    this.vistaGUI.BotonRetiro.addActionListener(this);
    this.vistaGUI.BotonTransferenciaValidarPIN.addActionListener(this);
    this.vistaGUI.BotonTransferenciaValidarPalabra.addActionListener(this);
    this.vistaGUI.BotonTransferencia.addActionListener(this);
    this.vistaGUI.BotonRegresarEstadoCuenta.addActionListener(this);
    
    //Botones de regreso
    this.vistaGUI.BotonRegresarRegistrarCliente.addActionListener(this);
    this.vistaGUI.BotonRegresarRegistrarUsuario.addActionListener(this);
    this.vistaGUI.BotonRegresarRegistrarCuenta.addActionListener(this);
    this.vistaGUI.BotonRegresarCambiarPIN.addActionListener(this);
    this.vistaGUI.BotonRegresarConsultarEstatus.addActionListener(this);
    this.vistaGUI.BotonRegresarComisiones.addActionListener(this);
    this.vistaGUI.BotonRegresarTipoCambio.addActionListener(this);
    this.vistaGUI.BotonRegresarConsultarSaldo.addActionListener(this);
    this.vistaGUI.BotonRegresarTransferencia.addActionListener(this);
    this.vistaGUI.BotonRegresarDeposito.addActionListener(this);
    this.vistaGUI.BotonRegresarRetiro.addActionListener(this);
    
    //Ítems del menú
    this.vistaGUI.ListarConsultarCliente.addActionListener(this);
    this.vistaGUI.MenuListarConsultarCuentas.addActionListener(this);
    this.vistaGUI.MenuRegistrarCuenta.addActionListener(this);
    
  }
  
  //-------------------------LLAMADO DE MÉTODOS-------------------------
  public String llamarMetodoRegistrarCliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
    String pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true && ExpresionRegular.validarFormatoCorreoElectronico(pCorreoElectronico) == true && ExpresionRegular.validarFormatoNumeroTelefonico(pNumeroTelefonico) == true){
      Cliente nuevoCliente = new Cliente(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, logicadeintegracion.ControladorCliente.convertirTextoAFecha(pFechaNacimiento), pNumeroTelefonico, pCorreoElectronico);
      ControladorCliente.clientes.add(nuevoCliente);
      return nuevoCliente.registrarPersona();   
    }
    else{
      return "Error en el formato del correo y/o fecha";
    }
  }
  
  public static String llamarMetodoRegistrarCuentaPersonaGUI(String pMonto, String pPin, String pIdentificacion){
    if(ExpresionRegular.validarNumerosEnterosPositivos(pMonto) == true && ExpresionRegular.validarFormatoPin(pPin) == true){
      Cliente nuevoCliente = ControladorCliente.buscarCliente(pIdentificacion);
      String mensaje = nuevoCliente.registrarCuenta(Integer.parseInt(pMonto), pPin, pIdentificacion);
      ControladorCuenta.cuentas.add(nuevoCliente.cuentas.get(nuevoCliente.cuentas.size()-1));       
      return mensaje;         
   }
   else{
     return "El monto no es un número entero o el formato del PIN es erroneo";    
   }
 }
  
   public static void recorrerResultadoConsultaListarCuentas(JComboBox pComboBox){
     pComboBox.removeAllItems();
     try{
        ResultSet resultado = CuentaDao.ConsultaListarCuentas();
        while(resultado.next()){
          String mensaje = String.valueOf(resultado.getObject(1)) + " - " + String.valueOf(resultado.getObject(2)) + " " +String.valueOf(resultado.getObject(3));
          pComboBox.addItem(mensaje);
        }    
     }   
     catch(Exception error){
       error.printStackTrace();
     }
   }
   
      public static void recorrerResultadoConsultaListarClientes(JComboBox pComboBox){
     pComboBox.removeAllItems();
     try{
        ResultSet resultado = ClienteDao.ConsultaListarClientes();
        while(resultado.next()){
          String mensaje = String.valueOf(resultado.getObject(1)) + " - " + String.valueOf(resultado.getObject(2)) + " " +String.valueOf(resultado.getObject(3));
          pComboBox.addItem(mensaje);
        }    
     }   
     catch(Exception error){
       error.printStackTrace();
     }
   }
   
     public static void recorrerResultadoConsultaListarCuentas(JTable pTabla){;
     try{
         DefaultTableModel modelo = (DefaultTableModel) pTabla.getModel();
         modelo.setRowCount(0);
         ResultSetMetaData datosResultado;
         int cantidadColumnas = 0;
        ResultSet resultado = CuentaDao.ConsultaListarCuentasTabla();
        datosResultado = resultado.getMetaData();
        cantidadColumnas = datosResultado.getColumnCount();
        while(resultado.next()){
          Object [] fila = new Object[cantidadColumnas];
          for(int indice = 0; indice<cantidadColumnas; indice++){
            fila[indice] = resultado.getObject(indice + 1);
          }
          modelo.addRow(fila);
        }  
     }   
     catch(Exception error){
       error.printStackTrace();
     }
   }
     
   public static void recorrerResultadoConsultaListarClientes(JTable pTabla){;
     try{
         DefaultTableModel modelo = (DefaultTableModel) pTabla.getModel();
         modelo.setRowCount(0);
         ResultSetMetaData datosResultado;
         int cantidadColumnas = 0;
        ResultSet resultado = ClienteDao.ConsultaListarClientesTabla();
        datosResultado = resultado.getMetaData();
        cantidadColumnas = datosResultado.getColumnCount();
        while(resultado.next()){
          Object [] fila = new Object[cantidadColumnas];
          for(int indice = 0; indice<cantidadColumnas; indice++){
            fila[indice] = resultado.getObject(indice + 1);
          }
          modelo.addRow(fila);
        }  
     }   
     catch(Exception error){
       error.printStackTrace();
     }
   }
  
  
  
  public int contadorIntentosPIN = 1;
  public int contadorIntentosPalabra = 1;
  

  MensajeTexto mensaje = new MensajeTexto();

  @Override
  public void actionPerformed(ActionEvent evento) {//Inicio  método actionPerformed
  
    if(evento.getSource() == vistaGUI.BotonRegistrarCliente){
      
      if(vistaGUI.RegistrarClientePrimerApellido.getText().equals("") || vistaGUI.RegistrarClienteSegundoApellido.getText().equals("") || vistaGUI.RegistrarClienteNombre.getText().equals("") || vistaGUI.RegistrarClienteIdentificacion.getText().equals("") || vistaGUI.RegistrarClienteNumero.getText().equals("") || vistaGUI.RegistrarClienteCorreo.getText().equals("") || vistaGUI.RegistrarClienteFechaNacimiento.getDate() == null){
        JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);    
      }
      else{
      
       try {
        if(ExpresionRegular.validarFormatoNumeroTelefonico(vistaGUI.RegistrarClienteNumero.getText()) == true){
          String fecha = String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getDate()) + "/0" + String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getDay()+1) + "/" + String.valueOf(vistaGUI.RegistrarClienteFechaNacimiento.getDate().getYear()+1900); 
          JOptionPane.showMessageDialog(null,llamarMetodoRegistrarCliente(vistaGUI.RegistrarClienteIdentificacion.getText(), vistaGUI.RegistrarClienteNombre.getText(), vistaGUI.RegistrarClientePrimerApellido.getText(), vistaGUI.RegistrarClienteSegundoApellido.getText(), fecha, vistaGUI.RegistrarClienteNumero.getText(), vistaGUI.RegistrarClienteCorreo.getText()));
          vistaGUI.RegistrarClienteIdentificacion.setText(""); vistaGUI.RegistrarClienteNombre.setText(""); vistaGUI.RegistrarClientePrimerApellido.setText(""); vistaGUI.RegistrarClienteSegundoApellido.setText(""); vistaGUI.RegistrarClienteCorreo.setText(""); vistaGUI.RegistrarClienteNumero.setText(""); vistaGUI.RegistrarClienteFechaNacimiento.setCalendar(null);
        }
        else{
          JOptionPane.showMessageDialog(null, "Por favor, ingrese un número telefónico de 8 dígitos", "Advertencia", JOptionPane.WARNING_MESSAGE);    
        }
      } 
      catch (ParseException ex) {
        Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
      }    
          
      }
        

    } 
    
    else if(evento.getSource() == vistaGUI.BotonRegistrarUsuario){
      if(vistaGUI.RegistrarUsuarioIdentificacion.getText().equals("") || vistaGUI.RegistrarUsuarioNombre.getText().equals("") || vistaGUI.RegistrarUsuarioPrimerApellido.getText().equals("") || vistaGUI.RegistrarUsuarioSegundoApellido.getText().equals("") || vistaGUI.RegistrarUsuarioFechaNacimiento.getDate() == null){
        JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);    
      }
      else{
        try {
        String fecha = String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDate()) + "/0" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDay()+1) + "/" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getYear()+1900); 
        JOptionPane.showMessageDialog(null, ControladorPersona.llamarMetodoRegistrarUsuarioCLI(vistaGUI.RegistrarUsuarioIdentificacion.getText(), vistaGUI.RegistrarUsuarioNombre.getText(), vistaGUI.RegistrarUsuarioPrimerApellido.getText(), vistaGUI.RegistrarUsuarioSegundoApellido.getText(), fecha));
        vistaGUI.RegistrarUsuarioIdentificacion.setText(""); vistaGUI.RegistrarUsuarioNombre.setText(""); vistaGUI.RegistrarUsuarioPrimerApellido.setText(""); vistaGUI.RegistrarUsuarioSegundoApellido.setText(""); vistaGUI.RegistrarUsuarioFechaNacimiento.setCalendar(null);
      } 
      catch (ParseException ex) {
        Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
      }    
      }

    }
    
    else if(evento.getSource() == vistaGUI.BotonRegistrarCuenta){
      if(vistaGUI.RegistrarCuentaMontoInicial.getText().equals("") || vistaGUI.RegistrarCuentaPIN.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);    
      }
      else{
        String mensaje = vistaGUI.ComboIdentificacionRegistrarCuenta.getSelectedItem().toString();
        String [] partesMensaje = mensaje.split(" ");
        JOptionPane.showMessageDialog(null, llamarMetodoRegistrarCuentaPersonaGUI(vistaGUI.RegistrarCuentaMontoInicial.getText(), vistaGUI.RegistrarCuentaPIN.getText(), partesMensaje[0]));
        vistaGUI.RegistrarCuentaMontoInicial.setText(""); vistaGUI.RegistrarCuentaPIN.setText("");    
      }

    }  
    
    else if(evento.getSource() == vistaGUI.BotonListarConsultarClientes){
      String mensaje = vistaGUI.ComboListarConsultarClientes.getSelectedItem().toString();
      String [] partesMensaje = mensaje.split(" ");
      JOptionPane.showMessageDialog(null, ControladorCliente.consultarDatosCliente(partesMensaje[0]));
    } 
            
    else if(evento.getSource() == vistaGUI.BotonListarConsultarCuentas){
      String mensaje = vistaGUI.ComboListarConsultaCuentas.getSelectedItem().toString();
      String [] partesMensaje = mensaje.split(" ");
      JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarCuentaParticular(partesMensaje[0]));
      
    }
    
    else if(evento.getSource() == vistaGUI.BotonConsultarTipoCambio){
      String resultado = "";
      if(vistaGUI.RadioBotonCompra.isSelected() == true && vistaGUI.RadioBotonVenta.isSelected() == false){
        resultado = ControladorCuenta.consultarCompraDolar();
        vistaGUI.TipoCambioResultado.setText(resultado);
      }
      else if (vistaGUI.RadioBotonCompra.isSelected() == false && vistaGUI.RadioBotonVenta.isSelected() == true){
        resultado = ControladorCuenta.consultarVentaDolar(); 
        vistaGUI.TipoCambioResultado.setText(resultado);
      }
      else if(vistaGUI.RadioBotonCompra.isSelected() == false && vistaGUI.RadioBotonVenta.isSelected() == false){
        JOptionPane.showMessageDialog(null, "Por favor, seleccione una opción", "Advertencia", JOptionPane.WARNING_MESSAGE);       
      } 
      else{
        vistaGUI.TipoCambioResultado.setText("");
        JOptionPane.showMessageDialog(null, "Por favor, seleccione únicamente una opción", "Advertencia", JOptionPane.WARNING_MESSAGE);
        
      }    
    }
    
    else if(evento.getSource() == vistaGUI.BotonDepositar){
      if(vistaGUI.DepositoNumeroCuenta.getText().equals("") || vistaGUI.DepositoMonto.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);  
      }
      else{
        if(vistaGUI.RadioBotonColonesDeposito.isSelected() == true && vistaGUI.RadioBotonDolaresDeposito.isSelected() == false){
        JOptionPane.showMessageDialog(null, ControladorCuenta.llamarDepositarColones(vistaGUI.DepositoNumeroCuenta.getText(), vistaGUI.DepositoMonto.getText()));
        vistaGUI.DepositoNumeroCuenta.setText(""); vistaGUI.DepositoMonto.setText(""); 
        
        }
        else if (vistaGUI.RadioBotonColonesDeposito.isSelected() == false && vistaGUI.RadioBotonDolaresDeposito.isSelected() == true){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarDepositarDolares(vistaGUI.DepositoNumeroCuenta.getText(), vistaGUI.DepositoMonto.getText()));
          vistaGUI.DepositoNumeroCuenta.setText(""); vistaGUI.DepositoMonto.setText("");     
        }    
      }

   
    }
    
    
    else if(evento.getSource() == vistaGUI.BotonValidarPinConsultarSaldo){
        if(vistaGUI.ConsultarSaldoNumeroCuenta.getText().equals("") || vistaGUI.ConsultarSaldoPIN.getText().equals("")){
         JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);   
        
        }
        else{
                  if (contadorIntentosPIN<3){
          if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.ConsultarSaldoPIN.getText(), vistaGUI.ConsultarSaldoNumeroCuenta.getText()) == true){
            if(vistaGUI.RadioBotonColonesConsultarSaldo.isSelected() == true && vistaGUI.RadioBotonDolaresConsultarSaldo.isSelected() == false){
                JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarSaldoColones(vistaGUI.ConsultarSaldoNumeroCuenta.getText(), vistaGUI.ConsultarSaldoPIN.getText()));
              contadorIntentosPIN = 1;
              vistaGUI.ConsultarSaldoNumeroCuenta.setText(""); vistaGUI.ConsultarSaldoPIN.setText("");
              vistaGUI.LabelConsultarSaldoIntentos.setText("Intentos restantes: 2");
            }
            else if (vistaGUI.RadioBotonColonesConsultarSaldo.isSelected() == false && vistaGUI.RadioBotonDolaresConsultarSaldo.isSelected() == true){
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarSaldoDolares(vistaGUI.ConsultarSaldoNumeroCuenta.getText(), vistaGUI.ConsultarSaldoPIN.getText()));
              contadorIntentosPIN = 1;
              vistaGUI.ConsultarSaldoNumeroCuenta.setText(""); vistaGUI.ConsultarSaldoPIN.setText("");
            }
          }  
          else{
              
            contadorIntentosPIN++;  
            vistaGUI.LabelConsultarSaldoIntentos.setText("Intentos restantes: " + (3-contadorIntentosPIN));
            vistaGUI.ConsultarSaldoPIN.setText("");
            if(contadorIntentosPIN == 3){
              try {  
                JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", vistaGUI.ConsultarSaldoNumeroCuenta.getText()));
              } 
              catch (MessagingException ex) {
                Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
              }   
            }
          }    
        }      
        }
      
    } 
    
    //PIN ESTADO CUENTA
    else if(evento.getSource() == vistaGUI.BotonValidarEstadoCuenta){
        if(vistaGUI.EstadoCuentaNumeroCuenta.getText().equals("") || vistaGUI.EstadoCuentaPIN.getText().equals("")){
         JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);   
        }
        else{
         if (contadorIntentosPIN<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.EstadoCuentaPIN.getText(), vistaGUI.EstadoCuentaNumeroCuenta.getText()) == true){
          if(vistaGUI.EstadoCuentaColones.isSelected() == true && vistaGUI.EstadoCuentaDolares.isSelected() == false){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarGenerarEstadoCuentaColones(vistaGUI.EstadoCuentaNumeroCuenta.getText(), vistaGUI.EstadoCuentaPIN.getText()));
            contadorIntentosPIN = 1;
            vistaGUI.EstadoCuentaNumeroCuenta.setText(""); vistaGUI.EstadoCuentaPIN.setText("");
            vistaGUI.EstadoCuentaIntentos.setText("Intentos restantes: 2");
          }
          else if (vistaGUI.EstadoCuentaColones.isSelected() == false && vistaGUI.EstadoCuentaDolares.isSelected() == true){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarGenerarEstadoCuentaDolares(vistaGUI.EstadoCuentaNumeroCuenta.getText(), vistaGUI.EstadoCuentaPIN.getText()));
            contadorIntentosPIN = 1;
            vistaGUI.EstadoCuentaNumeroCuenta.setText(""); vistaGUI.EstadoCuentaPIN.setText("");
            vistaGUI.EstadoCuentaIntentos.setText("Intentos restantes: 2");
          }
        }  
        else{
              
          contadorIntentosPIN++;  
          vistaGUI.EstadoCuentaIntentos.setText("Intentos restantes: " + (3-contadorIntentosPIN));
          vistaGUI.EstadoCuentaPIN.setText("");
          if(contadorIntentosPIN == 3){
            try {  
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", vistaGUI.EstadoCuentaNumeroCuenta.getText()));
            } 
            catch (MessagingException ex) {
              Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }   
          }
        }    
      }     
        }
       
  }
    
  //PIN CAMBIO PIN
      else if(evento.getSource() == vistaGUI.BotonCambiarPINValidar){

      if (contadorIntentosPIN<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.CambiarPINActual.getText(), vistaGUI.CambiarPINNumeroCuenta.getText()) == true){
          contadorIntentosPIN = 1;
          vistaGUI.CambiarPINNuevo.setEnabled(true);
          vistaGUI.BotonCambiarPINCambiar.setEnabled(true);
        }  
        else{              
          contadorIntentosPIN++;  
          vistaGUI.CambioPINIntentos.setText("Intentos restantes: " + (3-contadorIntentosPIN));
          vistaGUI.CambiarPINActual.setText("");
          if(contadorIntentosPIN == 3){
            try {  
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", vistaGUI.CambiarPINNumeroCuenta.getText()));
            } 
            catch (MessagingException ex) {
              Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }   
          }
        }    
      }        
  }  
   
    else if(evento.getSource() == vistaGUI.BotonCambiarPINCambiar){
      if(vistaGUI.CambiarPINNumeroCuenta.getText().equals("") || vistaGUI.CambiarPINActual.getText().equals("") || vistaGUI.CambiarPINNuevo.getText().equals("")){
       JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);   
      }
      else{
        if(ExpresionRegular.validarFormatoPin(vistaGUI.CambiarPINNuevo.getText()) == true){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCambiarPinCLI(vistaGUI.CambiarPINNumeroCuenta.getText(), vistaGUI.CambiarPINActual.getText(),vistaGUI.CambiarPINNuevo.getText()));          
          vistaGUI.CambiarPINNumeroCuenta.setText(""); vistaGUI.CambiarPINActual.setText(""); vistaGUI.CambiarPINNuevo.setText("");
          vistaGUI.CambioPINIntentos.setText("Intentos restantes: 2");
        }
        else{
          JOptionPane.showMessageDialog(null, "Formato de PIN erróneo", "Advertencia", JOptionPane.WARNING_MESSAGE);    
        }    
      }

      
    }
    
   //PIN RETIRO
    
   else if(evento.getSource() == vistaGUI.BotonRetiroValidarPIN){

      if (contadorIntentosPIN<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroNumeroCuenta.getText()) == true){
          contadorIntentosPIN = 1;
          vistaGUI.RetiroPalabra.setEnabled(true);
          vistaGUI.BotonRetiroValidarPalabra.setEnabled(true);
          String pNumeroReceptor = ControladorCliente.consultarNumeroClientePorCuenta(vistaGUI.RetiroNumeroCuenta.getText());
          
          try {
            mensaje.enviarPalabraSecreta(pNumeroReceptor);
            JOptionPane.showMessageDialog(null, "Estimado usuario, se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digital la palabra enviada");
          } 
          catch (Exception ex) {
            Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
          }
        }  
        else{              
          contadorIntentosPIN++;  
          vistaGUI.RetiroIntentosPIN.setText("Intentos restantes: " + (3-contadorIntentosPIN));
          vistaGUI.RetiroPIN.setText("");
          if(contadorIntentosPIN == 3){
            try {  
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", vistaGUI.RetiroNumeroCuenta.getText()));
            } 
            catch (MessagingException ex) {
              Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }   
          }
        }    
      }        
  } 
   //Palabra Retiro
    else if(evento.getSource() == vistaGUI.BotonRetiroValidarPalabra){
        String pNumeroReceptor = ControladorCliente.consultarNumeroClientePorCuenta(vistaGUI.RetiroNumeroCuenta.getText());
      if (contadorIntentosPalabra<3){
        try {
                if(mensaje.corroborarPalabraSecreta(vistaGUI.RetiroPalabra.getText(), pNumeroReceptor)){
                    contadorIntentosPalabra = 1;
                    vistaGUI.RetiroMonto.setEnabled(true);
                    vistaGUI.BotonRetiro.setEnabled(true);
                }
                else{
                    contadorIntentosPalabra++;
                    vistaGUI.RetiroIntentosPalabra.setText("Intentos restantes: " + (3-contadorIntentosPalabra));
                    vistaGUI.RetiroPalabra.setText("");
                    if(contadorIntentosPalabra == 3){
                        try {
                            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de palabra", vistaGUI.RetiroNumeroCuenta.getText()));
                        }
                        catch (MessagingException ex) {
                            Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }    
                }   
        } catch (Exception ex) {
                Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
         }
      }        
  } 
  //Retiro  
  else if(evento.getSource() == vistaGUI.BotonRetiro){
      
      if(vistaGUI.RetiroNumeroCuenta.getText().equals("") || vistaGUI.RetiroPIN.getText().equals("") || vistaGUI.RetiroPalabra.getText().equals("") || vistaGUI.RetiroMonto.getText().equals("")){
       JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);   
      }
      else{
        if(ExpresionRegular.validarNumerosEnterosPositivos(vistaGUI.RetiroMonto.getText()) == true && CuentaDao.verificarSuficienciaDeFondos(vistaGUI.RetiroNumeroCuenta.getText(), Double.parseDouble(vistaGUI.RetiroMonto.getText())) == true){
          if(vistaGUI.RetiroColones.isSelected() == true && vistaGUI.RetiroDolares.isSelected() == false){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarRetirarColones(vistaGUI.RetiroNumeroCuenta.getText(), vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroMonto.getText()));
            vistaGUI.RetiroNumeroCuenta.setText(""); vistaGUI.RetiroPIN.setText(""); vistaGUI.RetiroPalabra.setText(""); vistaGUI.RetiroMonto.setText("");
            vistaGUI.RetiroIntentosPIN.setText("Intentos restantes: 2"); vistaGUI.RetiroIntentosPalabra.setText("Intentos restantes: 2");
          }
          else if (vistaGUI.RetiroColones.isSelected() == false && vistaGUI.RetiroDolares.isSelected() == true){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarRetirarDolares(vistaGUI.RetiroNumeroCuenta.getText(), vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroMonto.getText()));
            vistaGUI.RetiroNumeroCuenta.setText(""); vistaGUI.RetiroPIN.setText(""); vistaGUI.RetiroPalabra.setText(""); vistaGUI.RetiroMonto.setText("");
            vistaGUI.RetiroIntentosPIN.setText("Intentos restantes: 2"); vistaGUI.RetiroIntentosPalabra.setText("Intentos restantes: 2");
          }          
        }
        else{
          JOptionPane.showMessageDialog(null, "Favor verificar el monto y/o  cuenta no posee fondos suficientes", "Advertencia", JOptionPane.WARNING_MESSAGE);    
        }   
      }

      
    } 
    
    
  
  
  
  
  
  //PIN Transferencia
    
   else if(evento.getSource() == vistaGUI.BotonTransferenciaValidarPIN){

      if (contadorIntentosPIN<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.TransferenciaPIN.getText(), vistaGUI.TransferenciaCuentaOrigen.getText()) == true){
          contadorIntentosPIN = 1;
          vistaGUI.TransferenciaPalabra.setEnabled(true);
          vistaGUI.BotonTransferenciaValidarPalabra.setEnabled(true);
          String pNumeroReceptor = ControladorCliente.consultarNumeroClientePorCuenta(vistaGUI.TransferenciaCuentaOrigen.getText());
          
          try {
            mensaje.enviarPalabraSecreta(pNumeroReceptor);
            JOptionPane.showMessageDialog(null, "Estimado usuario, se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digital la palabra enviada");
          } 
          catch (Exception ex) {
            Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
          }
        }  
        else{              
          contadorIntentosPIN++;  
          vistaGUI.TransferenciaIntentosPIN.setText("Intentos restantes: " + (3-contadorIntentosPIN));
          vistaGUI.TransferenciaPIN.setText("");
          if(contadorIntentosPIN == 3){
            try {  
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", vistaGUI.TransferenciaCuentaOrigen.getText()));
            } 
            catch (MessagingException ex) {
              Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }   
          }
        }    
      }        
  } 
   //Palabra Transferencia
    else if(evento.getSource() == vistaGUI.BotonTransferenciaValidarPalabra){
        String pNumeroReceptor = ControladorCliente.consultarNumeroClientePorCuenta(vistaGUI.TransferenciaCuentaOrigen.getText());
      if (contadorIntentosPalabra<3){
        try {
                if(mensaje.corroborarPalabraSecreta(vistaGUI.TransferenciaPalabra.getText(), pNumeroReceptor)){
                    contadorIntentosPalabra = 1;
                    vistaGUI.TransferenciaMonto.setEnabled(true);
                    vistaGUI.TransferenciaCuentaDestino.setEnabled(true);
                    vistaGUI.BotonTransferencia.setEnabled(true);
                }
                else{
                    contadorIntentosPalabra++;
                    vistaGUI.TransferenciaintentosPalabra.setText("Intentos restantes: " + (3-contadorIntentosPalabra));
                    vistaGUI.TransferenciaPalabra.setText("");
                    if(contadorIntentosPalabra == 3){
                        try {
                            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de palabra", vistaGUI.TransferenciaCuentaOrigen.getText()));
                        }
                        catch (MessagingException ex) {
                            Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }    
                }   
        } catch (Exception ex) {
                Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
         }
      }        
  } 
  
  //Transferencia
  else if(evento.getSource() == vistaGUI.BotonTransferencia){
      if(vistaGUI.TransferenciaCuentaOrigen.getText().equals("") || vistaGUI.TransferenciaPIN.getText().equals("") || vistaGUI.TransferenciaPalabra.getText().equals("") || vistaGUI.TransferenciaMonto.getText().equals("") || vistaGUI.TransferenciaCuentaDestino.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);     
      }
      else{
        if(ExpresionRegular.validarNumerosEnterosPositivos(vistaGUI.TransferenciaMonto.getText()) == true && CuentaDao.verificarSuficienciaDeFondos(vistaGUI.TransferenciaCuentaOrigen.getText(), Double.parseDouble(vistaGUI.TransferenciaMonto.getText())) == true){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarTransferirFondos(vistaGUI.TransferenciaCuentaOrigen.getText(), vistaGUI.TransferenciaPIN.getText(), vistaGUI.TransferenciaMonto.getText(), vistaGUI.TransferenciaCuentaDestino.getText()));     
          vistaGUI.TransferenciaCuentaOrigen.setText(""); vistaGUI.TransferenciaPIN.setText(""); vistaGUI.TransferenciaPalabra.setText(""); vistaGUI.TransferenciaMonto.setText(""); vistaGUI.TransferenciaCuentaDestino.setText("");
          vistaGUI.TransferenciaIntentosPIN.setText("Intentos restantes: 2"); vistaGUI.TransferenciaintentosPalabra.setText("Intentos restantes: 2");
        }
        else{
          JOptionPane.showMessageDialog(null, "Favor verificar el monto y/o  cuenta no posee fondos suficientes", "Advertencia", JOptionPane.WARNING_MESSAGE);    
        }   
      }

      
    } 
  
  
  
  
  
  
  
  
  
  
  
    
    
    else if(evento.getSource() == vistaGUI.BotonConsultarEstatus){
      if(vistaGUI.ConsultarEstatusNumeroCuenta.getText().equals("")){
       JOptionPane.showMessageDialog(null, "Todos los campos son imprescindibles para llevar a cabo la operación", "Error", JOptionPane.ERROR_MESSAGE);   
      }
      else{
        JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarEstatus(vistaGUI.ConsultarEstatusNumeroCuenta.getText()));
        vistaGUI.ConsultarEstatusNumeroCuenta.setText("");
      }
            
    }
    
    
    else if(evento.getSource() == vistaGUI.BotonGanancias){
      if(vistaGUI.GananciasUniversoCuentas.isSelected() == true && vistaGUI.GananciasCuentaEspecifico.isSelected() == false){
          JOptionPane.showMessageDialog(null, ControladorCuenta.calcularTodasLasComisionesUniversoCuentas());    
      }
      else if (vistaGUI.GananciasUniversoCuentas.isSelected() == false && vistaGUI.GananciasCuentaEspecifico.isSelected() == true){
        if(vistaGUI.GananciasNumeroCuenta.getText().equals("")){
          JOptionPane.showMessageDialog(null, "Por favor, indique un número de cuenta", "Error", JOptionPane.ERROR_MESSAGE);    
        }
        else{
          JOptionPane.showMessageDialog(null, ControladorCuenta.calcularTodasLasComisionesCuentaUnica(vistaGUI.GananciasNumeroCuenta.getText()));      
          vistaGUI.GananciasNumeroCuenta.setText("");
        }    
      }        
    }
    
    //Llenado de elementos
    else if(evento.getSource() == vistaGUI.ListarConsultarCliente){
      recorrerResultadoConsultaListarClientes(vistaGUI.TablaListarConsultarCliente);
      recorrerResultadoConsultaListarClientes(vistaGUI.ComboListarConsultarClientes);
    } 
      
    else if(evento.getSource() == vistaGUI.MenuRegistrarCuenta){
      recorrerResultadoConsultaListarClientes(vistaGUI.ComboIdentificacionRegistrarCuenta);
    }
    
    else if(evento.getSource() == vistaGUI.MenuListarConsultarCuentas){
      recorrerResultadoConsultaListarCuentas(vistaGUI.TablaListarConsultarCuenta);;
      recorrerResultadoConsultaListarCuentas(vistaGUI.ComboListarConsultaCuentas);
    } 
    
    //Acciones de botones de regreso  
    else if(evento.getSource() == vistaGUI.BotonRegresarRegistrarCliente){
      vistaGUI.RegistrarClienteIdentificacion.setText(""); vistaGUI.RegistrarClienteNombre.setText(""); vistaGUI.RegistrarClientePrimerApellido.setText(""); vistaGUI.RegistrarClienteSegundoApellido.setText(""); vistaGUI.RegistrarClienteCorreo.setText(""); vistaGUI.RegistrarClienteNumero.setText(""); vistaGUI.RegistrarClienteFechaNacimiento.setCalendar(null);
    } 
    
    else if(evento.getSource() == vistaGUI.BotonRegresarRegistrarUsuario){
      vistaGUI.RegistrarUsuarioIdentificacion.setText(""); vistaGUI.RegistrarUsuarioNombre.setText(""); vistaGUI.RegistrarUsuarioPrimerApellido.setText(""); vistaGUI.RegistrarUsuarioSegundoApellido.setText(""); vistaGUI.RegistrarUsuarioFechaNacimiento.setCalendar(null);
    } 
    
    else if(evento.getSource() == vistaGUI.BotonRegresarRegistrarCuenta){
      vistaGUI.RegistrarCuentaMontoInicial.setText(""); vistaGUI.RegistrarCuentaPIN.setText("");
    }

    else if(evento.getSource() == vistaGUI.BotonRegresarCambiarPIN){
     vistaGUI.CambiarPINNumeroCuenta.setText(""); vistaGUI.CambiarPINActual.setText(""); vistaGUI.CambiarPINNuevo.setText("");
     contadorIntentosPIN = 1;
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarConsultarEstatus){
     vistaGUI.ConsultarEstatusNumeroCuenta.setText("");
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarComisiones){
      vistaGUI.GananciasNumeroCuenta.setText(""); 
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarTipoCambio){
      vistaGUI.TipoCambioResultado.setText("");   
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarEstadoCuenta){
      vistaGUI.EstadoCuentaNumeroCuenta.setText(""); vistaGUI.EstadoCuentaPIN.setText("");   
      contadorIntentosPIN = 1;
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarConsultarSaldo){
      vistaGUI.ConsultarSaldoNumeroCuenta.setText(""); vistaGUI.ConsultarSaldoPIN.setText("");   
      contadorIntentosPIN = 1;
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarTransferencia){
      vistaGUI.TransferenciaCuentaOrigen.setText(""); vistaGUI.TransferenciaPIN.setText(""); vistaGUI.TransferenciaPalabra.setText(""); vistaGUI.TransferenciaMonto.setText(""); vistaGUI.TransferenciaCuentaDestino.setText("");   
      contadorIntentosPIN = 1; contadorIntentosPalabra = 1;
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarDeposito){
      vistaGUI.DepositoNumeroCuenta.setText(""); vistaGUI.DepositoMonto.setText("");   
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegresarRetiro){
      vistaGUI.RetiroNumeroCuenta.setText(""); vistaGUI.RetiroPIN.setText(""); vistaGUI.RetiroPalabra.setText(""); vistaGUI.RetiroMonto.setText("");   
      contadorIntentosPIN = 1; contadorIntentosPalabra = 1;
    }
    
  }//Fin método actionPerformed
  
  
    
       
}//Fin ControladorInterfazUsuario
