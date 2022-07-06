package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.MiddleFinger;
import com.chubbychump.hunterxhunter.server.entities.models.MiddleFingerModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MiddleFingerRenderer extends MobRenderer<MiddleFinger, MiddleFingerModel<MiddleFinger>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/middle_finger.png");

    public MiddleFingerRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MiddleFingerModel<>(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTexture(MiddleFinger entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(MiddleFinger entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        //float f = 2F;
        //matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(MiddleFinger entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}