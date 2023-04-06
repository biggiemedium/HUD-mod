package dev.px.hud.Command.Commands;

import dev.px.hud.Command.Command;
import dev.px.hud.Command.CommandManifest;

@CommandManifest(name = "Auto Insult", description = "Insults opponents", aliases = "insult")
public class AutoInsult extends Command {

    String[] insults = { "Ur cheeks", "U seem like u don't have many friends", "Screw u", ""};

    @Override
    public void onRun(String[] args) {

    }
}
