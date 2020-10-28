package com.chubbychump.hunterxhunter.client.rendering;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

import static net.minecraft.client.renderer.RenderType.makeType;

public class RenderTypeLine extends RenderType {

    // Dummy
    public RenderTypeLine(String name, VertexFormat format, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable runnablePre, Runnable runnablePost) {
        super(name, format, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, runnablePre, runnablePost);
    }

    private static final RenderState.LineState THICK_LINES = new RenderState.LineState(OptionalDouble.of(3.0D));

    public static final RenderType OVERLAY_LINES = makeType("overlay_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_QUADS, 256,
            RenderType.State.getBuilder().line(THICK_LINES)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .texture(NO_TEXTURE)
                    .depthTest(DEPTH_ALWAYS)
                    .fog(FOG)
                    .cull(CULL_DISABLED)
                    .lightmap(LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(true));
}
