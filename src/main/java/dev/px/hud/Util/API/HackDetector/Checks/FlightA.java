package dev.px.hud.Util.API.HackDetector.Checks;

import dev.px.hud.Util.API.HackDetector.Detection;
import net.minecraft.entity.player.EntityPlayer;

public class FlightA extends Detection {

    public FlightA() {
        super("FlightA", HackType.Movement);
    }

    @Override
    public boolean runCheck(EntityPlayer player) {
        return !player.onGround && player.motionY == 0 && this.isMoving(player);
    }
}
