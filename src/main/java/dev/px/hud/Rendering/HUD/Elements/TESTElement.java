package dev.px.hud.Rendering.HUD.Elements;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.Panel.PanelGUIScreen;
import dev.px.hud.Util.API.Render.RoundedShader;
import dev.px.hud.Util.Event.Render.EventRenderScoreBoard;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class TESTElement extends RenderElement {

    public TESTElement() {
        super("Test Element", 20, 20, 135, 50, HUDType.INFO);
        setTextElement(true);
        this.setWidth(mc.fontRendererObj.getStringWidth("TEST Element"));
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT);
    }

    private int offset = 13;
    protected float zLevelFloat;

    private boolean playing = false;
    private ResourceLocation currentAlbumCover;
    public final float albumCoverSize = getHeight();
    private final float playerWidth = 135;
    private Renderutil.ScissorStack stack = new Renderutil.ScissorStack();

    public Setting<Boolean> background = create(new Setting<>("Background", true));
    public Setting<Mode> mode = create(new Setting<>("Mode", Mode.Round, v -> background.getValue()));
    public Setting<Boolean> num = create(new Setting<>("Num", true));

    private enum Mode {
        Round,
        Square
    }
    @Override
    public void render(float partialTicks) {
        String s = "";
    }

    @Override
    public void enable() {
    }

    @Override
    public void render2D(Render2DEvent event) {

    }

}
