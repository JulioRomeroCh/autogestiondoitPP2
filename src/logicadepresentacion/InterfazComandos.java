package logicadepresentacion;

import java.text.ParseException;
   import java.util.Scanner;
import javax.mail.MessagingException;
import logicadeaccesoadatos.CuentaDao;
   import logicadeintegracion.*;
import logicadevalidacion.*;

public class InterfazComandos {
    
     Scanner entrada = new Scanner(System.in);
    
 public void ejecutarMenuPrincipal() throws ParseException, MessagingException{
   boolean mostrarMenu = true;
   while (mostrarMenu == true){
     mostrarMenu = menuPrincipal();
   }
 }
     
  public boolean menuPrincipal() throws ParseException, MessagingException{
      
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
  
  public void menuCuenta() throws ParseException, MessagingException{
      
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
  
    public void menuTransaccionesConDinero() throws ParseException{
      
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
        //Falta método para transferir fondos

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
   public void menuDepositosYRetiros() throws ParseException{
      
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
        //Falta método depositar en colones
       
      }
      
      else if (opcion.equals("2")){
        //Falta método para depositar en dolares
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
  }
      
     public void menuRetirosCuenta() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de retiros***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Retiro colones" + "\n");
      System.out.println("2. Retiro dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
       //Falta método para retirar en colones
       
      }
      
      else if (opcion.equals("2")){
        //Falta método para retirar en dólares

      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

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
      CuentaDao nuevoDaoCuenta = new CuentaDao();
      if (opcion.equals("1")){
          
        System.out.println("Ingrese su identificación: ");
        identificadorDueno = entrada.nextLine();   
        
        System.out.println("Ingrese el PIN: ");
        pin = entrada.nextLine(); 
        
        System.out.println("Ingrese el monto inicial a depositar: ");
        montoInicialADepositar = entrada.nextLine();  
        
        System.out.println(ControladorCliente.llamarMetodoRegistrarCuentaPersonaCLI(Integer.parseInt(montoInicialADepositar), pin, identificadorDueno));
      
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
    return ControladorCuenta.llamarBloquearCuenta(numeroCuenta);
  }
        
  public void menuOperacionesTipoCambioOtrasConsultas() throws ParseException{
      
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
        //Falta operacion para obtener compra dolar
       
      }
      
      else if (opcion.equals("2")){
        //Falta operacion para obtener venta dolar
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
    public void menuOperacionesConsultaEstadoSaldoEstatusComision() throws ParseException{
      
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
    
    public void menuOperacionesConsultaEstadoYListarCuentas() throws ParseException{
      
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
  
  public void menuEstadosDeCuenta() throws ParseException{
      System.out.println("\n" + "***Módulo de gestión de estado de cuenta***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar estado de cuenta en colones " + "\n");
      System.out.println("2. Consultar estado de cuenta en dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");
      
      String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
          //Falta método
      }
      
      else if (opcion.equals("2")){
        //Falta método consultar 
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }    
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
    
  public void menuOperacionesConsultaSaldoEstatusComisiones() throws ParseException{
      
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
  
    public void menuOperacionesConsultaSaldoEstatus() throws ParseException{
      
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
        //Falta metodo consultar estatus
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

  }
    
  public void menuOperacionesConsultaSaldoColonesDolares() throws ParseException{
      
      System.out.println("\n" + "***Módulo de gestión de consultas***" + "\n");
      System.out.println("Por favor, seleccione una de las siguientes opciones: " + "\n");
      System.out.println("1. Consultar saldo en colones" + "\n");
      System.out.println("2. Consultar saldo en dólares" + "\n");
      System.out.println("Presione cualquier otra tecla para volver al menú principal" + "\n");  
      
        String opcion = entrada.nextLine();    
      if (opcion.equals("1")){
        //Falta metodo para consultar saldo colones
       
      }
      
      else if (opcion.equals("2")){
        //Falta metodo para consultar saldo dolares
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }
      
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
        //Falta funbcionalidad para total comisiones de depositos Y retiros del UC
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
        //Falta metodo total comisiones por depositos del UC      
      }
      
      else if (opcion.equals("2")){
        //Falta metodo total comisiones por retiros del UC
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
        //Falta funcionalidad calcular comisiones totales por depositos Y retiros cuenta unica
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
        //Falta metodo calcular total comisiones por depositos de una cuenta
      }
      
      else if (opcion.equals("2")){
        //Falta metodo calcular total comisiones por retiros de una cuenta
      }
      
      else{
        System.out.println("\n" + "Entrada erronea" + "\n");
      }

      
  }
  
  
  
    
    
    
}
