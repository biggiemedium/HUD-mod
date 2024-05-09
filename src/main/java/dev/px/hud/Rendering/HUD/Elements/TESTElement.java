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

    protected void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
        Scoreboard scoreboard = p_180475_1_.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(p_180475_1_);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
        {
            public boolean apply(Score p_apply_1_)
            {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15)
        {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else
        {
            collection = list;
        }

        int i = mc.fontRendererObj.getStringWidth(p_180475_1_.getDisplayName());

        for (Score score : collection)
        {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, mc.fontRendererObj.getStringWidth(s));
        }

        int i1 = collection.size() * mc.fontRendererObj.FONT_HEIGHT;
        int j1 = p_180475_2_.getScaledHeight() / 2 + i1 / 3;
        int k1 = 3;
        int l1 = p_180475_2_.getScaledWidth() - i - k1;
        int j = 0;

        for (Score score1 : collection) {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            int k = j1 - j * mc.fontRendererObj.FONT_HEIGHT;
            int l = p_180475_2_.getScaledWidth() - k1 + 2;

            if(background.getValue()) {
                if(mode.getValue() == Mode.Round) {
                    RoundedShader.drawRound(l1 - 2, k, l, k+ mc.fontRendererObj.FONT_HEIGHT, 2, new Color(26, 26, 26, 100));
                } else {
                    Gui.drawRect(l1 - 2, k, l, k + mc.fontRendererObj.FONT_HEIGHT, 1342177280);
                }
            }

            mc.fontRendererObj.drawString(s1, l1, k, 553648127);

            if(num.getValue()) {
                mc.fontRendererObj.drawString(s2, l - mc.fontRendererObj.getStringWidth(s2), k, 553648127);
            }

            if (j == collection.size())
            {
                String s3 = p_180475_1_.getDisplayName();
                if(background.getValue()) {
                    if(mode.getValue() == Mode.Square) {
                        Gui.drawRect(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, 1610612736);
                        Gui.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                    } else {
                        RoundedShader.drawRound(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, 2, new Color(26, 26, 26, 80));
                        RoundedShader.drawRound(l1 - 2, k - 1, l, k, 2, new Color(26, 26, 26, 80));
                    }
                }
                mc.fontRendererObj.drawString(s3, l1 + i / 2 - mc.fontRendererObj.getStringWidth(s3) / 2, k - mc.fontRendererObj.FONT_HEIGHT, 553648127);
            }
        }
    }

    public static int shadowX = 0, shadowY = 0, shadowWidth = 0, shadowHeight = 0;

    @SubscribeEvent
    public void onRenderScoreBoard(EventRenderScoreBoard event) {
        event.setCanceled(true);
        renderScoreboard(event.getScoreboard(), new ScaledResolution(mc));

    }

}
