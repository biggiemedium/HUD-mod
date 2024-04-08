package dev.px.hud.Util.API.HackDetector;

import dev.px.hud.Util.API.HackDetector.Checks.FlightA;
import dev.px.hud.Util.API.HackDetector.Checks.FlightB;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class HackDetector {

    private ArrayList<Detection> detections = new ArrayList<>();

    public HackDetector() {
        addDetections(
                new FlightA(),
                new FlightB()
        );
    }

    public void addDetections(Detection... detections) {
        this.detections.addAll(Arrays.asList(detections));
    }

    public ArrayList<Detection> getDetections() {
        return detections;
    }
}
