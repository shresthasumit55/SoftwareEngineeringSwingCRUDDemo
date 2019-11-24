package data;

import java.util.ArrayList;
import java.util.List;

public class Farmer {
	private String name;
	private int id;
	private List<Cow> cows;
	
	public Farmer() {
		cows = new ArrayList<Cow>();
	}
	public List<Cow> getCows() {
		return cows;
	}
	public void setCows(List<Cow> cows) {
		this.cows = cows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Farmer [name=" + name + ", id=" + id + "]";
	}
	
	
	
}
