package application;
//Importamos clases que se usaran
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Report {

	/*
	 * creo un archivo
	 * entrega las ganacias de todas las ordenes ya efectuadas
	 */

	public void ganaciasTotales(ListaOrdenes ordenes) throws IOException{
		Orden o;
		int suma;

		FileWriter flwriter = null;
		try {
			suma=0;
			flwriter = new FileWriter("C:\\Users\\");
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			bfwriter.write(" Numero de orden 	    	Tecnico			Ganacia por orden " + "\n");
			for (int i = 0; i < ordenes.size(); i++) {
				o = ordenes.get(i);
				if (o.isDone()) {
					bfwriter.write(" " + o.getOrderNumber() + "				" + o.getTechNumber() + "				" + o.getProfit() + "\n");
					suma = suma + o.getProfit();
				}
			}
			bfwriter.close();
			System.out.println("Archivo Reporte Creado Correctamente");
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			if (flwriter!=null){
				try{
					flwriter.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}

		
		/*File f= new File ("Reporte.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter("Reporte.txt"));
		if(f.exists()) {
			bw.write(" Numero de orden 	    	Tecnico			Ganacia por orden ");
			for (int i=0;i<ordenes.size();i++) {
				o = (Orden)ordenes.get(i);
				if(o.isDone()) {
				   bw.newLine();
				   bw.write(" " + o.getOrderNumber()+"				" + o.getTechNumber()+ "				" + o.getProfit());
				   suma = suma + o.getProfit();
				}
			}
			bw.newLine();
			bw.write("el precio total es: "+ suma);
			bw.close();	
		}*/
	}

	
	public void stock(SList stock) throws IOException {
		Pieza p;
		File f= new File ("ReporteStock.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter("ReporteStock.txt"));
		if(f.exists()) {
			bw.write(" Matarial del inventario \n");
			bw.write("Codigo           Nombre                       Cantidad \n");
			for (int i=0;i<stock.size();i++) {
				p = (Pieza)stock.get(i);
				bw.newLine();
				bw.write(" " +p.getCode()+ "		"+p.getDescription()+"		"+p.getCant() +"\n");	
			}
			bw.newLine();
			
			bw.close();	
		}
	}

}


