package com.napier.maxwell;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This is where all the integration tests are stored:
 */
public class MainIntegrationTest
{
    /**
     * Initialising main
     */
    static Main main;

    @BeforeAll
    static void init()
    {
        main = new Main();
        main.connect("localhost:3366", 30000);

    }

    /**
     * Integration test for testCitiesInContinent
     */
    @Test
    void testCitiesInContinent()
    {
        ArrayList<City> getNCitiesInContinent = new ArrayList<>();
        getNCitiesInContinent = main.getNCitiesInContinent("Europe", 3);
        main.displayCity(getNCitiesInContinent);

    }

    /**
     * Integration test for testCitiesInDistrict
     */
    @Test
    void testCitiesInDistrict()
    {
        ArrayList<City> getCitiesInDistrict = new ArrayList<>();
        getCitiesInDistrict = main.getCitiesInDistrict("Scotland");
        //Scotland is a District in the database fyi.
        main.displayCity(getCitiesInDistrict);
    }
}