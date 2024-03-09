package dev.px.hud.Rendering.Panel.ClickGUI.Settings;

import dev.px.hud.Rendering.Panel.ClickGUI.Button;
import dev.px.hud.Util.API.Animation.Animation;
import dev.px.hud.Util.API.Animation.Easing;
import dev.px.hud.Util.API.Font.Fontutil;
import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/*
Taken from cosmos client
 */
public class ColorButton extends SettingButton<Color> {

    private Setting<Color> setting;
    private double featureHeight;
    private Animation animation = new Animation(300, false, Easing.LINEAR);
    private boolean leftHeld = false;
    private ColorHSB selectedColor;
    private boolean open = false;

    public ColorButton(Button button, double x, double y, Setting<Color> setting) {
        super(button, x, y, setting);
        this.setting = setting;
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        selectedColor = new ColorHSB(setting.getValue());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        this.featureHeight = getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getSettingOffset() + getButton().getParent().getScroll() + 2;
        Renderutil.drawRect((float) getX(), (float)  featureHeight, (float) getWidth(), (float)  getHeight(), new Color(0x181A18).darker());
        double expandHeight = 0 + 15; // 15 is getHeight();

        glScaled(0.55, 0.55, 0.55); {
            double scaledX = (getX() + 6) * 1.81818181F;
            double scaledY = (featureHeight + 5) * 1.81818181F;
            Fontutil.drawTextShadow(getSetting().getName(), (float) scaledX, (float) scaledY, -1);
        }
        glScaled(1.81818181, 1.81818181, 1.81818181);

        if(animation.getAnimationFactor() > 0) {
            expandHeight += 70 * animation.getAnimationFactor();
            Vector2f circleCenter = new Vector2f((float) getX() + ((float) (getWidth() - 34) / 2F) + 4, (float) (featureHeight + getHeight() + 34));
            if(leftHeld) {
                if (isWithinCircle(circleCenter.x, circleCenter.y, 32, mouseX, mouseY)) {
                    float xDistance = mouseX - circleCenter.x;
                    float yDistance = mouseY - circleCenter.y;
                    double radius = Math.hypot(xDistance, yDistance);
                    double angle = -Math.toDegrees(Math.atan2(yDistance, xDistance) + (Math.PI / 2)) % 360;
                    selectedColor.setHue(angle / 360F);
                    selectedColor.setSaturation(radius / 32F);
                }

                // Brightness
                if (isWithinRect(getX() + getWidth() - 24, featureHeight + getHeight() + 4, 62, mouseX, mouseY)) {
                    selectedColor.setBrightness(1 - ((mouseY - (featureHeight + getHeight() + 4)) / 64));
                }

                // trasparency
                if (isWithinRect(getX() + getWidth() - 10, featureHeight + getHeight() + 4, 62, mouseX, mouseY)) {
                    selectedColor.setTransparency(1 - ((mouseY - (featureHeight + getHeight() + 4)) / 64));
                }

            }

            int color = Color.HSBtoRGB((float) selectedColor.getHue(), (float) selectedColor.getSaturation(), (float) selectedColor.getBrightness());
            getSetting().setValue(new Color((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, MathHelper.clamp_float((float) selectedColor.getTransparency(), 0, 1)));

            // color picker
            mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "Textures/picker.png"));
            Gui.drawModalRectWithCustomSizedTexture((int) getX() + 4, (int) (featureHeight + getHeight() + 2), 0, 0, (int) getWidth() - 34, 64, (float) getWidth() - 34, 64);
            drawPolygon((float) (circleCenter.x + ((selectedColor.getSaturation() * 32) * Math.cos(Math.toRadians(selectedColor.getHue() * 360) + (Math.PI / 2)))), (float) (circleCenter.y - ((selectedColor.getSaturation() * 32) * Math.sin(Math.toRadians(selectedColor.getHue() * 360) + (Math.PI / 2)))), 1.5F, 360, Color.WHITE);

            // brightness slider
            drawGradientRoundedRect((float) (getX() + getWidth() - 24), (float) (featureHeight + getHeight() + 2), 3, 62, 2, false);
            drawPolygon(getX() + getWidth() - 22.5, featureHeight + getHeight() + (64 * (1 - selectedColor.getBrightness())) + 2, 2, 360, Color.WHITE);

            // transparency slider
            drawGradientRoundedRect((float) (getX() + getWidth() - 10), (float) (featureHeight + getHeight() + 2), 3, 62, 2, true);
            drawPolygon(getX() + getWidth() - 8.5, featureHeight + getHeight() + (64 * (1 - selectedColor.getTransparency())) + 2, 2, 360, Color.WHITE);
            
            getButton().addSettingOffset((float) (expandHeight));
            getButton().getParent().addFeatureOffset((expandHeight));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(isMouseOver(getX(), featureHeight, getWidth(), getHeight() + (70 * animation.getAnimationFactor()), mouseX, mouseY)) {
            if(button == 0) {
                this.leftHeld = true;
            } else if(button == 1) {
                double highestPoint = featureHeight;
                double lowestPoint = highestPoint + getHeight() + (70 * animation.getAnimationFactor());

                // check if it's able to be interacted with
                if (highestPoint >= getButton().getParent().getY() + getButton().getParent().getHeight() + 2 && lowestPoint <= getButton().getParent().getY() + getButton().getParent().getHeight() + getButton().getParent().getFullheight() + 2) {
                    open = !open;
                    animation.setState(open);
                }

            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.leftHeld = false;
    }

    public boolean isWithinCircle(double x, double y, double radius, int mouseX, int mouseY) {
        return Math.sqrt(StrictMath.pow(mouseX - x, 2) + StrictMath.pow(mouseY - y, 2)) <= radius;
    }

    public boolean isWithinRect(float x, float y, float height, int mouseX, int mouseY) {
        return mouseX > (x - 2) && mouseY > y && mouseX < (x + 6) && mouseY < (y + height);
    }

    public boolean isWithinRect(double x, double y, double height, int mouseX, int mouseY) {
        return mouseX > (x - 2) && mouseY > y && mouseX < (x + 6) && mouseY < (y + height);
    }

    boolean isMouseOver(double x, double y, double width, double height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= (x + width) && mouseY <= (y + height);
    }

    public static void drawPolygon(double x, double y, float radius, int sides, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferBuilder = tessellator.getWorldRenderer();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
        bufferBuilder.begin(GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(x, y, 0).endVertex();
        double TWICE_PI = Math.PI * 2;

        for (int i = 0; i <= sides; i++) {
            double angle = (TWICE_PI * i / sides) + Math.toRadians(180);
            bufferBuilder.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }

        tessellator.draw();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public void drawGradientRoundedRect(float x, float y, float width, float height, int radius, boolean transparency) {
        glPushAttrib(GL_POINTS);

        glScaled(0.5, 0.5, 0.5); {
            x *= 2;
            y *= 2;
            width *= 2;
            height *= 2;

            width += x;
            height += y;

            glEnable(GL_BLEND);
            glDisable(GL_TEXTURE_2D);
            glEnable(GL_LINE_SMOOTH);
            glBegin(GL_POLYGON);

            int i;

            // convert to rgb
            int color = Color.HSBtoRGB((float) selectedColor.getHue(), (float) selectedColor.getSaturation(), 1);
            float red = (color >> 16 & 255) / 255F;
            float green = (color >> 8 & 255) / 255F;
            float blue = (color & 255) / 255F;

            glColor4f(red, green, blue, 1);
            for (i = 0; i <= 90; i++) {
                glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
            }

            if (transparency) {
                glColor4f(1, 1, 1, 1);
            } else {
                glColor4f(0, 0, 0, 1);
            }

            for (i = 90; i <= 180; i++) {
                glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
            }

            if (transparency) {
                glColor4f(1, 1, 1, 1);
            }

            else {
                glColor4f(0, 0, 0, 1);
            }

            for (i = 0; i <= 90; i++) {
                glVertex2d(width - radius + Math.sin(i * Math.PI / 180.0D) * radius, height - radius + Math.cos(i * Math.PI / 180.0D) * radius);
            }

            glColor4f(red, green, blue, 1);
            for (i = 90; i <= 180; i++) {
                glVertex2d(width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
            }

            glEnd();
            glEnable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            glDisable(GL_LINE_SMOOTH);
            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
        }

        glScaled(2, 2, 2);
        glPopAttrib();
    }

    /**
     * @author linusTouchTips
     */
    public static class ColorHSB {

        // hsb values
        private double hue, saturation, brightness, transparency;

        public ColorHSB(double hue, double saturation, double brightness, double transparency) {
            this.hue = hue;
            this.saturation = saturation;
            this.brightness = brightness;
            this.transparency = transparency;
        }

        public ColorHSB(Color color) {
            float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            // this() constructor has to be first call
            hue = hsb[0];
            saturation = hsb[1];
            brightness = hsb[2];
            transparency = color.getAlpha() / 255F;
        }

        /**
         * Gets the hue
         * @return The hue
         */
        public double getHue() {
            return hue;
        }

        /**
         * Sets the hue
         * @param in The new hue
         */
        public void setHue(double in) {
            hue = in;
        }

        /**
         * Gets the saturation
         * @return The saturation
         */
        public double getSaturation() {
            return saturation;
        }

        /**
         * Sets the saturation
         * @param in The new saturation
         */
        public void setSaturation(double in) {
            saturation = in;
        }

        /**
         * Gets the brightness
         * @return The brightness
         */
        public double getBrightness() {
            return brightness;
        }

        /**
         * Sets the brightness
         * @param in The new brightness
         */
        public void setBrightness(double in) {
            brightness = in;
        }

        /**
         * Gets the transparency
         * @return The transparency
         */
        public double getTransparency() {
            return transparency;
        }

        /**
         * Sets the transparency
         * @param in The new transparency
         */
        public void setTransparency(double in) {
            transparency = in;
        }
    }
}
