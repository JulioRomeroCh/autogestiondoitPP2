package logicadenegocios;

import java.util.ArrayList;
import java.util.Date;
import logicadeaccesoadatos.*;

public class Persona implements Comparable{
    
  protected String identificacion;
  protected String nombre;
  protected String primerApellido;
  protected String segundoApellido;
  protected Date fechaNacimiento;
  
  public ArrayList<Cuenta> cuentas;
  /**
   * <p> Constructor de la clase Persona.
   * @param pIdentificacion: atributo de tipo String.
   * @param pNombre: atributo de tipo String.
   * @param pPrimerApellido: atributo de tipo String.
   * @param pSegundoApellido: atributo de tipo String.
   * @param pFechaNacimiento : atributo de tipo Date.
   */
  public Persona (String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido, Date pFechaNacimiento) {
      
    cuentas = new ArrayList<Cuenta>();        
    this.identificacion = pIdentificacion;   
    this.nombre = pNombre;
    this.primerApellido = pPrimerApellido;
    this.segundoApellido = pSegundoApellido;
    this.fechaNacimiento = pFechaNacimiento;
     
  } 
  /**
   * <p> Constructor de la clase Persona que inicializa el ArrayList "cuentas".
   */ 
  public Persona(){
   cuentas = new ArrayList<Cuenta>();   
  }
  
  /**
   * <p> Método que registra una persona a nivel de base de datos.
   * @return String que contiene un mensaje de confirmación de la inserción.
   */
  public String registrarPersona(){
       
   PersonaDao.insertarPersona(this.identificacion, this.nombre, this.primerApellido, this.segundoApellido, 
       this.fechaNacimiento, "Usuario");  
   String mensaje = "Se ha creado un nuevo usuario en el sistema, los datos que fueron almacenados son: " + "\n";
   mensaje+= this.toString();  
   return mensaje;
   
  }
  
  /**
   * <p> Método que registra una cuenta y la asocia a una persona.
   * @param pMonto: double que representa el saldo inicial de la cuenta.
   * @param pPin: String que representa el Pin de acceso a la cuenta.
   * @param pIdentificacion: String que indica la persona dueña de la cuenta.
   * @return String con mensaje de confirmación de la inserción.
   */
  public String registrarCuenta(double pMonto, String pPin, String pIdentificacion){
    try{
    Cuenta nuevaCuenta;
    nuevaCuenta = logicacreacional.SimpleCuentaFactory.crearCuenta();
    nuevaCuenta.setSaldo(pMonto);
    nuevaCuenta.setPin(pPin);
    cuentas.add(nuevaCuenta);
    CuentaDao.insertarCuenta(nuevaCuenta.getNumeroCuenta(),new java.util.Date(), pMonto, pPin, "activa");
    PersonaDao.insertarPersonaTieneCuenta(pIdentificacion, nuevaCuenta.getNumeroCuenta());
    
    String mensaje = "";
    
    mensaje = "Se ha creado una nueva cuenta en el sistema, los datos de la cuenta son:" + "\n";
    mensaje+= "Número de cuenta: " + nuevaCuenta.getNumeroCuenta() + "\n";
    mensaje+= "Estatus de la cuenta: " + nuevaCuenta.getEstatus() + "\n";
    mensaje+= "Saldo actual: " + nuevaCuenta.getSaldo() + ".00" + "\n";
    mensaje+= "---" + "\n";
    mensaje+= "Nombre del dueño de la cuenta: " + this.nombre + " " + this.primerApellido + " " + this.segundoApellido + "\n";
    mensaje+= "Número del teléfono 'asociado' a la cuenta: ";
    mensaje+= "Dirección de correo electrónico 'asociada' a la cuenta: ";
           
    return mensaje;
     
  }
    catch (Exception error){
        System.out.println("Error al instanciar una cuenta en Persona, registrarCuenta");
        return "";
    }
 }
  
  /**
   * <p> Método que retorna la información asociada a la persona.
   * @return String que contiene: apellidos, nombre, identificación y fecha de nacimiento de la persona.
   */  
  public String toString(){
   
    String mensaje = "";
    
    mensaje = "Identificación: " + this.identificacion + "\n";
    mensaje+= "Nombre: " + this.nombre + "\n";
    mensaje+= "Primer apellido: " + this.primerApellido + "\n";
    mensaje+= "Segundo apellido: " + this.segundoApellido + "\n";
    mensaje+= "Fecha nacimiento: " + this.fechaNacimiento + "\n";
    
    return mensaje;
       
  }
  
  /**
   * <p> Método accesor del atributo primerApellido.
   * @return String que contiene el primero apellido de la persona,
   */    
  public String getPrimerApellido(){
    return this.primerApellido;
  }
  
  /**
   * <p> Método accesor del atributo identificacion.
   * @return String que contiene la identificación de la persona,
   */      
  public String getIdentificador(){
    return this.identificacion;
  }
  
  /**
   * <p> Método accesor del atributo segundoApellido.
   * @return String que contiene el segundo apellido de la persona,
   */   
  public String getSegundoApellido(){
    return this.segundoApellido;
  }
  
  /**
   * <p> Método accesor del atributo nombre.
   * @return String que contiene el nombre de la persona,
   */     
  public String getNombre(){
    return this.nombre;
  }
  
  /**
   * <p> Método accesor del atributo cuentas.
   * @return ArrayList<Cuenta> que contiene las cuentas de la persona,
   */     
  public ArrayList<Cuenta> getCuentas(){
    return this.cuentas;
  }
  
  /**
   * <p> Método que compara los apellidos de dos personas, de forma ascendente.
   * @param pPersona: Comparable que se contrasta con la persona proveedora del método.
   * @return boolean que indica si el apellido de la persona es menor alfabéticamente al parámetro 
   * pPersona.
   */
  public boolean comparar (Comparable pPersona){
    Persona nuevaPersona = (Persona) pPersona;
    
    if (this.getPrimerApellido().compareTo(nuevaPersona.getPrimerApellido()) < 1){
    
      return true;
    }
    
    else{
      return false;
    }
  }
  
  public void setIdentificador(String pIdentificador){
    this.identificacion = pIdentificador;
  }
  
  public void setNombre(String pNombre){
    this.nombre = pNombre;
  }
  
  public void setPrimerApellido (String pPrimerApellido){
    this.primerApellido = pPrimerApellido;  
  }
  
  public void setSegundoApellido (String pSegundoApellido){
    this.segundoApellido = pSegundoApellido;
  }
  
  public void setFechaNacimiento (Date pFechaNacimiento){
    this.fechaNacimiento = pFechaNacimiento;
  }
  
}



