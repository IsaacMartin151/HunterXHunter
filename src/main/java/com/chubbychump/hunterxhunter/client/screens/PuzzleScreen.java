package com.chubbychump.hunterxhunter.client.screens;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;
import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser.updateServer;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;
import static org.lwjgl.opengl.GL11.*;

@OnlyIn(Dist.CLIENT)
public class PuzzleScreen extends Screen {
    private Button modButton;
    private double p = Math.PI;
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private int timer = 60;
    private int initialtimer;
    private long timing = Util.getMillis();
    private boolean success = false;
    private boolean failed = false;
    private int[] array = new int[15];
    private int bruh = 0;
    private int postpuzzleticks = 0;
    public ResourceLocation[] icons = new ResourceLocation[4];
    public ResourceLocation BUBBLES = new ResourceLocation(MOD_ID, "textures/gui/bubbles.png");

    public PuzzleScreen(int q) {
        super(CommonComponents.EMPTY);
        //this.width = 1000;
        //this.height = 500;
        ResourceLocation sprite0 = new ResourceLocation( MOD_ID, "puzzles/gon.png");
        ResourceLocation sprite1 = new ResourceLocation( MOD_ID, "puzzles/killua.png");
        ResourceLocation sprite2 = new ResourceLocation( MOD_ID, "puzzles/kurapika.png");
        ResourceLocation sprite3 = new ResourceLocation( MOD_ID, "puzzles/leorio.png");
        icons[0] = sprite0;
        icons[1] = sprite1;
        icons[2] = sprite2;
        icons[3] = sprite3;
        timer += q;
        initialtimer = timer;
        //Allocate array;
        for (int i = 0; i < 15; i++) {
            array[i] = i;
        }
        printArray();
        array[14] = -1;
        Random rand = new Random();
        for (int z = 0; z < 200; z++) {
            int j = 0;
            for (int i = 0; i < 15; i++) {
                if (array[i] == -1) {
                    j = i;
                }
            }
            int k = rand.nextInt(4);
            int l = verifyRandomSwap(j, k);
            while (l == -1) {
                k = rand.nextInt(4);
                l = verifyRandomSwap(j, k);
            }

            int placeholder = array[j];
            array[j] = array[l];
            array[l] = placeholder;
            //HunterXHunter.LOGGER.info("Swapped locations "+j+" and "+l);
        }
        //printArray();
        bruh = rand.nextInt(4);
        Minecraft.getInstance().player.playSound(LOUNGE_MUSIC.get(), 1, 1);
    }

    @Override
    public void init() {
        modButton = this.addWidget(new Button(width/2 - 50, height/2 - 10, 100, 20, Component.literal("Confirm"), button -> {
            this.minecraft.popGuiLayer();
        }));
        modButton.visible = false;
        modButton.active = false;
    }

