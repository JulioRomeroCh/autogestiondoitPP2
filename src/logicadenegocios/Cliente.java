package logicadenegocios;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import logicadeaccesoadatos.*;


public class Cliente extends Persona{
   
    
  private String codigo;
  private static int cantidadClientes = 0;
  public String numeroTelefonico;
  public String correoElectronico;
  
  public Cliente(String pIdentificacion, String pNombre,  String pPrimerApellido, String pSegundoApellido,
      Date pFechaNacimiento, String pNumeroTelefonico, String pCorreoElectronico){
  
    super(pIdentificacion, pNombre, pPrimerApellido, pSegundoApellido, pFechaNacimiento);
    this.cantidadClientes += 1;
    this.codigo = "CIF_" + cantidadClientes;        
    this.numeroTelefonico = pNumeroTelefonico;
    this.correoElectronico = pCorreoElectronico;
  }
  
  public Cliente (){
      
  }
  
  
    public String registrarCuenta(double pMonto, String pPin, String pIdentificacion){
    logicadenegocios.Cuenta nuevaCuenta = new logicadenegocios.Cuenta(pMonto, pPin);
    super.cuentas.add(nuevaCuenta);
    java.sql.Date fechaSQL = new java.sql.Date((new Date()).getDate());
    CuentaDao.insertarCuenta(nuevaCuenta.getNumeroCuenta(), fechaSQL, pMonto, pPin, "activa");
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
  
  public String registrarPersona(){
   
   PersonaDao.insertarPersona(super.identificacion, super.nombre, super.primerApellido, super.segundoApellido, super.fechaNacimiento, "Cliente");
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
  
  public String toString(){
    
    String mensaje = "";
    
    mensaje = "Código: " + this.codigo + "\n";
    mensaje+= "Número telefónico: " + this.numeroTelefonico + "\n";
    mensaje+= "Correo electrónico: " + this.correoElectronico + "\n";
    mensaje+= super.toString() + "\n";
    
    return mensaje;
  }    
    
  public String getSegundoApellido(){
    return this.segundoApellido;
  }
  
  public String getNombre(){
    return this.nombre;
  }
  

  
}
