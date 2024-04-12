package dev.px.hud.Rendering.HUD.Elements;

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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
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

    private enum Mode {
        UP, SIDE
    }

    private int offset = 13;
    protected float zLevelFloat;

    private boolean playing = false;
    private ResourceLocation currentAlbumCover;
    public final float albumCoverSize = getHeight();
    private final float playerWidth = 135;
    private Renderutil.ScissorStack stack = new Renderutil.ScissorStack();

    public Setting<Boolean> background = create(new Setting<>("Background", true));
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

    public static int shadowX = 0, shadowY = 0, shadowWidth = 0, shadowHeight = 0;

    @SubscribeEvent
    public void onRenderScoreBoard(EventRenderScoreBoard event) {

        Scoreboard scoreboard = event.getScoreboard().getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(event.getScoreboard());
        List<Score> list = Lists.newArrayList(Iterables.filter(collection,
                p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")));

        event.setCanceled(true);

        if(list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else {
            collection = list;
        }

        int i = mc.fontRendererObj.getStringWidth(event.getScoreboard().getDisplayName());

        for(Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + ChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, mc.fontRendererObj.getStringWidth(s));
        }

        int i1 = collection.size() * mc.fontRendererObj.FONT_HEIGHT;
        int j1 = this.getY() + i1 / 3;
        int k1 = 3;
        int l1 = this.getX() - i - k1;
        int j = 0;

        for(Score score1 : collection) {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = "" + ChatFormatting.RED + score1.getScorePoints();
            int k = j1 - j * mc.fontRendererObj.FONT_HEIGHT;
            int l = this.getX() - k1 + 2;

            if(background.getValue()) {
               // Gui.drawRect(l1 - 2, k, l - (13), k + mc.fontRendererObj.FONT_HEIGHT, 1342177280);
                RoundedShader.drawRound(l1 - 2, k, l - (13), k + mc.fontRendererObj.FONT_HEIGHT, 4, new Color(1342177280));
            }
            mc.fontRendererObj.drawString(s1, l1, k, 553648127);


            if(j == collection.size()) {
                String s3 = event.getScoreboard().getDisplayName();

                if(background.getValue()) {
                   // Gui.drawRect(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l - (13), k - 1, 1610612736);
                   // Gui.drawRect(l1 - 2, k - 1, l - (13), k, 1342177280);
                    RoundedShader.drawRound(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l - (13), k - 1, 4, new Color(1610612736));
                    RoundedShader.drawRound(l1 - 2, k - 1, l - (13), k, 4, new Color(1342177280));
                }
                mc.fontRendererObj.drawString(s3, l1 + i / 2 - mc.fontRendererObj.getStringWidth(s3) / 2, k - mc.fontRendererObj.FONT_HEIGHT, 553648127);
            }
        }

        String fuckme;

        int top = ((j1 - j * mc.fontRendererObj.FONT_HEIGHT) - mc.fontRendererObj.FONT_HEIGHT) - 2;

        shadowX = l1 - 3;
        shadowY = top;
        shadowWidth = this.getX() - k1 + 2 - (13);
        shadowHeight = top + mc.fontRendererObj.FONT_HEIGHT + 3 + i1;

        this.setWidth(shadowWidth);
        this.setHeight(shadowHeight);
    }

}
