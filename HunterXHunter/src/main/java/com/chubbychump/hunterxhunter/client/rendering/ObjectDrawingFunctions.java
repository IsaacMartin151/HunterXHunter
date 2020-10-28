package com.chubbychump.hunterxhunter.client.rendering;

import static net.minecraft.util.math.MathHelper.cos;
import static net.minecraft.util.math.MathHelper.sin;
import static org.lwjgl.opengl.GL11.*;
import com.chubbychump.hunterxhunter.client.rendering.Point;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;

public class ObjectDrawingFunctions {
    private static final int radius = 5;
    private static final float M_PI = 3.14f;
    private static final boolean Distort = true;
    private static Point bot = new Point();
    private static Point top = new Point();
    private static int NumLngs = 16;
    private static int NumLats = 16;
    private static Point[] Pts = new Point[NumLats*NumLngs];

    private static void add(IVertexBuilder renderer, MatrixStack stack, Point p) {
        renderer.pos(stack.getLast().getMatrix(), p.x, p.y, p.z)
                .color(.5f, .5f, 0f, .5f)
                //.tex(p.s, p.y)
                //.lightmap(0, 240)
                //.normal(p.nx, p.ny, p.nz)
                .endVertex();
    }

    static Point PtsPointer(int lat, int lng) {
        if (lat < 0)	lat += (NumLats - 1);
        if (lng < 0)	lng += (NumLngs - 1);
        if (lat > NumLats - 1)	lat -= (NumLats - 1);
        if (lng > NumLngs - 1)	lng -= (NumLngs - 1);
        return Pts[NumLngs * lat + lng];
    }

    static Point p;

    public static void DrawSphere(IVertexBuilder builder, MatrixStack stack, Entity entity) {
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
                if (Distort == true) {
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
            add(builder, stack, p);

            p = PtsPointer(NumLats - 2, ilng);
            add(builder, stack, p);

            p = PtsPointer(NumLats - 2, ilng + 1);
            add(builder, stack, p);

            p = PtsPointer(NumLats - 1, ilng + 1);
            add(builder, stack, p);
        }

        // connect the south pole to the latitude 1:
        for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
            p = PtsPointer(0, ilng);
            add(builder, stack, p);

            p = PtsPointer(0, ilng + 1);
            add(builder, stack, p);

            p = PtsPointer(1, ilng + 1);
            add(builder, stack, p);

            p = PtsPointer(1, ilng);
            add(builder, stack, p);
        }

        // connect the other 4-sided polygons:
        for (int ilat = 2; ilat < NumLats - 1; ilat++) {
            for (int ilng = 0; ilng < NumLngs - 1; ilng++) {
                p = PtsPointer(ilat - 1, ilng);
                add(builder, stack, p);

                p = PtsPointer(ilat - 1, ilng + 1);
                add(builder, stack, p);

                p = PtsPointer(ilat, ilng + 1);
                add(builder, stack, p);

                p = PtsPointer(ilat, ilng);
                add(builder, stack, p);
            }
        }
    }
}
