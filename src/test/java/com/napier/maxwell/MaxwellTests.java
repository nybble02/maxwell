package com.napier.maxwell;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 *  This is where all the unit tests are stored:
 */
class MyTest
{
    /**
     * Initialising main
     */
    static Main main;
    @BeforeAll
    static void init()
    {
        main = new Main();
    }

    /**
     * Unit Test for testing Country outputs
     */
    @Test
    void printCountry()
    {
        //Add first Unit Test here.
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.Code = "GBR";
        country.Name = "United Kingdom";
        country.Continent = "Europe";
        country.Region = "British Islands";
        country.Population = 59623400;
        country.Capital = "London";
        countries.add(country);
        main.displayCountries(countries);
    }

    /**
     * Unit Test for testing City outputs
     */
    @Test
    void printCity()
    {
        //Add first Unit Test here.
        ArrayList<City> cityList = new ArrayList<>();
        City city = new City();
        city = new City();
        city.ID = 101;
        city.Name = "London";
        city.CountryCode = "GBR";
        city.District = "Yorkshire";
        city.Population = 696969420;
        cityList.add(city);
        main.displayCity(cityList);
    }
}