package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
	public void addPart(int code, String description, int cant, int price, int complex) throws SQLException {
		stockMap.put(code, description, cant, price, complex, connection);
	}

	public void updateStock(int code, int cant) throws SinStockException {
	    stockMap.updateStock(code, cant);
    }

    public Orden getOrder(int orderNumber) {
	    return ordersMap.get(orderNumber);
    }

    public int createOrder(String description, int clientRut) throws Exception {
	    orderNumber = getNewOrderNumber();
	    ordersMap.createOrder(orderNumber, description, clientRut, techsMap.leastWorkload());
        return orderNumber;
	}

	public void addOrder(int orderNumber) {
	    clientsMap.addOrder(ordersMap.get(orderNumber));
	    techsMap.addOrder(ordersMap.get(orderNumber));
	    orderPartsMap.put(ordersMap.get(orderNumber));
    }

    public void cancelOrder(int orderNumber) {
	    recoverParts(ordersMap.get(orderNumber));
	    ordersMap.remove(orderNumber);
	    this.orderNumber--;
    }

    private void recoverParts(Orden order) {
	    int i = 0;
	    while(i < order.getPartsList().size()) {
            recoverPart(order.getPartsList().get(i));
            i++;
        }
    }

    private void recoverPart(Pieza part) {
	    stockMap.get(part.getCode()).updateCant(part.getCant());
    }
	
	public boolean removeOrder(int orderNumber) {
		techsMap.removeOrder(ordersMap.get(orderNumber));
		clientsMap.removeOrder(ordersMap.get(orderNumber));
		orderPartsMap.remove(orderNumber);
		ordersMap.remove(orderNumber);
		return true;
	}
	
	public void addClient(int rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws TelefonoInvalidoException, SQLException {
		clientsMap.put(rut, name, phoneNumber, eMail, isBusiness, connection);
	}

	public void removeClient(int rut) throws ClienteTieneOrdenesException {
		clientsMap.remove(rut);
	}

	public boolean addTechnician(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh) {
		return techsMap.put(rut, name, phoneNumber, eMail, techNumber, dwh);
	}

	public void removeTechnician(int techNumber) throws TecnicoOcupadoException {
		techsMap.remove(techNumber);
	}

	public void removePart(int partNumber){
		stockMap.remove(partNumber);
	}

	public Cliente getClient(String rut) throws RutInvalidoException {
		return clientsMap.get(rut);
	}

    public Cliente getClient(int rut) {
        return clientsMap.get(rut);
    }

    public Tecnico getTechnician(int techNumber) {
	    return techsMap.get(techNumber);
    }

	public boolean exist(Cliente client) {
		return clientsMap.contains(client.getRut());
	}
	
	public void updatePartCant(int code, int difference) {
		stockMap.get(code).updateCant(difference);
	}
	
	public String calculateDateOut(int orderNumber) { //calcula la fecha estimada de entrega del pedido
	    SimpleDateFormat dateOut = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, delayOfReturn(ordersMap.get(orderNumber)));

        return dateOut.format(calendar.getTime());
    }
	
	private int delayOfReturn(Orden order) {	//calcula cuantos dias despues se estima la entrega si es que se ingresara hoy
		return techsMap.get(order.getTechNumber()).estimateDateOut(order.getComplex());
	}

	public ListaPiezas getListaPiezas() {
		return stockMap.toListaPiezas();
	}

	public ListaOrdenes getListaOrdenes(){
		return ordersMap.toListaOrdenes();
	}

	public ListaClientes getListaClientes() {return clientsMap.toListaClientes();}

	public ListaTecnicos getListaTecnicos() {return techsMap.toListaTecnicos();}



	public void addPartToOrder(int orderNumber, int codPart) throws SinStockException {
	    stockMap.get(codPart).oneLess();
        ordersMap.addPart(orderNumber, stockMap.get(codPart).clone());
    }

	public void removePartFromOrder(int orderNumber, int codPart) {
		ordersMap.removePart(orderNumber, codPart);
		stockMap.get(codPart).oneMore();
	}
}