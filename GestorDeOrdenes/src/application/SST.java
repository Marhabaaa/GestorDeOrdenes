package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import DBconnector.MySQLconnection;
import exceptions.*;

public class SST {	//Sistema Servicio Tecnico

	private static MySQLconnection conn;
	private Connection connection;
	private MapaStock stockMap;
	private MapaPiezasDeOrdenes orderPartsMap;
	private MapaOrdenes ordersMap;
	private MapaClientes clientsMap;
	private MapaTecnicos techsMap;
	
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

	public void updateStock(int code, int cant) throws SinStockException {
	    stockMap.updateStock(code, cant);
    }

    public Pieza getPart(int code) {
	    return stockMap.get(code);
    }

    public Orden getOrder(int orderNumber) {
	    return ordersMap.get(orderNumber);
    }

    public void recoverParts(ListaPiezas partsList) {
	    int i = 0;
	    while(i < partsList.size()) {
            recoverPart(partsList.get(i));
            i++;
        }
    }

    private void recoverPart(Pieza part) {
	    stockMap.get(part.getCode()).updateCant(part.getCant());
    }

    public void returnParts(ListaPiezas partsList) {
        int i = 0;
        while(i < partsList.size()) {
            returnPart(partsList.get(i));
            i++;
        }
    }

    private void returnPart(Pieza part) {
        stockMap.get(part.getCode()).updateCant((-1) * part.getCant());
    }

    public int createOrder(String description, int clientRut) throws Exception {
        return ordersMap.createOrder(description, clientRut, techsMap.leastWorkload());
    }

    public void addOrder(int orderNumber) throws SQLException, MaxOrdenesSobrepasadoException {
        clientsMap.addOrder(ordersMap.get(orderNumber));
        techsMap.addOrder(ordersMap.get(orderNumber));
        ordersMap.get(orderNumber).toDB(connection);
        //updateStock(ordersMap.get(orderNumber).getPartsList());
    }

    public void cancelOrder(int orderNumber) {
        recoverParts(ordersMap.get(orderNumber).getPartsList());
        ordersMap.remove(orderNumber);
    }

    public void removeOrder(int orderNumber) throws SQLException {
		techsMap.removeOrder(ordersMap.get(orderNumber));
		clientsMap.removeOrder(ordersMap.get(orderNumber));
		ordersMap.remove(orderNumber, connection);
	}
	
	public void addClient(String rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws TelefonoInvalidoException, SQLException, RutInvalidoException {
		clientsMap.put(rut, name, phoneNumber, eMail, isBusiness, connection);
	}

	public void removeClient(int rut) throws TieneOrdenesException, SQLException {
		clientsMap.remove(rut, connection);
	}

	public void updateClient(String rut, String name, String phoneNumber, String eMail, boolean isBusiness) throws RutInvalidoException, TelefonoInvalidoException, SQLException {
	    clientsMap.get(rut).update(connection, rut, name, phoneNumber, eMail, isBusiness);
    }

	public void addTechnician(String rut, String name, String phoneNumber, String eMail, int dwh) throws SQLException, TelefonoInvalidoException, RutInvalidoException {
		techsMap.put(rut, name, phoneNumber, eMail, dwh, connection);
	}

	public void removeTechnician(int techNumber) throws TieneOrdenesException, SQLException {
		techsMap.remove(techNumber, connection);
	}

	public void updateTechnician(int techNumber, String rut, String name, String phoneNumber, String eMail, int dwh) throws SQLException, TelefonoInvalidoException, RutInvalidoException {
		techsMap.get(techNumber).update(connection, rut, name, phoneNumber, eMail, dwh);
	}

    public void addPart(int code, String description, int cant, int price, int complex) throws SQLException {
        stockMap.put(code, description, cant, price, complex, connection);
    }

    public void removePart(int partNumber) throws SQLException {
		stockMap.remove(partNumber, connection);
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

	public ListaClientes getListaClientes() {
	    return clientsMap.toListaClientes();
	}

	public ListaTecnicos getListaTecnicos() {
	    return techsMap.toListaTecnicos();
	}

	public void addPartToOrder(int orderNumber, int codPart) throws SinStockException {
	    stockMap.get(codPart).oneLess();
        ordersMap.addPart(orderNumber, stockMap.get(codPart));
    }

	public void removePartFromOrder(int orderNumber, int codPart) {
		ordersMap.removePart(orderNumber, codPart);
		stockMap.get(codPart).oneMore();
	}

	public void updateOrder(int orderNumber) throws SQLException {
		getOrder(orderNumber).updateDB(connection);
	}

	public void updateStock(ListaPiezas partsList) throws SQLException {
	    //ListaPiezas partsList = stockMap.toListaPiezas();

	    int i = 0;
	    while(i < partsList.size()) {
	        stockMap.get(partsList.get(i).getCode()).updateDB(connection);
	        i++;
        }
    }
}