    private int verifyRandomSwap(int j, int k) {
        int l = -1;
        if (k == 0 && j+5 < 15) {
            l = j+5;
        }
        else if (k == 1 && j-5 >= 0) {
            l = j-5;
        }
        else if (k == 2 && (j%5)+1 != 5) {
            l = j+1;
        }
        else if (k == 3 && (j%5)-1 != -1) {
            l = j-1;
        }
        return l;
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.init(minecraft, width, height);
        if (success || failed) {
            modButton.active = true;
            modButton.visible = true;
        }
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
            buffer.vertex(matrix, oobruh, oobruhy, 0).color(1f, 1f, 1f, 1f).uv(0, 0).endVertex();
            buffer.vertex(matrix, obruh, obruhy, 0).color(1f, 1f, 1f, 1f).uv(.5f, 1).endVertex();
            buffer.vertex(matrix, bruh, bruhy, 0).color(1f, 1f, 1f, 1f).uv(1, 0).endVertex();

        }
    }


    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //HunterXHunter.LOGGER.info("width, height: "+this.width+", "+this.height);
        matrixStack.pushPose();
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        RenderSystem.enableTexture();
        RenderSystem.enableBlend();

        if (success == true || failed == true) {
            RenderSystem.disableCull();
            matrixStack.pushPose();

            matrixStack.translate(this.width/2, this.height/2, 0);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees((Util.getMillis() / 60) % 360));
            matrixStack.translate(-this.width/2, -this.height/2, 0);
            buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX);
            yo.bindForSetup(BUBBLES);
            RenderSystem.bindTexture(yo.getTexture(BUBBLES).getId());
            drawTriangles(buffer, matrixStack.last().pose());
            tessellator.end();
            matrixStack.popPose();

            if (success) {
                font.draw(matrixStack, "Success! Nen power increased", width/2 - 75, height/2 - 20, 0xffff80ff);
            }
            else {
                font.draw(matrixStack, "Fail. Better luck next time", width/2 - 60, height/2 - 20, 0xff8080ff);
            }
            super.render(matrixStack, mouseX, mouseY, partialTicks);

        }
        else {
            timer = initialtimer - (int) (Util.getMillis()/1000 - timing/1000);
            if (timer < 0) {
                failed = true;
                modButton.visible = true;
                modButton.active = true;
            }

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            yo.bindForSetup(icons[bruh]);
            RenderSystem.bindTexture(yo.getTexture(icons[bruh]).getId());
            int upperleftX = 0;
            int upperleftY = 0;
            int Pxl = width / 7;
            for (int i = 0; i < 15; i++) {
                if (array[i] != -1) {
                    upperleftX = array[i] % 5;
                    upperleftY = (int) Math.floor(array[i] / 5);

                    int k = (int) Math.floor(i / 5);
                    float u1 = upperleftX * 200 / 1000f;
                    float u2 = (upperleftX + 1) * 200 / 1000f;
                    float v1 = upperleftY * 200 / 600f;
                    float v2 = (upperleftY + 1) * 200 / 600f;

                    buffer.vertex(width / 7 + (i % 5) * Pxl, k * Pxl, 0).uv(u1, v1).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.vertex(width / 7 + (i % 5) * Pxl, (k + 1) * Pxl, 0).uv(u1, v2).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.vertex(width / 7 + ((i % 5) + 1) * Pxl, (k + 1) * Pxl, 0).uv(u2, v2).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.vertex(width / 7 + ((i % 5) + 1) * Pxl, k * Pxl, 0).uv(u2, v1).color(1f, 1f, 1f, 1f).endVertex();
                }
            }

            tessellator.end();

            for (int i = 0; i < 15; i++) {
                if (array[i] != -1) {
                    int k = (int) Math.floor(i / 5);
                    int oof = getArrayIndex(width / 7 + (i % 5) * Pxl + 1, k * Pxl + 1);
                    String yeet = "" + array[oof];
                    font.draw(matrixStack, yeet, width / 7 + (i % 5) * Pxl, k * Pxl, 0xffffffff);
                }
            }
            String timerstring = "" + timer;
            if (timer >= 20) {
                font.draw(matrixStack, timerstring, 0, 0, 0x0000ff00);
            } else if (timer < 10) {
                font.draw(matrixStack, timerstring, 0, 0, 0xffff00ff);
            } else if (timer < 20) {
                font.draw(matrixStack, timerstring, 0, 0, 0x000000ff);
            }
        }
        matrixStack.popPose();
    }

    private void printArray() {
        for (int i = 0; i < 15; i++) {
            HunterXHunter.LOGGER.info("i is "+i+", stored value is "+array[i]);
        }
    }

    private int getArrayIndex(double x, double y) {
        int Pxl = width/7;
        int rowOne = Pxl;
        int rowTwo = 2 * Pxl;
        int rowThree = 3 * Pxl;
        int columnOne = Pxl * 2;
        int columnTwo = Pxl * 3;
        int columnThree = Pxl * 4;
        int columnFour = Pxl * 5;
        int columnFive = Pxl * 6;
        if (x >= Pxl) {
            if (y <= rowOne) {
                if (x <= columnOne) {
                    return 0;
                }
                if (x <= columnTwo) {
                    return 1;
                }
                if (x <= columnThree) {
                    return 2;
                }
                if (x <= columnFour) {
                    return 3;
                }
                if (x <= columnFive) {
                    return 4;
                }
            }
            if (y <= rowTwo) {
                if (x <= columnOne) {
                    return 5;
                }
                if (x <= columnTwo) {
                    return 6;
                }
                if (x <= columnThree) {
                    return 7;
                }
                if (x <= columnFour) {
                    return 8;
                }
                if (x <= columnFive) {
                    return 9;
                }
            }
            if (y <= rowThree) {
                if (x <= columnOne) {
                    return 10;
                }
                if (x <= columnTwo) {
                    return 11;
                }
                if (x <= columnThree) {
                    return 12;
                }
                if (x <= columnFour) {
                    return 13;
                }
                if (x <= columnFive) {
                    return 14;
                }
            }
        }
        return -1;
    }

    @Override
    public void onClose() {
        super.onClose();
        INenUser yo = minecraft.player.getCapability(NENUSER).orElseThrow(null);
        if (success == true) {
            yo.setFailCounter(0);
            yo.increaseNenPower();
            Minecraft.getInstance().player.playSound(WORLD_OF_ADVENTURES.get(), 1f, 1f);
        }
        else {
            int j = yo.getFailCounter() + 1;
            yo.setFailCounter(j);
            if (j >= 3) {
                yo.setFailCounter(0);
                yo.increaseNenPower();
                Minecraft.getInstance().player.playSound(WORLD_OF_ADVENTURES.get(), 1f, .5f);
            }
        }
        Minecraft.getInstance().getSoundManager().stop(LOUNGE_MUSIC.getId(), null);
        updateServer(minecraft.player, yo);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private void swap(int i, int j) {
        this.minecraft.player.playSound(STONESLIDE.get(), 1, 1);
        int k = array[i];
        array[i] = array[j];
        array[j] = k;
        for (int z = 0; z < 14; z++) {
            if (array[z] != z) {
                return;
            }
        }

        success = true;
        postpuzzleticks = 1;
        modButton.visible = true;
        modButton.active = true;
    }

    private void moveEmptyTile(int index) {
        if (index == -1) {
            return;
        }
        if (index + 5 < 15) {
            if (array[index+5] == -1) {
                swap(index, index+5);
            }
        }
        if (index - 5 >= 0) {
            if (array[index - 5] == -1) {
                swap(index, index - 5);
            }
        }
        if ((index % 5) + 1 != 5) {
            if (array[index + 1] == -1) {
                swap(index, index + 1);
            }
        }
        if ((index % 5) - 1 != -1) {
            if (array[index - 1] == -1) {
                swap(index, index - 1);
            }
        }
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (success != true && failed != true) {
            int index = getArrayIndex(mouseX, mouseY);
            moveEmptyTile(index);
        }

        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}