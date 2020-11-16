package com.chubbychump.hunterxhunter.client.rendering;

import static com.mojang.blaze3d.systems.RenderSystem.bindTexture;
import static net.minecraft.util.math.MathHelper.cos;
import static net.minecraft.util.math.MathHelper.sin;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.jemalloc.JEmalloc.Functions.free;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.BotaniaShader;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderWrapper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.b3d.B3DModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


public class ObjectDrawingFunctions {
    //private static ResourceLocation flameTexture2 = new ResourceLocation(HunterXHunter.MOD_ID, "textures/entity/raybeam2.png");
    public static ResourceLocation noiseTexture2 = new ResourceLocation(HunterXHunter.MOD_ID,"textures/entity/noise3.png");
    private static BufferedImage noiseTexture1 = null;

    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private static final int radius = 4;
    private static final float M_PI = 3.14f;
    private static final boolean Distort = true;
    private static Point bot = new Point();
    private static Point top = new Point();
    private static int NumLngs = 16;
    private static int NumLats = 16;
    private static int i = 0;
    private static int j = 0;
    private static Point[] Pts = new Point[NumLats*NumLngs];

    static Point PtsPointer(int lat, int lng) {
        if (lat < 0)	lat += (NumLats - 1);
        if (lng < 0)	lng += (NumLngs - 1);
        if (lat > NumLats - 1)	lat -= (NumLats - 1);
        if (lng > NumLngs - 1)	lng -= (NumLngs - 1);
        return Pts[NumLngs * lat + lng];
    }

    static Point p;

    public static void beginRenderCommon() {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
    }

    private static void endRenderCommon() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.disableTexture();
    }

    public static void DrawSphere(MatrixStack stack, IRenderTypeBuffer buffer, LivingEntity entity) {
        long time = System.currentTimeMillis();
        double speed = 9;
        float angle = (time / (int)speed) % 360;
        stack.push();
        yo.bindTexture(noiseTexture2);
        i = yo.getTexture(noiseTexture2).getGlTextureId();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);

        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);
        Quaternion revrotation = Vector3f.YP.rotationDegrees(-angle);
        stack.rotate(rotation);

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
                Pts[(NumLngs * lat1 + lng1)].s = (lng + M_PI) / (2f * M_PI);
                Pts[(NumLngs * lat1 + lng1)].t = (lat + M_PI / 2f) / M_PI;
            }
        }
        top.setTheNumbers(0f, 1f, 0f, 0f, 1f, 0f, radius, 0f);
        bot.setTheNumbers(0f, -1, 0f, 0f, 0f, 0f, -radius, 0f);

        // connect the north pole to the latitude NumLats-2:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(NumLats - 1, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 1, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();
        }

        // connect the south pole to the latitude 1:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(0, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(0, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng + 1);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng);
            bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();
        }

        // connect the other 4-sided polygons:
        for (int ilat = 2; ilat < NumLats - 1; ilat++) {
            for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
                p = PtsPointer(ilat - 1, ilng);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat - 1, ilng + 1);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng + 1);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng);
                bufferbuilder.pos(stack.getLast().getMatrix(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).tex(p.s, p.t).endVertex();
            }
        }

        stack.rotate(revrotation);
        ShaderHelper.useShader(BotaniaShader.FILM_GRAIN, j);
        tessellator.draw();

        stack.pop();
        ShaderHelper.releaseShader();
        endRenderCommon();
    }
}
