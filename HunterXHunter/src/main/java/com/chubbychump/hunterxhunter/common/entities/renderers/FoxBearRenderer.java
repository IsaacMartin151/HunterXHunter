package com.chubbychump.hunterxhunter.common.entities.renderers;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.FoxBear;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.ShiapoufClone;
import com.chubbychump.hunterxhunter.common.entities.models.FoxBearModel;
import com.chubbychump.hunterxhunter.common.entities.models.ShiapoufCloneModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxBearRenderer extends MobRenderer<FoxBear, FoxBearModel<FoxBear>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/bossbattle/shiapoufclone.png");

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
        int i = entitylivingbaseIn.getPhantomSize();
        float f = 1.0F + 0.15F * (float)i;
        matrixStackIn.scale(f, f, f);
        matrixStackIn.translate(0.0D, 1.3125D, 0.1875D);
    }

    protected void applyRotations(FoxBear entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}