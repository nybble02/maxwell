package com.napier.maxwell;

import java.sql.*;
import java.util.ArrayList;

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

        // List that contains cities
        ArrayList<City> Cities = new ArrayList<>();

        // Gets all the cities in the world sorted by largest population to the smallest population
        //Cities = main.getCitiesInWorld();

        // Gets the cities that are in a continent
        //Cities = main.getCitiesInContinent("Africa");

        // Gets the cities in a Region
        //Cities = main.getCitiesInRegion("Southern Europe");

        //Gets the cities in a Country
        //Cities = main.getCitiesInCountry("South Africa");

        //Gets the cities in a District
        Cities = main.getCitiesInDistrict("Scotland");

        // Display city
        main.displayCity(Cities);

        // Disconnect from database
        main.disconnect();
    }

    /**
     * Gets all the Cities in the World
     * @return A List of Cities in the World
     */
    public ArrayList<City> getCitiesInWorld()
    {
        ArrayList<City> citiesInWorld = new ArrayList<>();
        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();

            // String for SQL statement
            String strSelect = "SELECT city.ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.countryCode = country.Code ORDER BY city.Population DESC;";

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
     * @return A List of Cities in a Continent
     */
    public ArrayList<City> getCitiesInContinent(String continent)
    {
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
     * @return
     */
    public ArrayList<City> getCitiesInRegion(String region)
    {
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

    public ArrayList<City> getCitiesInDistrict(String district)
    {
        ArrayList<City> citiesInDistrict =  new ArrayList<>();

        try
        {
            // Create a SQL statement
            Statement statement = con.createStatement();
            String strSelect = "SELECT ID, city.Name AS 'City', country.Name AS 'Country', District, city.Population FROM city INNER JOIN country ON city.CountryCode = country.Code WHERE country.Name = " + "\'" + district + "\'" + " ORDER BY city.Population DESC;";

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
     * Displays A City Report
     * @param cities ArrayList
     */
    public void displayCity(ArrayList<City> cities)
    {
        String leftAlignFormat = "| %-40s | %-40s | %-40s | %-21s |%n";
        System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");
        System.out.format("| City                                     | Country                                  | District                                 | Population            |%n");
        System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");

        for (City city: cities)
        {
            System.out.format(leftAlignFormat, city.Name, city.CountryCode, city.District, city.Population);
            System.out.format("+------------------------------------------+------------------------------------------+------------------------------------------+-----------------------+%n");
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