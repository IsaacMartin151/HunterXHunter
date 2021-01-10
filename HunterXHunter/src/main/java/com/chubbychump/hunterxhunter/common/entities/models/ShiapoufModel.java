package com.chubbychump.hunterxhunter.common.entities.models;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.Shiapouf;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ShiapoufModel<T extends Shiapouf> extends EntityModel<Shiapouf> {
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
    private final ModelRenderer ear2_r2;
    private final ModelRenderer ear2_r3;
    private final ModelRenderer ear1_r1;

    public ShiapoufModel() {
        textureWidth = 64;
        textureHeight = 64;

        upperBodyPart1 = new ModelRenderer(this);
        upperBodyPart1.setRotationPoint(0.0F, 24.0F, 0.0F);


        upperBodyPart2 = new ModelRenderer(this);
        upperBodyPart2.setRotationPoint(-2.0F, -17.1F, -0.5F);
        upperBodyPart1.addChild(upperBodyPart2);
        upperBodyPart2.setTextureOffset(3, 36).addBox(-1.0F, -11.0F, 0.0F, 6.0F, 17.0F, 2.0F, 0.0F, false);

        upperBodyPart3 = new ModelRenderer(this);
        upperBodyPart3.setRotationPoint(2.0F, 10.1F, 0.5F);
        upperBodyPart2.addChild(upperBodyPart3);


        upperBodyPart3_r1 = new ModelRenderer(this);
        upperBodyPart3_r1.setRotationPoint(0.0F, 4.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r1);
        setRotationAngle(upperBodyPart3_r1, 0.0F, 0.0F, -0.0436F);
        upperBodyPart3_r1.setTextureOffset(28, 26).addBox(1.5F, -9.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        upperBodyPart3_r2 = new ModelRenderer(this);
        upperBodyPart3_r2.setRotationPoint(0.0F, 4.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r2);
        setRotationAngle(upperBodyPart3_r2, 0.0F, 0.0F, 0.0436F);
        upperBodyPart3_r2.setTextureOffset(28, 26).addBox(-3.5F, -9.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, true);

        head1 = new ModelRenderer(this);
        head1.setRotationPoint(0.0F, -20.0F, 0.0F);
        upperBodyPart1.addChild(head1);
        head1.setTextureOffset(0, 0).addBox(-4.0F, -18.0F, -3.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

        shoulders = new ModelRenderer(this);
        shoulders.setRotationPoint(0.0F, 0.0F, 0.0F);
        head1.addChild(shoulders);
        setRotationAngle(shoulders, -0.3491F, 0.0F, 0.0F);


        shoulder2_r1 = new ModelRenderer(this);
        shoulder2_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
        shoulders.addChild(shoulder2_r1);
        setRotationAngle(shoulder2_r1, 0.0F, 0.0F, -0.1745F);
        shoulder2_r1.setTextureOffset(32, 0).addBox(7.0F, -27.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        shoulder1_r1 = new ModelRenderer(this);
        shoulder1_r1.setRotationPoint(0.0F, 20.0F, 0.0F);
        shoulders.addChild(shoulder1_r1);
        setRotationAngle(shoulder1_r1, 0.0F, 0.0F, 0.1745F);
        shoulder1_r1.setTextureOffset(32, 0).addBox(-9.0F, -27.0F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, 0.0F, 0.0F);
        shoulders.addChild(arms);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -4.0F, 0.0F);
        setRotationAngle(head, 0.4363F, 0.0F, 0.0F);


        ear2_r1 = new ModelRenderer(this);
        ear2_r1.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear2_r1);
        setRotationAngle(ear2_r1, -0.0436F, 0.0F, 0.4363F);
        ear2_r1.setTextureOffset(41, 16).addBox(-6.5F, -21.5F, 4.8598F, 2.0F, 6.0F, 0.0F, 0.0F, true);

        ear2_r2 = new ModelRenderer(this);
        ear2_r2.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear2_r2);
        setRotationAngle(ear2_r2, 0.0436F, 0.0F, -0.4363F);
        ear2_r2.setTextureOffset(41, 16).addBox(4.5F, -21.5F, 5.8598F, 2.0F, 6.0F, 0.0F, 0.0F, true);

        ear2_r3 = new ModelRenderer(this);
        ear2_r3.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear2_r3);
        setRotationAngle(ear2_r3, 0.0F, 0.0F, -0.4363F);
        ear2_r3.setTextureOffset(41, 0).addBox(1.8F, -4.0F, 2.0F, 10.0F, 16.0F, 0.0F, 0.0F, true);

        ear1_r1 = new ModelRenderer(this);
        ear1_r1.setRotationPoint(0.0F, 8.0F, 0.0F);
        head.addChild(ear1_r1);
        setRotationAngle(ear1_r1, 0.0F, 0.0F, 0.48F);
        ear1_r1.setTextureOffset(41, 0).addBox(-12.0F, -4.0F, 2.0F, 10.0F, 16.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Shiapouf entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        upperBodyPart1.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}