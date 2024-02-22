package dev.px.hud.Util.Settings;

import dev.px.hud.Util.API.Util;
import dev.px.hud.Util.Event.Client.SettingUpdateEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Setting<T> {

    private String name;

    private T value;
    private T min;
    private T max;

    private Predicate<T> visibility;

    // Taken from cosmos client
    private List<T> exclusions = new ArrayList<>();

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Setting(String name, T value, T min, T max) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public Setting(String name, T value, Predicate<T> visibility) {
        this.name = name;
        this.value = value;
        this.visibility = visibility;
    }

    public Setting(String name, T value, T min, T max, Predicate<T> visibility) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if(!Util.isNull()) {
            SettingUpdateEvent setting = new SettingUpdateEvent(this);
            MinecraftForge.EVENT_BUS.post(setting);
        }
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public boolean isNumberSetting() {
        return this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float;
    }

    public boolean isEnumSetting() {
        return !this.isNumberSetting() && !(this.value instanceof String) && !(this.value instanceof Character) && !(this.value instanceof Boolean);
    }

    public boolean isVisible() {
        if (this.visibility == null) {
            return true;
        }
        return this.visibility.test(this.getValue());
    }

    public boolean isExclusion(T in) {
        return exclusions.contains(in);
    }

    @SafeVarargs
    public final Setting<T> setExclusion(T... in) {
        exclusions.addAll(Arrays.asList(in));
        return this;
    }
}
