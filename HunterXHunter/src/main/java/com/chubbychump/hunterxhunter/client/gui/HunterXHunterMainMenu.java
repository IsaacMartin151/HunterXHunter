package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.rendering.TextureHandler;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MinecartTickableSound;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.DEPARTURE;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.OSU;


public class HunterXHunterMainMenu extends MainMenuScreen {
    private float lastpartialticks;
    private MenuMusic bruhh = new MenuMusic();
    private static File file = new File(Minecraft.getInstance().getFileResourcePacks().getAbsolutePath()+"/departure.mp4");
    private static FFmpegFrameGrabber eff = new FFmpegFrameGrabber(file);
    private NativeImage eee = new NativeImage(640, 360, true);
    private TextureHandler handler;
    private NativeImage.PixelFormat oof = null;
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation MINECRAFT_TITLE_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    private final boolean showFadeInAnimation = false;
    private long firstRenderTime;

    public HunterXHunterMainMenu() {
        this(false);
    }
    public HunterXHunterMainMenu(boolean fadeIn) {
        //eee.PixelFormat.RGBA;
        this.oof = eee.getFormat();
        this.handler = new TextureHandler(Minecraft.getInstance().getTextureManager(), new DynamicTexture(eee));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        int i = 24;
        int j = this.height / 4 + 48;
        this.addSingleplayerMultiplayerButtons(j, 24);
        this.addButton(new ImageButton(this.width / 2 - 124, j + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_213090_1_) -> {
            this.minecraft.displayGuiScreen(new LanguageScreen(this, this.minecraft.gameSettings, this.minecraft.getLanguageManager()));
        }, new TranslationTextComponent("narrator.button.language")));
        this.addButton(new Button(this.width / 2 - 100, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.options"), (p_213096_1_) -> {
            this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
        }));
        this.addButton(new Button(this.width / 2 + 2, j + 72 + 12, 98, 20, new TranslationTextComponent("menu.quit"), (p_213094_1_) -> {
            this.minecraft.shutdown();
        }));
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int yIn, int rowHeightIn) {
        this.addButton(new Button(this.width / 100 - 100, yIn, 200, 20, new TranslationTextComponent("menu.singleplayer"), (p_213089_1_) -> {
            this.minecraft.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        boolean flag = this.minecraft.isMultiplayerEnabled();
        Button.ITooltip button$itooltip = flag ? Button.field_238486_s_ : (p_238659_1_, p_238659_2_, p_238659_3_, p_238659_4_) -> {
            if (!p_238659_1_.active) {
                this.renderTooltip(p_238659_2_, this.minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), p_238659_3_, p_238659_4_);
            }
        };
        (this.addButton(new Button(this.width / 100 - 100, yIn + rowHeightIn * 1, 200, 20, new TranslationTextComponent("menu.multiplayer"), (p_213095_1_) -> {
            Screen screen = (Screen)(this.minecraft.gameSettings.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
            this.minecraft.displayGuiScreen(screen);
        }, button$itooltip))).active = flag;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Frame yeet = null;
        if (this.firstRenderTime == 0L) {
            lastpartialticks = -200;
            this.firstRenderTime = Util.milliTime();
            try {
                eff.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            eff.setAudioChannels(0);
            try {
                yeet = eff.grabImage();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            //eff.setPixelFormat(oof.getGlFormat());
        }
        if (eff.getFrameNumber() > eff.getLengthInFrames() - 10) {
            if (!Minecraft.getInstance().getSoundHandler().isPlaying(bruhh)) {
                Minecraft.getInstance().getSoundHandler().play(bruhh);
            }
            try {
                eff.restart();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            lastpartialticks = 0;
        }
        float f = this.showFadeInAnimation ? (float) (Util.milliTime() - this.firstRenderTime) / 1000.0F : 1.0F;
        //fill(matrixStack, 0, 0, this.width, this.height, -1);
        int j = this.width / 2 - 137;

        //if (firstRenderTime-Util.milliTime() > )
        HunterXHunter.LOGGER.info("video framerate " + eff.getVideoFrameRate());
        HunterXHunter.LOGGER.info("frames per second grabber " + eff.getFrameRate());

        if (lastpartialticks > (20f / 24f)) {
            if (!Minecraft.getInstance().getSoundHandler().isPlaying(bruhh)) {
                Minecraft.getInstance().getSoundHandler().play(bruhh);
            }
            try {
                yeet = eff.grabImage();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            lastpartialticks -= (20f/24f);
        }
        HunterXHunter.LOGGER.info("partial ticks is " + lastpartialticks); //Need to increase tickrate while gui open, render isn't called frequently enough
        HunterXHunter.LOGGER.info("Current frame is " + eff.getFrameNumber());

        if (yeet != null) {
            if (yeet.image != null) {
                if (yeet.imageHeight != 0) {
                    handler.updateTextureData(Java2DFrameUtils.toBufferedImage(yeet));
                }
            }
        }

        Minecraft.getInstance().getTextureManager().bindTexture(handler.getTextureLocation());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        float f1 = 1.0F;
        int l = MathHelper.ceil(f1 * 255.0F) << 24;
        if ((l & -67108864) != 0) {
            this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);
            this.blitBlackOutline(j, 30, (p_238657_2_, p_238657_3_) -> {
                this.blit(matrixStack, p_238657_2_ + 0, p_238657_3_, 0, 0, 155, 44);
                this.blit(matrixStack, p_238657_2_ + 155, p_238657_3_, 0, 45, 155, 44);
            });

            this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_EDITION);
            blit(matrixStack, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);

            net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, matrixStack, this.font, this.width, this.height);
            String s = "Minecraft " + SharedConstants.getVersion().getName();
            s = s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            if (this.minecraft.isModdedClient()) {
                s = s + I18n.format("menu.modded");
            }
            for (Widget widget : this.buttons) {
                widget.setAlpha(f1);
            }
            for (int i = 0; i < this.buttons.size(); ++i) {
                this.buttons.get(i).render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }
        lastpartialticks += partialTicks;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            if (mouseY > (double)(this.height - 10) && mouseY < (double)this.height) {
                super.minecraft.displayGuiScreen(new WinGameScreen(false, Runnables.doNothing()));
            }
            return false;
        }
    }

    public void onClose() {
        try {
            eff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        Minecraft.getInstance().getSoundHandler().stop(bruhh);
    }
}