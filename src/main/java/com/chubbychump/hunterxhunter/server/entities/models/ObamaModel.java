package com.chubbychump.hunterxhunter.server.entities.models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.chubbychump.hunterxhunter.server.entities.entityclasses.Obama;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ObamaModel<T extends Obama> extends EntityModel<Obama> {
	private final ModelRenderer bone4;
	private final ModelRenderer bone3;
	private final ModelRenderer bone2;
	private final ModelRenderer bone;
	private final ModelRenderer bb_main;

	public ObamaModel() {
		textureWidth = 24;
		textureHeight = 12;

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(6.0F, 24.0F, -6.0F);
		setRotationAngle(bone4, -0.5236F, 0.0F, -0.5672F);
		bone4.setTextureOffset(1, 0).addBox(-1.0F, -12.0F, 0.0F, 1.0F, 12.0F, 1.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(6.0F, 24.0F, 5.0F);
		setRotationAngle(bone3, 0.5236F, 0.0F, -0.5672F);
		bone3.setTextureOffset(1, 0).addBox(-1.0F, -11.0F, 0.0F, 1.0F, 11.0F, 1.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(-6.0F, 24.0F, -6.0F);
		setRotationAngle(bone2, -0.5236F, 0.0F, 0.5672F);
		bone2.setTextureOffset(1, 0).addBox(0.0F, -12.0F, 0.0F, 1.0F, 12.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-5.0F, 24.0F, 5.0F);
		setRotationAngle(bone, 0.5236F, 0.0F, 0.5672F);
		bone.setTextureOffset(1, 0).addBox(-1.0F, -11.0F, 0.0F, 1.0F, 11.0F, 1.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Obama entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone4.render(matrixStack, buffer, packedLight, packedOverlay);
		bone3.render(matrixStack, buffer, packedLight, packedOverlay);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}