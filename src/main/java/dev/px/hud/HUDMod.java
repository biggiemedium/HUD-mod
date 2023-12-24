package dev.px.hud;

import dev.px.hud.Initalizer.CommandInitalizer;
import dev.px.hud.Initalizer.ConfigInitalizer;
import dev.px.hud.Initalizer.ElementInitalizer;
import dev.px.hud.Util.Classutil;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = HUDMod.MODID, version = HUDMod.VERSION)
public class HUDMod {

    public static final String MODID = "hudmod";
    public static final String VERSION = "1.0";

    public static ElementInitalizer elementInitalizer;
    public static Classutil clazz;
    public static ConfigInitalizer configInitalizer;
    public static CommandInitalizer commandInitalizer;

    private static Minecraft mc = Wrapper.mc;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        clazz = new Classutil();
        elementInitalizer = new ElementInitalizer();
        configInitalizer = new ConfigInitalizer();
        commandInitalizer = new CommandInitalizer();

        configInitalizer.loads();
    }

    public <T> void subscribe(T object) {
        MinecraftForge.EVENT_BUS.register(object);
    }
}
