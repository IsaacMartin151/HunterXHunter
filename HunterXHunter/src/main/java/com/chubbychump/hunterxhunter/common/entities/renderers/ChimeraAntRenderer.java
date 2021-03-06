package com.chubbychump.hunterxhunter.common.entities.renderers;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.ChimeraAnt;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.FoxBear;
import com.chubbychump.hunterxhunter.common.entities.models.ChimeraAntModel;
import com.chubbychump.hunterxhunter.common.entities.models.FoxBearModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChimeraAntRenderer extends MobRenderer<ChimeraAnt, ChimeraAntModel<ChimeraAnt>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/chimeraant.png");

    public ChimeraAntRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ChimeraAntModel<>(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(ChimeraAnt entity) {
        return TEXTURE;
    }

    protected void preRenderCallback(ChimeraAnt entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {

    }

    protected void applyRotations(ChimeraAnt entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityLiving.rotationPitch));
    }
}