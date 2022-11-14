package com.screens;

import com.container.BookMenu;
import com.example.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BookInventoryScreen extends AbstractContainerScreen<BookMenu> {
    private final ResourceLocation GUI = new ResourceLocation(HunterXHunter.MODID, "textures/gui/book/book_inventory.png");

    private final int textureWidth = 194;
    private final int textureHeight = 294;

    public BookInventoryScreen(BookMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.literal("Book"));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI);

        int pX = (this.width - this.textureWidth) / 2;
        int pY = (this.height - this.textureHeight) / 2;

        blit(pPoseStack, pX, pY, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int pXinventory = 10;
        int pYinventory = 200;
        int pXbook = 0;
        int pYbook = 0;

        drawString(pPoseStack, font, title, pXbook, pYbook, 0xFFFFFF);
        drawString(pPoseStack, font, "Inventory", pXinventory, pYinventory, 0xFFFFFF);
    }
}