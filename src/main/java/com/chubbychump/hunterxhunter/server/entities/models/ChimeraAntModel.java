package com.chubbychump.hunterxhunter.server.entities.models;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.ChimeraAnt;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ChimeraAntModel<T extends ChimeraAnt> extends EntityModel<ChimeraAnt> {
    private final ModelRenderer head;
    private final ModelRenderer neck_r1;
    private final ModelRenderer head_r1;
    private final ModelRenderer mouth;
    private final ModelRenderer mouthpincer2_r1;
    private final ModelRenderer mouthpincer1_r1;
    private final ModelRenderer antenna;
    private final ModelRenderer antenna2_r1;
    private final ModelRenderer antenna1_r1;
    private final ModelRenderer body;
    private final ModelRenderer torso;
    private final ModelRenderer tail_r1;
    private final ModelRenderer shoulders;
    private final ModelRenderer arms;
    private final ModelRenderer arm2_r1;
    private final ModelRenderer legs;
    private final ModelRenderer leg2_r1;
    private final ModelRenderer leg1_r1;
    private final float PI = (float) Math.PI;
    private final float CYCLE_FREQUENCY = 2f;
    protected float[][] animationCycle = new float[][]
            {
                    {   0F    , 0F     , 0F },
                    {   PI/18F, PI/16f , 0F },
                    {   PI/9F , PI/8f  , 0F },
                    {   PI/18F, PI/16f , 0F },
                    {   0F    , 0f     , 0F },
                    {  -PI/18F, -PI/16f, 0F },
                    {  -PI/9F , -PI/8f , 0F },
                    {  -PI/18F, -PI/16f, 0F },
            };

    public ChimeraAntModel() {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);


        neck_r1 = new ModelRenderer(this);
        neck_r1.setRotationPoint(0.5F, -14.0F, 2.0F);
        head.addChild(neck_r1);
        setRotationAngle(neck_r1, 0.3927F, 0.0F, 0.0F);
        neck_r1.setTextureOffset(18, 0).addBox(-2.0F, -5.0F, 0.5F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        head_r1 = new ModelRenderer(this);
        head_r1.setRotationPoint(-1.0F, -15.0F, -3.0F);
        head.addChild(head_r1);
        setRotationAngle(head_r1, -0.7854F, -0.6545F, 1.5708F);
        head_r1.setTextureOffset(0, 7).addBox(-6.0F, -8.0F, 1.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        mouth = new ModelRenderer(this);
        mouth.setRotationPoint(-2.0F, -16.0F, -3.0F);
        head.addChild(mouth);
        setRotationAngle(mouth, -0.48F, 0.0F, 0.0F);


        mouthpincer2_r1 = new ModelRenderer(this);
        mouthpincer2_r1.setRotationPoint(0.0F, -1.0F, -1.0F);
        mouth.addChild(mouthpincer2_r1);
        setRotationAngle(mouthpincer2_r1, 0.0F, 0.0F, -0.3054F);
        mouthpincer2_r1.setTextureOffset(7, 0).addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        mouthpincer1_r1 = new ModelRenderer(this);
        mouthpincer1_r1.setRotationPoint(3.0F, -1.0F, -1.0F);
        mouth.addChild(mouthpincer1_r1);
        setRotationAngle(mouthpincer1_r1, 0.0F, 0.0F, 0.3054F);
        mouthpincer1_r1.setTextureOffset(7, 0).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        antenna = new ModelRenderer(this);
        antenna.setRotationPoint(0.0F, -25.0F, 0.0F);
        head.addChild(antenna);
        setRotationAngle(antenna, 1.0908F, 0.0F, 0.0F);


        antenna2_r1 = new ModelRenderer(this);
        antenna2_r1.setRotationPoint(0.0F, 1.0F, 0.0F);
        antenna.addChild(antenna2_r1);
        setRotationAngle(antenna2_r1, 0.0F, 0.0F, -0.4363F);
        antenna2_r1.setTextureOffset(0, 0).addBox(-2.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        antenna1_r1 = new ModelRenderer(this);
        antenna1_r1.setRotationPoint(0.0F, 1.0F, 0.0F);
        antenna.addChild(antenna1_r1);
        setRotationAngle(antenna1_r1, 0.0F, 0.0F, 0.4363F);
        antenna1_r1.setTextureOffset(0, 0).addBox(1.0F, -7.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 2.0F);


        torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(torso);
        torso.setTextureOffset(0, 27).addBox(-2.5F, -14.0F, 0.0F, 4.0F, 8.0F, 2.0F, 0.0F, false);

        tail_r1 = new ModelRenderer(this);
        tail_r1.setRotationPoint(1.0F, -5.0F, 2.0F);
        torso.addChild(tail_r1);
        setRotationAngle(tail_r1, -0.2182F, 0.0F, 0.0F);
        tail_r1.setTextureOffset(25, 0).addBox(-4.5F, -5.0F, 0.0F, 6.0F, 5.0F, 8.0F, 0.0F, false);

        shoulders = new ModelRenderer(this);
        shoulders.setRotationPoint(0.0F, 0.0F, 0.0F);
        torso.addChild(shoulders);
        shoulders.setTextureOffset(0, 19).addBox(-8.0F, -15.0F, -0.3F, 6.0F, 5.0F, 3.0F, 0.0F, false);
        shoulders.setTextureOffset(0, 47).addBox(1.0F, -15.0F, -0.3F, 6.0F, 5.0F, 3.0F, 0.0F, false);

        arms = new ModelRenderer(this);
        arms.setRotationPoint(0.0F, -7.0F, -1.0F);
        torso.addChild(arms);


        arm2_r1 = new ModelRenderer(this);
        arm2_r1.setRotationPoint(6.0F, 0.0F, 0.0F);
        arms.addChild(arm2_r1);
        setRotationAngle(arm2_r1, -0.2618F, 0.0F, 0.0F);
        arm2_r1.setTextureOffset(0, 38).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 2.0F, 0.0F, false);
        arm2_r1.setTextureOffset(0, 38).addBox(-13.0F, -6.0F, 0.0F, 3.0F, 6.0F, 2.0F, 0.0F, false);

        legs = new ModelRenderer(this);
        legs.setRotationPoint(0.0F, 24.0F, 0.0F);

        leg2_r1 = new ModelRenderer(this);
        leg2_r1.setRotationPoint(-1.0F, 0.0F, 2.0F);
        legs.addChild(leg2_r1);
        setRotationAngle(leg2_r1, 0.0F, 0.1309F, 0.1745F);
        leg2_r1.setTextureOffset(25, 14).addBox(-4.0F, -6.5F, -0.5F, 2.0F, 7.0F, 3.0F, 0.0F, false);

        leg1_r1 = new ModelRenderer(this);
        leg1_r1.setRotationPoint(3.0F, 0.0F, 2.0F);
        legs.addChild(leg1_r1);
        setRotationAngle(leg1_r1, 0.0F, -0.1309F, -0.1745F);
        leg1_r1.setTextureOffset(25, 14).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 3.0F, 0.0F, false);

    }

    @Override
    public void setRotationAngles(ChimeraAnt entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
        // TODO: body rotation 15 to -15 in the y direction
        //entity.rotationPitch = 0;
        if (moved(entity)) {
            //HunterXHunter.LOGGER.info("Entity moved, total distance moved is "+entity.getDistanceMoved());
            entity.cycleIndex = (int) (entity.getDistanceMoved() / 5 * CYCLE_FREQUENCY) % 8;
            //HunterXHunter.LOGGER.info("cycle index is "+entity.cycleIndex);
        }
        else {
            entity.cycleIndex = 0;
        }
        //HunterXHunter.LOGGER.info("angles are: leg1 "+animationCycle[entity.cycleIndex][0]+", "+animationCycle[entity.cycleIndex][1]);
        leg1_r1.rotateAngleX = animationCycle[entity.cycleIndex][0];
        body.rotateAngleY = animationCycle[entity.cycleIndex][1];
        leg2_r1.rotateAngleX = -animationCycle[entity.cycleIndex][0];
    }

    public boolean moved(ChimeraAnt entity) {
        if (entity.getDistanceMoved() != entity.distanceAnimation ) {
            entity.distanceAnimation = entity.getDistanceMoved();
            return true;
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.push();
        matrixStack.translate(0, -1.6, 0);
        matrixStack.scale(2, 2, 2);

        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        legs.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}