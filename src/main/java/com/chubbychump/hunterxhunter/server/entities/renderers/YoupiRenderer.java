package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.Youpi;
import com.chubbychump.hunterxhunter.server.entities.models.YoupiModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YoupiRenderer extends MobRenderer<Youpi, YoupiModel<Youpi>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/bossbattle/youpi.png");

    public YoupiRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new YoupiModel(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTexture(Youpi entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(Youpi entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 2.0F;
        matrixStackIn.scale(f, f, f);
    }

    protected void applyRotations(Youpi entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}