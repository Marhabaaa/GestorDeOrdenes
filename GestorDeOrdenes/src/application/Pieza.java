package application;

import exceptions.SinStockException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pieza {
	
	private int 	code;
    private String  description;
    private int 	cant;
    private int 	price;
    private int 	complex; //complejidad de trabajo con la pieza traducida en horas de trabajo
    
    public Pieza(int code, String description, int cant, int price, int complex) {
    	this.code 		 = code;
    	this.description = description;
        this.cant 		 = cant;
        this.price 		 = price;
        this.complex 	 = complex;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String name) {
		this.description = name;
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int stock) {
		this.cant = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getComplex() {
		return complex;
	}

	public  int getTotalComplex() {
    	return complex * cant;
	}

	public void setComplex(int complex) {
		this.complex = complex;
	}

	public void oneLess() throws SinStockException {
		if(cant == 0)
			throw new SinStockException();
		cant--;
	}

	public  void oneMore() {
    	cant++;
	}

	public void updateCant(int diferencia) {
        cant += diferencia;
	}

	public void toDB(Connection connection) throws SQLException {
        String insertTableSQL = "INSERT INTO inventario"
                + "(codPieza, descripcion, cant, precioUnit, complejidad) VALUES"
                + "(?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(insertTableSQL);

        statement.setInt(1, code);
        statement.setString(2, description);
        statement.setInt(3, cant);
        statement.setInt(4, price);
        statement.setInt(5, complex);

        // execute insert SQL stetement
        statement.executeUpdate();

        System.out.println("Record is inserted into Inventario table!");
	}

	public Pieza newPartClone(){
    	Pieza aux = new Pieza(code, description, 1, price, complex);
    	return aux;
	}

	public Pieza clone(){
		Pieza aux = new Pieza(code, description, cant, price, complex);
		return aux;
	}
}
