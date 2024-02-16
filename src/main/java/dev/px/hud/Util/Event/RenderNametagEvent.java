package dev.px.hud.Util.Event;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderNametagEvent extends Event {

    private AbstractClientPlayer player;
    private double x, y, z;

    public RenderNametagEvent(AbstractClientPlayer player, double x, double y, double z) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AbstractClientPlayer player) {
        this.player = player;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
