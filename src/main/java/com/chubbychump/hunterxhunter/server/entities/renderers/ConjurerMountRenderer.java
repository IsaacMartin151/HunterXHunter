package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.ConjurerMount;
import com.chubbychump.hunterxhunter.server.entities.models.ConjurerMountModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConjurerMountRenderer extends MobRenderer<ConjurerMount, ConjurerMountModel<ConjurerMount>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/conjurer_mount.png");

    public ConjurerMountRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ConjurerMountModel<>(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTexture(ConjurerMount entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(ConjurerMount entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 1.0F + 0.15F;
        matrixStackIn.scale(f, f, f);
        //matrixStackIn.translate(0.0D, 1.3125D, 0.1875D);

    }

    protected void applyRotations(ConjurerMount entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        //matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}