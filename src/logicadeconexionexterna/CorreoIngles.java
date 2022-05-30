package logicadeconexionexterna;



public class CorreoIngles extends DecoradorCorreo{
    
  public CorreoIngles (ICorreoElectronico pCorreoElectronico){
   super.correoElectronico = pCorreoElectronico;
  }
 
  @Override
  public String crearTextoCorreo(String pMotivo){
      try {
          return super.correoElectronico.crearTextoCorreo(Traductor.traducirEspanolIngles(pMotivo));
      } catch (Exception error) {
          System.out.println("Error al traducir texto");
          error.printStackTrace();
          return "";
      }
    
  }
  
}
