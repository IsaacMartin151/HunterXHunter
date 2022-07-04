package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.client.rendering.ObjectDrawingFunctions;
import com.chubbychump.hunterxhunter.server.entities.projectiles.EmitterBaseProjectile;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoGravityProjectileRenderer extends EntityRenderer<EmitterBaseProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/projectile.png");

    public NoGravityProjectileRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);

    }

    @Override
    public ResourceLocation getTextureLocation(EmitterBaseProjectile p_114482_) {
        return TEXTURE;
    }
}