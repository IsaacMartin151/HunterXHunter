package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.FoxBear;
import com.chubbychump.hunterxhunter.server.entities.models.FoxBearModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxBearRenderer extends MobRenderer<FoxBear, FoxBearModel<FoxBear>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/foxbear.png");

    public FoxBearRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FoxBearModel(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(FoxBear entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(FoxBear entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        //float f = 2F;
        //matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(FoxBear entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}