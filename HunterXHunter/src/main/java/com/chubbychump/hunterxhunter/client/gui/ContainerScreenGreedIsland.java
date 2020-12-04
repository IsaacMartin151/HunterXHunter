package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.items.ItemFlowerBag;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.GREED_ISLAND_BOOK;

public class ContainerScreenGreedIsland extends ContainerScreen<GreedIslandContainer> {

    // This is the resource location for the background image
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/gui/bookinv.png");

    public ContainerScreenGreedIsland(GreedIslandContainer container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);
        this.xSize = 213;
        this.ySize = 244;
        this.guiLeft = 0;
        this.guiTop = 0;
    }

    // deobfuscated name is render
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);                          //     this.renderBackground();
        super.render(matrixStack, mouseX, mouseY, partialTicks);     //super.render
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);  //this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    // drawGuiContainerForegroundLayer
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        final float PLAYER_LABEL_XPOS = 27;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (92);

        final float BAG_LABEL_YPOS = 8;
        TranslationTextComponent bagLabel = new TranslationTextComponent(GREED_ISLAND_BOOK.get().getTranslationKey());
        float BAG_LABEL_XPOS = 10;//(xSize / 2.0F) - this.field_230712_o_.getStringWidth(bagLabel.getString()) / 2.0F;                  // centre the label             //this.font.
        this.font.drawString(matrixStack, GREED_ISLAND_BOOK.toString(), BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.drawString(matrixStack, this.playerInventory.toString(),                              //this.font.drawString;
                PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());
    }

    @Override
    // drawGuiContainerBackgroundLayer is the deobfuscated name
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);                //this.minecraft
        // width and height are the size provided to the window when initialised after creation.
        // xSize, ySize are the expected size of the texture-? usually seems to be left as a default.
        // The code below is typical for vanilla containers, so I've just copied that- it appears to centre the texture within
        //  the available window
        int edgeSpacingX = (this.width - this.xSize) / 2;   //.width
        int edgeSpacingY = (this.height - this.ySize) / 2;  //.height
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);    //.blit
    }
}