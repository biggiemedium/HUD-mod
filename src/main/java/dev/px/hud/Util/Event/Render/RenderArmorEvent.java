package dev.px.hud.Util.Event.Render;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderArmorEvent extends Event {

    public ModelBiped model;
    public int slot;

    public RenderArmorEvent(ModelBiped model, int slot) {
        this.model = model;
        this.slot = slot;
    }

    public ModelBiped getModel() {
        return model;
    }

    public void setModel(ModelBiped model) {
        this.model = model;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
