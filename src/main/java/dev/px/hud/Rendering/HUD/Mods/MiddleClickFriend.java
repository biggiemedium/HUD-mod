package dev.px.hud.Rendering.HUD.Mods;

import dev.px.hud.HUDMod;
import dev.px.hud.Manager.SocialManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Notification.DropdownNotification;
import dev.px.hud.Util.API.Input.Keybind;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MiddleClickFriend extends ToggleableElement {

    public MiddleClickFriend() {
        super("Add Friend", HUDType.MOD);
    }

    @Override
    public void enable() {
        HUDMod.notificationManager.AddDropdownNotification(new DropdownNotification("Press middle click on a player to add them to your friends list!", 4));
    }

    @SubscribeEvent
    public void onMouse(InputEvent.MouseInputEvent event) {
        if(Mouse.isButtonDown(2)) {
            if(mc.objectMouseOver.entityHit != null) {
                Entity e = mc.objectMouseOver.entityHit;
                if(e instanceof EntityPlayer) {
                    if(HUDMod.socialManager.getState(e.getName()) != SocialManager.SocialState.FRIEND) {
                        HUDMod.socialManager.addSocial(e.getName(), SocialManager.SocialState.FRIEND);
                    } else if(HUDMod.socialManager.getState(e.getName()) == SocialManager.SocialState.FRIEND) {
                        HUDMod.socialManager.removeSocial(e.getName());
                    }

                }
            }
        }
    }

}
