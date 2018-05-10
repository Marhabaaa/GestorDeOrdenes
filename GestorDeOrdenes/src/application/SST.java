package application;

import java.sql.Connection;
import java.sql.SQLException;

import DBconnector.MySQLconnection;

public class SST {	//Sistema Servicio Tecnico

	private static MySQLconnection conn;
	private Connection connection;
	private MapaStock stockMap;
	private MapaPiezasDeOrdenes orderPartsMap;
	private MapaOrdenes ordersMap;
	private MapaClientes clientsMap;
	private MapaTecnicos techsMap;
	private int  orderNumber;	//variable para asignacion de numero de orden
	private int  techNumber;	//variable para asignacion de numero de tecnico
	
	public SST() throws SQLException {
		conn  = new MySQLconnection();
		connection = conn.getConnection();
		
		stockMap 			 = new MapaStock(connection);
		orderPartsMap 		 = new MapaPiezasDeOrdenes(connection, stockMap);
		ordersMap 			 = new MapaOrdenes(connection, orderPartsMap);
		SMap clientOrdersMap = getClientOrdersMap();
		SMap techOrdersMap 	 = getTechOrdersMap();
		clientsMap 			 = new MapaClientes(connection, clientOrdersMap);
		techsMap 			 = new MapaTecnicos(connection, techOrdersMap);
	}
	
	private void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}


	private void setTechNumber(int techNumber) {
		this.techNumber = techNumber;
	}
	
	private SMap getClientOrdersMap() {
		int i = 0;
		ListaOrdenes ordersList = ordersMap.toListaOrdenes();
		Orden aux;
		SMap clientOrdersMap = new SMap();
		
		while(i < ordersList.size()) {
			aux = ordersList.get(i);
			
			if(!clientOrdersMap.contains(aux.getClientRut())) {
				ListaOrdenes auxL = new ListaOrdenes();
				clientOrdersMap.put(aux.getClientRut(), auxL);
			}
			
			((ListaOrdenes) clientOrdersMap.get(aux.getClientRut())).add(aux);
			i++;
		}
		
		return clientOrdersMap;
	}
	
	private SMap getTechOrdersMap() {
		int i = 0;
		ListaOrdenes ordersList = ordersMap.toListaOrdenes();
		Orden aux;
		SMap techOrdersMap = new SMap();
		
		while(i < ordersList.size()) {
			aux = ordersList.get(i);
			
			if(!techOrdersMap.contains(aux.getTechNumber())) {
				ListaOrdenes auxL = new ListaOrdenes();
				techOrdersMap.put(aux.getTechNumber(), auxL);
			}
			
			((ListaOrdenes) techOrdersMap.get(aux.getTechNumber())).add(aux);
			i++;
		}
		
		return techOrdersMap;
	}
	
	public int getNewOrderNumber() {
		orderNumber++;
		return orderNumber;
	}
	
	public int getNewTechNumber() {
		techNumber++;
		return techNumber;
	}
	
	public boolean addPart(int code, String desc, int cant, int price, int complex) {
		Pieza part = new Pieza(code, desc, cant, price, complex);
		return stockMap.put(part);
	}
	
	public boolean addOrder(int orderNumber, String desc, String dateIn, int clientRut, int techNumber) {
		Orden order = new Orden(orderNumber, desc, dateIn, clientRut, techNumber);
		if(!clientsMap.addOrder(order, order.getClientRut()))
			return false;
		
		ordersMap.put(order);
		orderPartsMap.put(order);
		techsMap.addOrder(order, order.getTechNumber());
		return true;
	}
	
	public boolean removeOrder(int code) {
		techsMap.removeOrder(ordersMap.get(code));
		clientsMap.removeOrder(ordersMap.get(code));
		orderPartsMap.remove(code);
		ordersMap.remove(code);
		return true;
	}
	
	public boolean addClient(int rut, String name, int phoneNumber, String eMail, boolean isBusiness) {
		if(eMail.length() == 0)
		    eMail = "null";
		Cliente client = new Cliente(rut, name, phoneNumber, eMail, isBusiness);
        if(!client.toDB(connection))
            return false;
		return clientsMap.put(client);
	}

	public boolean removeClient(int rut) {
		if(!clientsMap.get(rut).getOrders().isEmpty()) return false;
		clientsMap.remove(rut);
		return true;
	}

	public boolean addTechnician(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh) {
		Tecnico tech = new Tecnico(rut, name, phoneNumber, eMail, techNumber, dwh);
		return techsMap.put(tech);
	}

	public boolean removeTechnician(int rut) {
		if(!techsMap.get(rut).getOrders().isEmpty()) return false;
		techsMap.remove(rut);
		return true;
	}
	
	public Cliente getClient(int rut) {
		return clientsMap.get(rut);
	}
	
	public boolean exist(Cliente client) {
		return clientsMap.contains(client.getRut());
	}
	
	public void updatePartCant(int code, int difference) {
		stockMap.get(code).updateCant(difference);
	}
	
	//public String calculateDateOut(Orden order)	//calcula la fecha estimada de entrega del pedido
	
	public int delayOfReturn(Orden order) {	//calcula cuantos dias despues de la fecha de ingreso de la orden, podr� �sta, ser entregada
		Tecnico aux = techsMap.get(order.getTechNumber());
		return aux.estimateDateOut(order.getComplex());
	}
}
