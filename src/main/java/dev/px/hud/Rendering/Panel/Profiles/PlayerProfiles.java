package dev.px.hud.Rendering.Panel.Profiles;

import dev.px.hud.Rendering.Panel.Panel;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

/*
Gets stats of nearby players
 */
public class PlayerProfiles extends Panel {

    public PlayerProfiles() {
        super("Profiles");
    }

    private ScaledResolution sc = new ScaledResolution(mc);
    private ProfileRenderer player = new ProfileRenderer(sc.getScaledWidth() / 2 - (200), 25, 200, 200);

    @Override
    public void draw(int mouseX, int mouseY) {
        player.render(mouseX, mouseY);
        player.scroll(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {

    }
}
