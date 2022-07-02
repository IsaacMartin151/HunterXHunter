package com.chubbychump.hunterxhunter.server.entities.models;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.Neferpitou;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NeferpitouModel<T extends Neferpitou> extends EntityModel<Neferpitou> {
    private final ModelRenderer upperBodyPart1;
    private final ModelRenderer upperBodyPart2;
    private final ModelRenderer upperBodyPart3;
    private final ModelRenderer upperBodyPart3_r1;
    private final ModelRenderer upperBodyPart3_r2;
    private final ModelRenderer head1;
    private final ModelRenderer shoulders;
    private final ModelRenderer shoulder2_r1;
    private final ModelRenderer shoulder1_r1;
    private final ModelRenderer arms;
    private final ModelRenderer head;
    private final ModelRenderer ear2_r1;
    private final ModelRenderer ear1_r1;

    public NeferpitouModel() {
        textureWidth = 64;
        textureHeight = 64;

        upperBodyPart1 = new ModelRenderer(this);
        upperBodyPart1.setRotationPoint(0.0F, 24.0F, 0.0F);


        upperBodyPart2 = new ModelRenderer(this);
        upperBodyPart2.setRotationPoint(-2.0F, -17.1F, -0.5F);
        upperBodyPart1.addChild(upperBodyPart2);
        upperBodyPart2.setTextureOffset(3, 36).addBox(-1.0F, -3.0F, 0.0F, 6.0F, 13.0F, 2.0F, 0.0F, false);

        upperBodyPart3 = new ModelRenderer(this);
        upperBodyPart3.setRotationPoint(2.0F, 10.1F, 0.5F);
        upperBodyPart2.addChild(upperBodyPart3);


        upperBodyPart3_r1 = new ModelRenderer(this);
        upperBodyPart3_r1.setRotationPoint(0.0F, 4.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r1);
        setRotationAngle(upperBodyPart3_r1, 0.0F, 0.0F, -0.0873F);
        upperBodyPart3_r1.setTextureOffset(28, 26).addBox(1.5F, -7.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        upperBodyPart3_r2 = new ModelRenderer(this);
        upperBodyPart3_r2.setRotationPoint(0.0F, 4.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r2);
        setRotationAngle(upperBodyPart3_r2, 0.0F, 0.0F, 0.0873F);
        upperBodyPart3_r2.setTextureOffset(28, 26).addBox(-4.5F, -7.0F, -1.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        head1 = new ModelRenderer(this);
        head1.setRotationPoint(0.0F, -20.0F, 0.0F);
        upperBodyPart1.addChild(head1);
        head1.setTextureOffset(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

        shoulders = new ModelRenderer(this);
        shoulders.setRotationPoint(0.0F, 0.0F, 0.0F);
        head1.addChild(shoulders);
        setRotationAngle(shoulders, -0.3491F, 0.0F, 0.0F);


        shoulder2_r1 = new ModelRenderer(this);
        shoulder2_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
        shoulders.addChild(shoulder2_r1);
        setRotationAngle(shoulder2_r1, 0.0F, 0.0F, -0.1745F);
        shoulder2_r1.setTextureOffset(32, 0).addBox(6.0F, -20.0F, 1.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        shoulder1_r1 = new ModelRenderer(this);
        shoulder1_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
        shoulders.addChild(shoulder1_r1);
        setRotationAngle(shoulder1_r1, 0.0F, 0.0F, 0.1745F);
        shoulder1_r1.setTextureOffset(32, 0).addBox(-9.0F, -20.0F, 1.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, 0.0F, 0.0F);
        shoulders.addChild(arms);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -4.0F, 0.0F);


        ear2_r1 = new ModelRenderer(this);
        ear2_r1.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear2_r1);
        setRotationAngle(ear2_r1, 0.0F, 0.0F, -0.4363F);
        ear2_r1.setTextureOffset(32, 38).addBox(4.8F, -10.0F, 1.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);

        ear1_r1 = new ModelRenderer(this);
        ear1_r1.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear1_r1);
        setRotationAngle(ear1_r1, 0.0F, 0.0F, 0.48F);
        ear1_r1.setTextureOffset(29, 38).addBox(-10.0F, -10.0F, 1.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Neferpitou entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.push();
        matrixStack.translate(0, -1.6, 0);
        matrixStack.scale(2, 2, 2);

        upperBodyPart1.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}