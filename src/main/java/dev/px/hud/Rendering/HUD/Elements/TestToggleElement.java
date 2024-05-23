package dev.px.hud.Rendering.HUD.Elements;

import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.ThanosSnap;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Settings.Setting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TestToggleElement extends ToggleableElement {

    public TestToggleElement() {
        super("Test Toggle", HUDType.MOD);
    }

    Setting<Integer> distance = create(new Setting<>("Distance", 50, 0, 150));
    Setting<Double> speed = create(new Setting<>("Speed", 1D, 0D, 10D));
    Setting<SnapMode> snapMode = create(new Setting<>("Mode", SnapMode.SkinChange));
    Setting<Boolean> blending = create(new Setting<>("Blending", true));
    public ThanosSnap snap;

    @Override
    public void enable() {
        snap = new ThanosSnap();
        snap.generate();

    }

    @Override
    public void disable() {
        snap = null;
    }

    @Override
    public void onUpdate() {
        if(snap != null) {
            snap.onTick(snapMode.getValue().getMode(), speed.getValue(), blending.getValue());
        }
    }

    @Override
    public void onRender(Render3dEvent event) {
        if(snap != null) {
            snap.renderWorld(event, distance.getValue());
        }
    }

    @SubscribeEvent
    public void onWorldSwitch(WorldEvent.Unload event) {
        if(snap != null) {
            snap.switchWorld(event);
        }
    }

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Pre event) {
        if(snap != null) {
            snap.renderPlayer(event);
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if(snap != null) {
            snap.renderTick();
        }
    }

    private enum SnapMode {
        SkinChange(0),
        Fade(1),
        DarkFade(2),
        Mode3(3);

        int mode;
        SnapMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return this.mode;
        }
    }
}
