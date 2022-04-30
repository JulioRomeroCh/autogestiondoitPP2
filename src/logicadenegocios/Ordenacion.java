package logicadenegocios;


public class Ordenacion {//Inicio de la clase Ordenacion
    
  public void ordenamientoInsercion(Comparable [] pArreglo){//Inicio del método ordenamientoInserción
   
    for(int contador = 1; contador < pArreglo.length; contador++){//Inicio ciclo for
        
      Comparable nuevoDato = pArreglo[contador];
      int indice = contador - 1;
      
      while(indice >= 0 && ! pArreglo[indice].comparar(nuevoDato)){//Inicio ciclo while
        pArreglo[indice + 1] = pArreglo[indice];
        indice--;
      }//Fin ciclo while
      
    pArreglo[indice + 1] = nuevoDato;
              
    }//Fin ciclo for
      
      
  }//Fin del método ordenamientoInserción
    
}//Fin de la clase Ordenacion
