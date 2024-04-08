package dev.px.hud.Util.API.HackDetector.Checks;

import dev.px.hud.Util.API.HackDetector.Detection;
import net.minecraft.entity.player.EntityPlayer;

public class FlightB extends Detection {

    public FlightB() {
        super("Flight B", HackType.Movement);
    }

    @Override
    public boolean runCheck(EntityPlayer player) {
        return airTicks > 20 && player.motionY == 0 && this.isMoving(player);
    }

}
