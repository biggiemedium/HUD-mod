package dev.px.hud.Manager;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Math.Timer;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Settings.Setting;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Taken from Flux b39 client
@SideOnly(Side.CLIENT)
public class ServerManager extends Util {

    private Timer timer = new Timer();
    private Server server;
    public boolean isHypixelScoreboard = false;

    public ServerManager() {
        timer.reset();
        this.server = Server.Unknown;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if(event.type == TickEvent.Type.CLIENT) {
            if(timer.passed(1000)) {
                processScoreboard();
                if(!isOnHypixel()) {
                    tick();
                } else {
                    server = Server.Hypixel;
                }

                timer.reset();
            }
        }
    }

    public void tick() {
        if (mc.isSingleplayer()) {
            server = Server.SinglePlayer;
        } else {
            ServerData data = mc.getCurrentServerData();
            String motd = data.serverMOTD;

            if (motd.contains("Mineplex")) {
                server = Server.Mineplex;
            } else if (motd.contains("CubeCraft")) {
                server = Server.CubeCraft;
            } else {
                server = Server.Unknown;
            }
        }
    }

    public boolean isOnHypixel() {
        return isHypixelScoreboard && !mc.isSingleplayer();
    }

    public boolean isHypixelLobby() {
        String[] strings = new String[] {"CLICK TO PLAY", "点击开始游戏"};
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity.getName().startsWith("§e§l")) {
                for (String string : strings) {
                    if (entity.getName().equals("§e§l" + string)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void processScoreboard() {
        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());

        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective
                : scoreboard.getObjectiveInDisplaySlot(1);

        if (scoreobjective1 != null) {
            fakeRenderScoreboard(scoreobjective1);
        }
    }

    private void fakeRenderScoreboard(ScoreObjective objective) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection collection = scoreboard.getSortedScores(objective);

        List<Score> filteredList = (List<Score>) collection.stream()
                .filter(score -> {
                    if (score instanceof Score) { // Check if the element is actually a Score instance
                        Score s = (Score) score;
                        return s.getPlayerName() != null && !s.getPlayerName().startsWith("#");
                    }
                    return false; // If it's not a Score instance, exclude it
                })
                .collect(Collectors.toList());
        ArrayList<Score> arraylist = new ArrayList<>(filteredList);


        ArrayList arraylist1;

        if (arraylist.size() > 15) {
            arraylist1 = Lists.newArrayList(Iterables.skip(arraylist, collection.size() - 15));
        } else {
            arraylist1 = arraylist;
        }

        for (Object b : arraylist1) {
            Score score1 = (Score) b;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            int chars = 0;
            String str = "www.hypixel.net";

            for (char c : str.toCharArray()) {
                if (s1.contains(String.valueOf(c))) chars++;
            }

            if (chars == str.length() && this.server != Server.Hypixel) {
              //  Flux.INSTANCE.receivedPacket = true;
                this.server = Server.Hypixel;
                this.isHypixelScoreboard = true;
            }
        }
    }

    public enum Server {
        Hypixel, HypixelCN, Mineplex, CubeCraft, Unknown, SinglePlayer
    }
}
