package application;

import exceptions.RutInvalidoException;
import exceptions.TelefonoInvalidoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tecnico extends Persona {
	
    private int 			techNumber;
    private int 			dwh;		//daily working hours; horas de trabajo diario de tecnico segun contrato
    private ListaOrdenes	orders;
    private int 			workload;	//cantidad de trabajo asignada al momento

    
    public Tecnico(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh, ListaOrdenes orders) {
		super(rut, name, phoneNumber, eMail);
		this.techNumber = techNumber;
		this.dwh 		= dwh;
		this.orders 	= orders;
		this.workload 	= calculateWorkload();
	}

	public Tecnico(int rut, String name, int phoneNumber, String eMail, int tecNumber, int dwh) {
		super(rut, name, phoneNumber, eMail);
		this.techNumber = tecNumber;
		this.dwh 		= dwh;
		this.orders 	= new ListaOrdenes();
		this.workload 	= 0;
	}
    
	public int getTechNumber() {
		return techNumber;
	}

	public int getWorkload() {
		return workload;
	}

	public void setWorkload() {
        this.workload = calculateWorkload();
    }

	public ListaOrdenes getOrders() {
		return orders;
	}
	
	public void addOrder(Orden order) {
		orders.add(order);
		setWorkload();
	}
	
	public void removeOrder(Orden order) {
    	orders.remove(order);
    	setWorkload();
	}

	public int calculateWorkload() {	//Calcula la carga de trabajo actual del Tecnico
		int i = 0, sum = 0;
		
		while(i < orders.size()) {
			sum += orders.get(i).getComplex();
			i++;
		}
		
		return sum;
	}
	
	public int estimateDateOut(int orderComplexity) {	//Entrega la cantidad de dias que tardaria una orden en ser entregada
		int delay = 0, sum = workload + orderComplexity;
		
		while(sum / dwh > 1) {
			delay++;
			sum -= dwh;
		}
		
		return delay;
	}

	public void toDB(Connection connection) throws SQLException {
		String insertTableSQL = "INSERT INTO tecnicos"
				+ "(rut, nombre, telefono, eMail, esEmpresa, tecNumber, dwh) VALUES"
				+ "(?,?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(insertTableSQL);

		statement.setInt(1, rut);
		statement.setString(2, name);
		statement.setInt(3, phoneNumber);
		statement.setString(4, eMail);
		statement.setInt(5, techNumber);
		statement.setInt(6, dwh);

		statement.executeUpdate();

		System.out.println("Tecnico nsertado exitosamente a tabla tecnicos.");
	}

    public void updateDB(Connection connection) throws SQLException {
        String insertTableSQL = "UPDATE tecnicos"
                + " SET rut = ?, nombre = ?, telefono = ?, eMail = ?, tecNumber = ?, dwh = ?"
                + " WHERE tecNumber = ?";

        PreparedStatement statement = connection.prepareStatement(insertTableSQL);

        statement.setInt(1, rut);
        statement.setString(2, name);
        statement.setInt(3, phoneNumber);
        statement.setString(4, eMail);
        statement.setInt(5, techNumber);
        statement.setInt(6, dwh);
        statement.setInt(7, techNumber);

        statement.executeUpdate();
        statement.close();

        System.out.println("Tecnico actualizado exitosamente.");
    }

    public void update(Connection connection, String rut, String name, String phoneNumber, String eMail, int dwh) throws RutInvalidoException, TelefonoInvalidoException, SQLException {
        setRut(rut);
        this.name = name;
        setPhoneNumber(phoneNumber);
        seteMail(eMail);
        this.dwh = dwh;

        updateDB(connection);
    }
}
