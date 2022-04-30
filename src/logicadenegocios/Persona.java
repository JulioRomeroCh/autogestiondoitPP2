package logicadenegocios;

import java.util.ArrayList;
import java.util.Date;

public /*abstract*/ class Persona /*implements Comparable*/{
    
  protected String identificacion;
  protected String nombre;
  protected String primerApellido;
  protected String segundoApellido;
  protected Date fechaNacimiento;
  
  protected ArrayList<Cuenta> cuentas;
  
  public Persona (String pIdentificacion, String pNombre, String pPrimerApellido,
      String pSegundoApellido, Date pFechaNacimiento) {
      
    this.identificacion = pIdentificacion;   
    this.nombre = pNombre;
    this.primerApellido = pPrimerApellido;
    this.segundoApellido = pSegundoApellido;
    this.fechaNacimiento = pFechaNacimiento;
    
    cuentas = new ArrayList<Cuenta>();
    
  } 
  
  public void registrarCuenta(double pSaldo, String pPin){
       
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
  
}

