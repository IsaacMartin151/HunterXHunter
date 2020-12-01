package com.chubbychump.hunterxhunter.client.rendering;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.bytedeco.opencv.opencv_core.IplImage;
import sun.awt.image.PixelConverter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

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
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                int x = newImage.getRGB(i, j);
                int y =
                        ((x & 0x000000FF) >> 24) |  //______AA
                        ((x & 0x0000FF00) >>  8) | //____RR__
                        ((x & 0x00FF0000) <<  8) | //__GG____
                        ((x & 0xFF000000) << 24); //BB______
                y = PixelConverter.Rgba.Argb.instance.rgbToPixel(x, ColorModel.getRGBdefault());

                this.textureData.setPixelRGBA(i, j, y);
            }
        }
        //HunterXHunter.LOGGER.info("pixel 0 0 has value "+newImage.getRGB(0, 0));
        this.texture.updateDynamicTexture();
    }

    public ResourceLocation getTextureLocation() {
        return loc;
    }
}