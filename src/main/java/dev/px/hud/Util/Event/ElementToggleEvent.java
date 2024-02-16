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

    public static class ElementEnableEvent extends ElementToggleEvent {

        public ElementEnableEvent(Element element) {
            super(element);
        }
    }

    public static class ElementDisableEvent extends ElementToggleEvent {

        public ElementDisableEvent(Element element) {
            super(element);
        }
    }
}
