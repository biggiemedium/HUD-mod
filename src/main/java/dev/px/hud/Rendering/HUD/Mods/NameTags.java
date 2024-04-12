package dev.px.hud.Rendering.HUD.Mods;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.px.hud.HUDMod;
import dev.px.hud.Manager.SocialManager;
import dev.px.hud.Mixin.Render.IMixinRenderManager;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Util.API.Entity.Entityutil;
import dev.px.hud.Util.API.Math.Mathutil;
import dev.px.hud.Util.Event.Render.Render3dEvent;
import dev.px.hud.Util.Renderutil;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.*;

public class NameTags extends ToggleableElement {

    public NameTags() {
        super("Name tags", "", HUDType.RENDER);
    }

    Setting<Integer> distance = create(new Setting<>("Distance", 35, 1, 75));
    Setting<Float> scale = create(new Setting<>("Scale", 0.3f, 0.1f, 8f));
    Setting<Boolean> outline = create(new Setting<>("Outline", true));

    Setting<Boolean> armor = create(new Setting<>("Armor", true));
    Setting<Boolean> durability = create(new Setting<>("Durability", false, v-> armor.getValue()));
    Setting<Boolean> enchantment = create(new Setting<>("Enchantment", true, v-> armor.getValue()));
    Setting<Boolean> health = create(new Setting<>("Health", true));
    Setting<Boolean> ping = create(new Setting<>("Ping", true));
    Setting<Boolean> item = create(new Setting<>("Item Held", true));

    @Override
    public void enable() {

    }


    @Override
    public void onRender(Render3dEvent event) {

        if(mc.getRenderManager() == null || mc.getRenderManager().options == null) return;
        for(EntityPlayer e : mc.theWorld.playerEntities) {
            if(e == null) { continue; }
            if(!e.isEntityAlive()) { continue; }
            if(HUDMod.preferenceManager.NCPCluster.getValue() && Entityutil.isHypixelNPC(e)
                    || Entityutil.isPlayerFake(e)) { continue; }

            if(mc.thePlayer.getDistance(e.posX, e.posY, e.posZ) > distance.getValue()) { continue; }
            double pX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosX();
            double pY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosY();
            double pZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosZ();
            Vec3 pos = new Vec3(pX, pY, pZ);

            renderNameTag(e, pX, pY, pZ, event.getPartialTicks());
        }

    }

    /*
    @SubscribeEvent
    public void onNameTagRender(RenderLivingEvent.Specials.Pre<EntityPlayer> event) {
        if(event.entity instanceof EntityPlayer) {
            if (event.entity instanceof EntityLivingBase) {
                event.setCanceled(true);
            }
        }
    }

     */

    private void renderNameTag(EntityPlayer target, double x, double y, double z, float ticks) {
        Entity camera = mc.getRenderViewEntity();
        assert (camera != null);
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = Mathutil.interpolate(camera.prevPosX, camera.posX, ticks);
        camera.posY = Mathutil.interpolate(camera.prevPosY, camera.posY, ticks);
        camera.posZ = Mathutil.interpolate(camera.prevPosZ, camera.posZ, ticks);

        double tempY = y;
        tempY += target.isSneaking() ? 0.5 : 0.7;
        double distance = camera.getDistance(x + mc.getRenderManager().viewerPosX, y + mc.getRenderManager().viewerPosY, z + mc.getRenderManager().viewerPosZ);
        double scale = (0.0018 + (double) this.scale.getValue() * (distance * (double) 0.4f)) / 1000.0;
        if (distance > 0.0) {
            scale = 0.02 + (this.scale.getValue() / 100f) * distance;
        }


        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) tempY + 1.4f, (float) z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        String nameTag = getDisplayTag(target);

        float width = mc.fontRendererObj.getStringWidth(nameTag) / 2;
        float height = mc.fontRendererObj.FONT_HEIGHT;

        GlStateManager.enableBlend();
        if(outline.getValue()) {
            Renderutil.drawRect(-width, -(height), width, 2, 0x5F0A0A0A);
        }
        GlStateManager.disableBlend();
        Color c = (HUDMod.preferenceManager.FRIENDS.getValue() && HUDMod.socialManager.getState(target.getName()) == SocialManager.SocialState.FRIEND ? new Color(18, 150, 238) : new Color(255, 255, 255));
        mc.fontRendererObj.drawStringWithShadow(nameTag, -width+1, -height+3, c.getRGB());

        GlStateManager.pushMatrix();

        final Iterator<ItemStack> items = Arrays.stream(target.inventory.armorInventory).iterator();
        final ArrayList<ItemStack> stacks = new ArrayList<>();

        //stacks.add(player.getHeldItemOffhand());

        while (items.hasNext())
        {
            final ItemStack stack = items.next();
            if(stack != null) {
                if (stack.stackSize > 0 && armor.getValue()) {
                    stacks.add(stack);
                }
            }
        }

        if(target.getHeldItem() != null) {
            if(item.getValue()) {
                stacks.add(target.getHeldItem());
            }
        }

        Collections.reverse(stacks);

        int x2 = (int) -width;
        int y2 = -32;
        int z2 = 0;

