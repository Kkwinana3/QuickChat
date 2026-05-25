/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.part2poe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Student
 */
public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        // Success case: content is under 250 characters
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // Failure case: content exceeds 250 characters
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        Message msg = new Message("+27718693002", longText.toString());
        String expectedMessage = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(expectedMessage, msg.checkMessageLength());
    }

    @Test
    public void testRecipientNumberFormattingSuccess() {
        // Test Task 1 style international code validation
        Message msg1 = new Message("+27718693002", "Valid Number");
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());

        // Test Task 2 style local formatting validation
        Message msg2 = new Message("08575975889", "Valid Number");
        assertEquals("Cell phone number successfully captured.", msg2.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberFormattingFailure() {
        // Incorrect format/missing codes
        Message msg = new Message("12345", "Invalid Number");
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expected, msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashGeneration() {
        // Testing specific string return requirement for Test Case 1 matching "Hi Mike..."
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testMessageIDCreation() {
        Message msg = new Message("+27718693002", "Checking ID");
        // Ensure ID generates automatically and is within 10 characters constraint
        assertTrue(msg.checkMessageID());
        assertNotNull(msg.getMessageId());
    }

    @Test
    public void testMessageSentActions() {
        Message msg = new Message("+27718693002", "Checking Actions");

        // 1) User selected 'Send Message'
        assertEquals("Message successfully sent.", msg.sentMessage("Send Message"));

        // 2) User selected 'Disregard Message'
        assertEquals("Press 0 to delete the message.", msg.sentMessage("Disregard Message"));

        // 3) User selected 'Store Message'
        assertEquals("Message successfully stored.", msg.sentMessage("Store Message"));
    }
}
