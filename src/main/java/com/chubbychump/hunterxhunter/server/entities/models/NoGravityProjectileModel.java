package com.chubbychump.hunterxhunter.server.entities.models;// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NoGravityProjectileModel<T extends EmitterBaseProjectile> extends EntityModel<EmitterBaseProjectile> {
	private final ModelRenderer tricombo;
	private final ModelRenderer cube3_r1;
	private final ModelRenderer cube2_r1;
	private final ModelRenderer cube1_r1;

	public NoGravityProjectileModel() {
		textureWidth = 8;
		textureHeight = 4;

		tricombo = new ModelRenderer(this);
		tricombo.setRotationPoint(0.0F, 19.0F, 0.0F);
		

		cube3_r1 = new ModelRenderer(this);
		cube3_r1.setRotationPoint(0.0F, 4.0F, 0.0F);
		tricombo.addChild(cube3_r1);
		setRotationAngle(cube3_r1, -0.7854F, -0.7854F, 0.7854F);
		cube3_r1.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		cube2_r1 = new ModelRenderer(this);
		cube2_r1.setRotationPoint(3.0F, 0.0F, 0.0F);
		tricombo.addChild(cube2_r1);
		setRotationAngle(cube2_r1, -0.7854F, -0.7854F, 0.7854F);
		cube2_r1.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		cube1_r1 = new ModelRenderer(this);
		cube1_r1.setRotationPoint(-3.0F, -1.0F, 0.0F);
		tricombo.addChild(cube1_r1);
		setRotationAngle(cube1_r1, -0.7854F, -0.7854F, 0.7854F);
		cube1_r1.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(EmitterBaseProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		tricombo.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}