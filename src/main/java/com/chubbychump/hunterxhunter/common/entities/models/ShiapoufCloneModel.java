package com.chubbychump.hunterxhunter.common.entities.models;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.ShiapoufClone;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShiapoufCloneModel<T extends Entity> extends EntityModel<ShiapoufClone> {
    private final ModelRenderer body;
    private final ModelRenderer rightwing_bone;
    private final ModelRenderer leftwing_bone;

    public ShiapoufCloneModel() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.5F, 19.0F, 0.0F);
        setRotationAngle(body, -1.4399F, 0.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-3.5F, -4.0F, -10.0F, 7.0F, 7.0F, 14.0F, 0.0F, false);
        body.setTextureOffset(2, 3).addBox(-2.5F, 0.0F, -13.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(2, 3).addBox(1.5F, 0.0F, -13.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(1.0F, -0.8167F, 4.0535F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(-2.0F, -0.8167F, 4.0535F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        rightwing_bone = new ModelRenderer(this);
        rightwing_bone.setRotationPoint(-1.5F, -4.0F, -3.0F);
        body.addChild(rightwing_bone);
        setRotationAngle(rightwing_bone, 0.0F, -0.1745F, 0.4363F);
        rightwing_bone.setTextureOffset(0, 54).addBox(-9.0F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, 0.0F, false);

        leftwing_bone = new ModelRenderer(this);
        leftwing_bone.setRotationPoint(1.5F, -4.0F, -3.0F);
        body.addChild(leftwing_bone);
        setRotationAngle(leftwing_bone, 0.0F, 0.1745F, -0.4363F);
        leftwing_bone.setTextureOffset(0, 54).addBox(0.0F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(ShiapoufClone entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}