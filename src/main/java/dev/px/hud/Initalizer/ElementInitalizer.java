package dev.px.hud.Initalizer;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Elements.Combat.Armor;
import dev.px.hud.Rendering.HUD.Elements.Combat.TargetHUD;
import dev.px.hud.Rendering.HUD.Elements.Info.*;
import dev.px.hud.Rendering.HUD.Elements.TESTElement;
import dev.px.hud.Rendering.HUD.Mods.*;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class ElementInitalizer {

    private ArrayList<Element> elements = new ArrayList<>();
    private Minecraft mc = Minecraft.getMinecraft();

    public ElementInitalizer() {
         Add(new TESTElement());

        // Combat
        Add(new Armor());
        Add(new TargetHUD());

        // Info
        Add(new CoordinateElement());
        Add(new FPSElement());
        Add(new HitInfoElement());
        Add(new RotationElement());
        Add(new SneakInfoElement());
        Add(new SpeedElement());
        Add(new WelcomeElement());

        // Render

        // Mod
        Add(new AdditionalInfo());
        this.elements.add(new AutoSprint()); // WHY WONT THIS ADD
        Add(new CritParticles());
        Add(new ESPMod());
        Add(new FakePlayer());
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

    public boolean isElementToggled(Element element) {
        Element el =  this.elements.stream()
                .filter(e -> e == element)
                .filter(e -> e != null)
                .findFirst()
                .orElse(null);
        assert el != null;
        return el.isToggled();
    }

    public boolean isElementToggled(Class clazz) {
        Element el =  this.elements.stream()
                .filter(e -> e.getClass() == clazz)
                .filter(e -> e != null)
                .findFirst()
                .orElse(null);
        assert el != null;
        return el.isToggled();
    }

    public ArrayList<Element> toggledElements() {
        ArrayList<Element> e = new ArrayList<>();
        this.elements.forEach(element -> {
            if(element instanceof ToggleableElement) {
                if(((ToggleableElement) element).isEnabled()) {
                    e.add(element);
                }
            }
        });

        return e;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

}
