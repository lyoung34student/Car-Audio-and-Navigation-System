package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SystemState {

    public static class Track {
        private final String title;
        private final String url;

        public Track(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }

    /* ===================== USB ===================== */
    private boolean usbConnected = false;

    /* ===================== AUDIO PLAYBACK ===================== */
    private final List<Track> tracks = new ArrayList<>();
    private int currentIndex = 0;
    private boolean playing = false;
    private boolean paused = false;
    private boolean muted = false;
    private int volume = 50;
    private int previousVolume = 50;
    private double playbackPosition = 0.0;

    /* ===================== AUDIO TONE SETTINGS ===================== */
    private int bass = 0;
    private int treble = 0;
    private int balance = 0;

    /* ===================== GPS ===================== */
    private double latitude = 32.7157;
    private double longitude = -117.1611;

    /* ===================== RADIO ===================== */
    private String radioMode = "FM";
    private Double currentFrequency = null;
    private String radioMessage = "No station selected.";
    private boolean radioPlaying = false;

    /* ===================== ROUTE / MAP ===================== */
    private double startLat = 32.7157;
    private double startLng = -117.1611;
    private double destLat = 32.7357;
    private double destLng = -117.1496;
    private String routeMessage = "No route calculated yet.";

    public SystemState() {
        tracks.add(new Track("Song 1", "/audio/song1.mp3"));
        tracks.add(new Track("Song 2", "/audio/song2.mp3"));
    }

    /* ===================== USB ===================== */
    public boolean isUsbConnected() {
        return usbConnected;
    }

    public void connectUsb() {
        usbConnected = true;
    }

    /* ===================== AUDIO ===================== */
    public List<Track> getTracks() {
        return tracks;
    }

    public Track getCurrentTrackObject() {
        if (tracks.isEmpty()) {
            return null;
        }
        return tracks.get(currentIndex);
    }

    public String getCurrentTrack() {
        Track t = getCurrentTrackObject();
        return t == null ? "No tracks available" : t.getTitle();
    }

    public String getCurrentTrackUrl() {
        Track t = getCurrentTrackObject();
        return t == null ? "" : t.getUrl();
    }

    public void play() {
        if (!tracks.isEmpty()) {
            playing = true;
            paused = false;
            radioPlaying = false;
        }
    }

    public void pause() {
        if (playing) {
            playing = false;
            paused = true;
            radioPlaying = false;
        }
    }

    public void resume() {
        if (!tracks.isEmpty() && paused) {
            playing = true;
            paused = false;
            radioPlaying = false;
        }
    }

    public void next() {
        if (!tracks.isEmpty()) {
            currentIndex = (currentIndex + 1) % tracks.size();
            playing = true;
            paused = false;
            playbackPosition = 0.0;
            radioPlaying = false;
        }
    }

    public void previous() {
        if (!tracks.isEmpty()) {
            currentIndex = (currentIndex - 1 + tracks.size()) % tracks.size();
            playing = true;
            paused = false;
            playbackPosition = 0.0;
            radioPlaying = false;
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isMuted() {
        return muted;
    }

    public int getVolume() {
        return volume;
    }

    public int getPreviousVolume() {
        return previousVolume;
    }

    public double getPlaybackPosition() {
        return playbackPosition;
    }

    public void setPlaybackPosition(double playbackPosition) {
        this.playbackPosition = Math.max(0.0, playbackPosition);
    }

    public void setVolume(int newVolume) {
        if (muted) {
            return;
        }

        if (newVolume < 0) newVolume = 0;
        if (newVolume > 100) newVolume = 100;

        volume = newVolume;
        previousVolume = newVolume;
    }

    public void toggleMute() {
        if (!muted) {
            previousVolume = volume;
            volume = 0;
            muted = true;
            playing = false;
        } else {
            muted = false;
            volume = previousVolume;
            if (!tracks.isEmpty()) {
                playing = true;
                paused = false;
            }
        }
    }

    /* ===================== AUDIO SETTINGS ===================== */
    public int getBass() {
        return bass;
    }

    public int getTreble() {
        return treble;
    }

    public int getBalance() {
        return balance;
    }

    public void setBass(int bass) {
        this.bass = clampAudioSetting(bass);
    }

    public void setTreble(int treble) {
        this.treble = clampAudioSetting(treble);
    }

    public void setBalance(int balance) {
        this.balance = clampAudioSetting(balance);
    }

    private int clampAudioSetting(int value) {
        if (value < -10) return -10;
        if (value > 10) return 10;
        return value;
    }

    /* ===================== GPS ===================== */
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /* ===================== RADIO ===================== */
    public String getRadioMode() {
        return radioMode;
    }

    public Double getCurrentFrequency() {
        return currentFrequency;
    }

    public String getRadioMessage() {
        return radioMessage;
    }

    public boolean isRadioPlaying() {
        return radioPlaying;
    }

    public void setRadioMode(String mode) {
        if (mode != null && (mode.equalsIgnoreCase("AM") || mode.equalsIgnoreCase("FM"))) {
            this.radioMode = mode.toUpperCase();
            this.currentFrequency = null;
            this.radioPlaying = false;
            this.radioMessage = "Switched to " + this.radioMode + " band. No station selected.";
            this.playing = false;
            this.paused = false;
        }
    }

    public void tuneFrequency(double frequency) {
        if ("FM".equals(radioMode)) {
            if (frequency >= 87.5 && frequency <= 108.0) {
                currentFrequency = frequency;
                radioPlaying = !muted;
                playing = false;
                paused = false;
                radioMessage = "Tuned to FM " + frequency;
            } else {
                radioMessage = "Invalid FM frequency. Enter a value between 87.5 and 108.0.";
            }
        } else {
            if (frequency >= 530 && frequency <= 1700) {
                currentFrequency = frequency;
                radioPlaying = !muted;
                playing = false;
                paused = false;
                radioMessage = "Tuned to AM " + frequency;
            } else {
                radioMessage = "Invalid AM frequency. Enter a value between 530 and 1700.";
            }
        }
    }

    /* ===================== ROUTE / MAP ===================== */
    public double getStartLat() {
        return startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public double getDestLat() {
        return destLat;
    }

    public double getDestLng() {
        return destLng;
    }

    public String getRouteMessage() {
        return routeMessage;
    }

    public void calculateRoute(double startLat, double startLng, double destLat, double destLng) {
        this.startLat = startLat;
        this.startLng = startLng;
        this.destLat = destLat;
        this.destLng = destLng;
        this.routeMessage = "Route calculated successfully.";
    }

    public List<List<Double>> getRoutePoints() {
        return Arrays.asList(
            Arrays.asList(startLat, startLng),
            Arrays.asList((startLat + destLat) / 2 + 0.005, (startLng + destLng) / 2 - 0.005),
            Arrays.asList(destLat, destLng)
        );
    }
}