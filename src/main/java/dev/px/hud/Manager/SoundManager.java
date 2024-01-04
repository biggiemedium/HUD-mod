package dev.px.hud.Manager;

import dev.px.hud.Util.API.Util;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LinusTouchTips
 */
@SideOnly(Side.CLIENT)
public class SoundManager extends Util {

    public SoundManager() {

    }

    public void playSound(String sound) {
        mc.getSoundHandler().playSound(new ISound() {

            @Override
            public ResourceLocation getSoundLocation() {
                return new ResourceLocation("/Sounds/" + sound + ".ogg");
            }

            @Override
            public boolean canRepeat() {
                return false;
            }

            @Override
            public int getRepeatDelay() {
                return 0;
            }

            @Override
            public float getVolume() {
                return 1;
            }

            @Override
            public float getPitch() {
                return 1;
            }

            @Override
            public float getXPosF() {
                return mc.thePlayer != null ? (float) mc.thePlayer.posX : 0;
            }

            @Override
            public float getYPosF() {
                return mc.thePlayer != null ? (float) mc.thePlayer.posY: 0;
            }

            @Override
            public float getZPosF() {
                return mc.thePlayer != null ? (float) mc.thePlayer.posZ : 0;
            }

            @Override
            public AttenuationType getAttenuationType() {
                return AttenuationType.LINEAR;
            }
        });
    }

}
