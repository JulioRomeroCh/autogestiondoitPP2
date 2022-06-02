
package logicadenegocios;

import java.util.Date;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.awt.*;
import java.util.ArrayList;
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

      

      
      texto += recorrerLista(pCondicion);
      
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
    
    
    private ArrayList<ArrayList<String>> crearTextoBitacoraSegunCondicion(String pCondicion){
       
      ArrayList<ArrayList<String>> listaFinal  = new ArrayList<ArrayList<String>>();  
      
      if (pCondicion.equals("todos")){
        listaFinal = BitacoraDao.recorrerConsultaBitacoraTodaVista();
      }
      
      else if (pCondicion.equals("hoy")){
        listaFinal = BitacoraDao.recorrerConsultaBitacoraHoy();
      }
      
      else{
        listaFinal = BitacoraDao.recorrerConsultaBitacoraSegunVista(pCondicion);
      }  
      return listaFinal;
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
    
  
    private String generarPalabraPosicional (String pPalabra, int pLargoDefinido){
      
      int largoPalabra = pPalabra.length();
      String resultado = pPalabra;
      
      for (int contador = largoPalabra; contador <= pLargoDefinido; contador++){
        resultado += " ";
      }
      return resultado;
    }
    
    private String recorrerLista(String pCondicion){
      
      String textoFinal = "";
      int largoDefinidoFechaConHora = 17;
      int largoDefinidoAccion = 16;
      int largoDefinidoVista = 6;
      
      ArrayList<Integer> listaLargos = new ArrayList<Integer>();
      listaLargos.add(largoDefinidoAccion);
      listaLargos.add(largoDefinidoFechaConHora);
      listaLargos.add(largoDefinidoVista);
      
      ArrayList<ArrayList<String>> listaFinal = crearTextoBitacoraSegunCondicion(pCondicion);
      
      for (int contador = 0; contador != listaFinal.size(); contador++){
        for (int indice = 0; indice != listaFinal.get(contador).size(); indice++){
           textoFinal += generarPalabraPosicional(listaFinal.get(contador).get(indice), listaLargos.get(indice));
        }
        textoFinal += "\r\n";
      }
      return textoFinal;
    }
    
  
  
}
