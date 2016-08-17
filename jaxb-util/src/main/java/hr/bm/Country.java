package hr.bm;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(propOrder = { "name", "capital", "continent", "towns", "population", "foundation" })
@XmlRootElement(name = "Country")
public class Country {

	private int population;
	private String name;
	private String capital;
	private int importance;
	private List<String> towns;
	private Date foundation;
	private String continent;

	@XmlElement(name = "Country_Continent")
	public void setContinent(String continent) {
		this.continent = continent;
	}

	@XmlElement(name = "Country_Foundation_Date")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public void setFoundation(Date foundation) {
		this.foundation = foundation;
	}

	@XmlElement(name = "Country_Towns")
	public void setTowns(List<String> otherTowns) {
		this.towns = otherTowns;
	}

	@XmlElement(name = "Country_Population")
	public void setPopulation(int population) {
		this.population = population;
	}

	@XmlElement(name = "Country_Name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "Country_Capital")
	public void setCapital(String capital) {
		this.capital = capital;
	}

	@XmlAttribute(name = "importance", required = true)
	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getPopulation() {
		return population;
	}

	public String getName() {
		return name;
	}

	public String getCapital() {
		return capital;
	}

	public int getImportance() {
		return importance;
	}

	public Date getFoundation() {
		return foundation;
	}

	public List<String> getTowns() {
		return towns;
	}

	public String getContinent() {
		return continent;
	}

	public String toString() {
		return "Country : " + this.name + "" + ", population : " + this.population + "" + ", capital : " + this.capital + ", continent : " + this.continent;
	}
}
