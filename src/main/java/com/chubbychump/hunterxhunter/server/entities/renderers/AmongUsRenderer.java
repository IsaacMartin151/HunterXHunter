package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.AmongUs;
import com.chubbychump.hunterxhunter.server.entities.models.AmongUsModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.renderer.entity.MobRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AmongUsRenderer extends MobRenderer<AmongUs, AmongUsModel<AmongUs>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/among_us.png");

    public AmongUsRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AmongUsModel<>(), 0.75F);
    }

    @Override
    public ResourceLocation getTexture(AmongUs entity) {
        return TEXTURE;
    }
}