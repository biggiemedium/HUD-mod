package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.HUDMod;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Entity.Playerutil;
import dev.px.hud.Util.API.Render.Colorutil;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, HUDType.INFO);
        setTextElement(true);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }

    private enum Mode {
        UP, SIDE
    }


    private final ArrayList<Float> speeds = new ArrayList<>();
    private double lastVertices;
    private float currentSpeed;



    @Override
    public void render(float partialTicks) {

    }

    @Override
    public void render2D(Render2DEvent event) {

    }

}
