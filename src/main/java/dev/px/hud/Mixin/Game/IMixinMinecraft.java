package dev.px.hud.Mixin.Game;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMixinMinecraft {

    @Accessor("timer")
    Timer timer();

    @Accessor("fpsCounter")
    int currentFPS();

    @Accessor("session")
    public void setSession(Session session);
}
