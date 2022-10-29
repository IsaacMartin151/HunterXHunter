package com.screens;

import com.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Debug extends Screen {
    public Debug() {
        super(Component.literal("Debug"));
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float z) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 40, 16777215);

        LocalPlayer player =  Minecraft.getInstance().player;
        NenUser.getFromPlayer(player);

        super.render(poseStack, x, y, z);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
