package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.CameraEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CameraEntityRenderer extends EntityRenderer<CameraEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/projectile.png");

    public CameraEntityRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTexture(CameraEntity entity) {
        return TEXTURE;
    }

    public void render(CameraEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        // asdf
    }
}