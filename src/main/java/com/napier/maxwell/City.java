package com.napier.maxwell;

/**
* Represents a city
*/

import java.sql.Connection;

public class City
{
    private Connection con = null;

    /**
     * City ID
     */
    public int ID;

    /**
     * City Name
     */
    public String Name;

    /**
     * City's Country Code
     */
    public String CountryCode;

    /**
     * City's district
     */
    public String District;

    /**
     * The population of a City
     */
    public int Population;


}
