package dev.px.hud.Util.API.HackDetector.Checks;

import dev.px.hud.Util.API.HackDetector.Detection;
import dev.px.hud.Util.Event.ReceivePacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpeedA extends Detection {

    public SpeedA() {
        super("Speed A", HackType.Movement);
    }

    private double deltaXOffGround, deltaZOffGround;

    @Override
    public boolean runCheck(EntityPlayer player) {
        return false;
    }
}
