package dev.px.hud.Util.API.HackDetector;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.Notification.Notification;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class DetectionCheck {

    public int VL;
    private int airTicks;

    private Minecraft mc = Minecraft.getMinecraft();
    public HackDetector detector = new HackDetector();

    public void onUpdate() {
        if(mc.theWorld == null || mc.thePlayer == null) return;
        for(Entity entity : mc.theWorld.getLoadedEntityList()) {
            if(entity instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer) entity;
                if(entityPlayer != mc.thePlayer) {
                    for(Detection d : detector.getDetections()) {
                            if(d.runCheck(entityPlayer) && System.currentTimeMillis() > d.getLastViolated() + 500) {
                                if(HUDMod.preferenceManager.HACKERDETECTOR.getValue()) {
                                    HUDMod.notificationManager.AddPushNotification(new Notification("Hacker", entityPlayer.getName() + " could be hacking!", Notification.NotificationType.WARNING, 4000));
                                }
                        //        entityPlayer.VL++;
                                d.setLastViolated(System.currentTimeMillis());
                            }
                    }
                }
            }
        }
    }

}
