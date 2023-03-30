package dev.px.hud.Initalizer;

import dev.px.hud.HUD.Element;
import dev.px.hud.HUD.Elements.Combat.Armor;
import dev.px.hud.HUD.Elements.ExampleElement;
import dev.px.hud.HUD.Elements.TESTElement;
import dev.px.hud.HUDMod;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class ElementInitalizer {

    private ArrayList<Element> elements = new ArrayList<Element>();

    public ElementInitalizer() {
        Add(new ExampleElement());
        Add(new TESTElement());

        // Combat
        Add(new Armor());
    }

    private void Add(Element element) {
        this.elements.add(element);
    }

    public ArrayList<Element> getElements() {
        return this.elements;
    }

    public Element getElementByName(String name) {

        for(Element e : this.elements) {
            if(e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }

        return null;
    }

    public Element getElementByClass(Class clazz) {
        for(Element e : this.elements) {
            if(e.getClass() == clazz) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Element> getElementbyType(Element.Category category) {
        ArrayList<Element> elementss = new ArrayList<>();
        for(Element e : this.elements) {
            if(e.getCategory() == category) {
                elementss.add(e);
            }
        }
        return elementss;
    }

    public void onRender(float partialTicks) {
        HUDMod.elementInitalizer.getElements().forEach(element -> {
            element.render(partialTicks);
        });
    }

}
