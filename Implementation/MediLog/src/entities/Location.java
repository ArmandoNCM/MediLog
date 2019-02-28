package entities;

public class Location {
	
	private int countryId;
	
	private String country;
	
	private int stateProvinceId;
	
	private String stateProvince;
	
	private int cityId;
	
	private String city;

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getStateProvinceId() {
		return stateProvinceId;
	}

	public void setStateProvinceId(int stateProvinceId) {
		this.stateProvinceId = stateProvinceId;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
