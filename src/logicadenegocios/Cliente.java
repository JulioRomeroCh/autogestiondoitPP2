package logicadenegocios;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import logicadeaccesoadatos.*;


public class Cliente extends Persona{
   
    
  public String codigo;
  public static int cantidadClientes = 0;
  public String numeroTelefonico;
  public String correoElectronico;
  
  /**
   * <p> Constructor de la clase Cliente.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento: atributo de tipo Date.
   * @param pNumeroTelefonico: atributo de tipo String.
   * @param pCorreoElectronico : atributo de tipo String.
   */
  public Cliente(String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido,
    Date pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico){
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    this.cantidadClientes += 1;
    this.codigo = "CIF_" + cantidadClientes;        
    this.numeroTelefonico = pNumeroTelefonico;
    this.correoElectronico = pCorreoElectronico;
  }
  
  
  /**
   * <p> Constructor por defecto de la clase Cliente.
   */
  public Cliente (){
    this.cantidadClientes += 1;
    this.codigo = "CIF_" + cantidadClientes;  
  }
  /**
   * <p> Constructor sobrecargado de la clase Cliente.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento: atributo de tipo Date.
   * @param pCodigo: atributo de tipo String.
   * @param pNumeroTelefonico: atributo de tipo String.
   * @param pCorreoElectronico : atributo de tipo String.
   */
  public Cliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
    Date pFechaNacimiento, String pCodigo, String pNumeroTelefonico, String pCorreoElectronico){
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    this.cantidadClientes += 1;
    this.codigo = pCodigo;        
    this.numeroTelefonico = pNumeroTelefonico;
    this.correoElectronico = pCorreoElectronico;
  }
  
  /**
   * <p> Método que crea una cuenta y la asocia al cliente.
   * @param pMonto: atributo de la cuenta de tipo double, indica el saldo inicial de la cuenta.
   * @param pPin: atributo de la cuenta de tipo String.
   * @param pIdentificacion: String que representa la identificación del cliente dueño de la cuenta.
   * @return String que contiene la información de la nueva cuenta.
   */
  public String registrarCuenta(double pMonto, String pPin, String pIdentificacion){
    try{
    logicadenegocios.Cuenta nuevaCuenta;
    nuevaCuenta = logicacreacional.SimpleCuentaFactory.crearCuenta();
    nuevaCuenta.setSaldo(pMonto);
    nuevaCuenta.setPin(pPin);
    super.cuentas.add(nuevaCuenta);
    java.util.Date fechaActual = new Date();
    CuentaDao.insertarCuenta(nuevaCuenta.getNumeroCuenta(), fechaActual, pMonto, pPin, "activa");
    PersonaDao.insertarPersonaTieneCuenta(pIdentificacion, nuevaCuenta.getNumeroCuenta());
    
    DecimalFormat formatoDosDecimales = new DecimalFormat("#.00");
    
    String mensaje = "";
    
    mensaje = "Se ha creado una nueva cuenta en el sistema, los datos de la cuenta son:" + "\n";
    mensaje+= "Número de cuenta: " + nuevaCuenta.getNumeroCuenta() + "\n";
    mensaje+= "Estatus de la cuenta: " + nuevaCuenta.getEstatus() + "\n";
    
    mensaje+= "Saldo actual: " + formatoDosDecimales.format(nuevaCuenta.getSaldo()) + "\n";
    mensaje+= "---" + "\n";
    mensaje+= "Nombre del dueño de la cuenta: " + getNombre() + " " + getPrimerApellido() + " " + getSegundoApellido() + "\n";
    mensaje+= "Número del teléfono 'asociado' a la cuenta: " + numeroTelefonico + "\n";
    mensaje+= "Dirección de correo electrónico 'asociada' a la cuenta: " + correoElectronico + "\n";
           
    return mensaje;   
  }
  catch (Exception error){
      System.out.println("Error al instanciar una cuenta en Cliente, registrarCuenta");
      return "";
  }   
 }
  
  /**
   * <p> Método que registra un cliente en la base de datos.
   * @return String que contiene la información del nuevo cliente registrado.
   */
  public String registrarPersona(){
   
    PersonaDao.insertarPersona(super.identificacion, super.nombre, super.primerApellido, super.segundoApellido, 
    super.fechaNacimiento, "Cliente");
    ClienteDao.insertarCliente(codigo, numeroTelefonico, correoElectronico);
    PersonaDao.insertarClienteEsPersona(super.identificacion, this.codigo);
   
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    String fechaNacimiento = formatoFecha.format(super.fechaNacimiento);
   
    String mensaje = "Se ha creado un nuevo cliente en el sistema, los datos que fueron almacenados son: " + "\n";
    mensaje+= "Código: " + this.codigo+ "\n";
    mensaje+= "Nombre: " + super.nombre + " " + super.primerApellido + " " + super.segundoApellido + "\n";
    mensaje+= "Identificación: " + super.identificacion + "\n";
    mensaje+= "Fecha de Nacimiento: " + fechaNacimiento + "\n";
    mensaje+= "Número telefónico: " + this.numeroTelefonico+ "\n";  
    return mensaje;   
  }
  /**
   * <p> Método que retorna la información asociada al cliente.
   * @return String que contiene: código, teléfono, correo electrónico, apellidos, nombre e identificación
   * del cliente.
   */
  public String toString(){
    
    String mensaje = "";
    
    mensaje = "Código: " + this.codigo + "\n";
    mensaje+= "Número telefónico: " + this.numeroTelefonico + "\n";
    mensaje+= "Correo electrónico: " + this.correoElectronico + "\n";
    mensaje+= super.toString() + "\n";
    
    return mensaje;
  }    
  /**
   * <p> Método accesor del atributo segundoApellido.
   * @return String que contiene el segundo apellido del cliente,
   */  
  public String getSegundoApellido(){
    return this.segundoApellido;
  }
  
  /**
   * <p> Método accesor del atributo nombre.
   * @return String que contiene el nombre del cliente,
   */    
  public String getNombre(){
    return this.nombre;
  }
  

  
  
  
  
}
