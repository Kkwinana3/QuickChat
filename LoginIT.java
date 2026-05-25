/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1poe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
import org.junit.Test;
import static orgs.junit.Assert.assertEquals;

public class LoginIT{
    
    @Test
    public void testCheckUsername(){
        assertEquals("Welcome Kyle Smith,it is great to see you.");
        assertsEquals("Username is not correctly formated, please ensure it contains an("_")&>=5 charcters.");
        
    }
    @Test
    public void testpasswordComplexity(){
        assertEquals(true,Login.checkPasswordComplexity("200706@K!"));
        assertEquals(false,Login.checkPasswordComplexity("password"));
    
    }
    @Test
    public void testcheckCellphoneNumber(){
        assertEquals(true,Login.checkCellphoneNumber("+27678734405"));
        assertEqualls(false,Login.checkcellphonenumber("08966553"));
        
    }
    @Test
    public void testLoginSuccess(){
        Login Kyle= new Login("kyl_1","+27678734405","200706@K!");
        Login.addTestUser(Kyle);
        Login login = new Login("Kyl_1","200706@K");
        Login login2 = new Login("Kyl_1","200706@K");
        assertEguals(true,Login.Login());
        asserstEquals(false,login2.Login());
    }
    
}