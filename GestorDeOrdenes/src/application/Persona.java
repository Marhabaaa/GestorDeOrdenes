package application;

import com.mysql.jdbc.StringUtils;

public abstract class Persona {
	
	protected int 	 rut;
    protected String name;
    protected int phoneNumber;
    protected String eMail;

    public Persona(int rut, String name, int phoneNumber, String eMail) {
        this.rut         = rut;
        this.name 		 = name;
        this.phoneNumber = phoneNumber;
        this.eMail 		 = eMail;
    }

    public Persona(int rut, String name, String phoneNumber, String eMail) throws TelefonoInvalidoException {
        this.rut         = rut;
        this.name 		 = name;
        setPhoneNumber(phoneNumber);
        seteMail(eMail);
    }

	public int getRut() {
		return rut;
	}

	public void setRut(int rut) {
        this.rut = rut;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws TelefonoInvalidoException {
        if(phoneNumber.length() != 9) throw new TelefonoInvalidoException();

        if(!StringUtils.isStrictlyNumeric(phoneNumber)) throw new TelefonoInvalidoException();

        this.phoneNumber = Integer.parseInt(phoneNumber);
	}

	public String geteMail() {
		return eMail;
	}
	
	public void seteMail(String eMail) {
        if(eMail.length() == 0)
            eMail = "null";

		this.eMail = eMail;
	}
}
