package application;

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

	public void setComplex(int complex) {
		this.complex = complex;
	}
	
	public void updateCant(int diferencia) {
		cant =+ diferencia;
	}
}

