package dev.px.hud.Util.Event;

import dev.px.hud.Rendering.HUD.Element;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ElementToggleEvent extends Event {

    private Element element;

    public ElementToggleEvent(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
