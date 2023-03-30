package dev.px.hud.Util.Config;

import dev.px.hud.HUDMod;

import java.io.File;

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

    public Config(String path) {
        this.mainFile = new File(HUDMod.MODID);
        if(!mainFile.exists()) { mainFile.mkdirs(); }

        //String Sfile = txt ? path + ".txt" : path;

        this.subFile = new File(mainFile + File.separator + path);
        if(!subFile.exists()) { subFile.mkdirs(); }
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
