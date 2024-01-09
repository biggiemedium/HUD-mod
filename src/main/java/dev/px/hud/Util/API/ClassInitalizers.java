package dev.px.hud.Util.API;

import dev.px.hud.Util.EventProcessor;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class ClassInitalizers {

    private ArrayList<Class<?>> clazz;

    public ClassInitalizers() {
        this.clazz = new ArrayList<Class<?>>();

        this.clazz.add(EventProcessor.class);

        for(Class<?> c : clazz) {
            MinecraftForge.EVENT_BUS.register(c);
        }
    }
}
