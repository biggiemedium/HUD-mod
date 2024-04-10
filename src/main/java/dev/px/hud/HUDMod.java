package dev.px.hud;

import dev.px.hud.Initalizer.CommandInitalizer;
import dev.px.hud.Initalizer.ElementInitalizer;
import dev.px.hud.Manager.*;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Input.BindRegistry;
import dev.px.hud.Util.Config.ConfigManager;
import dev.px.hud.Util.Event.Bus.EventBus;
import dev.px.hud.Util.Event.Bus.EventManager;
import dev.px.hud.Util.EventProcessor;
import dev.px.hud.Util.Settings.ClientSettings.PreferenceManager;
import jline.internal.Log;
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

    /* TODO ORDER
     *
     *  - fix Grid system
     *  - Redo Notifications
     */
    public static final String NAME = "HUD Mod";
    public static final String MODID = "hudmod";
    public static final String VERSION = "2.1";

    // Initalizers
    public static ElementInitalizer elementInitalizer;
    public static EventProcessor eventProcessor;
    public static ConfigManager configManager;
    public static PreferenceManager preferenceManager;
    public static CommandInitalizer commandInitalizer;

    // Manager
    public static SoundManager soundInitalizer;
    public static ColorManager colorManager;
    public static FontManager fontManager;
    public static NotificationManager notificationManager;
    public static ServerManager serverManager;
    public static SocialManager socialManager;
    public static TimeManager timeManager;

    public static PanelGUIScreen screen;
    public static EventBus EVENT_BUS = new EventManager();
    public static org.apache.logging.log4j.Logger LOG = LogManager.getLogger("[" + NAME + "]"); //"[" + NAME + "]"
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private long startTime = -1;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        System.setProperty("devauth.enabled", "true");
        System.setProperty("devauth.configDir", "/Users/jameskemp/Devauth");
        System.setProperty("devauth.account", "main");
        Log.info(System.getProperty("devauth.enabled", "true"));
        Log.info(System.getProperty("devauth.configDir", "/Users/jameskemp/Devauth"));
        Log.info(System.getProperty("devauth.account", "main"));
        Log.info("Account stuff");

        startTime = System.currentTimeMillis();
        BindRegistry.register();
        fontManager = new FontManager();
        timeManager = new TimeManager();
        timeManager.setTotalElapsed(System.currentTimeMillis());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ProgressManager.ProgressBar progressManager = ProgressManager.push("HUD Mod", 9);

        colorManager = new ColorManager();
        progressManager.step("Loading Color Manager");
        eventProcessor = new EventProcessor();
        progressManager.step("Loading Event Processor");
        elementInitalizer = new ElementInitalizer();
        progressManager.step("Loading Element Initializer");
        preferenceManager = new PreferenceManager();
        commandInitalizer = new CommandInitalizer();
        progressManager.step("Loading Command Initializer");

        serverManager = new ServerManager();
        progressManager.step("Loading server Manager...");
        soundInitalizer = new SoundManager();
        progressManager.step("Queueing up the dua lipa...");
        notificationManager = new NotificationManager();
        progressManager.step("Loading Notification Manager");
        screen = new PanelGUIScreen();
        configManager = new ConfigManager();
        progressManager.step("Loading Configs");
        socialManager = new SocialManager();
        progressManager.step("Initializing Friend System");

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
