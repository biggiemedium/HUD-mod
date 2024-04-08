package dev.px.hud.Manager;

import dev.px.hud.HUDMod;

import java.util.HashMap;
import java.util.Map;

public class SocialManager {

    private Map<String, SocialState> socialMap = new HashMap<>();

    public SocialManager() {

    }

    public void addSocial(String name, SocialState state) {
        this.socialMap.put(name, state);
    }

    public void removeSocial(String name) {
        socialMap.remove(name);
    }

    public SocialState getState(String name) {
        return socialMap.getOrDefault(name, SocialState.NEUTRAL);
    }

    public Map<String, SocialState> getSocialMap() {
        return socialMap;
    }

    public enum SocialState {
        FRIEND,
        ENEMY,
        NEUTRAL
    }
}
