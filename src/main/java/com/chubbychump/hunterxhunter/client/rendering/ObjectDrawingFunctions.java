package com.chubbychump.hunterxhunter.client.rendering;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.BotaniaShader;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.chubbychump.hunterxhunter.client.rendering.AddVertices.addvertices;
import static net.minecraft.util.math.MathHelper.cos;
import static net.minecraft.util.math.MathHelper.sin;
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

    public static void DrawSphere(MatrixStack stack, int[] nencolor, float radius) {
        beginRenderCommon();
        long time = System.currentTimeMillis();
        double speed = 9;
        float angle = (time / (int)speed) % 360;

        stack.push();
        yo.bindTexture(noiseTexture2);
        a = yo.getTexture(noiseTexture2).getGlTextureId();
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
        ShaderHelper.useShader(BotaniaShader.FILM_GRAIN, b, nencolor);
        tessellator.draw();
        stack.pop();
        ShaderHelper.releaseShader();
        endRenderCommon();
    }

    public static void DrawDragon(MatrixStack stack, int[] nencolor) {
        beginRenderCommon();
        long time = System.currentTimeMillis();
        double speed = 9;
        float angle = (time / (int)speed) % 360;

        stack.push();
        yo.bindTexture(scaleTexture);
        a = yo.getTexture(scaleTexture).getGlTextureId();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);

        Quaternion rotation = Vector3f.ZP.rotationDegrees(angle);
        Quaternion revrotation = Vector3f.ZP.rotationDegrees(-angle);
        Quaternion playerfacing = Vector3f.XP.rotationDegrees(90);


        Quaternion asdf = Vector3f.ZP.rotationDegrees(angle);

        //stack.rotate(rotation);
        //glShadeModel(GL_FLAT);
        //vec3 color = vec3(.5, .5, 1.);

        //Pattern->SetUniformVariable()
        //glDisable(GL_LIGHTING);


        //glColor3f(.1, 1., .2);

        stack.push();
        //glRotatef(10, 0, 0, 100);
        stack.translate(0, -3, 0);
        stack.scale(.5F, 2F, .5F);
        int curvePoints = 10;

        if (true) {
            float Time = (time % 1000) / 1000F;
            float twopi = 2 * 3.14F;
            float pts = 10F; //About as low as we want to go
            float rotangle = twopi / pts;
            float radiusmodifier = 1F;
            for (int j = 0; j < ((int) pts); j++) {

                setSingleVertex(0, cos(Time * twopi), -5, sin(Time * twopi));
                setSingleVertex(1, cos(Time * twopi) * radiusmodifier, -4, sin(Time * twopi) * radiusmodifier);
                setSingleVertex(2, cos(j * rotangle), -3, sin(j * rotangle));
                setSingleVertex(3, cos(j * rotangle) * radiusmodifier, -1, sin(j * rotangle) * radiusmodifier);
                setSingleVertex(4, cos(j * rotangle), 1, sin(j * rotangle));
                setSingleVertex(5, cos(j * rotangle) * radiusmodifier, 2, sin(j * rotangle) * radiusmodifier);
                setSingleVertex(6, cos(j * rotangle), 3, sin(j * rotangle));
                setSingleVertex(7, cos(j * rotangle) * radiusmodifier, 5, sin(j * rotangle) * radiusmodifier);

                float Time2 = Time + .2F;
                float k = j + 1;
                if (k == 10) {
                    k = 0;
                }
                setSingleVertex(8, cos(Time2 * twopi), -5, sin(Time2 * twopi));
                setSingleVertex(9, cos(Time2 * twopi) * radiusmodifier, -4, sin(Time2 * twopi) * radiusmodifier);
                setSingleVertex(10, cos(k * rotangle), -3, sin(k * rotangle));
                setSingleVertex(11, cos(k * rotangle) * radiusmodifier, -1, sin(k * rotangle) * radiusmodifier);
                setSingleVertex(12, cos(k * rotangle), 1, sin(k * rotangle));
                setSingleVertex(13, cos(k * rotangle) * radiusmodifier, 2, sin(k * rotangle) * radiusmodifier);
                setSingleVertex(14, cos(k * rotangle), 3, sin(k * rotangle));
                setSingleVertex(15, cos(k * rotangle) * radiusmodifier, 5, sin(k * rotangle) * radiusmodifier);

                float addedoutside = 1.5F;
                float ymodifier = .5F;
                setSingleVertex(16, cos(Time2 * twopi) * addedoutside, -5 - ymodifier, sin(Time2 * twopi) * addedoutside);
                setSingleVertex(17, cos(Time2 * twopi) * radiusmodifier * addedoutside, -4 - ymodifier, sin(Time2 * twopi) * radiusmodifier * addedoutside);
                setSingleVertex(18, cos(k * rotangle) * addedoutside, -3 - ymodifier, sin(k * rotangle) * addedoutside);
                setSingleVertex(19, cos(k * rotangle) * radiusmodifier * addedoutside, -1 - ymodifier, sin(k * rotangle) * radiusmodifier * addedoutside);
                setSingleVertex(20, cos(k * rotangle) * addedoutside, 1 - ymodifier, sin(k * rotangle) * addedoutside);
                setSingleVertex(21, cos(k * rotangle) * radiusmodifier * addedoutside, 2 - ymodifier, sin(k * rotangle) * radiusmodifier * addedoutside);
                setSingleVertex(22, cos(k * rotangle) * addedoutside, 3 - ymodifier, sin(k * rotangle) * addedoutside);
                setSingleVertex(23, cos(k * rotangle) * radiusmodifier * addedoutside, 5 - ymodifier, sin(k * rotangle) * radiusmodifier * addedoutside);

                stack.push();
                //glBegin(GL_TRIANGLE_STRIP);
                for (int i = 0; i < 8 - 3; i++) {
                    int te = i;
                    p = Pts2[te];
                    float P0X = p.x;
                    float P0Y = p.y;
                    float P0Z = p.z;
                    p = Pts2[te + 1];
                    float P1X = p.x;
                    float P1Y = p.y;
                    float P1Z = p.z;
                    p = Pts2[te + 2];
                    float P2X = p.x;
                    float P2Y = p.y;
                    float P2Z = p.z;
                    p = Pts2[te + 3];
                    float P3X = p.x;
                    float P3Y = p.y;
                    float P3Z = p.z;
                    te = i + 8;
                    p = Pts2[te];
                    float P0XB = p.x;
                    float P0YB = p.y;
                    float P0ZB = p.z;
                    p = Pts2[te + 1];
                    float P1XB = p.x;
                    float P1YB = p.y;
                    float P1ZB = p.z;
                    p = Pts2[te + 2];
                    float P2XB = p.x;
                    float P2YB = p.y;
                    float P2ZB = p.z;
                    p = Pts2[te + 3];
                    float P3XB = p.x;
                    float P3YB = p.y;
                    float P3ZB = p.z;
                    te = te + 8;
                    p = Pts2[te];
                    float P0XC = p.x;
                    float P0YC = p.y;
                    float P0ZC = p.z;
                    p = Pts2[te + 1];
                    float P1XC = p.x;
                    float P1YC = p.y;
                    float P1ZC = p.z;
                    p = Pts2[te + 2];
                    float P2XC = p.x;
                    float P2YC = p.y;
                    float P2ZC = p.z;
                    p = Pts2[te + 3];
                    float P3XC = p.x;
                    float P3YC = p.y;
                    float P3ZC = p.z;
                    for (int f = 0; f < 100; f = f + 25) {
                        float t = (float)(f / 100F);
                        float x1 = 0.5F * (2 * P1X + t * (-P0X + P2X) + t * t * (2 * P0X - 5.0F * P1X + 4 * P2X - P3X) + t * t * t * (-P0X + 3 * P1X - 3 * P2X + P3X));
                        float y1 = 0.5F * (2 * P1Y + t * (-P0Y + P2Y) + t * t * (2 * P0Y - 5.0F * P1Y + 4 * P2Y - P3Y) + t * t * t * (-P0Y + 3 * P1Y - 3 * P2Y + P3Y));
                        float z1 = 0.5F * (2 * P1Z + t * (-P0Z + P2Z) + t * t * (2 * P0Z - 5.0F * P1Z + 4 * P2Z - P3Z) + t * t * t * (-P0Z + 3 * P1Z - 3 * P2Z + P3Z));
                        //glColor3f(.1F, 1.F, .2F);
                        bufferbuilder.pos(stack.getLast().getMatrix(), x1, y1, z1).color(.1f, 1.f,.2f, 1f).endVertex();
                        x1 = 0.5F * (2 * P1XB + t * (-P0XB + P2XB) + t * t * (2 * P0XB - 5.0F * P1XB + 4 * P2XB - P3XB) + t * t * t * (-P0XB + 3 * P1XB - 3 * P2XB + P3XB));
                        y1 = 0.5F * (2 * P1YB + t * (-P0YB + P2YB) + t * t * (2 * P0YB - 5.0F * P1YB + 4 * P2YB - P3YB) + t * t * t * (-P0YB + 3 * P1YB - 3 * P2YB + P3YB));
                        z1 = 0.5F * (2 * P1ZB + t * (-P0ZB + P2ZB) + t * t * (2 * P0ZB - 5.0F * P1ZB + 4 * P2ZB - P3ZB) + t * t * t * (-P0ZB + 3 * P1ZB - 3 * P2ZB + P3ZB));
                        bufferbuilder.pos(stack.getLast().getMatrix(), x1, y1, z1).color(.1f, 1.f,.2f, 1f).endVertex();
                        x1 = 0.5F * (2 * P1XC + t * (-P0XC + P2XC) + t * t * (2 * P0XC - 5.0F * P1XC + 4 * P2XC - P3XC) + t * t * t * (-P0XC + 3 * P1XC - 3 * P2XC + P3XC));
                        y1 = 0.5F * (2 * P1YC + t * (-P0YC + P2YC) + t * t * (2 * P0YC - 5.0F * P1YC + 4 * P2YC - P3YC) + t * t * t * (-P0YC + 3 * P1YC - 3 * P2YC + P3YC));
                        z1 = 0.5F * (2 * P1ZC + t * (-P0ZC + P2ZC) + t * t * (2 * P0ZC - 5.0F * P1ZC + 4 * P2ZC - P3ZC) + t * t * t * (-P0ZC + 3 * P1ZC - 3 * P2ZC + P3ZC));
                        bufferbuilder.pos(stack.getLast().getMatrix(), x1, y1, z1).color(.1f, 1.f,.2f, 1f).endVertex();
                    }
                    //glRotatef(20, 0, 0, 1);
                }
                //glEnd();
                stack.pop();
            }
            stack.pop();
        }
        //stack.rotate(revrotation);

        ShaderHelper.useShader(BotaniaShader.DRAGON, b, nencolor);
        stack.pop();

        //tessellator.draw();
        stack.pop();
        ShaderHelper.releaseShader();

        stack.push();
        stack.translate(0, 2, 0);
        //glColor3f(.1, 0, 1);
        //LoadObjFile("DragonHead.obj");
        stack.push();
        stack.translate(0, 2, 0);
        stack.translate(0, 0, 3.3);
        stack.translate(0, 0, -( cos((Util.milliTime() % 1000) * 2 * M_PI/1000) + 3.6));
        addvertices(bufferbuilder, stack);
        tessellator.draw();

        stack.pop();
        endRenderCommon();
    }

    public static void BookRender(MatrixStack stack, long difference, PlayerEntity player) {
        RenderSystem.disableBlend();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        float fadein = (difference/1000f);
        if (fadein > 1f || fadein < 0f) {
            fadein = 1f;
        }

        stack.push();
        //stack.scale(.5f, .5f, .5f);
        float rotate = -player.rotationYaw;
        Quaternion rotation = Vector3f.YP.rotationDegrees(rotate);
        Quaternion revrotation = Vector3f.YP.rotationDegrees(-rotate);

        stack.rotate(rotation);
        stack.translate(0.11, .5, 0);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        //Top page
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .3f, .2f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .8f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.3f, 1.5f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.3f, 1f, .2f).color(1f, 1f,1f, fadein).endVertex();

        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .3f, .2f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .8f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.3f, 1.5f, 2.8f).color(1f, 1f,1f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.3f, 1f, .2f).color(1f, 1f,1f, fadein).endVertex();

        //Middle page
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .2f, .2f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .7f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.3f, 1.4f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.3f, .9f, .2f).color(.9f, .9f,.9f, fadein).endVertex();

        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .2f, .2f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .7f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.3f, 1.4f, 2.8f).color(.9f, .9f,.9f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.3f, .9f, .2f).color(.9f, .9f,.9f, fadein).endVertex();

        //Bottom page
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .1f, .2f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .6f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.4f, 1.3f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.4f, .8f, .2f).color(.8f, .8f,.8f, fadein).endVertex();

        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .1f, .2f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .6f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.4f, 1.3f, 2.8f).color(.8f, .8f,.8f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.4f, .8f, .2f).color(.8f, .8f,.8f, fadein).endVertex();



        //Spacer
        //stack.rotate(revrotation);
        ShaderHelper.useShader(BotaniaShader.BOOK, b, new int[5]);

        //Book cover
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, 0f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, 0.5f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.5f, 1.2f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), -1.5f, .7f, 0f).color(.668f, .246f,0f, fadein).endVertex();

        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, 0f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 0f, .5f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.5f, 1.2f, 3f).color(.668f, .246f,0f, fadein).endVertex();
        bufferbuilder.pos(stack.getLast().getMatrix(), 1.5f, .7f, 0f).color(.668f, .246f,0f, fadein).endVertex();
        tessellator.draw();
        ShaderHelper.releaseShader();
        stack.pop();

    }

    public static void ProjectileRender(MatrixStack matrixStackIn, long ticks, Entity entityIn) {
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
        RenderSystem.shadeModel(GL_SMOOTH);
        //RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();

        yo.bindTexture(projectileTexture);
        a = yo.getTexture(projectileTexture).getGlTextureId();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        builder.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

        //Initialial movements and stuff
        matrixStackIn.push();
        //matrixStackIn.getLast().getNormal().
        matrixStackIn.scale(.5f, .5f, .5f);
        matrixStackIn.rotate(new Quaternion(angle, 0, 0, true));
        matrixStackIn.rotate(new Quaternion(0, angle2, 0, true));
        matrixStackIn.translate(-1f/2f, -1f/2f, (-.5f - .433f / 2.0f)/2f);

        //Cube 1
        matrixStackIn.push();
        matrixStackIn.rotate(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        isaacCube(matrixStackIn.getLast().getMatrix(), builder, r0, g0, b0, a0);
        matrixStackIn.pop();

        //Cube 2
        matrixStackIn.push();
        matrixStackIn.translate(1f, 0.0f, 0.0f);
        matrixStackIn.rotate(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.getLast().getMatrix(), builder, r0, g0, b0, a0);
        matrixStackIn.pop();

        //Cube 3
        matrixStackIn.push();
        matrixStackIn.translate(.5f, .866f, 0.0f);
        matrixStackIn.rotate(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.getLast().getMatrix(), builder, r0, g0, b0, a0);
        matrixStackIn.pop();

        //Cube 4
        matrixStackIn.push();
        matrixStackIn.translate(.5f, .433f, .866f);
        matrixStackIn.rotate(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);
        isaacCube(matrixStackIn.getLast().getMatrix(), builder, r0, g0, b0, a0);
        matrixStackIn.pop();

        ShaderHelper.useShader(BotaniaShader.LIGHTING, a, new int[5]);
        tessellator.draw();

        matrixStackIn.pop();
        ShaderHelper.releaseShader();
        RenderSystem.shadeModel(GL_FLAT);
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
        bufferBuilder.pos(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).tex(0, 0).color(r, b, g, a).normal(nx, ny, nz).endVertex();
    }

    public static void PuzzleScreenSuccess(MatrixStack matrixStackIn, long ticks, Entity entityIn) {
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

        yo.bindTexture(projectileTexture);
        a = yo.getTexture(projectileTexture).getGlTextureId();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        builder.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

        //Cube 1
        matrixStackIn.push();
        matrixStackIn.rotate(rotall);
        matrixStackIn.translate(-.5f, -.5f, -.5f);

        isaacStar(matrixStackIn.getLast().getMatrix(), builder, r0, g0, b0, a0);
        matrixStackIn.pop();

        tessellator.draw();

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
