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
  
  public Persona (String pIdentificacion, String pNombre, String pPrimerApellido, String pSegundoApellido, Date pFechaNacimiento) {
      
    cuentas = new ArrayList<Cuenta>();        
    this.identificacion = pIdentificacion;   
    this.nombre = pNombre;
    this.primerApellido = pPrimerApellido;
    this.segundoApellido = pSegundoApellido;
    this.fechaNacimiento = pFechaNacimiento;
     
  } 
  
  public Persona(){
   cuentas = new ArrayList<Cuenta>();   
  }
  
  public String registrarPersona(){
       
   PersonaDao.insertarPersona(this.identificacion, this.nombre, this.primerApellido, this.segundoApellido, this.fechaNacimiento, "Usuario");  
   String mensaje = "Se ha creado un nuevo usuario en el sistema, los datos que fueron almacenados son: " + "\n";
   mensaje+= this.toString();  
   return mensaje;
   
  }
  
  public String registrarCuenta(double pMonto, String pPin, String pIdentificacion){
    Cuenta nuevaCuenta = new Cuenta(pMonto, pPin);
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
  
  
  /**
   * Método toString: coloca los atributos de un objeto en una misma cadena de caracteres.
   * 
   * @return mensaje: String que posee los valores de cada atributo
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
  
  public String getPrimerApellido(){
    return this.primerApellido;
  }
  
  public String getIdentificador(){
    return this.identificacion;
  }
  
  public String getSegundoApellido(){
    return this.segundoApellido;
  }
  
  public String getNombre(){
    return this.nombre;
  }
  
  public ArrayList<Cuenta> getCuentas(){
    return this.cuentas;
  }
  
  public boolean comparar (Comparable pPersona){
    Persona nuevaPersona = (Persona) pPersona;
    
    if (this.getPrimerApellido().compareTo(nuevaPersona.getPrimerApellido()) < 1){
    
      return true;
    }
    
    else{
      return false;
    }
  }
}



