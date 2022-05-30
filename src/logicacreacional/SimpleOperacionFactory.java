/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicacreacional;

import logicadenegocios.Operacion;

/**
 *
 * @author Jose Blanco
 */
public class SimpleOperacionFactory {
  
  public static Operacion crearOperacion() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
    Operacion nuevaOperacion = null;
    nuevaOperacion = (Operacion) Class.forName("logicadenegocios.Operacion").newInstance();
    return nuevaOperacion;
  }
}
