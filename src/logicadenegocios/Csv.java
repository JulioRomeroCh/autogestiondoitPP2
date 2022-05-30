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
        try{
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
        catch (Exception error){
            System.out.println("Error al crear un archivo CSV");
        }
    }
    
    private void consultarBitacoraSegunVistaCSV(String pVista) throws SQLException, FileNotFoundException{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraSegunVistaCsvXml(pVista);
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
    
    
    private void consultarBitacoraTodaVistaCSV() throws SQLException, FileNotFoundException{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraTodaVistaXmlCsv();
      for (int indice=0;indice!=listaTotal.size();indice++){
        stringBuilder.append(listaTotal.get(indice).get(0)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(1)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(2)+ ",");
        stringBuilder.append('\n');
        writer.write(stringBuilder.toString());  
      }
      writer.close();  
    }

    private void consultarBitacoraHoyCSV() throws FileNotFoundException, SQLException{
      PrintWriter writer = new PrintWriter(new File(ruta));
      StringBuilder stringBuilder = new StringBuilder();
      ArrayList<ArrayList<String>> listaTotal = BitacoraDao.recorrerConsultaBitacoraHoyXmlCsv();
      for (int indice=0;indice!=listaTotal.size();indice++){
        stringBuilder.append(listaTotal.get(indice).get(0)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(1)+ ",");
        stringBuilder.append(listaTotal.get(indice).get(2)+ ",");
        stringBuilder.append('\n');
        writer.write(stringBuilder.toString());     
      }
      writer.close();  
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
         System.out.println("Error al abrir la bitácora, CSV");
     }
    }
}
