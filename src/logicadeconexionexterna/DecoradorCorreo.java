/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeconexionexterna;

/**
 *
 * @author Jose Blanco
 */
public abstract class DecoradorCorreo implements ICorreoElectronico{
    
  protected ICorreoElectronico correoElectronico;
  
 
  @Override
  public abstract String crearTextoCorreo (String pMotivo);
    
}
