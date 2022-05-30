/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicacreacional;

import logicadenegocios.*;
/**
 *
 * @author Jose Blanco
 */
public class SimplePersonaFactory{
    
  public static Persona crearPersona(String pTipo) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
  
    Persona nuevaPersona = null;
    nuevaPersona = (Persona) Class.forName(pTipo).newInstance();
    return nuevaPersona;
  }
}
