package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Orden {
    
	private int 		orderNumber;	//numero de orden asignado automaticamente
	private String 		description; //descripcion del problema del aparato
    private String 		dateIn;
    private String 		dateOut;
    private int 		clientRut;		//rut del cliente al cual pertenece
    private int 		techNumber;	//numero del tecnico a quien fue asignada
    private int 		price;
    private ListaPiezas partsList;	//lista de piezas a cambiar
    private int 		complex;		//suma de las complejidades de las piezas
    private boolean 	checked;	//revision hecha
    private boolean 	done;		//orden lista

    
    public Orden(int orderNumber, String description, String dateIn, String dateOut, int clientRut, int techNumber,
			int price, ListaPiezas partsList, int complex, boolean checked, boolean done) {
		this.orderNumber = orderNumber;
		this.description = description;
		this.dateIn 	 = dateIn;
		this.dateOut 	 = dateOut;
		this.clientRut 	 = clientRut;
		this.techNumber  = techNumber;
		this.price 		 = price;
		this.partsList 	 = partsList;
		this.complex 	 = complex;
		this.checked 	 = checked;
		this.done 		 = done;
	}

	public Orden(int orderNumber, String description, int clientRut, int techNumber) {
    	this.orderNumber = orderNumber;
    	this.description = description;
    	this.dateIn 	 = getCurrentDate();
    	this.dateOut 	 = null;
        this.setClientRut(clientRut);
        this.setTechNumber(techNumber);
        this.price 		 = 0;
        this.partsList 	 = new ListaPiezas();
        this.complex 	 = 0;
        this.checked 	 = false;
        this.done 		 = false;
    }
    
    public void set() {
    	setChecked(true);
    	setComplex();
    	setPrice();
    }

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}

	public String getDateOut() {
		return dateOut;
	}

	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	public int getClientRut() {
		return clientRut;
	}

	public void setClientRut(int clientRut) {
		this.clientRut = clientRut;
	}

	public int getTechNumber() {
		return techNumber;
	}

	public void setTechNumber(int techNumber) {
		this.techNumber = techNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice() {
		this.price = calculatePrice();
	}

	public ListaPiezas getPartsList() {
		return partsList;
	}

	public void setPartsList(ListaPiezas partsList) {
		this.partsList = partsList;
	}
	
	public int getComplex() {
		return complex;
	}

	public void setComplex() {
		this.complex = calculateComplexity();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public int calculateComplexity() {	//calcula complejidad sumando la complejidad de cada pieza
		int i = 0, sum = 0;
		
		while(i < partsList.size()) {
			sum += ((Pieza) partsList.get(i)).getComplex();
			i++;
		}
		
		return sum;
	}
	
	public int calculatePrice() {	//calcula precio sumando el precio de cada pieza mas la complejidad multiplicada por 1000
		int i = 0, sum = 0;
		
		while(i < partsList.size()) {
			sum += ((Pieza) partsList.get(i)).getPrice();
			i++;
		}
		
		return sum + (calculateComplexity() * 1000);
	}
	/*
	 * getProfit da el valor de que gana la empresa 
	 */
	
	public int getProfit() {
		return calculateComplexity() * 1000;
	}

	public static String getCurrentDate() {
		Date now = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		return formateador.format(now);
	}

	public boolean containsPart(int partCode) {
	    return partsList.contains(partCode);
    }

    public void addPart(Pieza part) throws SinStockException {
	    partsList.add(part);
    }

    public void removePart(int partCode) {
		partsList.remove(partCode);
	}
}
