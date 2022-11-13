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
    private final ResourceLocation GUI = new ResourceLocation(HunterXHunter.MODID, "textures/gui/book/bookinv.png");


    public BookInventoryScreen(BookMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.literal("Book"));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI);

        int pX = (this.width - this.imageWidth) / 2;
        int pY = (this.height - this.imageHeight) / 2;

        blit(pPoseStack, pX, pY, 0, 0, imageWidth, imageHeight, 256, 256);
    }
}