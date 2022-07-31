package com.chubbychump.hunterxhunter.client.rendering;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.BotaniaShader;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.texture.TextureManager;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.util.Mth.cos;
import static net.minecraft.util.Mth.sin;
import static org.lwjgl.opengl.GL11.*;

@OnlyIn(Dist.CLIENT)
public class ObjectDrawingFunctions {
    //private static ResourceLocation flameTexture2 = new ResourceLocation(HunterXHunter.MOD_ID, "textures/entity/raybeam2.png");
    public static ResourceLocation noiseTexture2 = new ResourceLocation(HunterXHunter.MOD_ID,"textures/entity/noise3.png");
    public static ResourceLocation scaleTexture = new ResourceLocation(HunterXHunter.MOD_ID, "textures/scaletexture.png");
    public static ResourceLocation projectileTexture = new ResourceLocation(HunterXHunter.MOD_ID, "textures/entity/projectile.png");
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private static final float M_PI = 3.14f;
    private static final boolean Distort = true;
    private static Point bot = new Point();
    private static Point top = new Point();
    private static int NumLngs = 16;
    private static int NumLats = 16;
    private static int a = 0;
    private static int b = 0;
    private static Point[] Pts = new Point[NumLats*NumLngs];
    private static Point[] Pts2 = new Point[900];

    static Point PtsPointer(int lat, int lng) {
        if (lat < 0)	lat += (NumLats - 1);
        if (lng < 0)	lng += (NumLngs - 1);
        if (lat > NumLats - 1)	lat -= (NumLats - 1);
        if (lng > NumLngs - 1)	lng -= (NumLngs - 1);
        return Pts[NumLngs * lat + lng];
    }

    static Point p;

    private static void beginRenderCommon() {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        //RenderSystem.colorMask(false, false, false, false); ** STOPS ALL RENDERING, USE FOR MELOREON CHAMELEON POWER?
        RenderSystem.enableTexture();
    }

