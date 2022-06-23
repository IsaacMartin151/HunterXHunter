package com.chubbychump.hunterxhunter.common.entities.renderers;

import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.common.entities.projectiles.EmitterBaseProjectile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoGravityProjectileRenderer extends EntityRenderer<EmitterBaseProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/projectile.png");

    public NoGravityProjectileRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(EmitterBaseProjectile entity) {
        return TEXTURE;
    }

    public void render(EmitterBaseProjectile entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        ObjectDrawingFunctions.ProjectileRender(matrixStackIn, entityIn.ticksExisted, entityIn);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }




}