package database.persist;

public class StringPair {
	private String first;
	private String second;
	private int ID;
	
	public StringPair() {
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	
	public void setSecond(String second) {
		this.second = second;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getFirst() {
		return first;
	}
	
	public String getSecond() {
		return second;
	}
	
	public int getID() {
		return ID;
	}
}
