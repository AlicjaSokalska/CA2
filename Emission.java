package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alicjasokalska
 *
 */



@Entity
@XmlRootElement(name = "emission")
public class Emission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String category;
	private double value;
	private String gasUnit;




	public Emission() {

	}

	public Emission(int id, String category, double value, String gasUnit) {
		super();
		this.id = id;
		this.value = value;
        this.gasUnit=gasUnit;
		;
	
		
	}
	
	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlElement
	public double getValue() {
		return value;
	}

	

	public void setValue(double value) {
		this.value = value;
	}


	@XmlElement
	public String getGasUnit() {
		return gasUnit;
	}

	public void setGasUnit(String gasUnit) {
		this.gasUnit = gasUnit;
	}

	@Override
	public String toString() {
		return "Emission [id=" + id + ", category=" + category + ", value=" + value + ", gasUnit=" + gasUnit 
		+ "]";
	}





}
