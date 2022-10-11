package at.htlklu.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity																
@Table(name = "LOCATION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location extends RepresentationModel<Location> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -7790802681322726971L;
	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private Integer locationId;


	@Column(name = "ZIPCODE")
	private String zipCode;
	private String street;
	private String city;
	private String country;
	//endregion


	//region Constructors
	public Location()
	{
	}

	public Location(String zipCode,
					String street,
                    String city,
                    String country)
	{

		this.zipCode = zipCode;
		this.street = street;
		this.city = city;
		this.country = country;

	}
	//endregion


	//region Methods
	@Override
	public String toString()
	{
		return String.format("%1$s, %2$s %3$s (%4$d)",  this.zipCode,this.street, this.city, this.street,this.country);
	}
	//endregion


	//region Getter and Setter

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	//endregion


	//region HashCode and Equals
	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal;
		Location location1 = this;
		if (location1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Location)))
		{
			equal = false;
		}
		else
		{
			Location location2 = (Location)obj;
			equal = location1.locationId != null && Objects.equals(location1.locationId, location2.getLocationId());
		}
		return equal;
	}
	//endregion

}
