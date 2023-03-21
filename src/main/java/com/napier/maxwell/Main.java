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

        //Country countries = main.getCounties();
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
            String strSelect = "SELECT Code, Name, Continent, Population FROM country ORDER BY Population DESC";

            // Execute SQL statement
            ResultSet result = statement.executeQuery(strSelect);

            Country country;

            while (result.next())
            {
                country = new Country();
                country.Code = result.getString("Code");
                country.Name = result.getString("Name");
                country.Continent = result.getString("Continent");
                country.Population = result.getInt("Population");

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
     * Displays all the countries in the world sorted by largest population to the smallest population
     * @param countries
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        String leftAlignFormat = "| %-5s | %-44s | %-18s | %-12s |%n";
        System.out.format("+-------+----------------------------------------------+--------------------+--------------+%n");
        System.out.format("| Code  | Name                                         | Continent          | Population   |%n");
        System.out.format("+-------+----------------------------------------------+--------------------+--------------+%n");

        for (Country country: countries)
        {
            System.out.format(leftAlignFormat, country.Code, country.Name, country.Continent, country.Population);
            System.out.format("+-------+----------------------------------------------+--------------------+--------------+%n");
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