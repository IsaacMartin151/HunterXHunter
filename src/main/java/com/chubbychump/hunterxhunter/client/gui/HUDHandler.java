package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public final class HUDHandler {
    private HUDHandler() {}

    public static final ResourceLocation manaBar = new ResourceLocation(HunterXHunter.MOD_ID, "textures/gui/mana_hud.png");

    public static void renderManaBar(MatrixStack ms, int x, int y, int color, float alpha, int mana, int maxMana) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.color4f(1F, 1F, 1F, alpha);
        mc.textureManager.bindTexture(manaBar);
        drawTexturedModalRect(ms, x, y, 0, 0, 102, 5);

        int manaPercentage = Math.max(0, (int) ((double) mana / (double) maxMana * 100));

        if (manaPercentage == 0 && mana > 0) {
            manaPercentage = 1;
        }

        drawTexturedModalRect(ms, x + 1, y + 1, 0, 5, 100, 3);

        float red = (color >> 16 & 0xFF) / 255F;
        float green = (color >> 8 & 0xFF) / 255F;
        float blue = (color & 0xFF) / 255F;
        RenderSystem.color4f(red, green, blue, alpha);
        drawTexturedModalRect(ms, x + 1, y + 1, 0, 5, Math.min(100, manaPercentage), 3);
        RenderSystem.color4f(1, 1, 1, 1);
    }

    public static void drawSimpleManaHUD(MatrixStack ms, int color, int mana, int maxMana, String name) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft mc = Minecraft.getInstance();
        int x = mc.getMainWindow().getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(name) / 2;
        int y = mc.getMainWindow().getScaledHeight() - 60; // is this right?
        mc.fontRenderer.drawStringWithShadow(ms, name, x, y, color);
        x = mc.getMainWindow().getScaledWidth() / 2 - 51;
        y = y + 10;
        renderManaBar(ms, x, y, color, 1F, mana, maxMana);
        RenderSystem.disableBlend();
    }

    public static void drawTexturedModalRect(MatrixStack ms, int x, int y, int u, int v, int width, int height) {
        AbstractGui.blit(ms, x, y, u, v, width, height, 256, 256);
    }
}