package dev.px.hud.Command.Commands;

import dev.px.hud.Command.Command;
import dev.px.hud.Command.CommandManifest;

@CommandManifest(name = "Auto Cope", description = "Insults opponent to cope with your saltiness", aliases = "cope")
public class AutoCope extends Command {

    String[] cope = { "The sun was in my eyes",
            "Bro it was my little brother playing I wouldve clapped",
            "I wasn't even trying I swear",
            "i wouldve lived if i didn't run out of health",
            "I was playing with one hand 'cause nosebleed",
            "Was playing with touchpad",
            "a mosquito just bit my hand. gg."};

}
