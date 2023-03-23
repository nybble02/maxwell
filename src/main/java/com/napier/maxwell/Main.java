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

        // Connect to database
        main.connect();

        // Gets a city
        //City city = main.getCity(460);

        // Display city
        //main.displayCity(city);

        // list that contains countries
        ArrayList<Country> Countries = new ArrayList<>();

        // Gets all the countries in the world sorted by largest population to the smallest population
        Countries = main.getCountries();

        // Gets all the countries in a set continent sorted by largest population to the smallest population
        Countries = main.getCountriesInContinent("Europe");

        // Gets all the countries in a set region sorted by largest population to the smallest population
        Countries = main.getCountriesInRegion("British Islands");

        // Gets the top N populated countries
        Countries = main.getNCountries(10);

        // Gets the top N populated countries in a set continent
        Countries = main.getNCountriesInContinent(10, "Europe");

        // Gets the top N populated countries in a set region
        Countries = main.getNCountriesInRegion(1, "British Islands");


        // Display Country Reports
        main.displayCountries(Countries);

        // Disconnect from database
        main.disconnect();
    }

    /**
     * Gets a City
     * @param ID
     * @return City
     */
    public City getCity(int ID)
    {
        try
        {
            System.out.println("Getting city.....");
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT ID, Name, CountryCode, District, Population " + "FROM city " + "WHERE ID = " + ID;

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (result.next())
            {
                City city = new City();
                city.ID = result.getInt("ID");
                city.Name = result.getString("Name");
                city.CountryCode = result.getString("CountryCode");
                city.District = result.getString("District");
                city.Population = result.getInt("Population");

                return city;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
            return null;
        }
    }

    /**
     * Displays a City
     * @param city
     */

    public void displayCity(City city)
    {

        if (city != null)
        {
            System.out.println("Displaying City.....");

            System.out.println("City Name: "+ city.Name);
            System.out.println("City ID: "+ city.ID);
            System.out.println("City Country Code: "+ city.CountryCode);
            System.out.println("City District: "+ city.District);
            System.out.println("City Population: "+ city.Population);


        }
    }

    /**
     * Gets all the countries in the world sorted by largest population to the smallest population
     * @return ArrayList<Country>
     */
    public ArrayList<Country> getCountries()
    {
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
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Continent = " + "\''" + continent + "\''" + " ORDER BY Population DESC";

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
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Region = " + "\'" + region + "\'" + "ORDER BY Population DESC";

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
        ArrayList<Country> Countries = new ArrayList<>();
        try
        {
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name FROM country  INNER JOIN city ON city.id = country.Capital WHERE Continent = " + "\'" + continent + "\'" + "ORDER BY Population DESC LIMIT " + N;

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
        //format table for countries
        String leftAlignFormat = "| %-5s | %-44s | %-18s | %-28s | %-12s | %-34s |%n";
        System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");
        System.out.format("| Code  | Name                                         | Continent          | Region                       | Population   | Capital                            |%n");
        System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");

        for (Country country: countries)
        {
            System.out.format(leftAlignFormat, country.Code, country.Name, country.Continent, country.Region, country.Population, country.Capital);
            System.out.format("+-------+----------------------------------------------+--------------------+------------------------------+--------------+------------------------------------+%n");
        }

    }

    /**
     * Connect to MySQL database
     */
    public void connect()
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
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
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