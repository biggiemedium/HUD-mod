package dev.px.hud.Manager;

import dev.px.hud.Rendering.Notification.DropdownNotification;
import dev.px.hud.Rendering.Notification.Notification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {

    private List<Notification> notifications;
    private static LinkedBlockingQueue<DropdownNotification> pendingNotifications = new LinkedBlockingQueue<>();
    private static DropdownNotification currentNotification = null;

    private ScaledResolution sr;

    public NotificationManager() {
        this.notifications = new CopyOnWriteArrayList<>();
        sr = new ScaledResolution(Minecraft.getMinecraft());
    }

    public void AddDropdownNotification(DropdownNotification notification) {
        pendingNotifications.add(notification);
    }

    private void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }
    }

    public void AddPushNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void render2D() {
        if (notifications.size() > 8) { // overflow precautions
            notifications.remove(0);
        }
        float startY = (float) (sr.getScaledHeight() - 13);
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            notifications.removeIf(Notification::isRemoveable);
            notification.render(startY);
            startY -= notification.getHeight() + 3;
        }

        // Drop down notifs
        update();
        if (currentNotification != null)
            currentNotification.render();
    }
}
