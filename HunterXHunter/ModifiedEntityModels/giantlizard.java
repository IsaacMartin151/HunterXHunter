// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class custom extends EntityModel<Entity> {
	private final ModelRenderer rearFootLeft;
	private final ModelRenderer rearFootRight;
	private final ModelRenderer haunchLeft;
	private final ModelRenderer haunchRight;
	private final ModelRenderer body;
	private final ModelRenderer frontLegLeft;
	private final ModelRenderer frontLegRight;
	private final ModelRenderer head;

	public custom() {
		textureWidth = 128;
		textureHeight = 64;

		rearFootLeft = new ModelRenderer(this);
		rearFootLeft.setRotationPoint(4.0F, 24.0F, 7.0F);
		setRotationAngle(rearFootLeft, 0.0F, -0.4363F, 0.0F);
		rearFootLeft.setTextureOffset(32, 31).addBox(0.0F, -4.0F, -7.0F, 4.0F, 4.0F, 14.0F, 0.0F, true);

		rearFootRight = new ModelRenderer(this);
		rearFootRight.setRotationPoint(-2.0F, 24.0F, 7.0F);
		setRotationAngle(rearFootRight, 0.0F, 0.4363F, 0.0F);
		rearFootRight.setTextureOffset(32, 31).addBox(-6.0F, -4.0F, -7.0F, 4.0F, 4.0F, 14.0F, 0.0F, true);

		haunchLeft = new ModelRenderer(this);
		haunchLeft.setRotationPoint(3.0F, 17.5F, 3.7F);
		setRotationAngle(haunchLeft, -0.3491F, 0.0F, 0.0F);
		haunchLeft.setTextureOffset(71, 28).addBox(1.0F, -6.5F, 3.7F, 4.0F, 8.0F, 10.0F, 0.0F, true);

		haunchRight = new ModelRenderer(this);
		haunchRight.setRotationPoint(-3.0F, 17.5F, 3.7F);
		setRotationAngle(haunchRight, -0.3491F, 0.0F, 0.0F);
		haunchRight.setTextureOffset(70, 28).addBox(-5.0F, -6.5F, 3.7F, 4.0F, 8.0F, 10.0F, 0.0F, true);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 19.0F, 8.0F);
		setRotationAngle(body, -0.3491F, 0.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-6.0F, -9.0F, -12.0F, 12.0F, 10.0F, 20.0F, 0.0F, true);

		frontLegLeft = new ModelRenderer(this);
		frontLegLeft.setRotationPoint(3.0F, 17.0F, -1.0F);
		setRotationAngle(frontLegLeft, -0.5672F, 0.0F, 0.0F);
		frontLegLeft.setTextureOffset(16, 30).addBox(1.0F, -7.0F, -3.0F, 4.0F, 16.0F, 4.0F, 0.0F, true);

		frontLegRight = new ModelRenderer(this);
		frontLegRight.setRotationPoint(-3.0F, 17.0F, -1.0F);
		setRotationAngle(frontLegRight, -0.5672F, 0.0F, 0.0F);
		frontLegRight.setTextureOffset(0, 30).addBox(-5.0F, -7.0F, -3.0F, 4.0F, 16.0F, 4.0F, 0.0F, true);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 16.0F, -1.0F);
		head.setTextureOffset(68, 0).addBox(-7.5F, -20.0F, -11.0F, 15.0F, 12.0F, 15.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		rearFootLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		rearFootRight.render(matrixStack, buffer, packedLight, packedOverlay);
		haunchLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		haunchRight.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		frontLegLeft.render(matrixStack, buffer, packedLight, packedOverlay);
		frontLegRight.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}