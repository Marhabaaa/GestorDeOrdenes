package application;

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
	
	public void setTechNumber(int tec_number) {
		this.techNumber = tec_number;
	}
	
	public int getDwh() {
		return dwh;
	}

	public void setDwh(int dwh) {
		this.dwh = dwh;
	}

	public int getWorkload() {
		return workload;
	}

	public ListaOrdenes getOrders() {
		return orders;
	}
	
	public void setOrders(ListaOrdenes orders) {
		this.orders = orders;
	}
	
	public int getNewTechNumber() {
		techNumber++;
		return techNumber;
	}
	
	public boolean addOrder(Orden order) {
		orders.add(order);
		workload = workload + order.getComplex();
		return true;
	}
	
	public void removeOrder(Orden order) {
    	orders.remove(order);
	}
	
	/*
	 * calcula la carga de trabajo
	 */
	public int calculateWorkload() {
		int i = 0, sum = 0;
		
		while(i < orders.size()) {
			sum += ((Orden) orders.get(i)).getComplex();
			i++;
		}
		
		return sum;
	}
	/*
	 * da una fecha estimada de salida
	 */
	
	public int estimateDateOut(int orderComplexity) {
		int delay = 0, sum = workload + orderComplexity;
		
		while(sum / dwh > 1) {
			delay++;
			sum -= dwh;
		}
		
		return delay;
	}
}

