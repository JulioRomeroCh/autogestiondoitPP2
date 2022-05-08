package logicadeintegracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
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
    this.vistaGUI.BotonDepositar.addActionListener(this);
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
    
    //Ítems del menú
    this.vistaGUI.ListarConsultarCliente.addActionListener(this);
    this.vistaGUI.MenuListarConsultarCuentas.addActionListener(this);
    this.vistaGUI.MenuRegistrarCuenta.addActionListener(this);
    
  }
  
  //-------------------------LLAMADO DE MÉTODOS-------------------------
  public String llamarMetodoRegistrarCliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
    String pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico) throws ParseException{
    if (ExpresionRegular.validarFormatoFecha(pFechaNacimiento) == true && ExpresionRegular.validarFormatoCorreoElectronico(pCorreoElectronico) == true){
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
  


  
  int contadorIntentosPINConsultarSaldo = 1;
  int contadorIntentosPINEstadoCuenta = 1;
  int contadorIntentosPINCambioPIN = 1;
  int contadorIntentosPINRetiro = 1;
  int contadorIntentosPalabraRetiro = 1;
  int contadorIntentosPINTransferencia = 1;
  int contadorIntentosPalabraTransferencia = 1;
  MensajeTexto mensaje = new MensajeTexto();

  @Override
  public void actionPerformed(ActionEvent evento) {//Inicio  método actionPerformed
  
    if(evento.getSource() == vistaGUI.BotonRegistrarCliente){
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
    
    else if(evento.getSource() == vistaGUI.BotonRegistrarUsuario){
      try {
        String fecha = String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDate()) + "/0" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getDay()+1) + "/" + String.valueOf(vistaGUI.RegistrarUsuarioFechaNacimiento.getDate().getYear()+1900); 
        JOptionPane.showMessageDialog(null, ControladorPersona.llamarMetodoRegistrarUsuarioCLI(vistaGUI.RegistrarUsuarioIdentificacion.getText(), vistaGUI.RegistrarUsuarioNombre.getText(), vistaGUI.RegistrarUsuarioPrimerApellido.getText(), vistaGUI.RegistrarUsuarioSegundoApellido.getText(), fecha));
        vistaGUI.RegistrarUsuarioIdentificacion.setText(""); vistaGUI.RegistrarUsuarioNombre.setText(""); vistaGUI.RegistrarUsuarioPrimerApellido.setText(""); vistaGUI.RegistrarUsuarioSegundoApellido.setText(""); vistaGUI.RegistrarUsuarioFechaNacimiento.setCalendar(null);
      } 
      catch (ParseException ex) {
        Logger.getLogger(ControladorInterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    else if(evento.getSource() == vistaGUI.BotonRegistrarCuenta){
      String mensaje = vistaGUI.ComboIdentificacionRegistrarCuenta.getSelectedItem().toString();
      String [] partesMensaje = mensaje.split(" ");
      JOptionPane.showMessageDialog(null, llamarMetodoRegistrarCuentaPersonaGUI(vistaGUI.RegistrarCuentaMontoInicial.getText(), vistaGUI.RegistrarCuentaPIN.getText(), partesMensaje[0]));
      vistaGUI.RegistrarCuentaMontoInicial.setText(""); vistaGUI.RegistrarCuentaPIN.setText("");
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
      else{
        vistaGUI.TipoCambioResultado.setText("");
        JOptionPane.showMessageDialog(null, "Por favor, seleccione únicamente una opción", "Advertencia", JOptionPane.WARNING_MESSAGE);
        
      }    
    }
    
    else if(evento.getSource() == vistaGUI.BotonDepositar){
      if(vistaGUI.RadioBotonColonesDeposito.isSelected() == true && vistaGUI.RadioBotonDolaresDeposito.isSelected() == false){
        JOptionPane.showMessageDialog(null, ControladorCuenta.llamarDepositarColones(vistaGUI.DepositoNumeroCuenta.getText(), vistaGUI.DepositoMonto.getText()));
        vistaGUI.DepositoNumeroCuenta.setText(""); vistaGUI.DepositoMonto.setText(""); vistaGUI.RadioBotonColonesDeposito.setSelected(false);
        
      }
      else if (vistaGUI.RadioBotonColonesDeposito.isSelected() == false && vistaGUI.RadioBotonDolaresDeposito.isSelected() == true){
        JOptionPane.showMessageDialog(null, ControladorCuenta.llamarDepositarDolares(vistaGUI.DepositoNumeroCuenta.getText(), vistaGUI.DepositoMonto.getText()));
        vistaGUI.DepositoNumeroCuenta.setText(""); vistaGUI.DepositoMonto.setText(""); vistaGUI.RadioBotonDolaresDeposito.setSelected(false);    
      }
   
    }
    
    
    else if(evento.getSource() == vistaGUI.BotonValidarPinConsultarSaldo){

        if (contadorIntentosPINConsultarSaldo<3){
          if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.ConsultarSaldoPIN.getText(), vistaGUI.ConsultarSaldoNumeroCuenta.getText()) == true){
            if(vistaGUI.RadioBotonColonesConsultarSaldo.isSelected() == true && vistaGUI.RadioBotonDolaresConsultarSaldo.isSelected() == false){
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarSaldoColones(vistaGUI.ConsultarSaldoNumeroCuenta.getText(), vistaGUI.ConsultarSaldoPIN.getText()));
              contadorIntentosPINConsultarSaldo = 0;
            }
            else if (vistaGUI.RadioBotonColonesConsultarSaldo.isSelected() == false && vistaGUI.RadioBotonDolaresConsultarSaldo.isSelected() == true){
              JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarSaldoDolares(vistaGUI.ConsultarSaldoNumeroCuenta.getText(), vistaGUI.ConsultarSaldoPIN.getText()));
              contadorIntentosPINConsultarSaldo = 0;
            }
          }  
          else{
              
            contadorIntentosPINConsultarSaldo++;  
            vistaGUI.LabelConsultarSaldoIntentos.setText("Intentos restantes: " + (3-contadorIntentosPINConsultarSaldo));
            vistaGUI.ConsultarSaldoPIN.setText("");
            if(contadorIntentosPINConsultarSaldo == 3){
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
    
    //PIN ESTADO CUENTA
    else if(evento.getSource() == vistaGUI.BotonValidarEstadoCuenta){

      if (contadorIntentosPINEstadoCuenta<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.EstadoCuentaPIN.getText(), vistaGUI.EstadoCuentaNumeroCuenta.getText()) == true){
          if(vistaGUI.EstadoCuentaColones.isSelected() == true && vistaGUI.EstadoCuentaDolares.isSelected() == false){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarGenerarEstadoCuentaColones(vistaGUI.EstadoCuentaNumeroCuenta.getText(), vistaGUI.EstadoCuentaPIN.getText()));
            contadorIntentosPINEstadoCuenta = 0;
          }
          else if (vistaGUI.EstadoCuentaColones.isSelected() == false && vistaGUI.EstadoCuentaDolares.isSelected() == true){
            JOptionPane.showMessageDialog(null, ControladorCuenta.llamarGenerarEstadoCuentaDolares(vistaGUI.EstadoCuentaNumeroCuenta.getText(), vistaGUI.EstadoCuentaPIN.getText()));
            contadorIntentosPINEstadoCuenta = 0;
          }
        }  
        else{
              
          contadorIntentosPINEstadoCuenta++;  
          vistaGUI.EstadoCuentaIntentos.setText("Intentos restantes: " + (3-contadorIntentosPINEstadoCuenta));
          vistaGUI.EstadoCuentaPIN.setText("");
          if(contadorIntentosPINEstadoCuenta == 3){
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
    
  //PIN CAMBIO PIN
      else if(evento.getSource() == vistaGUI.BotonCambiarPINValidar){

      if (contadorIntentosPINCambioPIN<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.CambiarPINActual.getText(), vistaGUI.CambiarPINNumeroCuenta.getText()) == true){
          contadorIntentosPINCambioPIN = 0;
          vistaGUI.CambiarPINNuevo.setEnabled(true);
          vistaGUI.BotonCambiarPINCambiar.setEnabled(true);
        }  
        else{              
          contadorIntentosPINCambioPIN++;  
          vistaGUI.CambioPINIntentos.setText("Intentos restantes: " + (3-contadorIntentosPINCambioPIN));
          vistaGUI.CambiarPINActual.setText("");
          if(contadorIntentosPINCambioPIN == 3){
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
      if(ExpresionRegular.validarFormatoPin(vistaGUI.CambiarPINNuevo.getText()) == true){
        JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCambiarPinCLI(vistaGUI.CambiarPINNumeroCuenta.getText(), vistaGUI.CambiarPINActual.getText(),vistaGUI.CambiarPINNuevo.getText()));          
      }
      else{
        JOptionPane.showMessageDialog(null, "Formato de PIN erróneo", "Advertencia", JOptionPane.WARNING_MESSAGE);    
      }
      
    }
    
   //PIN RETIRO
    
   else if(evento.getSource() == vistaGUI.BotonRetiroValidarPIN){

      if (contadorIntentosPINRetiro<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroNumeroCuenta.getText()) == true){
          contadorIntentosPINRetiro = 0;
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
          contadorIntentosPINRetiro++;  
          vistaGUI.RetiroIntentosPIN.setText("Intentos restantes: " + (3-contadorIntentosPINRetiro));
          vistaGUI.RetiroPIN.setText("");
          if(contadorIntentosPINRetiro == 3){
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
      if (contadorIntentosPalabraRetiro<3){
        try {
                if(mensaje.corroborarPalabraSecreta(vistaGUI.RetiroPalabra.getText(), pNumeroReceptor)){
                    contadorIntentosPalabraRetiro = 0;
                    vistaGUI.RetiroMonto.setEnabled(true);
                    vistaGUI.BotonRetiro.setEnabled(true);
                }
                else{
                    contadorIntentosPalabraRetiro++;
                    vistaGUI.RetiroIntentosPalabra.setText("Intentos restantes: " + (3-contadorIntentosPalabraRetiro));
                    vistaGUI.RetiroPalabra.setText("");
                    if(contadorIntentosPalabraRetiro == 3){
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
      if(ExpresionRegular.validarNumerosEnterosPositivos(vistaGUI.RetiroMonto.getText()) == true && CuentaDao.verificarSuficienciaDeFondos(vistaGUI.RetiroNumeroCuenta.getText(), Double.parseDouble(vistaGUI.RetiroMonto.getText())) == true){
        if(vistaGUI.RetiroColones.isSelected() == true && vistaGUI.RetiroDolares.isSelected() == false){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarRetirarColones(vistaGUI.RetiroNumeroCuenta.getText(), vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroMonto.getText()));
        }
        else if (vistaGUI.RetiroColones.isSelected() == false && vistaGUI.RetiroDolares.isSelected() == true){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarRetirarDolares(vistaGUI.RetiroNumeroCuenta.getText(), vistaGUI.RetiroPIN.getText(), vistaGUI.RetiroMonto.getText()));
        }          
      }
      else{
        JOptionPane.showMessageDialog(null, "Favor verificar el monto y/o  cuenta no posee fondos suficientes", "Advertencia", JOptionPane.WARNING_MESSAGE);    
      }
      
    } 
    
    
  
  
  
  
  
  //PIN Transferencia
    
   else if(evento.getSource() == vistaGUI.BotonTransferenciaValidarPIN){

      if (contadorIntentosPINTransferencia<3){
        if(ValidacionIntentos.validarCantidadIntentosPin(vistaGUI.TransferenciaPIN.getText(), vistaGUI.TransferenciaCuentaOrigen.getText()) == true){
          contadorIntentosPINTransferencia = 0;
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
          contadorIntentosPINTransferencia++;  
          vistaGUI.TransferenciaIntentosPIN.setText("Intentos restantes: " + (3-contadorIntentosPINTransferencia));
          vistaGUI.TransferenciaPIN.setText("");
          if(contadorIntentosPINTransferencia == 3){
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
      if (contadorIntentosPalabraTransferencia<3){
        try {
                if(mensaje.corroborarPalabraSecreta(vistaGUI.TransferenciaPalabra.getText(), pNumeroReceptor)){
                    contadorIntentosPalabraTransferencia = 0;
                    vistaGUI.TransferenciaMonto.setEnabled(true);
                    vistaGUI.TransferenciaCuentaDestino.setEnabled(true);
                    vistaGUI.BotonTransferencia.setEnabled(true);
                }
                else{
                    contadorIntentosPalabraTransferencia++;
                    vistaGUI.TransferenciaintentosPalabra.setText("Intentos restantes: " + (3-contadorIntentosPalabraTransferencia));
                    vistaGUI.TransferenciaPalabra.setText("");
                    if(contadorIntentosPalabraTransferencia == 3){
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
      if(ExpresionRegular.validarNumerosEnterosPositivos(vistaGUI.TransferenciaMonto.getText()) == true && CuentaDao.verificarSuficienciaDeFondos(vistaGUI.TransferenciaCuentaOrigen.getText(), Double.parseDouble(vistaGUI.TransferenciaMonto.getText())) == true){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarTransferirFondos(vistaGUI.TransferenciaCuentaOrigen.getText(), vistaGUI.TransferenciaPIN.getText(), vistaGUI.TransferenciaMonto.getText(), vistaGUI.TransferenciaCuentaDestino.getText()));     
      }
      else{
        JOptionPane.showMessageDialog(null, "Favor verificar el monto y/o  cuenta no posee fondos suficientes", "Advertencia", JOptionPane.WARNING_MESSAGE);    
      }
      
    } 
  
  
  
  
  
  
  
  
  
  
  
    
    
    else if(evento.getSource() == vistaGUI.BotonConsultarEstatus){
      JOptionPane.showMessageDialog(null, ControladorCuenta.llamarConsultarEstatus(vistaGUI.ConsultarEstatusNumeroCuenta.getText()));      
    }
    
    
    else if(evento.getSource() == vistaGUI.BotonGanancias){
      if(vistaGUI.GananciasUniversoCuentas.isSelected() == true && vistaGUI.GananciasCuentaEspecifico.isSelected() == false){
        if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Depósitos")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCalcularComisionesDepositosUniversoCuentas());    
        } 
        else if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Retiros")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCalcularComisionesRetirosUniversoCuentas());    
        } 
        else if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Depósitos y retiros")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCalcularComisionesDepositosYRetirosUniversoCuentas());
        } 
      }
      else if (vistaGUI.GananciasUniversoCuentas.isSelected() == false && vistaGUI.GananciasCuentaEspecifico.isSelected() == true){
        if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Depósitos")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCalcularComisionesDepositosCuentaUnica(vistaGUI.GananciasNumeroCuenta.getText()));    
        } 
        else if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Retiros")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarCalcularComisionesRetirosCuentaUnica(vistaGUI.GananciasNumeroCuenta.getText()));   
        } 
        else if(vistaGUI.GananciasTipo.getSelectedItem().toString().equals("Depósitos y retiros")){
          JOptionPane.showMessageDialog(null, ControladorCuenta.llamarcalcularComisionesTotalesCuantaUnica(vistaGUI.GananciasNumeroCuenta.getText()));    
        } 
      }        
    }
    
    //Llenado de elementos
    else if(evento.getSource() == vistaGUI.ListarConsultarCliente){
      ClienteDao.ConsultaListarClientesTabla(vistaGUI.TablaListarConsultarCliente);
      ClienteDao.ConsultaListarClientes(vistaGUI.ComboListarConsultarClientes);
    } 
      
    else if(evento.getSource() == vistaGUI.MenuRegistrarCuenta){
      ClienteDao.ConsultaListarClientes(vistaGUI.ComboIdentificacionRegistrarCuenta);
    }
    
    else if(evento.getSource() == vistaGUI.MenuListarConsultarCuentas){
      CuentaDao.ConsultaListarCuentasTabla(vistaGUI.TablaListarConsultarCuenta);;
      CuentaDao.ConsultaListarCuentas(vistaGUI.ComboListarConsultaCuentas);
    } 
    
    
    
    
  }//Fin método actionPerformed
  
  
    
       
}//Fin ControladorInterfazUsuario
