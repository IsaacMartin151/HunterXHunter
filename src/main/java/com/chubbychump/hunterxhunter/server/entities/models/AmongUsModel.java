package com.chubbychump.hunterxhunter.server.entities.models;// Made with Blockbench 3.8.4
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.chubbychump.hunterxhunter.server.entities.entityclasses.AmongUs;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AmongUsModel<T extends AmongUs> extends EntityModel<AmongUs> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;

	public AmongUsModel() {
		textureWidth = 64;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.setTextureOffset(32, 12).addBox(-4.0F, -17.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false);
		body.setTextureOffset(56, 0).addBox(-1.5F, -15.0F, 4.0F, 3.0F, 8.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -9.0F, -1.0F);
		body.addChild(head);
		head.setTextureOffset(0, 0).addBox(-3.5F, -7.0F, -4.0F, 7.0F, 5.0F, 1.0F, 0.0F, false);

		leg0 = new ModelRenderer(this);
		leg0.setRotationPoint(-2.0F, -6.0F, 4.0F);
		body.addChild(leg0);
		leg0.setTextureOffset(18, 23).addBox(-1.4F, 1.0F, -6.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(2.0F, -6.0F, 4.0F);
		body.addChild(leg1);
		leg1.setTextureOffset(0, 23).addBox(-1.6F, 1.0F, -6.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(AmongUs entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
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