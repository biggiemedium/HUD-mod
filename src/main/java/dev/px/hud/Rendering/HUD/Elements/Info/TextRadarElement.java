package dev.px.hud.Rendering.HUD.Elements.Info;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.Event.Render.Render2DEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextRadarElement extends RenderElement {

    public TextRadarElement() {
        super("Text Radar", 75, 75, HUDType.INFO);
        setTextElement(true);
    }

    Setting<Integer> size = create(new Setting<>("Size", 5, 3, 20));
    Setting<Boolean> self = create(new Setting<>("Self", false));
    List<EntityPlayer> players = new ArrayList<>();

    @Override
    public void render2D(Render2DEvent event) {
        players.clear();
        for(EntityPlayer player : mc.theWorld.playerEntities) {
            if(player == null) {
                continue;
            }
            if(!(player instanceof EntityLivingBase)) {
                continue;
            }
            if(player.isDead) {
                continue;
            }
            if(!self.getValue() && player == mc.thePlayer) {
                continue;
            }

            players.add(player);
        }
        players = players.stream().limit(size.getValue()).collect(Collectors.toCollection(ArrayList::new));
        renderText("Nearby:", getX(), getY(), fontColor.getValue().getRGB());
        int off = getFontHeight();
        for(EntityPlayer p : players) {
            off += getFontHeight();
            renderText(p.getName() + " " + health(p), getX(), getY() + off, -1);
        }

        setHeight(getFontHeight() + off);
        setWidth((25));
    }

    private String health(EntityPlayer player) {
        double health = Entityutil.getHealth(player);
        if(health > 20) {
            return "" + ChatFormatting.YELLOW + health + ChatFormatting.RESET;
        }

        return ""+ health;
    }
}
