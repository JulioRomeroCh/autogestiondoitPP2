package logicadeaccesoadatos;

import java.sql.*;
public class Conexion {

  Connection conectar = null;
  /**
   * <p> Método que conecta la aplicación a Mysql, usando las credenciales necesarias.
   * @return Objeto de tipo Connection.
   */
  public Connection conectar(){
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/autogestiondoit", "root", "260301");
      //conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/autogestiondoit", "root", "1234");
      //System.out.println("Contectó correctamente");
    }
    catch(ClassNotFoundException | SQLException error){
    //System.out.println("No se pudo establecer la conexión");
    }
    return conectar;
  }
 
}
