package com.chubbychump.hunterxhunter.server.entities.models;// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.chubbychump.hunterxhunter.server.entities.entityclasses.ConjurerMount;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ConjurerMountModel<T extends ConjurerMount> extends EntityModel<ConjurerMount> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer right_ear;
	private final ModelRenderer left_ear;
	private final ModelRenderer leg_back_right;
	private final ModelRenderer leg_back_left;
	private final ModelRenderer leg_front_right;
	private final ModelRenderer leg_front_left;
	private final ModelRenderer leg_front_left_calf2;
	private final ModelRenderer leg_front_right_calf2;

	public ConjurerMountModel() {
		textureWidth = 128;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 5.0F, -3.0F);
		setRotationAngle(body, -0.1745F, 0.0F, 0.0F);
		body.setTextureOffset(0, 3).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 14.0F, 26.0F, 0.02F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -3.0F, -2.0F);
		body.addChild(head);
		setRotationAngle(head, 0.3491F, 0.0F, 0.0F);
		head.setTextureOffset(61, 1).addBox(-9.0F, -25.0F, -15.0F, 18.0F, 11.0F, 17.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-2.0F, -13.9507F, -2.867F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		right_ear = new ModelRenderer(this);
		right_ear.setRotationPoint(-7.0F, -5.0F, -2.0F);
		head.addChild(right_ear);
		setRotationAngle(right_ear, 0.0F, 0.0F, -0.8727F);
		right_ear.setTextureOffset(100, 41).addBox(5.0F, -15.0F, -1.0F, 8.0F, 2.0F, 6.0F, 0.0F, false);

		left_ear = new ModelRenderer(this);
		left_ear.setRotationPoint(7.0F, -5.0F, -2.0F);
		head.addChild(left_ear);
		setRotationAngle(left_ear, 0.0F, 0.0F, 0.8727F);
		left_ear.setTextureOffset(100, 31).addBox(-13.0F, -15.0F, -1.0F, 8.0F, 2.0F, 6.0F, 0.0F, false);

		leg_back_right = new ModelRenderer(this);
		leg_back_right.setRotationPoint(6.0F, 16.0F, 17.0F);
		setRotationAngle(leg_back_right, 0.1745F, 0.0F, 0.0F);
		leg_back_right.setTextureOffset(21, 45).addBox(-14.0F, -7.0F, -8.0F, 5.0F, 14.0F, 5.0F, 0.0F, false);

		leg_back_left = new ModelRenderer(this);
		leg_back_left.setRotationPoint(-6.0F, 16.0F, 17.0F);
		setRotationAngle(leg_back_left, 0.1745F, 0.0F, 0.0F);
		leg_back_left.setTextureOffset(0, 45).addBox(9.0F, -8.0F, -7.0F, 5.0F, 15.0F, 5.0F, 0.0F, false);

		leg_front_right = new ModelRenderer(this);
		leg_front_right.setRotationPoint(-6.0F, 12.0F, -3.0F);
		leg_front_right.setTextureOffset(66, 49).addBox(-4.0F, -5.0F, -10.0F, 7.0F, 8.0F, 7.0F, 0.0F, false);

		leg_front_left = new ModelRenderer(this);
		leg_front_left.setRotationPoint(6.0F, 12.0F, -3.0F);
		leg_front_left.setTextureOffset(108, 49).addBox(-15.0F, 2.0F, -9.0F, 5.0F, 10.0F, 5.0F, 0.0F, false);

		leg_front_left_calf2 = new ModelRenderer(this);
		leg_front_left_calf2.setRotationPoint(6.0F, 12.0F, -3.0F);
		leg_front_left_calf2.setTextureOffset(41, 49).addBox(-3.0F, -5.0F, -10.0F, 7.0F, 8.0F, 7.0F, 0.0F, false);

		leg_front_right_calf2 = new ModelRenderer(this);
		leg_front_right_calf2.setRotationPoint(21.0F, 12.0F, -3.0F);
		leg_front_right_calf2.setTextureOffset(108, 49).addBox(-17.0F, 2.0F, -9.0F, 5.0F, 10.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(ConjurerMount entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_back_right.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_back_left.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_front_right.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_front_left.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_front_left_calf2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_front_right_calf2.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}