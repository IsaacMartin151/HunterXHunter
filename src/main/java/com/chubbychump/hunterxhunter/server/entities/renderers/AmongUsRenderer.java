package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.AmongUs;
import com.chubbychump.hunterxhunter.server.entities.models.AmongUsModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AmongUsRenderer extends MobRenderer<AmongUs, AmongUsModel<AmongUs>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/among_us.png");

    public AmongUsRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AmongUsModel<>(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(AmongUs entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(AmongUs entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        //float f = 2F;
        //matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(AmongUs entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}