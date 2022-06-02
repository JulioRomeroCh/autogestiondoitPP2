package logicadenegocios;

import java.util.Date;

import java.awt.Desktop;
import logicadenegocios.Cuenta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import logicadeaccesoadatos.BitacoraDao;


public class Csv extends Bitacora{
    
    private String ruta = "D:\\Escritorio\\PruebaCSV.csv";
    
  public Csv (Cuenta pCuenta){
    this.cuenta = pCuenta;
    this.cuenta.anadirAccionBitacora(this);
  }
  
   @Override
    public void agregarAccion(String pAccion,String pVista){
        try{
            this.accion = pAccion;
            this.fechaConHora = new Date();
            this.vistaAccedida = pVista;
            
        }
        
        catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
    
    @Override
    public void crearBitacora(String pCondicion){
        
        if (pCondicion.equals("todos")){
            consultarBitacoraTodaVistaCSV();
        }
        else if(pCondicion.equals("hoy")){
            consultarBitacoraHoyCSV();
        }
        else{
            consultarBitacoraSegunVistaCSV(pCondicion);
        }
        
    }
    
    private void consultarBitacoraSegunVistaCSV(String pVista){
      try{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraSegunVista(pVista);
      for (int indice=0;indice!=listaTotal.size();indice++){
        System.out.println(listaTotal.get(indice).get(0));
        stringBuilder.append(listaTotal.get(indice).get(0)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(1)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(2)+ ",");
        stringBuilder.append('\n');
        
        System.out.println("Terminó la consulta!");        
      }
      writer.write(stringBuilder.toString());
      writer.close(); 
      }
      catch (Exception error){
          System.out.println("Error al consultar bitácora según vista CSV");
      }
    }
    
    
    private void consultarBitacoraTodaVistaCSV(){
      try{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraTodaVista();
      for (int indice=0;indice!=listaTotal.size();indice++){
        stringBuilder.append(listaTotal.get(indice).get(0)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(1)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(2)+ ",");
        stringBuilder.append('\n');
        writer.write(stringBuilder.toString());  
      }
      writer.close();  
      }
        catch (Exception error){
          System.out.println("Error al consultar bitácora toda vista CSV");
      }
    }

    private void consultarBitacoraHoyCSV(){
      try{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraHoy();
      for (int indice=0;indice!=listaTotal.size();indice++){
        stringBuilder.append(listaTotal.get(indice).get(0)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(1)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(2)+ ",");
        stringBuilder.append('\n');
        writer.write(stringBuilder.toString());     
      }
      writer.close(); 
      }
        catch (Exception error){
          System.out.println("Error al consultar bitácora registros de hoy CSV");
      }
    }
    
    
    
    @Override
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
         System.out.println("Error al abrir la bitácora, CSV");
     }
    }
}
