package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.Obama;
import com.chubbychump.hunterxhunter.server.entities.models.ObamaModel;

import com.chubbychump.hunterxhunter.server.entities.projectiles.EmitterBaseProjectile;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ObamaRenderer extends MobRenderer<Obama, ObamaModel<Obama>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/obama.png");

    public ObamaRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ObamaModel(), .75f);
    }

    @Override
    public ResourceLocation getTextureLocation(Obama p_114482_) {
        return TEXTURE;
    }
    
}