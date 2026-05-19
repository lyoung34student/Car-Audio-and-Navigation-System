package com.example.demo;

public class AudioTrack {
    private String title;
    private String fileName;

    public AudioTrack(String title, String fileName) {
        this.title = title;
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isSupported() {
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav");
    }
}