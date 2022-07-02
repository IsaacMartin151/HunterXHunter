package com.chubbychump.hunterxhunter.client.rendering;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.server.tileentities.ShiftyTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class ShiftyTERenderer extends TileEntityRenderer<ShiftyTileEntity> {

    public ShiftyTERenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    /**
     * render the tile entity - called every frame while the tileentity is in view of the player
     *
     * @param tileEntityMBE21 the associated tile entity
     * @param partialTicks    the fraction of a tick that this frame is being rendered at - used to interpolate frames between
     *                        ticks, to make animations smoother.  For example - if the frame rate is steady at 80 frames per second,
     *                        this method will be called four times per tick, with partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5, 0.75
     * @param matrixStack     the matrixStack is used to track the current view transformations that have been applied - i.e translation, rotation, scaling
     *                        it is needed for you to render the view properly.
     * @param renderBuffers    the buffer that you should render your model to
     * @param combinedLight   the blocklight + skylight value for the tileEntity.  see http://greyminecraftcoder.blogspot.com/2014/12/lighting-18.html (outdated, but the concepts are still valid)
     * @param combinedOverlay value for the "combined overlay" which changes the render based on an overlay texture (see OverlayTexture class).
     *                        Used by vanilla for (1) red tint when a living entity takes damage, and (2) "flash" effect for creeper when ignited
     *                        CreeperRenderer.getOverlayProgress()
     */
    @Override
    public void render(ShiftyTileEntity tileEntityMBE21, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffers, int combinedLight, int combinedOverlay) {
        if (NenUser.getFromPlayer(Minecraft.getInstance().player).isGyo()) {
            renderWireframe(tileEntityMBE21, partialTicks, matrixStack, renderBuffers, combinedLight, combinedOverlay);
            //case QUADS: RenderQuads.renderCubeUsingQuads(tileEntityMBE21, partialTicks, matrixStack, renderBuffers, combinedLight, combinedOverlay); break;
            //case BLOCKQUADS: RenderModelHourglass.renderUsingModel(tileEntityMBE21, partialTicks, matrixStack, renderBuffers, combinedLight, combinedOverlay); break;
            //case WAVEFRONT: renderWavefrontObj(tileEntityMBE21, partialTicks, matrixStack, renderBuffers, combinedLight, combinedOverlay); break;
            //default: { LOGGER.debug("Unexpected objectRenderStyle:" + objectRenderStyle);}

            // if you need to manually change the combinedLight you can use these helper functions...
            int blockLight = LightTexture.getLightBlock(combinedLight);
            int skyLight = LightTexture.getLightSky(combinedLight);
            int repackedValue = LightTexture.packLight(blockLight, skyLight);
        }
    }

    private void renderWireframe(ShiftyTileEntity tileEntityMBE21, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffers,
                                       int combinedLight, int combinedOverlay) {
        // draw the artifact using lines
        // (Draws an inverted tetrahedron wireframe above the rendered base block (hopper block model))
        // When the TER::render method is called, the origin [0,0,0] is at the current [x,y,z] of the block being rendered.
        // The tetrahedron-drawing method draws the tetrahedron in a cube region from [0,0,0] to [1,1,1] but we want it
        //   to be in the block one above this, i.e. from [0,1,0] to [1,2,1],
        //   so we need to translate up by one block, i.e. by [0,1,0]
        final Vector3d TRANSLATION_OFFSET = new Vector3d(0, 1, 0);

        matrixStack.push(); // push the current transformation matrix + normals matrix
        matrixStack.translate(TRANSLATION_OFFSET.x,TRANSLATION_OFFSET.y,TRANSLATION_OFFSET.z); // translate
        Color artifactColour = Color.GREEN;
        drawTetrahedronWireframe(matrixStack, renderBuffers, artifactColour);
        matrixStack.pop(); // restore the original transformation matrix + normals matrix
    }

    private void drawTetrahedronWireframe(MatrixStack matrixStack, IRenderTypeBuffer renderBuffers, java.awt.Color color) {

        final Vector3d[] BASE_VERTICES = {
                new Vector3d(0, 0, 0),
                new Vector3d(1, 0, 0),
                new Vector3d(1, 0, 1),
                new Vector3d(0, 0, 1),
        };
        final Vector3d APEX_VERTEX = new Vector3d(0.5, 0, 0.5);

        IVertexBuilder vertexBuilderLines = renderBuffers.getBuffer(RenderHelper.MBE_LINE_DEPTH_WRITING_ON);
        // Note that, although RenderType.getLines() might appear to be suitable, it leads to weird rendering if used in
        // tile entity rendering, because it doesn't write to the depth buffer.  In other words, any object in the scene
        // which is drawn after the lines, will render over the top of the line (erase it) even if the object is behind
        //  the lines.  This means that RenderType.getLines() is only suitable for lines which are the last thing drawn in
        //  the scene (such as DrawBlockHighlightEvent)
        // The solution I used here is a custom RenderType for lines which does write to the depth buffer.

        Matrix4f matrixPos = matrixStack.getLast().getMatrix();  //retrieves the current transformation matrix
        // draw the base
        for (int i = 1; i < BASE_VERTICES.length; ++i) {
            drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[i-1], BASE_VERTICES[i]);
        }
        drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[BASE_VERTICES.length - 1], BASE_VERTICES[0]);

        // draw the sides (from the corners of the base to the apex)
        for (Vector3d baseVertex : BASE_VERTICES) {
            drawLine(matrixPos, vertexBuilderLines, color, APEX_VERTEX, baseVertex);
        }
    }

    private static void drawLine(Matrix4f matrixPos, IVertexBuilder renderBuffer,
                                 java.awt.Color color,
                                 Vector3d startVertex, Vector3d endVertex) {
        renderBuffer.pos(matrixPos, (float) startVertex.getX(), (float) startVertex.getY(), (float) startVertex.getZ())
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
                .endVertex();
        renderBuffer.pos(matrixPos, (float) endVertex.getX(), (float) endVertex.getY(), (float) endVertex.getZ())
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
                .endVertex();
    }

    // this should be true for tileentities which render globally (no render bounding box), such as beacons.
    @Override
    public boolean isGlobalRenderer(ShiftyTileEntity tileEntityMBE21)
    {
        return false;
    }

    private static final Logger LOGGER = LogManager.getLogger();
}