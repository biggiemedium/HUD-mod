package dev.px.hud.Command.Commands;

import dev.px.hud.Command.Command;
import dev.px.hud.Command.CommandManifest;
import dev.px.hud.Util.API.Util;

@CommandManifest(name = "AutoGG", description = "Automatically sends gg to match", aliases = "gg")
public class AutoGG extends Command {

    private String[] gg = {
        "gg", "Good game", "gg wp", "Well played"
    };

    private String lastText = "";

    @Override
    public void onRun(String[] args) {
        if (args.length <= 1) {
            Util.sendClientSideMessage("Not enough args.");
            return;
        }


    }

}
