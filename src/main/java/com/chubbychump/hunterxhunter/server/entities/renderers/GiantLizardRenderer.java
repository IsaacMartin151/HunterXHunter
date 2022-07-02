package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.GiantLizard;
import com.chubbychump.hunterxhunter.server.entities.models.GiantLizardModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantLizardRenderer extends MobRenderer<GiantLizard, GiantLizardModel<GiantLizard>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/giantlizard.png");

    public GiantLizardRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GiantLizardModel<>(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(GiantLizard entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(GiantLizard entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        //float f = 2F;
        //matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(GiantLizard entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}