    private static void endRenderCommon() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.disableTexture();
    }

    private static void setSingleVertex(int index, float x, float y, float z) {
        Pts2[index] = new Point();
        Pts2[index].x = x;
        Pts2[index].y = y;
        Pts2[index].z = z;
    }

    public static void DrawSphere(PoseStack stack, int[] nencolor, float radius) {
        beginRenderCommon();
        long time = System.currentTimeMillis();
        double speed = 9;
        float angle = (time / (int)speed) % 360;

        stack.pushPose();
        yo.bindForSetup(noiseTexture2);
        a = yo.getTexture(noiseTexture2).getId();
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);
        Quaternion revrotation = Vector3f.YP.rotationDegrees(-angle);
        stack.mulPose(rotation);

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
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 2, ilng + 1);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(NumLats - 1, ilng + 1);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();
        }

        // connect the south pole to the latitude 1:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(0, ilng);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(0, ilng + 1);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng + 1);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

            p = PtsPointer(1, ilng);
            bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();
        }

        // connect the other 4-sided polygons:
        for (int ilat = 2; ilat < NumLats - 1; ilat++) {
            for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
                p = PtsPointer(ilat - 1, ilng);
                bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

                p = PtsPointer(ilat - 1, ilng + 1);
                bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng + 1);
                bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();

                p = PtsPointer(ilat, ilng);
                bufferbuilder.vertex(stack.last().pose(), p.x, p.y, p.z).color(1f, 0f,0f, .5f).uv(p.s, p.t).endVertex();
            }
        }

        stack.mulPose(revrotation);
        ShaderHelper.useShader(BotaniaShader.FILM_GRAIN, b, nencolor);
        tessellator.end();
        stack.popPose();
        ShaderHelper.releaseShader();
        endRenderCommon();
    }

    public static void BookRender(PoseStack stack, long difference, Player player) {
        RenderSystem.disableBlend();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        float fadein = (difference/1000f);
        if (fadein > 1f || fadein < 0f) {
            fadein = 1f;
        }

        stack.pushPose();
        //stack.scale(.5f, .5f, .5f);
        float rotate = -player.yRotO;
        Quaternion rotation = Vector3f.YP.rotationDegrees(rotate);
        Quaternion revrotation = Vector3f.YP.rotationDegrees(-rotate);

        stack.mulPose(rotation);
        stack.translate(0.11, .5, 0);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        //Top page
        bufferbuilder.vertex(stack.last().pose(), 0f, .3f, .2f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .8f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.3f, 1.5f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.3f, 1f, .2f).color(1f, 1f,1f, fadein).endVertex();

        bufferbuilder.vertex(stack.last().pose(), 0f, .3f, .2f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .8f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.3f, 1.5f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.3f, 1f, .2f).color(1f, 1f,1f, fadein).endVertex();

        //Middle page
        bufferbuilder.vertex(stack.last().pose(), 0f, .2f, .2f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .7f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.3f, 1.4f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.3f, .9f, .2f).color(.9f, .9f,.9f, fadein).endVertex();

        bufferbuilder.vertex(stack.last().pose(), 0f, .2f, .2f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .7f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.3f, 1.4f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.3f, .9f, .2f).color(.9f, .9f,.9f, fadein).endVertex();

        //Bottom page
        bufferbuilder.vertex(stack.last().pose(), 0f, .1f, .2f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .6f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.4f, 1.3f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.4f, .8f, .2f).color(.8f, .8f,.8f, fadein).endVertex();

        bufferbuilder.vertex(stack.last().pose(), 0f, .1f, .2f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .6f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.4f, 1.3f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.4f, .8f, .2f).color(.8f, .8f,.8f, fadein).endVertex();



        //Spacer
        //stack.rotate(revrotation);
        ShaderHelper.useShader(BotaniaShader.BOOK, b, new int[5]);

        //Book cover
        bufferbuilder.vertex(stack.last().pose(), 0f, 0f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, 0.5f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.5f, 1.2f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), -1.5f, .7f, 0f).color(.668f, .246f,0f, fadein).endVertex();

        bufferbuilder.vertex(stack.last().pose(), 0f, 0f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 0f, .5f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.5f, 1.2f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.vertex(stack.last().pose(), 1.5f, .7f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        tessellator.end();
        ShaderHelper.releaseShader();
        stack.popPose();

    }

    public static void ProjectileRender(PoseStack matrixStackIn, long ticks, Entity entityIn) {
        float r0 = .2f;
        float g0 = .2f;
        float b0 = .9f;
        float a0 = 1f;

        long time = System.currentTimeMillis();
        int angle = (int) ((time / 7) % 360);
        int angle2 = (int) ((time / 3) % 360);

        Quaternion rotall = new Quaternion(angle2, angle2, angle2, true);
        int type = NenUser.getFromPlayer(Minecraft.getInstance().player).getNenType();
        RenderSystem.disableBlend();
        RenderSystem.disableCull();
        //RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();

        yo.bindForSetup(projectileTexture);
        a = yo.getTexture(projectileTexture).getId();

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);

        //Initialial movements and stuff
        matrixStackIn.pushPose();
        //matrixStackIn.last().getNormal().
        matrixStackIn.scale(.5f, .5f, .5f);
        matrixStackIn.mulPose(new Quaternion(angle, 0, 0, true));
        matrixStackIn.mulPose(new Quaternion(0, angle2, 0, true));
        matrixStackIn.translate(-1f/2f, -1f/2f, (-.5f - .433f / 2.0f)/2f);

        //Cube 1
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        isaacCube(matrixStackIn.last().pose(), builder, r0, g0, b0, a0);
        matrixStackIn.popPose();

        //Cube 2
        matrixStackIn.pushPose();
        matrixStackIn.translate(1f, 0.0f, 0.0f);
        matrixStackIn.mulPose(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.last().pose(), builder, r0, g0, b0, a0);
        matrixStackIn.popPose();

        //Cube 3
        matrixStackIn.pushPose();
        matrixStackIn.translate(.5f, .866f, 0.0f);
        matrixStackIn.mulPose(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.last().pose(), builder, r0, g0, b0, a0);
        matrixStackIn.popPose();

        //Cube 4
        matrixStackIn.pushPose();
        matrixStackIn.translate(.5f, .433f, .866f);
        matrixStackIn.mulPose(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.last().pose(), builder, r0, g0, b0, a0);
        matrixStackIn.popPose();

        ShaderHelper.useShader(BotaniaShader.LIGHTING, a, new int[5]);
        tessellator.end();

        matrixStackIn.popPose();
        ShaderHelper.releaseShader();
        //RenderSystem.enableTexture();
    }

    public static void isaacCube(Matrix4f matrix, BufferBuilder bufferBuilder, float r, float g, float b, float a) {
        //Front
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r, g, b, a, 0, 0, 1);
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r, g, b, a, 0, 0, 1);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 0, 0, 1);
        drawVertex(matrix, bufferBuilder, 0, 1, 0, r, g, b, a, 0, 0, 1);

        //Top
        drawVertex(matrix, bufferBuilder, 0, 1, 0, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 0, 1, 0);

        //Left
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 0, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r, g, b, a, -1, 0, 0);

        //Back
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);

        //Bottom
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);

        //Right
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r, g, b, a, 1, 0, 0);
    }

    public static void drawVertex(Matrix4f matrix, BufferBuilder bufferBuilder, int offsetX, int offsetY, int offsetZ, float r, float b, float g, float a, int nx, int ny, int nz) {
        bufferBuilder.vertex(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).uv(0, 0).color(r, b, g, a).normal(nx, ny, nz).endVertex();
    }

    public static void PuzzleScreenSuccess(PoseStack matrixStackIn, long ticks, Entity entityIn) {
        float r0 = .2f;
        float g0 = .9f;
        float b0 = .2f;
        float a0 = 1f;

        long time = System.currentTimeMillis();
        int angle = (int) ((time / 7) % 360);
        int angle2 = (int) ((time / 3) % 360);

        Quaternion rotall = new Quaternion(0, 0, angle2, true);
        int type = NenUser.getFromPlayer(Minecraft.getInstance().player).getNenType();
        RenderSystem.disableBlend();
        RenderSystem.disableCull();
        //RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();

        yo.bindForSetup(projectileTexture);
        a = yo.getTexture(projectileTexture).getId();

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);

        //Cube 1
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        isaacStar(matrixStackIn.last().pose(), builder, r0, g0, b0, a0);
        matrixStackIn.popPose();

        tessellator.end();

    }

    public static void isaacStar(Matrix4f matrix, BufferBuilder bufferBuilder, float r, float g, float b, float a) {

        //Front
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r, g, b, a, 0, 0, 1);
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r, g, b, a, 0, 0, 1);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 0, 0, 1);

        //Top
        drawVertex(matrix, bufferBuilder, 0, 1, 0, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r, g, b, a, 0, 1, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 0, 1, 0);

        //Left
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 0, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r, g, b, a, -1, 0, 0);
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r, g, b, a, -1, 0, 0);

        //Back
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);
        drawVertex(matrix, bufferBuilder, 0, 1, 1, r/1.1f, g/1.1f, b/1.1f, a, 0, 0, -1);

        //Bottom
        drawVertex(matrix, bufferBuilder, 0, 0, 0, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);
        drawVertex(matrix, bufferBuilder, 0, 0, 1, r/1.2f, g/1.2f, b/1.2f, a, 0, -1, 0);

        //Right
        drawVertex(matrix, bufferBuilder, 1, 0, 0, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 0, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 1, 1, r, g, b, a, 1, 0, 0);
        drawVertex(matrix, bufferBuilder, 1, 0, 1, r, g, b, a, 1, 0, 0);
    }

}
