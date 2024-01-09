package dev.px.hud.Manager;

import dev.px.hud.Rendering.Notification.Notification;
import dev.px.hud.Util.Event.Render2dEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {

    private List<Notification> notifications;
    private ScaledResolution sr;

    public NotificationManager() {
        this.notifications = new CopyOnWriteArrayList<>();
        sr = new ScaledResolution(Minecraft.getMinecraft());
    }

    public void Add(Notification notification) {
        this.notifications.add(notification);
    }

    public void render2D() {
        if (notifications.size() > 8) { // overflow precautions
            notifications.remove(0);
        }
        float startY = (float) (sr.getScaledHeight() * 1 - 24);
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            notifications.removeIf(Notification::isRemoveable);
            notification.render(startY);
            startY -= notification.getHeight() + 3;
        }
    }
}
