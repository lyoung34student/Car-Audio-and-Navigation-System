package com.example.demo;

public class USBService {
    private boolean connected = false;

    public void connectUSB() {
        connected = true;
        System.out.println("USB Connected.");
    }

    public void showStatus() {
        System.out.println("USB Connected: " + connected);
    }
}