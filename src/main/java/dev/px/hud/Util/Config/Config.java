package dev.px.hud.Util.Config;

import dev.px.hud.Command.CommandManifest;
import dev.px.hud.HUDMod;
import dev.px.hud.Util.API.Util;

import java.io.File;
import java.io.IOException;

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
                Util.sendClientSideMessage("File dont be existing", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Util.sendClientSideMessage("File exists", true);
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

    public File getSaveDoc() {
        return saveDoc;
    }
}
