package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser.updateServer;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

@OnlyIn(Dist.CLIENT)
public class PuzzleScreen extends Screen {
    private Button modButton;
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private int timer = 60;
    private int initialtimer;
    private long timing = Util.milliTime();
    private boolean success = false;
    private boolean failed = false;
    private int[] array = new int[15];
    private int bruh = 0;
    private int postpuzzleticks = 0;
    public ResourceLocation[] icons = new ResourceLocation[4];

    public PuzzleScreen(int q) {
        super(new StringTextComponent("Puzzle"));
        ResourceLocation sprite0 = new ResourceLocation( "hunterxhunter", "puzzles/gon.png");
        ResourceLocation sprite1 = new ResourceLocation( "hunterxhunter", "puzzles/killua.png");
        ResourceLocation sprite2 = new ResourceLocation( "hunterxhunter", "puzzles/kurapika.png");
        ResourceLocation sprite3 = new ResourceLocation( "hunterxhunter", "puzzles/leorio.png");
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
            HunterXHunter.LOGGER.info("Swapped locations "+j+" and "+l);
        }
        //printArray();
        bruh = rand.nextInt(4);
        modButton = this.addButton(new Button(width/2, height/2 + 20, 50, 20, ITextComponent.getTextComponentOrEmpty("Confirm"), button -> {
            this.onClose();
            this.minecraft.displayGuiScreen(null);
            this.minecraft.setGameFocused(true);
        }));
        modButton.visible = false;
        Minecraft.getInstance().player.playSound(LOUNGE_MUSIC.get(), 1, 1);
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
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    private void drawTriangles(BufferBuilder buffer) {
        buffer.pos(width/2, height/2 + 20, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(width/2 -5, height/2, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(width/2 + 5, height/2, 0).color(1f, 1f, 1f, 1f).endVertex();
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        matrixStack.push();

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
        RenderSystem.color4f(1, 1, 1, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();

        if (success == true || failed == true) {
            //super.render(matrixStack, mouseX, mouseY, partialTicks);
            modButton.render(matrixStack, mouseX, mouseY, partialTicks);
            //blit(matrixStack, width, height, 0, 0, 16, 16);
            buffer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
            Quaternion rotation = Vector3f.ZP.rotationDegrees(72);
            for (int i = 0; i < 5; i++) {
                drawTriangles(buffer);
                //matrixStack.rotate(rotation);
            }
            tessellator.draw();



        }
        else {
            timer = initialtimer - (int) (Util.milliTime()/1000 - timing/1000);
            if (timer < 0) {
                failed = true;
            }
            fillGradient(matrixStack, 0, 0, width, height, 0, 10);

            buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            yo.bindTexture(icons[bruh]);
            RenderSystem.bindTexture(yo.getTexture(icons[bruh]).getGlTextureId());
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

                    buffer.pos(width / 7 + (i % 5) * Pxl, k * Pxl, 0).tex(u1, v1).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.pos(width / 7 + (i % 5) * Pxl, (k + 1) * Pxl, 0).tex(u1, v2).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.pos(width / 7 + ((i % 5) + 1) * Pxl, (k + 1) * Pxl, 0).tex(u2, v2).color(1f, 1f, 1f, 1f).endVertex();
                    buffer.pos(width / 7 + ((i % 5) + 1) * Pxl, k * Pxl, 0).tex(u2, v1).color(1f, 1f, 1f, 1f).endVertex();
                }
            }

            tessellator.draw();

            for (int i = 0; i < 15; i++) {
                if (array[i] != -1) {
                    int k = (int) Math.floor(i / 5);
                    int oof = getArrayIndex(width / 7 + (i % 5) * Pxl + 1, k * Pxl + 1);
                    String yeet = "" + array[oof];
                    font.drawStringWithShadow(matrixStack, yeet, width / 7 + (i % 5) * Pxl, k * Pxl, 0xffffffff);
                }
            }
            String timerstring = "" + timer;
            if (timer >= 20) {
                font.drawStringWithShadow(matrixStack, timerstring, 0, 0, 0x0000ff00);
            } else if (timer < 10) {
                font.drawStringWithShadow(matrixStack, timerstring, 0, 0, 0xffff00ff);
            } else if (timer < 20) {
                font.drawStringWithShadow(matrixStack, timerstring, 0, 0, 0x000000ff);
            }
        }
        matrixStack.pop();
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
        NenUser yo = minecraft.player.getCapability(NENUSER).orElseThrow(null);
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
                Minecraft.getInstance().player.sendChatMessage("Player failed three times, upgrading anyways");
                Minecraft.getInstance().player.playSound(WORLD_OF_ADVENTURES.get(), 1f, .5f);
            }
            Minecraft.getInstance().player.sendChatMessage("Failed their nen upgrade, fail counter is "+yo.getFailCounter());
        }
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
        if (success != true && failed != true) {
            int index = getArrayIndex(mouseX, mouseY);
            moveEmptyTile(index);
        }
        return true;
    }
}