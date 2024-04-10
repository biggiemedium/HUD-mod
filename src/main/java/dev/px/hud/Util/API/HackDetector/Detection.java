package dev.px.hud.Util.API.HackDetector;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraftforge.common.MinecraftForge;

// Idea from tenacity - I fucking hate skywars so I wanna know if someones hacking

public abstract class Detection {

    private String name;
    private HackType type;
    private long lastViolated;
    public float airTicks = 0;

    public Detection(String name, HackType type) {
        this.name = name;
        this.type = type;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public abstract boolean runCheck(EntityPlayer player);

    public void setAirTicks(float airTicks) {
        this.airTicks = airTicks;
    }

    protected boolean isMoving(EntityPlayer player) {
        return player.moveForward != 0F || player.moveStrafing != 0F;
    }

    protected boolean isFalseFlaggable(EntityPlayer player) {
        return player.isInWater() || player.isInLava() || player.isOnLadder() || player.ticksExisted < 10;
    }

    public boolean isRelMove(Packet<?> p) {
        return p instanceof S14PacketEntity.S15PacketEntityRelMove
                || p instanceof S14PacketEntity.S17PacketEntityLookMove
                || p instanceof S14PacketEntity.S16PacketEntityLook;
    }

    public boolean isLook(Packet<?> p) {
        return p instanceof S14PacketEntity.S17PacketEntityLookMove
                || p instanceof S14PacketEntity.S16PacketEntityLook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HackType getType() {
        return type;
    }

    public void setType(HackType type) {
        this.type = type;
    }

    public long getLastViolated() {
        return lastViolated;
    }

    public void setLastViolated(long lastViolated) {
        this.lastViolated = lastViolated;
    }

    public enum HackType {
        Combat,
        Movement
    }
}
