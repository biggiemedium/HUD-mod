package dev.px.hud;

import dev.px.hud.Initalizer.CommandInitalizer;
import dev.px.hud.Initalizer.ElementInitalizer;
import dev.px.hud.Initalizer.SettingInitalizer;
import dev.px.hud.Manager.*;
import dev.px.hud.Rendering.NewGUI.CSGOGui;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Input.BindRegistry;
import dev.px.hud.Util.Config.ConfigManager;
import dev.px.hud.Util.Event.Bus.EventBus;
import dev.px.hud.Util.Event.Bus.EventManager;
import dev.px.hud.Util.EventProcessor;
import dev.px.hud.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SideOnly(Side.CLIENT)
@Mod(modid = HUDMod.MODID, version = HUDMod.VERSION)
public class HUDMod {

    public static final String NAME = "HUD Mod";
    public static final String MODID = "hudmod";
    public static final String VERSION = "2.1";

    // Initalizers
    public static ElementInitalizer elementInitalizer;
    public static EventProcessor clazz;
    public static ConfigManager configManager;
    public static SettingInitalizer clientSettingsInitalizer;
    public static CommandInitalizer commandInitalizer;

    // Manager
    public static SoundManager soundInitalizer;
    public static ColorManager colorManager;
    public static FontManager fontManager;
    public static NotificationManager notificationManager;
    public static ServerManager serverManager;

    private static Minecraft mc = Wrapper.mc;
    public static PanelGUIScreen screen;
    public static CSGOGui screen2;
    public static EventBus EVENT_BUS = new EventManager();
    public static org.apache.logging.log4j.Logger LOG = LogManager.getLogger("[" + NAME + "]"); //"[" + NAME + "]"
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static long playTime = -1;
    private long startTime = -1;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        playTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        BindRegistry.register();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ProgressManager.ProgressBar progressManager = ProgressManager.push("HUD Mod", 10);

        colorManager = new ColorManager();
        progressManager.step("Loading Color Manager");
        clazz = new EventProcessor();
        progressManager.step("Loading Event Processor");
        clientSettingsInitalizer = new SettingInitalizer();
        progressManager.step("Loading Client Settings...");
        elementInitalizer = new ElementInitalizer();
        progressManager.step("Loading Element Initializer");
        commandInitalizer = new CommandInitalizer();
        progressManager.step("Loading Command Initializer");

        serverManager = new ServerManager();
        progressManager.step("Loading server Manager...");
        soundInitalizer = new SoundManager();
        progressManager.step("Queueing up the dua lipa...");
        fontManager = new FontManager();
        progressManager.step("Setting up fonts...");
        notificationManager = new NotificationManager();
        progressManager.step("Loading Notification Manager");
        screen = new PanelGUIScreen();
        screen2 = new CSGOGui();
        configManager = new ConfigManager();
        progressManager.step("Loading Configs");

        ProgressManager.pop(progressManager);
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        long initializeTime = System.currentTimeMillis() - startTime;
        LOG.info("Started client in " + initializeTime + " ms");
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public <T> void subscribe(T object) {
        MinecraftForge.EVENT_BUS.register(object);
    }
}