        for (ItemStack stack : stacks)
        {
            RenderItemStack(stack, x2, y2, z2);
            if(enchantment.getValue()) {
                RenderItemEnchantments(stack, x2, -62);
            }
            x2 += 16;
        }

        GlStateManager.popMatrix();

        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();

        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
    }

    // Ionar2 Code
    private int getColorByHealth(float maxHealth, float health) {
        Color green = new Color(72, 255, 94);
        Color yellow = new Color(255, 250, 57);
        Color red = new Color(255, 35, 40);

        float middleHealth = maxHealth / 2;

        if (health <= middleHealth)
        {
            return blend(yellow, red, (health / middleHealth)).getRGB();
        } else if (health <= (middleHealth * 2))
        {
            return blend(green, yellow, ((health - middleHealth) / middleHealth)).getRGB();
        }
        return green.getRGB();
    }

    public static Color blend(Color color1, Color color2, float ratio) {
        if (ratio < 0)
            return color2;
        if (ratio > 1)
            return color1;
        float ratio2 = (float) 1.0 - ratio;
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color((rgb1[0] * ratio) + (rgb2[0] * ratio2), (rgb1[1] * ratio) + (rgb2[1] * ratio2), (rgb1[2] * ratio) + (rgb2[2] * ratio2));
    }

    private String getDisplayTag(EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (name.contains(mc.getSession().getUsername())) {
            name = "You";
        }
        if (!this.health.getValue()) {
            return name;
        }
        float health = Entityutil.getHealth(player);
        String color = health > 18.0f ? "\u00a7a" : (health > 16.0f ? "\u00a72" : (health > 12.0f ? "\u00a7e" : (health > 8.0f ? "\u00a76" : (health > 5.0f ? "\u00a7c" : "\u00a74"))));
        String pingStr = "";

        if (this.ping.getValue()) {
            try {
                int responseTime = Objects.requireNonNull(mc.getNetHandler()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = pingStr + responseTime + "ms ";
            } catch (Exception responseTime) {}
        }

        name = Math.floor(health) == (double) health ? name + color + " " + (health > 0.0f ? Integer.valueOf((int) Math.floor(health)) : "dead") : name + color + " " + (health > 0.0f ? Integer.valueOf((int) health) : "dead");
        return pingStr + name;
    }

    private void RenderItemStack(final ItemStack itemStack, int n, final int n2, final int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int n4 = (n3 > 4) ? ((n3 - 4) * 8 / 2) : 0;
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, n, n2 + n4);
        mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        final float n5 = 0.5f;
        final float n6 = 0.5f;
        GlStateManager.scale(n6, n5, n6);
        GlStateManager.disableDepth();
        if (itemStack.getMaxDamage() > 1 && durability.getValue()) {
            this.RenderItemDamage(itemStack, n * 2, n2 - 100);
        }
        GlStateManager.enableDepth();
        final float n7 = 2.0f;
        final int n8 = 2;
        GlStateManager.scale((float) n8, n7, (float) n8);
        GlStateManager.popMatrix();
    }
    private void RenderItemDamage(final ItemStack itemStack, final int n, int n2)
    {
        final float n3 = ((float) (itemStack.getMaxDamage() - itemStack.getItemDamage())
                / (float) itemStack.getMaxDamage()) * 100.0f;

        int color = 0x1FFF00;

        if (n3 > 30 && n3 < 70)
            color = 0xFFFF00;
        else if (n3 <= 30)
            color = 0xFF0000;

        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();

        mc.fontRendererObj.drawStringWithShadow(new StringBuilder().insert(0, String.valueOf((int) (n3))).append('%').toString(), (float) (n * 2), (float) n2, color);

        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }

    private void RenderItemEnchantments(final ItemStack itemStack, int n, int n2)
    {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        final int n3 = -1;
        final Iterator<Integer> iterator2;
        Iterator<Integer> iterator = iterator2 = EnchantmentHelper.getEnchantments(itemStack).keySet().iterator();
        while (iterator.hasNext())
        {
            final Integer enchantment;
            if ((enchantment = iterator2.next()) == null)
            {
                iterator = iterator2;
            } else
            {

                mc.fontRendererObj.drawStringWithShadow(
                        GetEnchantName(Enchantment.getEnchantmentById(enchantment), EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack)),
                        (float) (n * 2), (float) n2, n3);

                n2 += 8;
                iterator = iterator2;
            }
        }
        if (itemStack.getItem().equals(Items.golden_apple) && itemStack.hasEffect())
        {
            mc.fontRendererObj.drawStringWithShadow(ChatFormatting.DARK_RED + "God", (float) (n * 2), (float) n2, -1);
        }
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    private String GetEnchantName(Enchantment enchantment, int n) {
        String substring = enchantment.getTranslatedName(n);
        final int n2 = (n > 1) ? 2 : 3;
        if (substring.length() > n2)
        {
            substring = substring.substring(0, n2);
        }
        final StringBuilder sb = new StringBuilder();
        final String s = substring;
        final int n3 = 0;
        String s2 = sb.insert(n3, s.substring(n3, 1).toUpperCase()).append(substring.substring(1)).toString();
        if (n > 1)
        {
            s2 = new StringBuilder().insert(0, s2).append(n).toString();
        }
        return s2;
    }

}
