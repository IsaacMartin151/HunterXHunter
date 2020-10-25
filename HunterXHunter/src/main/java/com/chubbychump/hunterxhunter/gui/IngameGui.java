package com.chubbychump.hunterxhunter.gui;

import com.chubbychump.hunterxhunter.Config;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class IngameGui extends AbstractGui {

    private final Random rand = new Random();
    private Minecraft mc;

    // Health stuff
    long healthUpdateCounter;
    int playerHealth;
    int lastPlayerHealth;
    long lastSystemTime;

    public IngameGui() {
        this.mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        // Disable all HUD modifications if this option is disabled
        if (!Config.customHud.get()) {
            return;
        }

        // If we're going to do something, we need the player
        PlayerEntity player = getPlayerEntity();
        if (player == null) {
            return;
        }

        // Determine the element type & then draw based on minimal hud
        ElementType type = event.getType();
        if (type == ElementType.ARMOR) {
            event.setCanceled(true);
            redrawArmor(player);
        } else if (type == ElementType.AIR && !Config.minimalHud.get()) {
            event.setCanceled(true);
            redrawAir(player);
        } else if (type == ElementType.HEALTH) {
            if (Config.hideHud.get()) {
                event.setCanceled(true);
            } else if (Config.minimalHud.get()) {
                event.setCanceled(true);
                redrawHealth(player);
            }
        }
    }

    private void redrawArmor(PlayerEntity player) {
        MatrixStack matrixStack = new MatrixStack();
        int top = mc.getMainWindow().getScaledHeight() - 49;
        int left = mc.getMainWindow().getScaledWidth() / 2;
        if (Config.minimalHud.get()) {
            left -= 91;
        } else {
            left += 10;
        }

        int armor = player.getTotalArmorValue();
        if (armor > 0) {
            for (int i = 0; i < 10; i++) {
                int threshold = i * 2 + 1;
                if (threshold < armor) {
                    this.func_238474_b_(matrixStack, left + i * 8, top, 34, 9, 9, 9);
                } else if (threshold == armor) {
                    this.func_238474_b_(matrixStack, left + i * 8, top, 25, 9, 9, 9);
                } else {
                    this.func_238474_b_(matrixStack, left + i * 8, top, 16, 9, 9, 9);
                }
            }
        }
    }

    private void redrawAir(PlayerEntity player) {
        MatrixStack matrixStack = new MatrixStack();
        int air = player.getAir();
        int maxAir = player.getMaxAir();
        if (!player.areEyesInFluid(FluidTags.WATER) && air >= maxAir) {
            return;
        }

        int top = mc.getMainWindow().getScaledHeight() - 49;
        int left = mc.getMainWindow().getScaledWidth() / 2 + 91;
        if (player.getTotalArmorValue() > 0) {
            top -= 10;
        }

        int current = MathHelper.ceil((air - 2) * 10.0D / maxAir);
        int changed = MathHelper.ceil(air * 10.0D / maxAir) - current;
        for (int i = 0; i < current + changed; i++) {
            if (i < current) {
                this.func_238474_b_(matrixStack, left - i * 8 - 9, top, 16, 18, 9, 9);
            } else {
                this.func_238474_b_(matrixStack, left - i * 8 - 9, top, 25, 18, 9, 9);
            }
        }
    }

    private void redrawHealth(PlayerEntity player) {
        MatrixStack matrixStack = new MatrixStack();
        int top = mc.getMainWindow().getScaledHeight() - 39;
        int left = mc.getMainWindow().getScaledWidth() / 2 - 91;

        // Get the player's health (rounded)
        int health = MathHelper.ceil(player.getHealth());

        // Health update counter
        boolean flash = this.healthUpdateCounter > this.mc.ingameGUI.getTicks() && (this.healthUpdateCounter - this.mc.ingameGUI.getTicks()) / 3L % 2L == 1L;

        // Calculate counter
        long time = Util.milliTime();
        if (health < this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = time;
            this.healthUpdateCounter = this.mc.ingameGUI.getTicks() + 20;
        } else if (health > this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = time;
            this.healthUpdateCounter = this.mc.ingameGUI.getTicks() + 10;
        }
        if (time - this.lastSystemTime > 1000L) {
            this.playerHealth = health;
            this.lastPlayerHealth = health;
            this.lastSystemTime = time;
        }

        // Set some variables
        this.playerHealth = health;
        this.rand.setSeed(this.mc.ingameGUI.getTicks() * 312871);

        // Get absorption amount
        int absHealth = MathHelper.ceil(player.getAbsorptionAmount());
        int absHealthTemp = absHealth;

        // Get amount in full hearts subtract one
        int hearts = MathHelper.ceil((player.getHealth() + absHealth) / 2.0F) - 1;

        // Get max amount in full hearts subtract one
        int maxHearts = MathHelper.ceil((player.getMaxHealth() + absHealth) / 2.0F) - 1;

        // Determine how many rows and fetch the top one (-1 for zero index)
        int row = MathHelper.ceil(((hearts + 1) * 2) / 20.0F) - 1;

        // Determine the maximum amount of rows possible
        int maxRow = MathHelper.ceil(((maxHearts + 1) * 2) / 20.0F) - 1;

        // Get minimum heart range for row
        int rowMin = row * 10;

        // Get the maximum amount of hearts for this row
        int rowMax = rowMin + 10;

        // Remove extra hearts that would have exceeded the player's maximum heart count on the last row only
        int rowPreferred = row == maxRow ? rowMax - (10 - (maxHearts + 1 - rowMin)) : rowMax;

        for (int i = rowMin; i < rowPreferred; i++) {
            int textureX = 16;
            if (player.isPotionActive(Effects.POISON)) {
                textureX += 36;
            } else if (player.isPotionActive(Effects.WITHER)) {
                textureX += 72;
            }

            int regen = -1;
            if (player.isPotionActive(Effects.REGENERATION)) {
                regen = this.mc.ingameGUI.getTicks() % MathHelper.ceil(player.getMaxHealth() + 5.0F);
            }
            int x = left + (i * 8) - ((8 * 10) * row);
            int y = top;

            // Check if hearts is less than 40% of default health (min 1)
            if (hearts <= Math.max((Config.defHealth.get() / 2.0) * 0.4, 1)) {
                y += this.rand.nextInt(2);
            }


            if (absHealthTemp <= 0 && i == regen) {
                y -= 2;
            }

            // Draw Empty Hearts
            this.func_238474_b_(matrixStack, x, y, 16 + (flash ? 9 : 0), 0, 9, 9);

            // Draw Flag Hearts?
            if (flash) {
                if (i * 2 + 1 < this.lastPlayerHealth) {
                    this.func_238474_b_(matrixStack, x, y, textureX + 54, 0, 9, 9);
                }
                if (i * 2 + 1 == this.lastPlayerHealth) {
                    this.func_238474_b_(matrixStack, x, y, textureX + 63, 0, 9, 9);
                }
            }

            // Draw Filled In Hearts
            if (i * 2 + 1 < health) {
                this.func_238474_b_(matrixStack, x, y, textureX + 36, 0, 9, 9);
            } else if (i * 2 + 1 == health) {
                this.func_238474_b_(matrixStack, x, y, textureX + 45, 0, 9, 9);
            } else if (i * 2 + 1 > health && absHealthTemp > 0) {
                if (absHealthTemp == absHealth && absHealth % 2 == 1) {
                    this.func_238474_b_(matrixStack, x, y, textureX + 153, 0, 9, 9);
                    absHealthTemp--;
                } else {
                    this.func_238474_b_(matrixStack, x, y, textureX + 144, 0, 9, 9);
                    absHealthTemp -= 2;
                }
            }
        }

        // Draw MinimalHUD Number
        String text = Integer.toString(row + 1);

        // Move left positioning for more positions in the number
        if (row >= 9)
            left -= 6;
        if (row >= 99)
            left -= 6;
        if (row >= 999)
            left -= 6;
        if (row >= 9999) {
            text = "9999+";
            left -= 6;
        }

        // Dropshadow
        //this.mc.fontRenderer.func_238475_b_(matrixStack, text, left - 6, top + 1, 0);
        //this.mc.fontRenderer.drawString(matrixStack, text, left - 8, top + 1, 0);
        //this.mc.fontRenderer.drawString(matrixStack, text, left - 7, top + 2, 0);
        //this.mc.fontRenderer.drawString(matrixStack, text, left - 7, top, 0);

        // The Number
        //this.mc.fontRenderer.drawString(matrixStack, text, left - 7, top + 1, 0xF00000);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
    }

    private PlayerEntity getPlayerEntity() {
        return this.mc.getRenderViewEntity() instanceof PlayerEntity ? (PlayerEntity) this.mc.getRenderViewEntity() : null;
    }

}

