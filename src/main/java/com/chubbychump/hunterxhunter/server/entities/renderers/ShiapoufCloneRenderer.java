package com.chubbychump.hunterxhunter.server.entities.renderers;

import com.chubbychump.hunterxhunter.server.entities.entityclasses.ShiapoufClone;
import com.chubbychump.hunterxhunter.server.entities.models.ShiapoufCloneModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShiapoufCloneRenderer extends MobRenderer<ShiapoufClone, ShiapoufCloneModel<ShiapoufClone>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("hunterxhunter", "textures/entity/bossbattle/shiapoufclone.png");

    public ShiapoufCloneRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ShiapoufCloneModel(), 0.75F);
        //this.addLayer(new PhantomEyesLayer<>(this));
    }
}