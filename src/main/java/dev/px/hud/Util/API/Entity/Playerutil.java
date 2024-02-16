package dev.px.hud.Util.API.Entity;

import dev.px.hud.Util.Wrapper;

public class Playerutil implements Wrapper {

    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

}
