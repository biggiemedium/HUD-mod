package dev.px.hud.Initalizer;

import dev.px.hud.Command.Command;
import dev.px.hud.Command.Commands.AutoCope;
import dev.px.hud.Command.Commands.AutoGG;
import dev.px.hud.Command.Commands.AutoInsult;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.HashMapManager;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Wrapper;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandInitalizer extends HashMapManager<String, Command> {

    public ArrayList<Command> commands = new ArrayList<>();
    private String prefix = ".";

    public CommandInitalizer() {
        Add(new AutoCope());
        Add(new AutoGG());
        Add(new AutoInsult());
    }

    public void Add(Command command) {
        this.commands.add(command);
    }

    public void dispatch(NetworkManager handler) {
        Wrapper.mc.getNetHandler().handleChat(new S02PacketChat());
        
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        String message = event.getComponent().getFormattedText();
        String[] command = message.split(" ");

        if(!message.startsWith(prefix))
            return;

        Util.sendClientSideMessage("Cancel event");
        event.setCanceled(true);

        /*
        Command co = getRegistry().get(command[0]);
        if (co != null) {
            co.onRun(command);
        }
         */

        this.commands.forEach(c -> {
            for(String s : c.getAlias()) {
                if(s.equalsIgnoreCase(command[0])) {
                    try {
                        c.onRun(command);
                    } catch (Exception e) { e.getStackTrace(); }


                }
            }
        });
    }
}
