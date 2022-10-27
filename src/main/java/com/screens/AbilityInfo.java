package com.screens;

import com.example.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbilityInfo extends Screen {
    public AbilityInfo() {
        super(Component.literal("Ability Info"));
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float z) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 40, 16777215);

        super.render(poseStack, x, y, z);
    }
}
