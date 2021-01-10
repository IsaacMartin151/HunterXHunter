package com.chubbychump.hunterxhunter.common.entities.renderers;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.Shiapouf;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.Youpi;
import com.chubbychump.hunterxhunter.common.entities.models.ShiapoufModel;
import com.chubbychump.hunterxhunter.common.entities.models.YoupiModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShiapoufRenderer extends MobRenderer<Shiapouf, ShiapoufModel<Shiapouf>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/bossbattle/shiapouf.png");

    public ShiapoufRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShiapoufModel(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(Shiapouf entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(Shiapouf entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 2.0F;
        matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(Shiapouf entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}