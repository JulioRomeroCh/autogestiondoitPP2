
package logicadenegocios;

import java.util.Date;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.awt.*;
import logicadeaccesoadatos.BitacoraDao;

public class TramaPlana extends Bitacora{
  
    
   private String ruta = "D:\\Escritorio\\bitacoraplana.txt";
   
   
  public TramaPlana (Cuenta pCuenta){
    super.cuenta = pCuenta;  
    this.cuenta.anadirAccionBitacora(this);
  }
  
  @Override
  public void agregarAccion (String pAccion, String pVista){
      this.accion = pAccion;
      this.fechaConHora = new Date();
      this.vistaAccedida = pVista;

    }
    
   @Override
    public void crearBitacora(String pCondicion){
      String texto = "";
      File archivo = new File(ruta);
      try{
      if (!archivo.exists()){
        archivo.createNewFile();
      }
      
      texto += crearTextoBitacoraSegunCondicion(pCondicion);
      
      FileWriter documento = new FileWriter(archivo);
      BufferedWriter escritor = new BufferedWriter(documento);
      escritor.write(texto);
      escritor.close();

      
          System.out.println("Bitácora generada con éxito");
      }
      
      catch (Exception error){
          System.out.println("Error en la bitácora de trama plana");
      }
      
    }
    
    
    private String crearTextoBitacoraSegunCondicion(String pCondicion){
       
      String texto = "";  
      
      if (pCondicion.equals("todos")){
        texto += BitacoraDao.recorrerConsultaBitacoraTodaVistaTramaPlana();
      }
      
      else if (pCondicion.equals("hoy")){
        texto += BitacoraDao.recorrerConsultaBitacoraHoyTramaPlana();
      }
      
      else{
        texto += BitacoraDao.recorrerConsultaBitacoraSegunVistaTramaPlana(pCondicion);
      }  
      return texto;
    }
    
    
    public void abrirArchivo(){
     try{
       File archivo = new File(ruta);
       
       Desktop escritorio = Desktop.getDesktop();
       if (archivo.exists()){
         escritorio.open(archivo);
           System.out.println("Archivo abierto");
       }
     }
     
     catch (Exception error){
         System.out.println("Error al abrir la bitácora, trama plana");
     }
    }
  
  
}
