package application;

import exceptions.SinStockException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private ListaPiezas partsListBackUp;
    private int 		complex;		//suma de las complejidades de las piezas
    private boolean 	isChecked;	//revision hecha
    private boolean     isDone;		//orden lista
	private String		entregada;

    
    public Orden(int orderNumber, String description, String dateIn, String dateOut, int clientRut, int techNumber,
			int price, ListaPiezas partsList, int complex, boolean checked, boolean isDone) {
		this.orderNumber	 = orderNumber;
		this.description 	 = description;
		this.dateIn 	 	 = dateIn;
		this.dateOut 	 	 = dateOut;
		this.clientRut 	 	 = clientRut;
		this.techNumber  	 = techNumber;
		this.price			 = price;
		this.partsListBackUp = partsList;
		this.partsList 	 	 = partsList.clone();
		this.complex 	 	 = complex;
		this.isChecked 	 	 = checked;
		setIsDone(isDone);
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
        this.isChecked 	 = false;
        this.isDone      = false;
        this.entregada 	 = "No";
    }
    
    public void set() {
        setIsChecked(true);
        setComplex();
        setPrice();
        setAuxPartsList();
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

	public void setAuxPartsList() {
        partsListBackUp = partsList.clone();
    }
	
	public int getComplex() {
		return complex;
	}

	public void setComplex() {
		this.complex = calculateComplexity();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
    	entregada="Si";
		this.isDone = isDone;
	}
	
	public int calculateComplexity() {	//calcula complejidad sumando la complejidad de cada pieza
		int i = 0, sum = 0;
		
		while(i < partsList.size()) {
			sum += partsList.get(i).getTotalComplex();
			i++;
		}
		
		return sum;
	}
	
	public int calculatePrice() {	//calcula precio sumando el precio de cada pieza mas la complejidad multiplicada por 1000
		int i = 0, sum = 0;
		
		while(i < partsList.size()) {
			sum += partsList.get(i).getPrice();
			i++;
		}
		
		return sum + getProfit();
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

	public void recoverPartsList() {
	    partsList = partsListBackUp.clone();
    }

	private void setEntregada() {
		if(isDone()){
			entregada="Si";
		}else{
			entregada="No";
		}
	}

	public void toDB(Connection connection) throws SQLException {

        String insertTableSQL;
        PreparedStatement statement;

		insertTableSQL = "INSERT INTO ordenes"
				+ "(orderNumber, descripcion, dateIn, dateOut, clientRut, techNumber, precio, complejidad, revisada, terminada) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";

		statement = connection.prepareStatement(insertTableSQL);

		statement.setInt(1, orderNumber);
		statement.setString(2, description);
		statement.setString(3, dateIn);
		statement.setString(4, dateOut);
		statement.setInt(5, clientRut);
		statement.setInt(6, techNumber);
		statement.setInt(7, price);
		statement.setInt(8, complex);
		statement.setBoolean(9, isChecked);
		statement.setBoolean(10, isDone);

		statement.executeUpdate();


        insertTableSQL = "INSERT INTO orderParts"
                + "(orderNumber, codPieza, cant) VALUES"
                + "(?,?,?)";

        statement = connection.prepareStatement(insertTableSQL);

        int i = 0;
        while(i < partsList.size()) {
            statement.setInt(1, orderNumber);
            statement.setInt(2, partsList.get(i).getCode());
            statement.setInt(3, partsList.get(i).getCant());

            statement.executeUpdate();

            i++;
        }

		System.out.println("Orden insertada exitosamente a tabla ordenes.");
	}
}