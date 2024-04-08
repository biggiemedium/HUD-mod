package dev.px.hud.Rendering.NewGUI.Components;

import dev.px.hud.Util.Settings.Setting;

public class BooleanComponent extends Component<Boolean> {

    private Setting<Boolean> booleanSetting;

    public BooleanComponent(int x, int y, int width, int height, Setting<Boolean> setting) {
        super(x, y, width, height, setting);
        this.booleanSetting = setting;
    }


}
