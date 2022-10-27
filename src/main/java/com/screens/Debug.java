package com.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ScreenUtils;

@OnlyIn(Dist.CLIENT)
public class Debug extends Screen {
    public Debug() {
        super(Component.literal("Debug"));
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float z) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 40, 16777215);

        super.render(poseStack, x, y, z);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
