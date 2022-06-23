package com.chubbychump.hunterxhunter.client.screens;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser.updateServer;

@OnlyIn(Dist.CLIENT)
public class CameraScreen extends Screen {
    public static CameraScreen cameraScreenInstance = new CameraScreen();
    protected CameraScreen() {
        super(new StringTextComponent("Screen9Camera"));
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        // Do absolutely nothing for right now
        //HunterXHunter.LOGGER.info("Rendering camera screen");
        //super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        return true;
    }
}
