package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class USBServiceTest {

    @Test
    public void testConnectUSBChangesStatusToTrue() {
        USBService usbService = new USBService();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            usbService.connectUSB();
            usbService.showStatus();
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString();

        assertTrue(output.contains("USB Connected."));
        assertTrue(output.contains("USB Connected: true"));
    }
}