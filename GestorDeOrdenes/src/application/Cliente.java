package application;

import exceptions.MaxOrdenesSobrepasadoException;
import exceptions.RutInvalidoException;
import exceptions.TelefonoInvalidoException;
import interfaces.ManejaBaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cliente extends Persona implements ManejaBaseDeDatos {
    
    private boolean 		isBusiness;
    private int 			maxOrders;	//maximo numero de ordenes permitidas por cliente segun tipo de cliente
    private ListaOrdenes	orders;

    
	public Cliente(int rut, String name, int phoneNumber, String eMail, boolean isBusiness, ListaOrdenes orders) {
		super(rut, name, phoneNumber, eMail);
		this.isBusiness = isBusiness;
		this.maxOrders = calculateMaxOrders(isBusiness);
		this.orders = orders;
	}

	public Cliente(String rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws TelefonoInvalidoException, RutInvalidoException {
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

	public ListaOrdenes getOrders() {
		return orders;
	}
	
	public void addOrder(Orden order) throws MaxOrdenesSobrepasadoException {
		if(orders.size() >= maxOrders)
			throw new MaxOrdenesSobrepasadoException();
		else
			orders.add(order);
	}

	public void removeOrder(Orden order) {
		orders.remove(order);
	}
	
	public int calculateMaxOrders(boolean isBusiness) {
		int max = 5;
        if(isBusiness)
            max = 20;
        
        return max;
	}

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

		statement.executeUpdate();

		System.out.println("Cliente insertado exitosamente a tabla clientes.");
	}

	public void deleteFromDB(Connection connection) throws SQLException {
		String deleteTableSQL = "DELETE FROM clientes"
				+ " WHERE rut = ?";

		PreparedStatement statement = connection.prepareStatement(deleteTableSQL);

		statement.setInt(1, rut);
		statement.executeUpdate();

		System.out.println("Cliente eliminado exitosamente de la base de datos.");
	}

    public void updateDB(Connection connection) throws SQLException {
	    String updateTableSQL = "UPDATE clientes"
                + " SET rut = ?, nombre = ?, telefono = ?, eMail = ?, esEmpresa = ?"
                + " WHERE rut = ?";

        PreparedStatement statement = connection.prepareStatement(updateTableSQL);

        statement.setInt(1, rut);
        statement.setString(2, name);
        statement.setInt(3, phoneNumber);
        statement.setString(4, eMail);
        statement.setBoolean(5, isBusiness);
        statement.setInt(6, rut);

        statement.executeUpdate();
        statement.close();

        System.out.println("Cliente actualizado exitosamente.");
    }

	public void update(Connection connection, String rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws RutInvalidoException, TelefonoInvalidoException, SQLException {
	    setRut(rut);
	    this.name = name;
	    setPhoneNumber(phoneNumber);
	    seteMail(eMail);
	    this.isBusiness = isBusiness;

	    updateDB(connection);
	}
}
