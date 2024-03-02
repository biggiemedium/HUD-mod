package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Notification.Notification;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldSettings;

public class FakePlayer extends ToggleableElement {

    public FakePlayer() {
        super("Fake Player", "Spawns fake entity (Client Side)", HUDType.MOD);
    }

    private EntityOtherPlayerMP fakePlayer;

    @Override
    public void enable() {
        this.fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakePlayer.rotationPitch = mc.thePlayer.rotationPitch;
        fakePlayer.rotationYaw = mc.thePlayer.rotationYaw;
        fakePlayer.setGameType(WorldSettings.GameType.SURVIVAL);
        mc.theWorld.addEntityToWorld(-69420, fakePlayer);

        fakePlayer.inventory.copyInventory(mc.thePlayer.inventory);
        HUDMod.notificationManager.Add(new Notification("Fake Player", "Fake player has been summoned!", Notification.NotificationType.INFO, 5));
    }

    @Override
    public void disable() {
        if(mc.theWorld.loadedEntityList.contains(fakePlayer) && fakePlayer != null) {
            mc.theWorld.removeEntity(fakePlayer);
        }
    }
}
