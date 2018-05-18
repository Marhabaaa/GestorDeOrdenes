package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cliente extends Persona {
    
    private boolean 		isBusiness;
    private int 			maxOrders;	//maximo numero de ordenes permitidas por cliente segun tipo de cliente
    private ListaOrdenes	orders;

    
	public Cliente(int rut, String name, int phoneNumber, String eMail, boolean isBusiness, ListaOrdenes orders) {
		super(rut, name, phoneNumber, eMail);
		this.isBusiness = isBusiness;
		this.maxOrders = calculateMaxOrders(isBusiness);
		this.orders = orders;
	}

	public Cliente(int rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws TelefonoInvalidoException {
    	super(rut, name, phoneNumber, eMail);
    	this.setBusiness(isBusiness);
        this.maxOrders = calculateMaxOrders(isBusiness);
        orders = new ListaOrdenes();
    }
	
    public boolean isBusiness() {
		return isBusiness;
	}
	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}
	
	public int getMaxOrders() {
		return maxOrders;
	}

	public void setMaxOrders(int maxOrders) {
		this.maxOrders = maxOrders;
	}

	public ListaOrdenes getOrders() {
		return orders;
	}

	public void setOrders(ListaOrdenes orders) {
		this.orders = orders;
	}
	/*
	 *entrega la cantidad maxima de ordenes que se pueden efectuar
	 */
	
	public boolean addOrder(Orden order) {
		if(maxOrders >= orders.size())
			return false;
		
		orders.add(order);
		return true;
	}
	
	public boolean removeOrder(Orden order) {
		return orders.remove(order);
	}
	
	public int calculateMaxOrders(boolean isBusiness) {
		int max = 5;
        if(isBusiness)
            max = 20;
        
        return max;
	}

	//public boolean hasPendingOrders

	public void toDB(Connection connection) throws SQLException {
		String insertTableSQL = "INSERT INTO clientes"
				+ "(rut, nombre, telefono, eMail, esEmpresa) VALUES"
				+ "(?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(insertTableSQL);

		statement.setInt(1, rut);
		statement.setString(2, name);
		statement.setInt(3, phoneNumber);
		statement.setString(4, eMail);
		statement.setBoolean(5, isBusiness);

		// execute insert SQL stetement
		statement.executeUpdate();

		System.out.println("Record is inserted into Clientes table!");
	}

	public void editClient(){

	}
}