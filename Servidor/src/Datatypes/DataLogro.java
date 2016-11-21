package Datatypes;

public class DataLogro {

	private String desc;
	private int cant;

	public DataLogro(String desc, int cant) {
		this.desc = desc;
		this.cant = cant;
	}
	
	//----GETTERS----//
	public String getDesc() {
		return desc;
	}
	
	public int getCant() {
		return cant;
	}
	
	//----SETTERS----//
	public void setDesc(String desc) {
		this.desc = desc;
	}
		
	public void setCant(int cant) {
		this.cant = cant;
	} 

}
