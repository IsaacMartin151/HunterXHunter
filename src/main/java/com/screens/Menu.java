package com.screens;

import com.example.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Menu extends Screen {
    private static final ResourceLocation HXH_LOGO = new ResourceLocation(HunterXHunter.MODID, "textures/gui/title/title.png");

    private long fadeInStart;

    public Menu() {
        super(Component.literal("Main Menu"));
    }

    @Override
    public void render(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_) {
        if (this.fadeInStart == 0L) {
            this.fadeInStart = Util.getMillis();
        }

        this.panorama.render(p_96742_, Mth.clamp(f, 0.0F, 1.0F));
        int j = this.width / 2 - 137;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(p_96739_, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        int l = Mth.ceil( 255.0F) << 24;
        if ((l & -67108864) != 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, HXH_LOGO);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            this.blitOutlineBlack(j, 30, (p_210862_, p_210863_) -> {
                this.blit(p_96739_, p_210862_ + 0, p_210863_, 0, 0, 155, 44);
                this.blit(p_96739_, p_210862_ + 155, p_210863_, 0, 45, 155, 44);
            });

            super.render(p_96739_, p_96740_, p_96741_, p_96742_);

        }
    }
}
