package dev.px.hud.Rendering.HUD.Elements.Combat;

import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Util.Settings.Setting;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class CPS extends RenderElement {

    public CPS() {
        super("CPS", 5, 15, HUDType.COMBAT);
        setTextElement(true);
    }

    private boolean pressed;
    private long last;
    private List<Long> clickList = new ArrayList<>();
 //  private Setting<Boolean> rClick = create(new Setting<>("RClick", false);

    @Override
    public void render(float partialTicks) {
        if(Mouse.isButtonDown(0) != pressed) {
            this.last = System.currentTimeMillis();
            pressed = Mouse.isButtonDown(0);
            if(pressed) {
                this.clickList.add(this.last);
            }
        }

        setWidth(getFontWidth("CPS: " + CPS()));
        setHeight(getFontHeight());
        renderText("CPS: " + CPS(), getX(), getY(), fontColor.getValue().getRGB());
    }


    private int CPS() {
        long time = System.currentTimeMillis();
        this.clickList.removeIf(x -> x + 1000 < time);
        return clickList.size();
    }

}
