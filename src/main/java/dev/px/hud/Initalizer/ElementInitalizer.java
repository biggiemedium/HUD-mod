package dev.px.hud.Initalizer;

import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.Elements.Combat.*;
import dev.px.hud.Rendering.HUD.Elements.Info.*;
import dev.px.hud.Rendering.HUD.Elements.TESTElement;
import dev.px.hud.Rendering.HUD.Elements.TestToggleElement;
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
        Add(new CPS());
        Add(new HitInfoElement());
        Add(new InventoryElement());
        Add(new ItemStackElement());
        Add(new Radar());
        //Add(new TargetHUD());

        // Info
        Add(new CompassElement());
        Add(new CoordinateElement());
        Add(new FPSElement());
        Add(new PingElement());
        //Add(new PlaytimeElement());
        Add(new PotionsElement());
        Add(new RotationElement());
        Add(new ServerElement());
        Add(new SneakInfoElement());
        Add(new SpeedElement());
        Add(new TextRadarElement());
        Add(new TimeElement());
        Add(new WatermarkElement());
        Add(new WelcomeElement());

        // Render

        // Mod
     //   Add(new AdditionalInfo());
        Add(new TestToggleElement());
        Add(new AutoSprint());
        Add(new BlockHighlight());
        Add(new ChatModifications());
        Add(new ChunkAnimator());
        Add(new CritParticles());
        Add(new ESPMod());
        Add(new FakePlayer());
        Add(new FullBright());
        Add(new HotbarModifications());
        Add(new ItemPhysics());
        Add(new MiddleClickFriend());
        Add(new MotionBlur());
        Add(new NoHurtCam());
        Add(new NameTags());
        Add(new NoRender());
        Add(new OldAnimations());
        Add(new Tracers());
        Add(new Trajectories());
        Add(new UnfocusedCPU());
      //  Add(new ViewModel());
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

    public <T extends Element> T getElementByClass(Class<T> clazz) {
        for (Element module : elements) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
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
                if((element).isToggled()) {
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
