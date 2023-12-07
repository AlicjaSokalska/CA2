package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "prediction")
public class Prediction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String category;
	private double value;
	private String gasUnit;
	 private String scenario;
	private int year;
	
	
	
    @OneToMany(fetch = FetchType.EAGER)
    private List<Emission> emissions;

	public Prediction() {

	}

	public Prediction(int id, String category, double value, int year,String gasUnit, String scenario) {
		super();
		this.id = id;
		this.category = category;
		this.value = value;
        this.gasUnit=gasUnit;
		this.year = year;
		this.scenario= scenario;
		
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
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
		return "Emission [id=" + id + ", category=" + category + ", value=" + value + ", scenario=" + scenario + ", gasUnit=" + gasUnit + ", year="
				+ year + "]";
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}



}
