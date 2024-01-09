package dev.px.hud.Manager;

import dev.px.hud.Rendering.Notification.Notification;
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

    public NotificationManager() {
        this.notifications = new CopyOnWriteArrayList<>();
    }

    private Notification removeNotification(int time) {
        notifications.removeIf(n -> n.getTimer().passed(time));
        return null;
    }

    public void Add(Notification notification) {
        this.notifications.add(notification);
    }

    @SubscribeEvent
    public void tick(TickEvent event) {
        if(event.type == TickEvent.Type.CLIENT) {
            removeNotification(5000);
        }
    }

}
