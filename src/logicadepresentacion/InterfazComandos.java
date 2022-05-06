package logicadepresentacion;

import java.text.ParseException;
import java.util.Scanner;
import javax.mail.MessagingException;
import logicadeaccesoadatos.CuentaDao;
import logicadeintegracion.*;
import logicadevalidacion.*;
import logicadeconexionexterna.MensajeTexto;

public class InterfazComandos {
    
 Scanner entrada = new Scanner(System.in);
    
 public void ejecutarMenuPrincipal() throws ParseException, MessagingException, Exception{
   boolean mostrarMenu = true;
   while (mostrarMenu == true){
     mostrarMenu = menuPrincipal();
   }
 }
     
  public boolean menuPrincipal() throws ParseException, MessagingException, Exception{
      
      System.out.println("\n" + "**********Bienvenido a Autogestión del grupo DoIT**********" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Gestionar usuarios o clientes" + "\n");
      System.out.println("2. Gestionar cuenta" + "\n");
      System.out.println("Presione cualquier otra tecla para salir (esto funciona para todo el menú)");
      
      String opcion = entrada.nextLine();
      
      if (opcion.equals("1")){
        menuPersonas();
        return true;
      }
      
      else if (opcion.equals("2")){
        menuCuenta();
        return true;
      }
      
      else{
        return false;
      }
  }     
  
  public void menuPersonas() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de personas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Registrar cliente o usuario" + "\n");
      System.out.println("2. Consultar clientes" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");

      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuRegistrarClienteUsuario();
      }
      
      else if (opcion.equals("2")){
        menuClientes();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }

  public void menuRegistrarClienteUsuario() throws ParseException{
      

      
      System.out.println("\n" + "***Módulo de registrar cliente o usuario***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Registrar cliente" + "\n");
      System.out.println("2. Registrar usuario" + "\n"); 
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
           String opcion = entrada.nextLine(); 
           String primerApellido;
           String segundoApellido;
           String nombre;
           String identificacion;
           String fechaNacimiento;
           String numeroTelefonico;
           String correoElectronico;
           
           
      if (opcion.equals("1")){
          System.out.println("Ingrese su primer apellido: ");
          primerApellido = entrada.nextLine();
          
          System.out.println("Ingrese su segundo apellido: ");
          segundoApellido = entrada.nextLine();
          
          System.out.println("Ingrese su nombre: ");
          nombre = entrada.nextLine();
          
          System.out.println("Ingrese su identificación: ");
          identificacion = entrada.nextLine();
          
          System.out.println("Ingrese su fecha de nacimiento, utilizando el formato (DD/MM/AAAA): ");
          fechaNacimiento = entrada.nextLine();
          
          System.out.println("Ingrese su número de teléfono: ");
          numeroTelefonico = entrada.nextLine();
          
          System.out.println("Ingrese su dirección de correo electrónico: ");
          correoElectronico = entrada.nextLine();
          
          System.out.println(ControladorCliente.llamarMetodoRegistrarClienteCLI(identificacion, nombre, primerApellido, segundoApellido, fechaNacimiento, numeroTelefonico, correoElectronico));
                 
      }
      
      else if (opcion.equals("2")){
          System.out.println("Ingrese su primer apellido: ");
          primerApellido = entrada.nextLine();
          
          System.out.println("Ingrese su segundo apellido: ");
          segundoApellido = entrada.nextLine();
          
          System.out.println("Ingrese su nombre: ");
          nombre = entrada.nextLine();
          
          System.out.println("Ingrese su identificación: ");
          identificacion = entrada.nextLine();
          
          System.out.println("Ingrese su fecha de nacimiento, utilizando el formato (DD/MM/AAAA): ");
          fechaNacimiento = entrada.nextLine();
          
          System.out.println(ControladorPersona.llamarMetodoRegistrarUsuarioCLI(identificacion, nombre, primerApellido, segundoApellido, fechaNacimiento));

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
  
  public void menuClientes() throws ParseException{
      
      System.out.println("\n" + "***Módulo de consultas sobre cliente***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Listar clientes" + "\n");
      System.out.println("2. Consultar datos de clientes" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();
      
      if (opcion.equals("1")){
       
          System.out.println(ControladorCliente.listarClientes());
          
      }
      
      else if (opcion.equals("2")){
          String identificacion;
          System.out.println("Indique la identificación del cliente que desea consultar: ");
          identificacion = entrada.nextLine();
          System.out.println(ControladorCliente.consultarDatosCliente(identificacion));

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
  
  public void menuCuenta() throws ParseException, MessagingException, Exception{
      
      System.out.println("\n" + "***Módulo de gestión de cuenta***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Transacciones con dinero (depósitos, retiros y transferencias)" + "\n");
      System.out.println("2. Otras operaciones (Registrar cuenta, cambio PIN, estado cuenta, listar y consultar cuentas,compra dolar, venta dolar, saldo, estatus, comisiones)" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");

      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuTransaccionesConDinero();      
      }
      
      else if (opcion.equals("2")){
        menuOtrasOperacionesCuenta();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
  
    public void menuTransaccionesConDinero() throws ParseException, MessagingException, Exception{
      
      System.out.println("\n" + "***Módulo de gestión de transacciones con dinero***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Depósitos y retiros" + "\n");
      System.out.println("2. Transferencias" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuDepositosYRetiros();      
      }
      
      else if (opcion.equals("2")){
          System.out.println(menuTransferirFondos());

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
    //--------------------------------------------
    public String menuTransferirFondos() throws ParseException, MessagingException, Exception{
    String numeroCuentaOrigen;
    String pin;
    System.out.println("Indique el número de cuenta del que desea transferir fondos: ");
    numeroCuentaOrigen = entrada.nextLine();
    String numeroTelefonico = ControladorCliente.consultarNumeroClientePorCuenta(numeroCuentaOrigen);
    int contadorPin = 0;
    while (contadorPin < 2){
         System.out.println("Indique el número PIN asociado a la cuenta: " + "\n" + "intentos restantes: " + (2 - contadorPin));
         pin = entrada.nextLine();
         if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuentaOrigen)){
           return menuPalabraTransferencia(numeroCuentaOrigen, pin, numeroTelefonico);
         }
         else{
           contadorPin++;
         }
      
    }
    
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuentaOrigen);
  }
    
    public String menuPalabraTransferencia(String pNumeroCuentaOrigen, String pPin, String pNumeroTelefonico) throws Exception{
      String palabraSecreta;
      String monto;
      String numeroCuentaDestino;
      int contadorPalabra = 0;
      MensajeTexto mensaje = new MensajeTexto();
      mensaje.enviarPalabraSecreta(pNumeroTelefonico);
      while (contadorPalabra < 2){
          System.out.println("Estimado usuario se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digitar la palabra enviada. Tiene " + (2 - contadorPalabra) + " intentos restantes");
          palabraSecreta = entrada.nextLine();
          if (mensaje.corroborarPalabraSecreta(palabraSecreta, pNumeroTelefonico)){
            System.out.println("Ingrese el monto a transferir: ");
            monto = entrada.nextLine();
            System.out.println("Indique el número de cuenta a la que desea transferir los fondos: ");
            numeroCuentaDestino = entrada.nextLine();
            return ControladorCuenta.llamarTransferirFondos(pNumeroCuentaOrigen, pPin, monto, numeroCuentaDestino);
          }
          else{
              System.out.println("Palabra incorrecta " + "\n");
              contadorPalabra++;
          }
      }
      return ControladorCuenta.llamarBloquearCuenta("Dos palabras secretas ingresadas de manera errónea y de forma consecutiva", pNumeroCuentaOrigen);
    }
    
   public void menuDepositosYRetiros() throws ParseException, MessagingException, Exception{
      
      System.out.println("\n" + "***Módulo de gestión de depósitos y retiros***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Depósitos" + "\n");
      System.out.println("2. Retiros" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuDepositosCuenta();   
      }
      
      else if (opcion.equals("2")){
        menuRetirosCuenta();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
 
  }
   
      public void menuDepositosCuenta() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de depósitos***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Depósito colones" + "\n");
      System.out.println("2. Depósito dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
         String numeroCuenta;
         String monto;
          System.out.println("Ingrese el número de cuenta para depositar en colones: ");
          numeroCuenta = entrada.nextLine();
          System.out.println("Ingrese el monto en colones, a depositar: ");
          monto = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarDepositarColones(numeroCuenta, monto));     
      }
      
      else if (opcion.equals("2")){
         String numeroCuenta;
         String monto;
          System.out.println("Ingrese el número de cuenta para depositar en dólares: ");
          numeroCuenta = entrada.nextLine();
          System.out.println("Ingrese el monto en dólares, a depositar: ");
          monto = entrada.nextLine();
                  
          System.out.println(ControladorCuenta.llamarDepositarDolares(numeroCuenta, monto)); 
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
      
     public void menuRetirosCuenta() throws ParseException, MessagingException, Exception{
      
      System.out.println("\n" + "***Módulo de gestión de retiros***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Retiro colones" + "\n");
      System.out.println("2. Retiro dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println(menuRetirarColones());
       
      }
      
      else if (opcion.equals("2")){
          System.out.println(menuRetirarDolares());

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
     
     
     
    public String menuRetirarColones() throws ParseException, MessagingException, Exception{
    String numeroCuenta;
    String pin;
    System.out.println("Indique el número de cuenta al que desea retirar dinero en colones: ");
    numeroCuenta = entrada.nextLine();
    String numeroTelefonico = ControladorCliente.consultarNumeroClientePorCuenta(numeroCuenta);
    int contadorPin = 0;
    while (contadorPin < 2){
         System.out.println("Indique el número PIN asociado a la cuenta: " + "\n" + "intentos restantes: " + (2 - contadorPin));
         pin = entrada.nextLine();
         if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta)){
           return menuPalabraRetirarColones(numeroCuenta, pin, numeroTelefonico);
         }
         else{
           contadorPin++;
         }
      
    }
    
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
    
    public String menuPalabraRetirarColones(String pNumeroCuenta, String pPin, String pNumeroTelefonico) throws Exception{
      String palabraSecreta;
      String monto;
      int contadorPalabra = 0;
      MensajeTexto mensaje = new MensajeTexto();
      mensaje.enviarPalabraSecreta(pNumeroTelefonico);
      while (contadorPalabra < 2){
          System.out.println("Estimado usuario se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digitar la palabra enviada. Tiene " + (2 - contadorPalabra) + " intentos restantes");
          palabraSecreta = entrada.nextLine();
          if (mensaje.corroborarPalabraSecreta(palabraSecreta, pNumeroTelefonico)){
            System.out.println("Ingrese el monto por retirar (en colones): ");
            monto = entrada.nextLine();
            return ControladorCuenta.llamarRetirarColones(pNumeroCuenta, pPin, monto);
          }
          else{
              System.out.println("Palabra incorrecta " + "\n");
              contadorPalabra++;
          }
      }
      return ControladorCuenta.llamarBloquearCuenta("Dos palabras secretas ingresadas de manera errónea y de forma consecutiva",pNumeroCuenta);
    }
    
    //--------------------------------------------------------------
    
    public String menuRetirarDolares() throws ParseException, MessagingException, Exception{
    String numeroCuenta;
    String pin;
    System.out.println("Indique el número de cuenta del que desea retirar dinero en dólares: ");
    numeroCuenta = entrada.nextLine();
    String numeroTelefonico = ControladorCliente.consultarNumeroClientePorCuenta(numeroCuenta);
    int contadorPin = 0;
    while (contadorPin < 2){
         System.out.println("Indique el número PIN asociado a la cuenta: " + "\n" + "intentos restantes: " + (2 - contadorPin));
         pin = entrada.nextLine();
         if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta)){
           return menuPalabraRetirarDolares(numeroCuenta, pin, numeroTelefonico);
         }
         else{
           contadorPin++;
         }
      
    }
    
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
    
    public String menuPalabraRetirarDolares(String pNumeroCuenta, String pPin, String pNumeroTelefonico) throws Exception{
      String palabraSecreta;
      String monto;
      int contadorPalabra = 0;
      MensajeTexto mensaje = new MensajeTexto();
      mensaje.enviarPalabraSecreta(pNumeroTelefonico);
      while (contadorPalabra < 2){
          System.out.println("Estimado usuario se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digitar la palabra enviada. Tiene " + (2 - contadorPalabra) + " intentos restantes");
          palabraSecreta = entrada.nextLine();
          if (mensaje.corroborarPalabraSecreta(palabraSecreta, pNumeroTelefonico)){
            System.out.println("Ingrese el monto por retirar (en dólares): ");
            monto = entrada.nextLine();
            return ControladorCuenta.llamarRetirarDolares(pNumeroCuenta, pPin, monto);
          }
          else{
              System.out.println("Palabra incorrecta " + "\n");
              contadorPalabra++;
          }
      }
      return ControladorCuenta.llamarBloquearCuenta("Dos palabras secretas ingresadas de manera errónea y de forma consecutiva",pNumeroCuenta);
    }
    
    
    
           
     public void menuOtrasOperacionesCuenta() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de otras operaciones***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Registrar cuenta o Cambio PIN" + "\n");
      System.out.println("2. Consultas" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuCrearCuentaCambiarPIN();
       
      }
      
      else if (opcion.equals("2")){
        menuOperacionesTipoCambioOtrasConsultas();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
     
  public void menuCrearCuentaCambiarPIN() throws ParseException, MessagingException{
      System.out.println("\n" + "***Módulo de gestión de cuentas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Registrar cuenta" + "\n");
      System.out.println("2. Cambiar PIN" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();   
      String pin;
      String montoInicialADepositar;
      String identificadorDueno;
      if (opcion.equals("1")){
            
        System.out.println("Ingrese su identificación: ");
        identificadorDueno = entrada.nextLine();   
        
        System.out.println("Ingrese el PIN: ");
        pin = entrada.nextLine(); 
        
        System.out.println("Ingrese el monto inicial a depositar: ");
        montoInicialADepositar = entrada.nextLine();  
        
        System.out.println(ControladorCliente.llamarMetodoRegistrarCuentaPersonaCLI(montoInicialADepositar, pin, identificadorDueno));
        
      }
      
    
      
      else if (opcion.equals("2")){
          System.out.println(menuCambiarPin());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
  
  public String menuCambiarPin() throws ParseException, MessagingException{
    String numeroCuenta;
    String pinActual;
    String nuevoPin;
    System.out.println("Indique el número de cuenta al que desea modificar el número PIN: ");
    numeroCuenta = entrada.nextLine();
    int contador = 0;
    while (contador < 2){
         System.out.println("Indique el número PIN actual: " + "\n" + "intentos restantes: " + (2 - contador));
         pinActual = entrada.nextLine();
      if (ValidacionIntentos.validarCantidadIntentosPin(pinActual, numeroCuenta) == true){
          System.out.println("Ingrese el nuevo PIN: ");
          nuevoPin = entrada.nextLine();
          return ControladorCuenta.llamarCambiarPinCLI(numeroCuenta, pinActual, nuevoPin);         
          
      }
      else{
          contador++;
          
      }
    
    }
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
        
  public void menuOperacionesTipoCambioOtrasConsultas() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Tipo cambio" + "\n");
      System.out.println("2. Otras consultas" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesTipoCambioCompraVenta();  
      }
      
      else if (opcion.equals("2")){
        menuOperacionesConsultaEstadoSaldoEstatusComision();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
      
  }
  
    public void menuOperacionesTipoCambioCompraVenta() throws ParseException{
      
      System.out.println("\n" + "***Módulo de consulta del tipo de cambio***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Valor de compra del dólar" + "\n");
      System.out.println("2. Valor de venta del dólar" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println("El tipo de cambio de compra del dólar es: " + ControladorCuenta.consultarCompraDolar());
       
      }
      
      else if (opcion.equals("2")){
        System.out.println("El tipo de cambio de venta del dólar es: " + ControladorCuenta.consultarVentaDolar());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
    public void menuOperacionesConsultaEstadoSaldoEstatusComision() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar estado de cuenta o listar y consultar cuentas" + "\n");
      System.out.println("2. Saldo y estatus, y consultas comisiones (universo cuenta o cada cuenta)" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesConsultaEstadoYListarCuentas();
      }
      
      else if (opcion.equals("2")){
        menuOperacionesConsultaSaldoEstatusComisiones();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
    public void menuOperacionesConsultaEstadoYListarCuentas() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar estado de cuenta en colones o dólares" + "\n");
      System.out.println("2. Listar o consultar cuentas" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
       menuEstadosDeCuenta();
       
      }
      
      else if (opcion.equals("2")){
       menuListarYConsultarCuentas();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
  
  public void menuEstadosDeCuenta() throws ParseException, MessagingException{
      System.out.println("\n" + "***Módulo de gestión de estado de cuenta***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar estado de cuenta en colones " + "\n");
      System.out.println("2. Consultar estado de cuenta en dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println(menuEstadoCuentaColones());
      }
      
      else if (opcion.equals("2")){
          System.out.println(menuEstadoCuentaDolares());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }    
  }
  
  public String menuEstadoCuentaColones() throws MessagingException{
      String numeroCuenta;
      String pin;
      System.out.println("Ingrese el número de cuenta del que desea consultar el estado en colones: ");
      numeroCuenta = entrada.nextLine();
      int contador = 0;
      while (contador < 2){
         System.out.println("Indique el número PIN actual: " + "\n" + "intentos restantes: " + (2 - contador));
         pin = entrada.nextLine();
        if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta) == true){
        
          return ControladorCuenta.llamarGenerarEstadoCuentaColones(numeroCuenta, pin);
 
        }
        else{
           contador++;  
        }
  }
      return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
  
    public String menuEstadoCuentaDolares() throws MessagingException{
      String numeroCuenta;
      String pin;
      System.out.println("Ingrese el número de cuenta del que desea consultar el estado en colones: ");
      numeroCuenta = entrada.nextLine();
      int contador = 0;
      while (contador < 2){
         System.out.println("Indique el número PIN actual: " + "\n" + "intentos restantes: " + (2 - contador));
         pin = entrada.nextLine();
        if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta) == true){
        
          return ControladorCuenta.llamarGenerarEstadoCuentaDolares(numeroCuenta, pin);
 
        }
        else{
           contador++;  
        }
  }
      return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
   
    
  public void menuListarYConsultarCuentas() throws ParseException{
      System.out.println("\n" + "***Módulo de gestión de cuentas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Listar cuentas" + "\n");
      System.out.println("2. Consultar cuenta en particular" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println(ControladorCuenta.listarCuentas());
      }
      
      else if (opcion.equals("2")){
        String numeroCuenta;
          System.out.println("Indique el número de cuenta que desea consultar");
          numeroCuenta = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarConsultarCuentaParticular(numeroCuenta));
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }   
  }  
    
  public void menuOperacionesConsultaSaldoEstatusComisiones() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar saldo o estatus" + "\n");
      System.out.println("2. Consultar comisiones de todo el universo de cuentas o de una cuenta en particular" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesConsultaSaldoEstatus();
      }
      
      else if (opcion.equals("2")){
        menuOperacionesComisionesUniversoUnica();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
      
  }
  
    public void menuOperacionesConsultaSaldoEstatus() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar saldo" + "\n");
      System.out.println("2. Consultar estatus" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesConsultaSaldoColonesDolares();
      }
      
      else if (opcion.equals("2")){
        String numeroCuenta;
          System.out.println("Ingrese el número de cuenta del cual desea conocer el estatus: ");
          numeroCuenta = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarConsultarEstatus(numeroCuenta));
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
  public void menuOperacionesConsultaSaldoColonesDolares() throws ParseException, MessagingException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar saldo en colones" + "\n");
      System.out.println("2. Consultar saldo en dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");  
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println(menuConsultarSaldoColones());
       
      }
      
      else if (opcion.equals("2")){
          System.out.println(menuConsultarSaldoDolares());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
      
  }
  
  public String menuConsultarSaldoColones() throws MessagingException{
    String numeroCuenta;
    String pin;
    System.out.println("Ingrese el número de cuenta de la que desea consultar su saldo en colones: ");
    numeroCuenta = entrada.nextLine();
    int contador = 0;
    while (contador < 2){
         System.out.println("Indique el número PIN actual: " + "\n" + "intentos restantes: " + (2 - contador));
         pin = entrada.nextLine();
      if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta) == true){
        return ControladorCuenta.llamarConsultarSaldoColones(numeroCuenta, pin);
          
      }
      else{
          contador++;
      }
    }
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
  
  
  //-------------------------------
  
  public String menuConsultarSaldoDolares() throws MessagingException{
    String numeroCuenta;
    String pin;
    System.out.println("Ingrese el número de cuenta de la que desea consultar su saldo en dólares: ");
    numeroCuenta = entrada.nextLine();
    int contador = 0;
    while (contador < 2){
         System.out.println("Indique el número PIN actual: " + "\n" + "intentos restantes: " + (2 - contador));
         pin = entrada.nextLine();
      if (ValidacionIntentos.validarCantidadIntentosPin(pin, numeroCuenta) == true){
        return ControladorCuenta.llamarConsultarSaldoDolares(numeroCuenta, pin);
          
      }
      else{
          contador++;
      }
    }
    return ControladorCuenta.llamarBloquearCuenta("Dos intentos consecutivos fallidos de PIN", numeroCuenta);
  }
  public void menuOperacionesComisionesUniversoUnica() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Comisiones para el universo de cuentas" + "\n");
      System.out.println("2. Comisiones para una cuenta" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n"); 
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesComisionesUniversoCuentas();
      }
      
      else if (opcion.equals("2")){
        menuOperacionesComisionesCuentaUnica();
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
    
  public void menuOperacionesComisionesUniversoCuentas() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Total comisiones para depósitos o retiros para todo el universo de cuentas" + "\n");
      System.out.println("2. Total comisiones para depósitos y retiros para todo el universo de cuentas" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesTotalRetirosDepositosUniverso();
      }
      
      else if (opcion.equals("2")){
          System.out.println(ControladorCuenta.llamarCalcularComisionesDepositosYRetirosUniversoCuentas());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
  public void menuOperacionesTotalRetirosDepositosUniverso() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Total depósitos del universo de cuentas" + "\n");
      System.out.println("2. Total retiros del universo de cuentas" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n"); 
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          System.out.println(ControladorCuenta.llamarCalcularComisionesDepositosUniversoCuentas());   
      }
      
      else if (opcion.equals("2")){
          System.out.println(ControladorCuenta.llamarCalcularComisionesRetirosUniversoCuentas());
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
    public void menuOperacionesComisionesCuentaUnica() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Total comisiones para depósitos o retiros para una cuenta" + "\n");
      System.out.println("2. Total comisiones para depósitos y retiros para una cuenta" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");   
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        menuOperacionesTotalRetirosDepositosCuentaUnica();
      }
      
      else if (opcion.equals("2")){
          String numeroCuenta;
          System.out.println("Ingrese el número de cuenta de la cual desea consultar el total de comisiones por concepto de depósitos y retiros: ");
          numeroCuenta = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarcalcularComisionesTotalesCuantaUnica(numeroCuenta));
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
  public void menuOperacionesTotalRetirosDepositosCuentaUnica() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Total depósitos de una cuenta" + "\n");
      System.out.println("2. Total retiros de una cuenta" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n"); 
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          String numeroCuenta;
          System.out.println("Ingrese el número de cuenta de la cual desea consultar el total de comisiones por concepto de depósitos: ");
          numeroCuenta = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarCalcularComisionesDepositosCuentaUnica(numeroCuenta));
      }
      
      else if (opcion.equals("2")){
          String numeroCuenta;
          System.out.println("Ingrese el número de cuenta de la cual desea consultar el total de comisiones por concepto de retiros: ");
          numeroCuenta = entrada.nextLine();
          System.out.println(ControladorCuenta.llamarCalcularComisionesRetirosCuentaUnica(numeroCuenta));
        //Falta metodo calcular total comisiones por retiros de una cuenta
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
  
  
    
    
    
}
