package com.chubbychump.hunterxhunter.client.rendering;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureHandler {
    private final TextureManager textureManager;
    private final DynamicTexture texture;
    private NativeImage textureData;
    private ResourceLocation loc;

    public TextureHandler(TextureManager manager, DynamicTexture textureIn) {
        this.textureManager = manager;
        this.texture = textureIn;
        this.textureData = textureIn.getTextureData();
        this.loc = textureManager.getDynamicTextureLocation("textures/", texture);
    }

    public void updateTextureData(BufferedImage newImage) {
        for (int i = 0; i < this.textureData.getWidth(); i++) {
            for (int j = 0; j < this.textureData.getHeight(); j++) {
                Color uh = new Color(newImage.getRGB(i, j));
                int color = uh.getRed() | (uh.getGreen() << 8) | (uh.getBlue() << 16) | (uh.getAlpha() << 24);
                this.textureData.setPixelRGBA(i, j, color);
            }
        }
        //HunterXHunter.LOGGER.info("pixel 0 0 has value "+newImage.getRGB(0, 0));
        this.texture.updateDynamicTexture();
    }
    public ResourceLocation getTextureLocation() {
        return loc;
    }
}