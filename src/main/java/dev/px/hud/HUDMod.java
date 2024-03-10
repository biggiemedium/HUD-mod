package dev.px.hud;

import dev.px.hud.Initalizer.CommandInitalizer;
import dev.px.hud.Initalizer.ConfigInitalizer;
import dev.px.hud.Initalizer.ElementInitalizer;
import dev.px.hud.Manager.ColorManager;
import dev.px.hud.Manager.FontManager;
import dev.px.hud.Manager.NotificationManager;
import dev.px.hud.Manager.SoundManager;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.Config.ConfigManager;
import dev.px.hud.Util.Event.Bus.EventBus;
import dev.px.hud.Util.Event.Bus.EventManager;
import dev.px.hud.Util.EventProcessor;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod(modid = HUDMod.MODID, version = HUDMod.VERSION)
public class HUDMod {

    public static final String NAME = "HUD Mod";
    public static final String MODID = "hudmod";
    public static final String VERSION = "2.0";

    // Initalizers
    public static ElementInitalizer elementInitalizer;
    public static EventProcessor clazz;
    public static ConfigManager configManager;
    public static CommandInitalizer commandInitalizer;

    // Manager
    public static SoundManager soundInitalizer;
    public static ColorManager colorManager;
    public static FontManager fontManager;
    public static NotificationManager notificationManager;

    private static Minecraft mc = Wrapper.mc;
    public static PanelGUIScreen screen;
    public static EventBus EVENT_BUS = new EventManager();
    public static long playTime = -1;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        playTime = System.currentTimeMillis();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        colorManager = new ColorManager();
        clazz = new EventProcessor();
        notificationManager = new NotificationManager();
        elementInitalizer = new ElementInitalizer();
        commandInitalizer = new CommandInitalizer();

        soundInitalizer = new SoundManager();
        fontManager = new FontManager();
        screen = new PanelGUIScreen();
        configManager = new ConfigManager();
    }

    @EventHandler
    public void prepost(FMLPostInitializationEvent event) {
    }

    public <T> void subscribe(T object) {
        MinecraftForge.EVENT_BUS.register(object);
    }
}
