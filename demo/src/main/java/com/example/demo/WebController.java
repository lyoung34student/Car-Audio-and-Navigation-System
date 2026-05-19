package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final SystemState systemState;

    public WebController(SystemState systemState) {
        this.systemState = systemState;
    }

    @GetMapping("/")
    public String home(Model model) {
        loadModel(model);
        return "index";
    }

    @PostMapping("/connect-usb")
    public String connectUsb() {
        systemState.connectUsb();
        return "redirect:/";
    }

    @PostMapping("/play")
    public String play() {
        systemState.play();
        return "redirect:/";
    }

    @PostMapping("/pause")
    public String pause(@RequestParam(required = false, defaultValue = "0") double position) {
        systemState.setPlaybackPosition(position);
        systemState.pause();
        return "redirect:/";
    }

    @PostMapping("/resume")
    public String resume() {
        systemState.resume();
        return "redirect:/";
    }

    @PostMapping("/next")
    public String next() {
        systemState.next();
        return "redirect:/";
    }

    @PostMapping("/previous")
    public String previous() {
        systemState.previous();
        return "redirect:/";
    }

    @PostMapping("/toggle-mute")
    public String toggleMute() {
        systemState.toggleMute();
        return "redirect:/";
    }

    @PostMapping("/set-volume")
    public String setVolume(@RequestParam int volume) {
        systemState.setVolume(volume);
        return "redirect:/";
    }

    @PostMapping("/set-audio-settings")
    public String setAudioSettings(@RequestParam int bass,
                                   @RequestParam int treble,
                                   @RequestParam int balance) {
        systemState.setBass(bass);
        systemState.setTreble(treble);
        systemState.setBalance(balance);
        return "redirect:/";
    }

    @PostMapping("/sync-playback")
    public String syncPlayback(@RequestParam double position,
                               @RequestParam boolean playing,
                               @RequestParam boolean paused) {
        systemState.setPlaybackPosition(position);
        if (paused) {
            systemState.pause();
        } else if (playing) {
            systemState.play();
        }
        return "redirect:/";
    }

    @PostMapping("/update-location")
    public String updateLocation(@RequestParam double latitude,
                                 @RequestParam double longitude) {
        systemState.updateLocation(latitude, longitude);
        return "redirect:/";
    }

    @PostMapping("/set-radio-mode")
    public String setRadioMode(@RequestParam String mode) {
        systemState.setRadioMode(mode);
        return "redirect:/";
    }

    @PostMapping("/tune-radio")
    public String tuneRadio(@RequestParam double frequency) {
        systemState.tuneFrequency(frequency);
        return "redirect:/";
    }

    @PostMapping("/calculate-route")
    public String calculateRoute(@RequestParam double startLat,
                                 @RequestParam double startLng,
                                 @RequestParam double destLat,
                                 @RequestParam double destLng) {
        systemState.calculateRoute(startLat, startLng, destLat, destLng);
        return "redirect:/";
    }

    private void loadModel(Model model) {
        model.addAttribute("usbConnected", systemState.isUsbConnected());
        model.addAttribute("tracks", systemState.getTracks());
        model.addAttribute("currentTrack", systemState.getCurrentTrack());
        model.addAttribute("currentTrackUrl", systemState.getCurrentTrackUrl());
        model.addAttribute("playing", systemState.isPlaying());
        model.addAttribute("paused", systemState.isPaused());
        model.addAttribute("muted", systemState.isMuted());
        model.addAttribute("volume", systemState.getVolume());
        model.addAttribute("previousVolume", systemState.getPreviousVolume());
        model.addAttribute("playbackPosition", systemState.getPlaybackPosition());

        model.addAttribute("bass", systemState.getBass());
        model.addAttribute("treble", systemState.getTreble());
        model.addAttribute("balance", systemState.getBalance());

        model.addAttribute("latitude", systemState.getLatitude());
        model.addAttribute("longitude", systemState.getLongitude());

        model.addAttribute("radioMode", systemState.getRadioMode());
        model.addAttribute("currentFrequency", systemState.getCurrentFrequency());
        model.addAttribute("radioMessage", systemState.getRadioMessage());
        model.addAttribute("radioPlaying", systemState.isRadioPlaying());

        model.addAttribute("startLat", systemState.getStartLat());
        model.addAttribute("startLng", systemState.getStartLng());
        model.addAttribute("destLat", systemState.getDestLat());
        model.addAttribute("destLng", systemState.getDestLng());
        model.addAttribute("routeMessage", systemState.getRouteMessage());
        model.addAttribute("routePoints", systemState.getRoutePoints());
    }
}