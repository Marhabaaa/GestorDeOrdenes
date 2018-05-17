package application;
//Importamos clases que se usaran
import java.io.File;
import java.io.FileWriter;
import java.io.*;


public class Report {
	private int suma;
	/*
	 * creo un archivo
	 * entrega las ganacias de todas las ordenes ya efectuadas
	 */

	public void ganaciasTotales(ListaOrdenes ordenes) throws IOException{
		Orden o;
		
		File f= new File ("Reporte.txt");
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
		}
		}
		/*
		 * muestra por pantalla todas las ordenes ya efectuadas y las ganancias totales
		 */
	public void ganaciasTotalesPantalla(SList ordenes) throws IOException {
		Orden o;
		
		System.out.println("    Numero de orden 	    	Tecnico			Ganacia por orden ");
		for (int i=0;i<ordenes.size();i++) {
			
			o = (Orden)ordenes.get(i);
			if(o.isDone()) {
			System.out.println(" " + o.getOrderNumber()+"		" + o.getTechNumber()+ "		" + o.getProfit()+"\n");
			   suma = suma + o.getProfit();
			}
			System.out.println("el precio total es: "+ suma);
		}
	}
		/*
		 * crea un archivo 
		 * con todo el inventario disponible al momento
		 */
	
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
	/*
	 * muestra por pantalle el inventario disponible al momento
	 */
	public void stockPantalla(SList stock) throws IOException {
		
		Pieza p;
			System.out.println("Codigo           Nombre                       Cantidad ");
			for (int i=0;i<stock.size();i++) {
				p = (Pieza)stock.get(i);
				System.out.println(" " +p.getCode()+ "		"+p.getDescription()+"		"+p.getCant() );	
			}

		
	}
}


