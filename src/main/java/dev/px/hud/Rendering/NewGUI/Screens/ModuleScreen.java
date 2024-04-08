package dev.px.hud.Rendering.NewGUI.Screens;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Math.Dimension;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.API.Shader.Shaders.GradientShader;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleScreen extends Screen {

    public int x, y, width, height;
    private boolean searchBarActive;
    private Dimension<Integer> moduleScissor;
    private double scrollY = 0;

    // Holyfuck
    private Element.HUDType currentType;
    private ArrayList<CategoryButton> types;
    private Map<ModuleButton, Element.HUDType> hudTypeMap;

    public ModuleScreen(int x, int y, int width, int height) {
        super("Mods");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.searchBarActive = false;
        this.hudTypeMap = new ConcurrentHashMap<>();
        this.currentType = Element.HUDType.COMBAT;
        this.moduleScissor = new Dimension<>(x, y + 9 + 17, width, height - (9 + 17));
        this.types = new ArrayList<>();
        this.setResourceLocation(new ResourceLocation("minecraft", "GUI/movement.png"));

        int offsetY = 0;
        for(Element e : HUDMod.elementInitalizer.getElements()) {
            ModuleButton b = new ModuleButton(x + 5, (int) (y + 35 + offsetY), 120, 20, e);
            hudTypeMap.put(b, e.getHudType());
            offsetY += b.getHeight() + 2; // 2 offset
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        this.moduleScissor.update(x, y + 9 + 17, width, height - (9 + 17));

        int xy = 5;
        int i = 0;
        for(Element.HUDType t : Element.HUDType.values()) {
            CategoryButton cb = new CategoryButton(x + 5 + xy, y + 14, (int) Fontutil.getWidth(t.getName()), (int) Fontutil.getHeight() - 3, t);
            cb.render(mouseX, mouseY, partialTicks);
            types.add(cb);


            if(i == 1) {
                xy += 45;
            }
            xy += Fontutil.getWidth(t.getName()) + 35;
            cb.increment(xy);
            i++;
        }

        //Renderutil.drawBlurredShadow(x + 5, y + 15, Fontutil.getWidth(currentType.getName()), 9, 10, new Color(255, 255, 255, 100));
        //Fontutil.drawText(currentType.getName(), x + 5, y + 5, -1);

        // Buttons
        GL11.glPushMatrix();
        int offsetY = 0;
        for(ModuleButton b : hudTypeMap.keySet()) {
            if(b.element.getHudType() == currentType) {
                b.setY(this.y + 35 + offsetY);
                b.setX(x + 5);
                b.render(mouseX, mouseY, partialTicks);
                offsetY += b.getHeight() + 2 + (b.featureHeight * (int) b.openAnimation.getAnimationFactor());
            }
        }

        GL11.glPopMatrix();

        // Hover Handler
        if(isHovered(x, y + 9, width, height - 9, mouseX, mouseY)) {
            scrollY += Mouse.getDWheel() * 0.03;
        }

    }

    public float animate(float value, float target) {
        return value + (target - value) / 8f;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int button) throws IOException {
        super.onClick(mouseX, mouseY, button);

        for(ModuleButton b : this.hudTypeMap.keySet()) {
            if(b.element.getHudType() == currentType) {
                if (isHovered(b.getX(), b.getY(), b.getWidth(), b.getHeight(), mouseX, mouseY)) {
                    b.onClick(mouseX, mouseY, button);
                }
            }
        }

        for(CategoryButton b : types) {
            if(isHovered(b.getX(), b.getY(), b.getWidth(), b.getWidth(), mouseX, mouseY)) {
                if(button == 0) {
                    currentType = b.type;
                    Util.sendClientSideMessage("Type " + currentType.getName());
                }
            }
        }

    }

    @Override
    public void onRelease(int mouseX, int mouseY, int button) {
    }

    public boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private class CategoryButton {

        public int x, y, width, height;
        private Element.HUDType type;

        public CategoryButton(int x, int y, int width, int height, Element.HUDType type) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.type = type;
        }

        public void render(int mouseX, int mousseY, float partialTicks) {
            Renderutil.drawBlurredShadow(x, y + 3, Fontutil.getWidth(type.getName()), (float) Fontutil.getHeight(), 14, new Color(255, 255, 255, 75));
            Fontutil.drawTextShadow(type.getName(), x, y + 3, -1);
        }

        public void increment(int xy) {
            this.x += xy;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Element.HUDType getType() {
            return type;
        }

        public void setType(Element.HUDType type) {
            this.type = type;
        }
    }

    private class ModuleButton {

        public int x, y, width, height;
        private Animation openAnimation = new Animation(300, false, Easing.LINEAR);
        private Animation toggleAnimation;
        private Element element;
        private float featureHeight = 0;
        private Map<Dimension<Integer>, Setting<?>> settingMap;
        private List<Component> settingList;

        public ModuleButton(int x, int y, int width, int height, Element element) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.element = element;
            this.toggleAnimation = new Animation(300, element.isToggled(), Easing.LINEAR);
            this.settingMap = new ConcurrentHashMap<>();
            this.settingList = new ArrayList<>();
        }

        public void render(int mouseX, int mouseY, float partialTicks) {
            toggleAnimation.setState(element.isToggled());

            RoundedShader.drawRoundOutline(x, y, width, height + (featureHeight * (float) openAnimation.getAnimationFactor()), 2, 0.1f, new Color(38, 38, 38),
                    new Color(HUDMod.colorManager.getAlternativeColor().getRed(),
                            HUDMod.colorManager.getAlternativeColor().getGreen(),
                            HUDMod.colorManager.getAlternativeColor().getBlue(), 100));
            if(toggleAnimation.getAnimationFactor() > 0) {
                RoundedShader.drawGradientCornerRL(x + 2, y + 2, (width * (float) toggleAnimation.getAnimationFactor()) - 2, (height + (featureHeight * (float) openAnimation.getAnimationFactor())) - 2, 2,
                        new Color(HUDMod.colorManager.getAlternativeColor().getRed(),
                                HUDMod.colorManager.getAlternativeColor().getGreen(),
                                HUDMod.colorManager.getAlternativeColor().getBlue(), 100).darker().darker(), new Color(38, 38, 38));
            }
            Fontutil.drawText(element.getName(), (x + 5), y + (int) height / 2, -1);

            Renderutil.ScissorStack stack = new Renderutil.ScissorStack();
            stack.pushScissor(x, y, width, height - 1 + (int) (featureHeight * openAnimation.getAnimationFactor()));
            this.featureHeight = 0;
            for(Setting<?> s : this.element.getSettings()) {
                if(s != null) {
                    if(s.getValue() instanceof Boolean) {
                        Dimension<Integer> xyz = new Dimension<>(x + 2, y + height + (int) featureHeight, width, 15);
                        Fontutil.drawTextShadow(s.getName(), xyz.getX(), xyz.getY(), Color.WHITE.getRGB());
                        if(isHovered(xyz.getX(), xyz.getY(), xyz.getWidth(), xyz.getHeight(), mouseX, mouseY)) {
                            RoundedShader.drawRound(xyz.getX(), xyz.getY(), xyz.getWidth(), xyz.getHeight(), 4, new Color(205, 24, 225, 50));
                        }
                        this.settingMap.put(xyz, s);
                        featureHeight += 16;
                    }
                }
            }
            stack.popScissor();
        }

        public void onClick(int mouseX, int mouseY, int button) {

            this.settingMap.forEach((k, v) -> {
                if(isHovered(k.getX(), k.getY(), k.getWidth(), k.getHeight(), mouseX, mouseY)) {
                    if(button == 0) {
                        if(this.openAnimation.getState()) {
                            Util.sendClientSideMessage("Setting " + v.getName());
                        }
                    }
                }
            });

            switch (button) {
                case 0:
                    this.element.toggle();
                    break;
                case 1:
                    openAnimation.setState(!openAnimation.getState());
                    break;
            }
        }

        public boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void scrollY(int in) {
            this.y += in;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
