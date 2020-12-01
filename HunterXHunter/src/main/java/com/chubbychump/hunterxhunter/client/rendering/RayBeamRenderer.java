package com.chubbychump.hunterxhunter.client.rendering;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.models.RayBeamEntityModel;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import java.util.Random;

public class RayBeamRenderer extends EntityRenderer<EntityRayBeam> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(HunterXHunter.MOD_ID, "textures/entity/raybeam.png");
    public RayBeamRenderer(EntityRendererManager manager) {
        super(manager);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private static float diffFunction(long time, long delta, float scale) {
        long dt = time % (delta * 2);
        if (dt > delta) {
            dt = 2*delta - dt;
        }
        return dt * scale;
    }

    @Override
    public void render(EntityRayBeam entityIn, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURE);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucent());

        Random rnd = new Random((long) (entityIn.getPosX() * 337L + entityIn.getPosY() * 37L + entityIn.getPosZ() * 13L));



        long time = System.currentTimeMillis();
        float dx1 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx2 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx3 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx4 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy1 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy2 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy3 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy4 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);

        double speed = 4;
        float angle = (time / (int)speed) % 360;
        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);
        float scale = 1.0f + diffFunction(time,900 + rnd.nextInt(800), 0.0001f + rnd.nextFloat() * 0.001f);

        matrixStack.push();
        matrixStack.translate(.5, .5, .5);
        matrixStack.rotate(rotation);
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(-.5, -.5, -.5);

        add(builder, matrixStack, 0 + dx1, 0 + dy1, .5f, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStack, 1 - dx2, 0 + dy2, .5f, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, 1 - dx3, 1 - dy3, .5f, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, 0 + dx4, 1 - dy4, .5f, sprite.getMinU(), sprite.getMaxV());

        add(builder, matrixStack, 0 + dx4, 1 - dy4, .5f, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStack, 1 - dx3, 1 - dy3, .5f, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStack, 1 - dx2, 0 + dy2, .5f, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStack, 0 + dx1, 0 + dy1, .5f, sprite.getMinU(), sprite.getMinV());

        matrixStack.pop();

        matrixStack.push();

        matrixStack.translate(0.5, 1.5, 0.5);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.DIAMOND);
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, entityIn.world, null);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, bufferIn, packedLightIn, (int) entityYaw, ibakedmodel);

        matrixStack.translate(-.5, 1, -.5);
        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        BlockState state = Blocks.ENDER_CHEST.getDefaultState();
        blockRenderer.renderBlock(state, matrixStack, bufferIn, packedLightIn, (int) entityYaw, EmptyModelData.INSTANCE);

        matrixStack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(EntityRayBeam entity) {
        return TEXTURE;
    }
}