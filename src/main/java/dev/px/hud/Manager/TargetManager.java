package dev.px.hud.Manager;

import dev.px.hud.Util.API.Util;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class TargetManager extends Util {

    private ArrayList<EntityPlayer> targetList;

    public TargetManager() {
        this.targetList = new ArrayList<>();
    }

    public void addTarget(EntityPlayer player) {
        this.targetList.add(player);
    }
}
