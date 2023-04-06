package dev.px.hud.Rendering.Panel.Profiles;

import dev.px.hud.Util.Renderutil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ProfileRenderer {

    private Minecraft mc = Minecraft.getMinecraft();
    private int x, y, width, height;
    private int scrollbar = 0;

    public ProfileRenderer(int x, int y, int width, int height) {
        this.mc = mc;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(int mouseX, int mouseY) {
        Renderutil.drawRect(x - 2, y + 14, x + this.width + 2, y + 29 + this.height, new Color(36, 36, 36, 60).getRGB());

        Renderutil.drawRect(x + this.width - 9, y + 14 + 2, x + this.width - 1, y + 27 + this.height, new Color(36, 36, 36, 70).getRGB());
        Renderutil.drawRect(x + this.width - 9, (int) MathHelper.clamp_double(this.y + 16 + MathHelper.clamp_double(this.scrollbar, 0, 216), y + 14, y + 12 + this.height), x + this.width - 1, (int) MathHelper.clamp_double(this.y + 31 + MathHelper.clamp_double(this.scrollbar, 0, 216), y + 14, y + 12 + this.height), new Color(0xff181A17).getRGB());

        int playerOffset = 0;

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            int friendColor = 0xCC232323;

            prepareScissor(x, y + 14, x + this.width - 9, y + 29 + this.height, Minecraft.getMinecraft());
            GlStateManager.translate(0, (int) MathHelper.clamp_double(this.scrollbar, -10000, 0), 1);

            Renderutil.drawRect(x + 1, y + 14 + 1 + (16 * playerOffset), x + this.width - 5, y + 14 + 18 + (16 * playerOffset), new Color(18, 18, 18, 90).getRGB());
            mc.fontRendererObj.drawString(player.getName(), x + 23, y + 14 + 6 + (16 * playerOffset), new Color(85, 231, 215).getRGB());

            try {
                ResourceLocation resourceLocation = AbstractClientPlayer.getLocationSkin(player.getName());
                getDownloadImageSkin(resourceLocation, player.getName());
                mc.getTextureManager().bindTexture(resourceLocation);
                GlStateManager.enableTexture2D();
                GL11.glColor4f(1F, 1F, 1F, 1F);
                GuiScreen.drawScaledCustomSizeModalRect(x + 3, y + 14 + 2 + (16 * playerOffset), 8, 8, 8, 8, 16, 16, 64, 64);
            } catch (Exception ignored) {

            }

        //    Renderutil.drawRect(x + this.width - 41, y + 14 + 1 + (16 * playerOffset), x + this.width - 5, y + 14 + 18 + (16 * playerOffset), new Color(90, 163, 212).getRGB());
            NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
            String side = "" + (player.getHealth() + player.getAbsorptionAmount());
            if(info != null && !mc.isIntegratedServerRunning()) {
                side += " " + info.getResponseTime() + " ms";
            }

            mc.fontRendererObj.drawString(side, x + this.width - (mc.fontRendererObj.getStringWidth(side) + 15), y + 14 + 4 + (16 * playerOffset) + 1, -1);

            GlStateManager.translate(0, (int) -MathHelper.clamp_double(this.scrollbar, -10000, 0), 1);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopAttrib();

            playerOffset++;
        }
    }

    public void scroll(int mouseX, int mouseY) {
        int scrollWheel = Mouse.getDWheel();

        if (isHovered(mouseX, mouseY)) {
            if (scrollWheel < 0)
                this.scrollbar += 11;
            else if (scrollWheel > 0)
                this.scrollbar -= 11;
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }

    public static void prepareScissor(int x, double y, int width, double height, Minecraft mc) {
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glScissor(x * new ScaledResolution(mc).getScaleFactor(), (int) (new ScaledResolution(mc).getScaledHeight() - height) * new ScaledResolution(mc).getScaleFactor(), (width - x) * new ScaledResolution(mc).getScaleFactor(), (int) (height - y) * new ScaledResolution(mc).getScaleFactor());
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    public ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        mc.getTextureManager().getTexture(resourceLocationIn);
        ThreadDownloadImageData textureObject = new ThreadDownloadImageData(null, String.format("https://minotar.net/avatar/%s/64.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(AbstractClientPlayer.getOfflineUUID(username)), new ImageBufferDownload());
        mc.getTextureManager().loadTexture(resourceLocationIn, textureObject);
        return textureObject;
    }
}
