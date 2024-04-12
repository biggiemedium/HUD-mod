package dev.px.hud.Command;

import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;

public abstract class Command {

    private String name;
    private String description;
    private String usage;
    private String[] alias;

    protected Minecraft mc = Wrapper.mc;

    public Command(String name, String description) {

    }

    public void onRun(String[] args) {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public String getUsage() {
        return this.usage;
    }
}
