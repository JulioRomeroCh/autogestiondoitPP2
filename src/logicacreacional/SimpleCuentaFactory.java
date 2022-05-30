/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicacreacional;

import logicadenegocios.Cuenta;

/**
 *
 * @author Jose Blanco
 */
public class SimpleCuentaFactory {
    
  public static Cuenta crearCuenta() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
    Cuenta nuevaCuenta = null;
    nuevaCuenta = (Cuenta) Class.forName("logicadenegocios.Cuenta").newInstance();
    return nuevaCuenta;
  }
}
