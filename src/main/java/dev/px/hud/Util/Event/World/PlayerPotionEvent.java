package dev.px.hud.Util.Event.World;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PlayerPotionEvent extends Event {

    private Potion potion;

    public PlayerPotionEvent(Potion potion) {
        this.potion = potion;
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }
}
