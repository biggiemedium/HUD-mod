package dev.px.hud.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public interface Wrapper {

    Minecraft mc = Minecraft.getMinecraft();

    World world = mc.theWorld;

    EntityPlayerSP player = mc.thePlayer;

}
