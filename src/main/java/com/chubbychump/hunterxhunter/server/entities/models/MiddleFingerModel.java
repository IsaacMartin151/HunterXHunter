package com.chubbychump.hunterxhunter.server.entities.models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.chubbychump.hunterxhunter.server.entities.entityclasses.MiddleFinger;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MiddleFingerModel<T extends MiddleFinger> extends EntityModel<MiddleFinger> {
	private final ModelRenderer bone3;
	private final ModelRenderer bone;
	private final ModelRenderer upperjoints;
	private final ModelRenderer fingers;
	private final ModelRenderer bone2;

	public MiddleFingerModel() {
		textureWidth = 64;
		textureHeight = 64;

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-1.0F, 24.0F, 1.0F);
		setRotationAngle(bone3, 0.1309F, 0.0F, 0.1309F);
		bone3.setTextureOffset(0, 0).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 7.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-2.5F, 20.0F, -0.2F);
		setRotationAngle(bone, 0.2618F, 0.0F, -0.48F);
		bone.setTextureOffset(0, 0).addBox(-1.5F, -1.7F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		upperjoints = new ModelRenderer(this);
		upperjoints.setRotationPoint(0.0F, 16.0F, 1.0F);
		setRotationAngle(upperjoints, 0.1745F, 0.0F, 0.0F);
		upperjoints.setTextureOffset(0, 0).addBox(2.0F, -2.4F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		upperjoints.setTextureOffset(0, 0).addBox(-2.7F, -3.3F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		upperjoints.setTextureOffset(0, 0).addBox(0.3F, -3.5F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		fingers = new ModelRenderer(this);
		fingers.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(fingers, -0.0436F, 0.0F, 0.0F);
		fingers.setTextureOffset(0, 12).addBox(-4.5F, -8.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		fingers.setTextureOffset(4, 12).addBox(-2.7F, -12.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		fingers.setTextureOffset(0, 0).addBox(-1.2F, -12.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		fingers.setTextureOffset(4, 12).addBox(0.3F, -12.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		fingers.setTextureOffset(0, 12).addBox(1.8F, -11.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 12.0F, 1.0F);
		setRotationAngle(bone2, -0.1745F, 0.0F, 0.0F);
		bone2.setTextureOffset(0, 0).addBox(-1.2F, -3.0F, -0.8F, 1.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(MiddleFinger entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone3.render(matrixStack, buffer, packedLight, packedOverlay);
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		upperjoints.render(matrixStack, buffer, packedLight, packedOverlay);
		fingers.render(matrixStack, buffer, packedLight, packedOverlay);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}