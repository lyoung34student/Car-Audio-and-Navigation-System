package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AudioTrackTest {

    @Test
    public void testIsSupportedForMp3AndTxt() {
        AudioTrack supportedTrack = new AudioTrack("My Song", "music.mp3");
        AudioTrack unsupportedTrack = new AudioTrack("Text File", "notes.txt");

        assertTrue(supportedTrack.isSupported());
        assertFalse(unsupportedTrack.isSupported());
    }
}