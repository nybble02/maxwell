package com.napier.maxwell;

import java.sql.*;
import java.util.Scanner;
public class Main
{
    /**
     *  Connection to MySQL database
     */
    private Connection con = null;



    public static void main(String[] args)
    {
        // Create Scanner Object - for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Start ~");

        String userName = scanner.nextLine();  // Read user input
        System.out.println("Username is: " + userName);  // Output user input


        // Create new Application
        //Main main = new Main();

        // Connect to database
        //main.connect();

        // Gets a city
        //City city = main.getCity(460);


        // Display city
        //main.displayCity(city);


        // Disconnect from database
        //main.disconnect();
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