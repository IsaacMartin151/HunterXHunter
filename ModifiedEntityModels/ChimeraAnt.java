// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class custom_model<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "custom_model"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart legs;

	public custom_model(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.legs = root.getChild("legs");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition neck_r1 = head.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(18, 0).addBox(-2.0F, -5.0F, 0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -14.0F, 2.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-6.0F, -8.0F, 1.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -15.0F, -3.0F, -0.7854F, -0.6545F, 1.5708F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -16.0F, -3.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition mouthpincer2_r1 = mouth.addOrReplaceChild("mouthpincer2_r1", CubeListBuilder.create().texOffs(7, 0).addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition mouthpincer1_r1 = mouth.addOrReplaceChild("mouthpincer1_r1", CubeListBuilder.create().texOffs(7, 0).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition antenna = head.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -25.0F, 0.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition antenna2_r1 = antenna.addOrReplaceChild("antenna2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition antenna1_r1 = antenna.addOrReplaceChild("antenna1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 2.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, -14.0F, 0.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = torso.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-4.5F, -5.0F, 0.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -5.0F, 2.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition shoulders = torso.addOrReplaceChild("shoulders", CubeListBuilder.create().texOffs(0, 19).addBox(-8.0F, -15.0F, -0.3F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(1.0F, -15.0F, -0.3F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arms = torso.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -1.0F));

		PartDefinition arm2_r1 = arms.addOrReplaceChild("arm2_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-13.0F, -6.0F, 0.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leg2_r1 = legs.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(25, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -5.0F, 3.0F, -0.4363F, 0.1309F, 0.1745F));

		PartDefinition leg1_r1 = legs.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(25, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 3.0F, 0.3491F, -0.1309F, -0.1745F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}