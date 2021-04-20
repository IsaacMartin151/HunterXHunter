package com.chubbychump.hunterxhunter.common.entities.models;

import com.chubbychump.hunterxhunter.common.entities.entityclasses.Youpi;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class YoupiModel<T extends Youpi> extends EntityModel<Youpi> {
    private final ModelRenderer upperBodyPart1;
    private final ModelRenderer upperBodyPart2;
    private final ModelRenderer upperBodyPart3;
    private final ModelRenderer upperBodyPart3_r1;
    private final ModelRenderer upperBodyPart3_r2;
    private final ModelRenderer head1;
    private final ModelRenderer shoulders;
    private final ModelRenderer arms;
    private final ModelRenderer arm2_r1;
    private final ModelRenderer arm1_r1;
    private final ModelRenderer arms2;
    private final ModelRenderer arm2_r2;
    private final ModelRenderer arm1_r2;

    public YoupiModel() {
        textureWidth = 64;
        textureHeight = 64;

        upperBodyPart1 = new ModelRenderer(this);
        upperBodyPart1.setRotationPoint(0.0F, 24.0F, 0.0F);
        upperBodyPart1.setTextureOffset(23, 16).addBox(-4.0F, -20.1F, -2.5F, 8.0F, 3.0F, 4.0F, 0.0F, false);

        upperBodyPart2 = new ModelRenderer(this);
        upperBodyPart2.setRotationPoint(-2.0F, -17.1F, -0.5F);
        upperBodyPart1.addChild(upperBodyPart2);
        upperBodyPart2.setTextureOffset(3, 36).addBox(-1.0F, 0.0F, 0.0F, 6.0F, 10.0F, 2.0F, 0.0F, false);

        upperBodyPart3 = new ModelRenderer(this);
        upperBodyPart3.setRotationPoint(2.0F, 10.1F, 0.5F);
        upperBodyPart2.addChild(upperBodyPart3);


        upperBodyPart3_r1 = new ModelRenderer(this);
        upperBodyPart3_r1.setRotationPoint(0.0F, 7.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r1);
        setRotationAngle(upperBodyPart3_r1, 0.0F, 0.0F, -0.1745F);
        upperBodyPart3_r1.setTextureOffset(28, 26).addBox(2.0F, -7.0F, -1.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        upperBodyPart3_r2 = new ModelRenderer(this);
        upperBodyPart3_r2.setRotationPoint(0.0F, 7.0F, 0.0F);
        upperBodyPart3.addChild(upperBodyPart3_r2);
        setRotationAngle(upperBodyPart3_r2, 0.0F, 0.0F, 0.1745F);
        upperBodyPart3_r2.setTextureOffset(28, 26).addBox(-5.0F, -7.0F, -1.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        head1 = new ModelRenderer(this);
        head1.setRotationPoint(0.0F, -20.0F, 0.0F);
        upperBodyPart1.addChild(head1);
        head1.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        shoulders = new ModelRenderer(this);
        shoulders.setRotationPoint(0.0F, 0.0F, 0.0F);
        head1.addChild(shoulders);
        setRotationAngle(shoulders, -0.8727F, 0.0F, 0.0F);
        shoulders.setTextureOffset(32, 0).addBox(-8.0F, -1.0F, 0.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        shoulders.setTextureOffset(32, 0).addBox(4.0F, -1.0F, 1.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, 0.0F, 0.0F);
        shoulders.addChild(arms);


        arm2_r1 = new ModelRenderer(this);
        arm2_r1.setRotationPoint(0.0F, 7.0F, 0.0F);
        arms.addChild(arm2_r1);
        setRotationAngle(arm2_r1, 0.5672F, 0.0F, 0.6545F);
        arm2_r1.setTextureOffset(29, 38).addBox(-1.0F, -22.0F, 5.0716F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        arm1_r1 = new ModelRenderer(this);
        arm1_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        arms.addChild(arm1_r1);
        setRotationAngle(arm1_r1, 0.5672F, 0.0F, -0.6545F);
        arm1_r1.setTextureOffset(29, 38).addBox(-7.0F, -18.0F, 1.0716F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        arms2 = new ModelRenderer(this);
        arms2.setRotationPoint(0.0F, 0.0F, 0.0F);
        shoulders.addChild(arms2);
        setRotationAngle(arms2, 0.7418F, 0.0F, 0.0F);


        arm2_r2 = new ModelRenderer(this);
        arm2_r2.setRotationPoint(0.0F, 7.0F, 0.0F);
        arms2.addChild(arm2_r2);
        setRotationAngle(arm2_r2, 0.0F, 0.0F, -0.4363F);
        arm2_r2.setTextureOffset(29, 38).addBox(5.0F, 1.0F, -2.0F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        arm1_r2 = new ModelRenderer(this);
        arm1_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        arms2.addChild(arm1_r2);
        setRotationAngle(arm1_r2, 0.0F, 0.0F, 0.4363F);
        arm1_r2.setTextureOffset(29, 38).addBox(-7.0F, 7.0F, -3.0F, 4.0F, 15.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Youpi entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.push();
        matrixStack.translate(0, -1.7, 0);
        matrixStack.scale(2, 2, 2);

        upperBodyPart1.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}