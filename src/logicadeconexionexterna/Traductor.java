/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadeconexionexterna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;




/**
 *
 * @author Jose Blanco
 */
public class Traductor {
 
public static String traducirEspanolIngles(String pMotivo) throws IOException{
                String lenguajeFuente = "es"; 
                String lenguajeDestino = "en";
		String enlace = "https://script.google.com/macros/s/AKfycbwe7CD7RnjxB7JhqpGj298Nw5UNmwIEHo7rzzl7hYrB5szvS41OdA6yuL1MHNujsnU6/exec" +
						"?q=" + URLEncoder.encode(pMotivo, "UTF-8") +
						"&target=" + lenguajeDestino +
						"&source=" + lenguajeFuente;
		
		URL url = new URL(enlace);
		StringBuilder respuesta = new StringBuilder();
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String valor;
		while ((valor = entrada.readLine()) != null){
			respuesta.append(valor);
		}
		entrada.close();
		return respuesta.toString();
	}


       
}
 
 
