package com.compiler.abohaoya.model;

import java.util.ArrayList;

/**
 * Created by User on 8/6/2016.
 */
public class City {
    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public City() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ArrayList<City> getAllCity(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Bagerhat"));
        cities.add(new City("Bandarban"));
        cities.add(new City("Barguna"));
        cities.add(new City("Barisal"));
        cities.add(new City("Bhola"));
        cities.add(new City("Bogra"));
        cities.add(new City("Brahmanbaria"));
        cities.add(new City("Chandpur"));
        cities.add(new City("Chapainawabganj"));
        cities.add(new City("Chittagong"));
        cities.add(new City("Chuadanga"));
        cities.add(new City("Comilla"));
        cities.add(new City("Cox's Bazar"));
        cities.add(new City("Dhaka"));
        cities.add(new City("Dinajpur"));
        cities.add(new City("Faridpur"));
        cities.add(new City("Feni"));
        cities.add(new City("Gaibandha"));
        cities.add(new City("Gazipur"));
        cities.add(new City("Gopalganj"));
        cities.add(new City("Habiganj"));
        cities.add(new City("Jamalpur"));
        cities.add(new City("Jessore"));
        cities.add(new City("Jhalokati"));
        cities.add(new City("Jhenaidah"));
        cities.add(new City("Joypurhat"));
        cities.add(new City("Khagrachhari"));
        cities.add(new City("Khulna"));
        cities.add(new City("Kishoreganj"));
        cities.add(new City("Kurigram"));
        cities.add(new City("Kushtia"));
        cities.add(new City("Lakshmipur"));
        cities.add(new City("Lalmonirhat"));
        cities.add(new City("Madaripur"));
        cities.add(new City("Magura"));
        cities.add(new City("Manikganj"));
        cities.add(new City("Meherpur"));
        cities.add(new City("Moulvibazar"));
        cities.add(new City("Munshiganj"));
        cities.add(new City("Mymensingh"));
        cities.add(new City("Naogaon"));
        cities.add(new City("Narail"));
        cities.add(new City("Narayanganj"));
        cities.add(new City("Narsingdi"));
        cities.add(new City("Natore"));
        cities.add(new City("Netrakona"));
        cities.add(new City("Nilphamari"));
        cities.add(new City("Noakhali"));
        cities.add(new City("Pabna"));
        cities.add(new City("Panchagarh"));
        cities.add(new City("Patuakhali"));
        cities.add(new City("Pirojpur"));
        cities.add(new City("Rajbari"));
        cities.add(new City("Rajshahi"));
        cities.add(new City("Rangamati"));
        cities.add(new City("Rangpur"));
        cities.add(new City("Satkhira"));
        cities.add(new City("Shariatpur"));
        cities.add(new City("Sherpur"));
        cities.add(new City("Sirajganj"));
        cities.add(new City("Sunamganj"));
        cities.add(new City("Sylhet"));
        cities.add(new City("Tangail"));
        cities.add(new City("Thakurgaon"));

        return cities;
    }
    @Override
    public String toString() {
        return cityName;
    }
}
