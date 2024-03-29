package com.napier.maxwell;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main
{
    /**
     *  Connection to MySQL database
     */
    private Connection con = null;
    public static void main(String[] args)
    {
        // Create new Application
        Main main = new Main();

        if(args.length < 1){
            main.connect("localhost:3366", 30000);
        }else{
            main.connect(args[0], Integer.parseInt(args[1]));
        }

        // List that contains cities
        ArrayList<City> Cities = new ArrayList<>();

        // List that contains capital cities
        ArrayList<City> CaptialCities = new ArrayList<>();

        // list that contains countries
        ArrayList<Country> Countries = new ArrayList<>();

        // list that contains population
        ArrayList<Population> Population = new ArrayList<>();

        // list that contains language information
        ArrayList<Language> Language = new ArrayList<>();


        // Gets all the cities in the world sorted by largest population to the smallest population
        Cities = main.getCitiesInWorld();
        main.displayCity(Cities);
        // Gets the cities that are in a continent
        Cities = main.getCitiesInContinent("Africa");
        main.displayCity(Cities);

        // Gets the cities in a Region
        Cities = main.getCitiesInRegion("Southern Europe");
        main.displayCity(Cities);

        //Gets the cities in a Country
        Cities = main.getCitiesInCountry("South Africa");
        main.displayCity(Cities);

        //Gets the cities in a District
        Cities = main.getCitiesInDistrict("Scotland");
        main.displayCity(Cities);

        // Get the count of populated cities in the world
        Cities = main.getNCities(3);
        main.displayCity(Cities);

        // Gets the count of populated cities in a continent
        Cities = main.getNCitiesInContinent("Africa", 3);
        main.displayCity(Cities);

        // Gets the count of populated cities in a Region
        Cities = main.getNCitiesInRegion("Eastern Europe", 5);
        main.displayCity(Cities);

        // Gets the count of populated cities in a Country
        Cities = main.getNCitiesInCountry("Japan", 10);
        main.displayCity(Cities);

        //Gets the count of populated cities in a District
        Cities = main.getNCitiesInDistrict("Scotland", 3);
        main.displayCity(Cities);

        // Gets all the countries in the world sorted by largest population to the smallest population
        Countries = main.getCountries();
        main.displayCountries(Countries);

        // Gets all the countries in a set continent sorted by largest population to the smallest population
        Countries = main.getCountriesInContinent("Europe");
        main.displayCountries(Countries);

        // Gets all the countries in a set region sorted by largest population to the smallest population
        Countries = main.getCountriesInRegion("British Islands");
        main.displayCountries(Countries);

        // Gets the top N populated countries
        Countries = main.getNCountries(10);
        main.displayCountries(Countries);

        // Gets the top N populated countries in a set continent
        Countries = main.getNCountriesInContinent(10, "Europe");
        main.displayCountries(Countries);

        // Gets the top N populated countries in a set region
        Countries = main.getNCountriesInRegion(1, "Caribbean");
        main.displayCountries(Countries);

        // Gets all the Capital Cities in the world
        CaptialCities = main.getCapitalCitiesInWorld();
        main.displayCapitalCity(CaptialCities);

        // Gets all the Capital Cities in the world
        CaptialCities = main.getCapitalCitiesInContinent("Africa");
        main.displayCapitalCity(CaptialCities);

        // Gets all the Capital Cities in the world
        CaptialCities = main.getCapitalCitiesInRegion("British Islands");
        main.displayCapitalCity(CaptialCities);

        // The top N populated capital cities in the world where N is provided by the user.
        CaptialCities = main.getNCapitalCities(10);
        main.displayCapitalCity(CaptialCities);

        // The top N populated capital cities in a continent where N is provided by the user.
        CaptialCities = main.getNCapitalCitiesInContinent("Africa", 10);
        main.displayCapitalCity(CaptialCities);

        //All the capital cities in a region organised by largest to smallest.
        CaptialCities = main.getNCapitalCitiesInRegion("Caribbean", 10);
        main.displayCapitalCity(CaptialCities);

        // Population of people who live in a continent and the number/percentage of people who live in and outside cities
        Population = main.getPopulationContinent();
        main.displayPopulation(Population);

        // Population of people who live in a region and the number/percentage of people who live in and outside cities
        Population = main.getPopulationRegion();
        main.displayPopulation(Population);

        // Population of people who live in a country and the number/percentage of people who live in and outside cities
        Population = main.getPopulationCountry();
        main.displayPopulation(Population);

        // Specified languages people speak listed most spoken to least spoken
        Language = main.getLanguage();
        main.displayLanguage(Language);

        main.disconnect();
    }

    /**
     * Gets all the Cities in the World
     * @return A List of Cities in the World
     */
    public ArrayList<City> getCitiesInWorld()
    {
        System.out.println("Getting all the cities in the world...");

        ArrayList<City> citiesInWorld = new ArrayList<>();
        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT city.ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.countryCode = country.Code ORDER BY city.Population DESC LIMIT 100;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");


                citiesInWorld.add(city);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

        return citiesInWorld;
    }

    /**
     * Gets all the Cities in a Continent
     * @param continent
     * @return ArrayList<City>
     */
    public ArrayList<City> getCitiesInContinent(String continent)
    {
        System.out.println("Getting all the cities in "+ continent + "...");
        ArrayList<City> citiesInContinent= new ArrayList<>();

        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Continent = " + "\'" + continent + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                citiesInContinent.add(city);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

        return citiesInContinent;
    }

    /**
     * Gets all the Cities in a Region
     * @param region
     * @return ArrayList<City>
     */
    public ArrayList<City> getCitiesInRegion(String region)
    {
        System.out.println("Getting all the cities in " + region + "...");

        ArrayList citiesInRegion =  new ArrayList<>();

        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Region = " + "\'" + region + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                citiesInRegion.add(city);
            }

            return citiesInRegion;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Gets a City in a country
     * @param country
     * @return
     */
    public ArrayList<City> getCitiesInCountry(String country)
    {
        System.out.println("Getting all the cities in a " + country + "...");

        ArrayList citiesInCountry =  new ArrayList<>();

        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Name = " + "\'" + country + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                citiesInCountry.add(city);
            }

            return citiesInCountry;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Gets a City in a District
     * @param district
     * @return ArrayList<City>
     */
    public ArrayList<City> getCitiesInDistrict(String district)
    {
        ArrayList<City> citiesInDistrict =  new ArrayList<>();
        System.out.println("Getting all the cities in a " + district +"...");


        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE city.District = " + "\'" + district + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                citiesInDistrict.add(city);
            }

            return citiesInDistrict;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Get the count of populated cities in the world
     * @param count
     * @return ArrayList<City>
     */

    public ArrayList<City> getNCities(int count)
    {
        System.out.println("Getting top " + count + " cities in the world...");

        ArrayList<City> cityList = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code ORDER BY city.Population DESC LIMIT " + count + ";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                cityList.add(city);
            }
            return cityList;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

    }


    /**
     * Get the count of populated cities in a continent
     * @param continent
     * @param count
     * @return
     */
    public ArrayList<City> getNCitiesInContinent(String continent ,int count)
    {
        System.out.println("Getting top " + count + " cities in a "+ continent + "...");

        ArrayList<City> cityList = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Continent = " + "\'" + continent + "\'" + " ORDER BY city.Population DESC LIMIT " + count +";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                cityList.add(city);
            }
            return cityList;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

    }

    /**
     *  Gets the count of populated cities in a Region
     * @param region
     * @param count
     * @return ArrayList<City>
     */
    public ArrayList<City> getNCitiesInRegion(String region ,int count)
    {
        System.out.println("Getting top " + count + " cities in a "+ region + "...");

        ArrayList<City> cityList = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Region = " + "\'" + region + "\'" + " ORDER BY city.Population DESC LIMIT " + count +";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                cityList.add(city);
            }
            return cityList;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

    }

    /**
     *  Gets the count of populated cities in a Country
     * @param country
     * @param count
     * @return ArrayList<City>
     */
    public ArrayList<City> getNCitiesInCountry(String country ,int count)
    {
        System.out.println("Getting top " + count + " cities in a "+ country + "...");

        ArrayList<City> cityList = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Name = " + "\'" + country + "\'" + " ORDER BY city.Population DESC LIMIT " + count +";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                cityList.add(city);
            }
            return cityList;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }

    }

    /**
     *  Gets the count of populated cities in a District
     * @param district
     * @param count
     * @return ArrayList<City>
     */
    public ArrayList<City> getNCitiesInDistrict(String district ,int count)
    {
        System.out.println("Getting top " + count + " cities in a "+ district + "...");

        ArrayList<City> cityList = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE District = " + "\'" + district + "\'" + " ORDER BY city.Population DESC LIMIT " + count +";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                cityList.add(city);
            }
            return cityList;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Displays A City Report
     * @param cities ArrayList
     */
    public void displayCity(ArrayList<City> cities)
    {
        if (cities == null)
        {
            System.out.println("No Cities");
        }
        else {
            String leftAlignFormat = "| %-40s | %-40s | %-40s | %-21s |%n";
            System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");
            System.out.format("| City                                     | Country                                  | District                                 | Population            |%n");
            System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");

            for (City city : cities) {
                System.out.format(leftAlignFormat, city.Name, city.CountryCode, city.District, city.Population);
                System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");
            }
        }

    }

    /**
     * Gets all the countries in the world sorted by largest population to the smallest population
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getCountries()
    {
        System.out.println("Getting countries from world sorted by largest population to the smallest population.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }

    /**
     * Gets all the countries in a set continent sorted by largest population to the smallest population
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getCountriesInContinent(String continent)
    {
        System.out.println("Getting countries in a set continent sorted by largest population to the smallest population.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Continent = " + "\'" + continent + "\'" + " ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }

    /**
     * Gets all the countries in a set region sorted by largest population to the smallest population
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getCountriesInRegion(String region)
    {
        System.out.println("Getting countries in a set region sorted by largest population to the smallest population.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Region = " + "\'" + region + "\'" + " ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }

    /**
     * Gets the top N populated countries in the world
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getNCountries(int N)
    {
        System.out.println("Getting top "+N+" populated countries in the world.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital ORDER BY Population DESC LIMIT " + N;

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }

    /**
     * Gets the top N populated countries in a set continent
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getNCountriesInContinent(int N, String continent)
    {
        System.out.println("Getting top "+N+" populated countries in a set continent.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Continent = " + "\'" + continent + "\'" + " ORDER BY Population DESC LIMIT " + N;

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }

    /**
     * Gets the top N populated countries in a set region
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getNCountriesInRegion(int N, String region)
    {
        System.out.println("Getting top "+N+" populated countries in a set region.....");
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Region = "+ "\'" + region + "\'" +" ORDER BY Population DESC LIMIT " + N;

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            // return countries
            Country country;
            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("country.Code");
                country.Name = result.getString("country.Name");
                country.Continent = result.getString("country.Continent");
                country.Region = result.getString("country.Region");
                country.Population = result.getInt("country.Population");
                country.Capital = result.getString("city.Name");

                Countries.add(country);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
            return null;
        }
        return Countries;
    }


    /**
     * Displays Country Report
     * @param countries
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        if (countries== null)
        {
            System.out.println("No countries");
            return;
        }
        else
        {
            //format table for countries
            String leftAlignFormat = "| %-5s | %-44s | %-18s | %-28s | %-12s | %-34s |%n";
            System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");
            System.out.format("| Code  | Name                                         | Continent          | Region                       | Population   | Capital                            |%n");
            System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");

            for (Country country : countries) {
                System.out.format(leftAlignFormat, country.Code, country.Name, country.Continent, country.Region, country.Population, country.Capital);
                System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");
            }
        }

    }

    /**
     * Gets all the Capital Cities in the World
     * @return A List of Cities in the World
     */
    public ArrayList<City> getCapitalCitiesInWorld()
    {
        System.out.println("Getting all the Capital Cities in the world...");

        ArrayList<City> capitalCitiesInWorld = new ArrayList<>();
        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            //String strSelect = "SELECT city.ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.countryCode = country.Code ORDER BY city.Population DESC;";
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name as 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");


                capitalCitiesInWorld.add(city);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital City details");
            return null;
        }

        return capitalCitiesInWorld;
    }

    /**
     * Gets all the Capital Cities in a continent
     * @return A List of Cities in a continent
     */
    public ArrayList<City> getCapitalCitiesInContinent(String continent)
    {
        System.out.println("Getting all the Capital Cities in a continent...");

        ArrayList<City> capitalCitiesInWorld = new ArrayList<>();
        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital WHERE country.Continent = " + "\'" + continent + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");


                capitalCitiesInWorld.add(city);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital City details");
            return null;
        }

        return capitalCitiesInWorld;
    }

    /**
     * Gets all the Capital Cities in a Region
     * @param region
     * @return
     */
    public ArrayList<City> getCapitalCitiesInRegion(String region)
    {
        System.out.println("Getting all the Capital Cities in a region...");

        ArrayList<City> capitalCitiesInWorld = new ArrayList<>();
        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital WHERE country.Region = " + "\'" + region + "\'" + " ORDER BY city.Population DESC;";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while(result.next())
            {
                city = new City();

                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");


                capitalCitiesInWorld.add(city);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital City details");
            return null;
        }

        return capitalCitiesInWorld;
    }

    /**
     * Gets top N populated capital cities in the world where N is provided by the user.
     * @param count
     * @return
     */
    public ArrayList<City> getNCapitalCities(int count)
    {
        System.out.println("Getting top " + count + " capital cities in the world...");

        ArrayList<City> capitalCities = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name as 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital ORDER BY city.Population DESC LIMIT " + count + ";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                capitalCities.add(city);
            }
            return capitalCities;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
            return null;
        }

    }

    /**
     * Gets top N populated capital cities in a continent where N is provided by the user.
     * @param continent
     * @param count
     * @return
     */
    public ArrayList<City> getNCapitalCitiesInContinent(String continent, int count)
    {
        System.out.println("Getting top " + count + " capital cities in the continent...");

        ArrayList<City> capitalCities = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital WHERE country.Continent = " + "\'" + continent + "\'" + " ORDER BY city.Population DESC LIMIT " + count + ";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                capitalCities.add(city);
            }
            return capitalCities;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
            return null;
        }

    }

    /**
     * Gets The top N populated capital cities in a region where N is provided by the user.
     * @param region
     * @param count
     * @return List of capital Cities in a region
     */
    public ArrayList<City> getNCapitalCitiesInRegion(String region, int count)
    {
        System.out.println("Getting top " + count + " capital cities in the region...");

        ArrayList<City> capitalCities = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM country INNER JOIN city ON city.id = country.Capital WHERE country.Region = " + "\'" + region + "\'" + " ORDER BY city.Population DESC LIMIT " + count + ";";

            ResultSet result = statement.executeQuery(strSelect);

            City city;

            while (result.next())
            {
                city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("City");
                city.CountryCode = result.getString("Country");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                capitalCities.add(city);
            }
            return capitalCities;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
            return null;
        }

    }

    /**
     * Gets the population of people who live in a continent and the number/percentage of people who live in and outside cities
     * @return list of continents
     */
    public ArrayList<Population> getPopulationContinent()
    {
        ArrayList<Population> population = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT SUM(countryPop.CityPopulation) AS 'cityPopulation', ROUND((SUM(countryPop.CityPopulation)/SUM(countryPop.Population)*100), 2) AS 'cityPopulation%', SUM(countryPop.Population) AS 'Population', (SUM(countryPop.Population) - SUM(countryPop.CityPopulation)) AS 'RuralPopulation', ROUND(((SUM(countryPop.Population) - SUM(countryPop.CityPopulation))/(SUM(countryPop.Population)))*100, 2) AS 'RuralPopulation%', countryPop.Continent FROM (SELECT SUM(city.Population) AS 'CityPopulation', country.Population AS 'Population', city.CountryCode, country.Continent FROM city INNER JOIN country ON city.CountryCode = country.Code GROUP BY city.CountryCode) AS countryPop GROUP BY countryPop.Continent";

            ResultSet result = statement.executeQuery(strSelect);

            Population areaPopulation;

            while (result.next())
            {
                areaPopulation = new Population();
                areaPopulation.Area = result.getString("Continent");
                areaPopulation.AreaPopulation = result.getLong("Population");
                areaPopulation.CityPopulation = result.getLong("CityPopulation");
                areaPopulation.RuralPopulation = result.getLong("RuralPopulation");
                areaPopulation.CityPopulationPercentage = result.getDouble("CityPopulation%");
                areaPopulation.RuralPopulationPercentage = result.getDouble("RuralPopulation%");

                population.add(areaPopulation);
            }
            return population;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population details");
            return null;
        }

    }

    /**
     * Gets the population of people who live in a region and the number/percentage of people who live in and outside cities
     * @return list of region
     */
    public ArrayList<Population> getPopulationRegion()
    {
        ArrayList<Population> population = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT SUM(countryPop.CityPopulation) AS 'cityPopulation', ROUND((SUM(countryPop.CityPopulation)/SUM(countryPop.Population)*100), 2) AS 'cityPopulation%', SUM(countryPop.Population) AS 'Population', (SUM(countryPop.Population) - SUM(countryPop.CityPopulation)) AS 'RuralPopulation', ROUND(((SUM(countryPop.Population) - SUM(countryPop.CityPopulation))/(SUM(countryPop.Population)))*100, 2) AS 'RuralPopulation%', countryPop.Region FROM (SELECT SUM(city.Population) AS 'CityPopulation', country.Population AS 'Population', city.CountryCode, country.Region FROM city INNER JOIN country ON city.CountryCode = country.Code GROUP BY city.CountryCode) AS countryPop GROUP BY countryPop.Region";

            ResultSet result = statement.executeQuery(strSelect);

            Population areaPopulation;

            while (result.next())
            {
                areaPopulation = new Population();
                areaPopulation.Area = result.getString("Region");
                areaPopulation.AreaPopulation = result.getLong("Population");
                areaPopulation.CityPopulation = result.getLong("CityPopulation");
                areaPopulation.RuralPopulation = result.getLong("RuralPopulation");
                areaPopulation.CityPopulationPercentage = result.getDouble("CityPopulation%");
                areaPopulation.RuralPopulationPercentage = result.getDouble("RuralPopulation%");

                population.add(areaPopulation);
            }
            return population;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population details");
            return null;
        }

    }

    /**
     * Gets the population of people who live in a country and the number/percentage of people who live in and outside cities
     * @return list of countries
     */
    public ArrayList<Population> getPopulationCountry()
    {
        ArrayList<Population> population = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT SUM(countryPop.CityPopulation) AS 'cityPopulation', ROUND((SUM(countryPop.CityPopulation)/SUM(countryPop.Population)*100), 2) AS 'cityPopulation%', SUM(countryPop.Population) AS 'Population', (SUM(countryPop.Population) - SUM(countryPop.CityPopulation)) AS 'RuralPopulation', ROUND(((SUM(countryPop.Population) - SUM(countryPop.CityPopulation))/(SUM(countryPop.Population)))*100, 2) AS 'RuralPopulation%', countryPop.Name FROM (SELECT SUM(city.Population) AS 'CityPopulation', country.Population AS 'Population', city.CountryCode, country.Name FROM city INNER JOIN country ON city.CountryCode = country.Code GROUP BY city.CountryCode) AS countryPop GROUP BY countryPop.Name";

            ResultSet result = statement.executeQuery(strSelect);

            Population areaPopulation;

            while (result.next())
            {
                areaPopulation = new Population();
                areaPopulation.Area = result.getString("Name");
                areaPopulation.AreaPopulation = result.getLong("Population");
                areaPopulation.CityPopulation = result.getLong("CityPopulation");
                areaPopulation.RuralPopulation = result.getLong("RuralPopulation");
                areaPopulation.CityPopulationPercentage = result.getDouble("CityPopulation%");
                areaPopulation.RuralPopulationPercentage = result.getDouble("RuralPopulation%");

                population.add(areaPopulation);
            }
            return population;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population details");
            return null;
        }

    }
    /**
     * Get the population of the world
     */
    public void populationOfWorld()
    {
        System.out.println("Getting the population of the world");
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT sum(Population) AS 'Population of world' FROM country;";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of world");

                System.out.println("Population of the world: " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }

    /**
     * Population of a continent
     * @param continent
     */
    public void populationOfContinent(String continent)
    {
        System.out.println("Getting the population of " + continent);
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT sum(Population) as 'Population of continent'  FROM country WHERE Continent = " + "\'" + continent +"\'";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of continent");

                System.out.println("Population of " + continent + " " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }

    /**
     * population of a region
     * @param region
     */
    public void populationOfRegion(String region)
    {
        System.out.println("Getting the population of " + region);
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT sum(Population) as 'Population of continent'  FROM country WHERE Region = " + "\'" + region +"\'";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of continent");

                System.out.println("Population of " + region + " " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }

    /**
     * Population of a country
     * @param country
     */
    public void populationOfCountry(String country)
    {
        System.out.println("Getting the population of " + country);
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT sum(Population) as 'Population of country'  FROM country WHERE country.Name = " + "\'" + country +"\'";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of country");

                System.out.println("Population of " + country + " " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }

    /**
     * Population of a district
     * @param district
     */
    public void populationOfDistrict(String district)
    {
        System.out.println("Getting the population of " + district);
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT SUM(Population) as 'Population of district'  FROM city WHERE city.District = " + "\'" + district +"\'";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of district");

                System.out.println("Population of " + district + " " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }


    /**
     * Gets the population of a city
     * @param city
     */
    public void populationOfCity(String city)
    {
        System.out.println("Getting the population of " + city);
        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT SUM(Population) as 'Population of city'  FROM city WHERE city.Name = " + "\'" + city +"\'";

            ResultSet result = statement.executeQuery(strSelect);
            while (result.next())
            {
                String pop = result.getString("Population of city");
                System.out.println("Population of " + city + " " + pop);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital City details");
        }

    }


    /**
     * Gets the specified languages people speak listed most spoken to least spoken
     * @return list of languages
     */
    public ArrayList<Language> getLanguage()
    {
        ArrayList<Language> language = new ArrayList<>();

        try
        {
            // Create SQL Statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT countrylanguage.Language AS 'Language',\n" +
                    "ROUND(SUM(((countrylanguage.Percentage)/100)*country.Population)) AS 'NoOfPeople',\n" +
                    "ROUND((((SUM(((countrylanguage.Percentage)/100)*country.Population))\n" +
                    "/(SELECT SUM(country.Population) FROM country))*100), 2)\n" +
                    "AS 'Percentage'\n" +
                    "\n" +
                    "FROM countrylanguage INNER JOIN country ON countrylanguage.CountryCode = country.Code\n" +
                    "WHERE countrylanguage.Language = 'English' OR countrylanguage.Language = 'Chinese'\n" +
                    "OR countrylanguage.Language = 'Hindi' OR countrylanguage.Language = 'Spanish'\n" +
                    "OR countrylanguage.Language = 'Arabic'\n" +
                    "GROUP BY countrylanguage.Language\n" +
                    "ORDER BY SUM(((countrylanguage.Percentage)/100)*country.Population) DESC;";

            ResultSet result = statement.executeQuery(strSelect);

            Language languages;

            while (result.next())
            {
                languages = new Language();
                languages.Name = result.getString("Language");
                languages.NoOfPeople = result.getLong("NoOfPeople");
                languages.Percentage = result.getDouble("Percentage");

                language.add(languages);
            }
            return language;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population details");
            return null;
        }

    }

    /**
     * Displays a capital city report
     * @param capitalCities
     */
    public void displayCapitalCity(ArrayList<City> capitalCities)
    {
        if (capitalCities == null)
        {
            System.out.println("No Cities");
        }
        else {
            String leftAlignFormat = "| %-40s | %-40s | %-40s |%n";
            System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+%n");
            System.out.format("| City                                     | Country                                  | Population                               |%n");
            System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+%n");

            for (City city : capitalCities) {
                System.out.format(leftAlignFormat, city.Name, city.CountryCode, city.Population);
                System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+%n");
            }
        }

    }

    /**
     * Displays a population report
     * @param Population
     */
    public void displayPopulation(ArrayList<Population> Population)
    {
        if (Population== null)
        {
            System.out.println("No countries");
            return;
        }
        else
        {
            //format table for countries
            String leftAlignFormat = "| %-58s | %-12s | %-15s | %-26s | %-16s | %-27s |%n";
            System.out.format("+------------------------------------------------------------+--------------+-----------------+----------------------------+------------------+-----------------------------+%n");
            System.out.format("| Continent/Region/Country                                   | Population   | City Population | City Population Percentage | Rural Population | Rural Population Percentage |%n");
            System.out.format("+------------------------------------------------------------+--------------+-----------------+----------------------------+------------------+-----------------------------+%n");

            for (Population population : Population) {
                System.out.format(leftAlignFormat, population.Area, population.AreaPopulation, population.CityPopulation,  population.CityPopulationPercentage,  population.RuralPopulation, population.RuralPopulationPercentage);
                System.out.format("+------------------------------------------------------------+--------------+-----------------+----------------------------+------------------+-----------------------------+%n");
            }
        }

    }

    /**
     * Displays a language report
     * @param Language
     */
    public void displayLanguage(ArrayList<Language> Language)
    {
        if (Language == null)
        {
            System.out.println("No Languages");
            return;
        }
        else {
            //format table for countries
            String leftAlignFormat = "| %-8s | %-10s | %-10s |%n";
            System.out.format("+----------+------------+------------+%n");
            System.out.format("| Language | NoOfPeople | Percentage |%n");
            System.out.format("+----------+------------+------------+%n");

            for (Language language : Language) {
                System.out.format(leftAlignFormat, language.Name, language.NoOfPeople, language.Percentage);
                System.out.format("+----------+------------+------------+%n");
            }
        }
    }


    /**
     * Connect to MySQL database
     */
    public void connect(String location, int delay)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database......");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                                "root", "example");
                //buggy part
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
}