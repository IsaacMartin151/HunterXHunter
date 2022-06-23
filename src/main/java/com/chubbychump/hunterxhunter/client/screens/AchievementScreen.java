package com.chubbychump.hunterxhunter.client.screens;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;
import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser.updateServer;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

@OnlyIn(Dist.CLIENT)
public class AchievementScreen extends Screen {
    private double p = Math.PI;
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private int timer = 60;
    private int initialtimer;
    private long timing = Util.milliTime();
    private boolean done = false;
    private ResourceLocation skin;
    private ITextComponent name;

    public AchievementScreen(int q, ResourceLocation s, ITextComponent n) {
        super(new StringTextComponent("Achievement"));
        this.skin = s;
        this.name = n;
        timer += q;
        initialtimer = timer;
        //Minecraft.getInstance().player.playSound(LOUNGE_MUSIC.get(), 1, 1);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.init(minecraft, width, height);
    }

    private void drawTriangles(BufferBuilder buffer, Matrix4f matrix) {
        int outerR = (int) (this.height/2.5);
        int innerR = this.height/5;
        for (int i = 0; i < 5; i++) {
            float oobruh = (float) (innerR * Math.cos(2 * p * i/5 + p/2 - p/5) + width/2);
            float oobruhy = (float) (innerR * Math.sin(2*p*i/5+p/2 - p/5) + height/2);
            float obruh = (float) (outerR * Math.cos(2 * p * i/5 + p/2) + width/2);
            float obruhy = (float) (outerR * Math.sin(2*p*i/5+p/2) + height/2);
            float bruh = (float) (innerR * Math.cos(2 * p * i/5 + p/2 + p/5) + width/2);
            float bruhy = (float) (innerR * Math.sin(2*p*i/5+p/2 + p/5) + height/2);
            buffer.pos(matrix, oobruh, oobruhy, 0).color(1f, 1f, 1f, 1f).tex(0, 0).endVertex();
            buffer.pos(matrix, obruh, obruhy, 0).color(1f, 1f, 1f, 1f).tex(.5f, 1).endVertex();
            buffer.pos(matrix, bruh, bruhy, 0).color(1f, 1f, 1f, 1f).tex(1, 0).endVertex();

        }
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //HunterXHunter.LOGGER.info("width, height: "+this.width+", "+this.height);
        matrixStack.push();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.enableTexture();
        RenderSystem.enableBlend();

        if (done) {
//            RenderSystem.disableCull();
//            matrixStack.push();
//
//            matrixStack.translate(this.width/2, this.height/2, 0);
//            matrixStack.rotate(Vector3f.ZP.rotationDegrees((Util.milliTime() / 60) % 360));
//            matrixStack.translate(-this.width/2, -this.height/2, 0);
//            buffer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_TEX);
//            yo.bindTexture(BUBBLES);
//            RenderSystem.bindTexture(yo.getTexture(BUBBLES).getGlTextureId());
//            drawTriangles(buffer, matrixStack.getLast().getMatrix());
//            tessellator.draw();
//            matrixStack.pop();
            super.render(matrixStack, mouseX, mouseY, partialTicks);

        }
        else {
            timer = initialtimer - (int) (Util.milliTime()/1000 - timing/1000);
            if (timer < 0) {
                done = true;
            }

            buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            yo.bindTexture(skin);
            RenderSystem.bindTexture(yo.getTexture(skin).getGlTextureId());
            int upperleftX = 0;
            int upperleftY = 0;
            int Pxl = width / 7;
//            for (int i = 0; i < 15; i++) {
//                if (array[i] != -1) {
//                    upperleftX = array[i] % 5;
//                    upperleftY = (int) Math.floor(array[i] / 5);
//
//                    int k = (int) Math.floor(i / 5);
//                    float u1 = upperleftX * 200 / 1000f;
//                    float u2 = (upperleftX + 1) * 200 / 1000f;
//                    float v1 = upperleftY * 200 / 600f;
//                    float v2 = (upperleftY + 1) * 200 / 600f;
//
//                    buffer.pos(width / 7 + (i % 5) * Pxl, k * Pxl, 0).tex(u1, v1).color(1f, 1f, 1f, 1f).endVertex();
//                    buffer.pos(width / 7 + (i % 5) * Pxl, (k + 1) * Pxl, 0).tex(u1, v2).color(1f, 1f, 1f, 1f).endVertex();
//                    buffer.pos(width / 7 + ((i % 5) + 1) * Pxl, (k + 1) * Pxl, 0).tex(u2, v2).color(1f, 1f, 1f, 1f).endVertex();
//                    buffer.pos(width / 7 + ((i % 5) + 1) * Pxl, k * Pxl, 0).tex(u2, v1).color(1f, 1f, 1f, 1f).endVertex();
//                }
//            }

            tessellator.draw();

//            for (int i = 0; i < 15; i++) {
//                if (array[i] != -1) {
//                    int k = (int) Math.floor(i / 5);
//                    int oof = getArrayIndex(width / 7 + (i % 5) * Pxl + 1, k * Pxl + 1);
//                    String yeet = "" + array[oof];
//                    font.drawStringWithShadow(matrixStack, yeet, width / 7 + (i % 5) * Pxl, k * Pxl, 0xffffffff);
//                }
//            }
        }
        matrixStack.pop();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}