package dev.px.hud.Command;

import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;

public abstract class Command {

    private String name;
    private String description;
    private String usage;
    private String[] alias;

    protected Minecraft mc = Wrapper.mc;

    public Command() {
        if (getClass().isAnnotationPresent(CommandManifest.class)) {
            CommandManifest moduleManifest = getClass().getAnnotation(CommandManifest.class);
            this.name = moduleManifest.name();
            this.alias = moduleManifest.aliases();
            this.description = moduleManifest.description();
            this.usage = moduleManifest.usage();
        }
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
