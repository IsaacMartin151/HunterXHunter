package com.chubbychump.hunterxhunter.client.rendering;

import static com.mojang.blaze3d.systems.RenderSystem.bindTexture;
import static net.minecraft.util.math.MathHelper.cos;
import static net.minecraft.util.math.MathHelper.sin;
import static org.lwjgl.opengl.GL11.*;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.BotaniaShader;
import com.chubbychump.hunterxhunter.client.rendering.Point;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import jdk.internal.loader.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ObjectDrawingFunctions {
    //private static ResourceLocation texture = new ResourceLocation(HunterXHunter.MOD_ID, "textures/entity/raybeam2.png");
    //private static ResourceLocation noise = new ResourceLocation(HunterXHunter.MOD_ID,"textures/entity/noise3.png");
    private static BufferedImage bufferedImage;

    static {
        try {
            bufferedImage = ImageIO.read(new File("noise3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage texture;

    static {
        try {
            texture = ImageIO.read(new File("raybeam2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static IntBuffer goTime = null;
    private static final int radius = 4;
    private static final float M_PI = 3.14f;
    private static final boolean Distort = true;
    private static Point bot = new Point();
    private static Point top = new Point();
    private static int NumLngs = 8;
    private static int NumLats = 8;
    private static Point[] Pts = new Point[NumLats*NumLngs];

    static Point PtsPointer(int lat, int lng) {
        if (lat < 0)	lat += (NumLats - 1);
        if (lng < 0)	lng += (NumLngs - 1);
        if (lat > NumLats - 1)	lat -= (NumLats - 1);
        if (lng > NumLngs - 1)	lng -= (NumLngs - 1);
        return Pts[NumLngs * lat + lng];
    }

    static Point p;

    public static IntBuffer convertImage(BufferedImage image)
    {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        IntBuffer buffer = BufferUtils.createIntBuffer(image.getWidth() * image.getHeight() * 4);

        for(int y = 0; y < image.getHeight(); y++)
        {
            for(int x = 0; x < image.getWidth(); x++)
            {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();

        return buffer;
    }

    private static void beginRenderCommon(BufferBuilder buffer, TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        RenderSystem.disableLighting();
        IntBuffer Oof = convertImage(bufferedImage);
        IntBuffer Oof2 = convertImage(texture);
        ShaderHelper.useShader(BotaniaShader.FILM_GRAIN, Oof, Oof2);
    }

    private static void endRenderCommon() {
        //Minecraft.getInstance().textureManager.getTexture(texture).restoreLastBlurMipmap();
        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }

    public static void DrawSphere(IVertexBuilder builder, MatrixStack stack, LivingEntity entity) {
        long time = System.currentTimeMillis();
        double speed = 9;
        float angle = (time / (int)speed) % 360;
        stack.push();
        GlStateManager.disableDepthTest();
        //GL11.glBindTexture();
        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);
        stack.rotate(rotation);


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
        for (int ilat = 0; ilat < NumLats; ilat++) {
            float lat = (float) (-M_PI / 2. + M_PI * (float) ilat / (float) (NumLats - 1));
            float xz = cos(lat);
            float y = sin(lat);
            for (int ilng = 0; ilng < NumLngs; ilng++) {
                float lng = (float) (-M_PI + 2. * M_PI * (float) ilng / (float) (NumLngs - 1));
                float x = xz * cos(lng);
                float z = -xz * sin(lng);
                int lat1 = ilat;
                int lng1 = ilng;
                if (lat1 < 0)	lat1 += (NumLats - 1);
                if (lng1 < 0)	lng1 += (NumLngs - 1);
                if (lat1 > NumLats - 1)	lat1 -= (NumLats - 1);
                if (lng1 > NumLngs - 1)	lng1 -= (NumLngs - 1);
                Pts[(NumLngs * lat1 + lng1)] = new Point();
                Pts[(NumLngs * lat1 + lng1)].x = radius * x;
                Pts[(NumLngs * lat1 + lng1)].y = radius * y;
                Pts[(NumLngs * lat1 + lng1)].z = radius * z;
                Pts[(NumLngs * lat1 + lng1)].nx = x;
                Pts[(NumLngs * lat1 + lng1)].ny = y;
                Pts[(NumLngs * lat1 + lng1)].nz = z;
                if (Distort == false) {
                    Pts[(NumLngs * lat1 + lng1)].s = (lng + M_PI) / (2f * M_PI)/* * ((rand() % 2))*/;
                    Pts[(NumLngs * lat1 + lng1)].t = (lat + M_PI / 2f) / M_PI;
                } else {
                    Pts[(NumLngs * lat1 + lng1)].s = (lng + M_PI) / (2f * M_PI);
                    Pts[(NumLngs * lat1 + lng1)].t = (lat + M_PI / 2f) / M_PI;
                }
            }
        }
        top.setTheNumbers(0f, 1f, 0f, 0f, 1f, 0f, radius, 0f);
        bot.setTheNumbers(0f, -1, 0f, 0f, 0f, 0f, -radius, 0f);

        // connect the north pole to the latitude NumLats-2:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(NumLats - 1, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 1, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();
        }

        // connect the south pole to the latitude 1:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(0, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(0, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();
        }

        // connect the other 4-sided polygons:
        for (int ilat = 2; ilat < NumLats - 1; ilat++) {
            for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
                p = PtsPointer(ilat - 1, ilng);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat - 1, ilng + 1);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng + 1);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, 1f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 1f,1f, .5f).tex(p.s, p.t).endVertex();
            }
        }
        TextureManager yo = Minecraft.getInstance().getTextureManager();
        beginRenderCommon(bufferbuilder, yo);


        tessellator.draw();
        ShaderHelper.releaseShader();
        endRenderCommon();
        stack.pop();
    }
}
