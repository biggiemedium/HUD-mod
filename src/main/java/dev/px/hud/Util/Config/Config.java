package dev.px.hud.Util.Config;

import dev.px.hud.Command.CommandManifest;
import dev.px.hud.HUDMod;

import java.io.File;
import java.io.IOException;

/**
 * @author PX
 *
 * if you are going to skid my config into your shitty clients
 * at least credit me, This is the config system for my old client Deteorite
 */

public class Config {

    private String name;

    private File mainFile;
    private File subFile;

    private File saveDoc;

    public Config(String path) {
        this.mainFile = new File(HUDMod.MODID);
        if(!mainFile.exists()) { mainFile.mkdirs(); }

        //String Sfile = txt ? path + ".txt" : path;

        this.subFile = new File(mainFile + File.separator + path + File.separator);
        if(!subFile.exists()) { subFile.mkdirs(); }
    }

    public Config(String path, String file) {
        this.mainFile = new File(HUDMod.MODID);
        if(!mainFile.exists()) { mainFile.mkdirs(); }

        //String Sfile = txt ? path + ".txt" : path;

        this.subFile = new File(mainFile + File.separator + path + File.separator);
        if(!subFile.exists()) { subFile.mkdirs(); }

        this.saveDoc = new File(subFile + File.separator + file + ".txt");
        if(!saveDoc.exists()) {
            try {
                saveDoc.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Config() {
        if (getClass().isAnnotationPresent(CommandManifest.class)) {
            CommandManifest moduleManifest = getClass().getAnnotation(CommandManifest.class);
            this.name = moduleManifest.name();
        }
    }

    public void loads() {

    }

    public void saves() {

    }

    public File getMainFile() {
        return this.mainFile;
    }

    public File getSubFile() {
        return this.subFile;
    }
}
