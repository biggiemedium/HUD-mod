package dev.px.hud.Initalizer;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Elements.Combat.Armor;
import dev.px.hud.Rendering.HUD.Elements.TESTElement;
import dev.px.hud.HUDMod;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class ElementInitalizer {

    private ArrayList<Element> elements = new ArrayList<>();
    private Minecraft mc = Minecraft.getMinecraft();

    public ElementInitalizer() {
         Add(new TESTElement());

        // Combat
        Add(new Armor());

        // Info

        // Render
    }

    private void Add(Element element) {
        this.elements.add(element);
    }

    public ArrayList<Element> getElementByType(Element.HUDType type) {
        ArrayList<Element> list = new ArrayList<>();
        for(Element e : this.elements) {
            if(e.getHudType() == type) {
                list.add(e);
            }
        }

        return list;
    }

    public Element getElementByName(String name) {
        for(Element e : elements) {
            if(e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

}
