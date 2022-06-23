package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.client.rendering.TextureHandler;
import com.chubbychump.hunterxhunter.client.sounds.DeathMusic;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;

import javax.annotation.Nullable;
import java.io.File;

@OnlyIn(Dist.CLIENT)
public class HunterXHunterDeathScreen extends Screen {
    private static File file2 = new File(Minecraft.getInstance().getFileResourcePacks().getAbsolutePath()+"/hisoka.avi");
    public static FFmpegFrameGrabber fff = new FFmpegFrameGrabber(file2);
    /** The integer value containing the number of ticks that have passed since the player's death */
    private float lastpartialticks = 0;
    private long firstRenderTime = 0L;
    public static DeathMusic bruhh = new DeathMusic();
    private float framerate = 30f;
    private boolean needstorestart = false;
    private int enableButtonsTimer;
    private TextureHandler handler;
    private final ITextComponent causeOfDeath;
    private final boolean isHardcoreMode;
    private ITextComponent field_243285_p;

    public HunterXHunterDeathScreen(@Nullable ITextComponent textComponent, boolean isHardcoreMode) {
        super(ITextComponent.getTextComponentOrEmpty("Boi u ded"));
        this.causeOfDeath = textComponent;
        this.isHardcoreMode = isHardcoreMode;
        this.handler = new TextureHandler(Minecraft.getInstance().getTextureManager(), new DynamicTexture(638, 360, true));
    }

    protected void init() {
        this.enableButtonsTimer = 0;
        this.addButton(new Button(this.width / 2 - 50, (int) (this.height - 20 * 1), 100, 20, this.isHardcoreMode ? new TranslationTextComponent("deathScreen.spectate") : new TranslationTextComponent("deathScreen.respawn"), (p_213021_1_) -> {
            this.minecraft.player.respawnPlayer();
            this.minecraft.displayGuiScreen((Screen)null);
        }));
        Button button = this.addButton(new Button(this.width / 2 - 50, (int) (this.height - 20*2), 100, 20, new TranslationTextComponent("deathScreen.titleScreen"), (p_213020_1_) -> {
            if (this.isHardcoreMode) {
                confirmCallback(true);
                this.func_228177_a_();
            } else {
                ConfirmScreen confirmscreen = new ConfirmScreen(this::confirmCallback, new TranslationTextComponent("deathScreen.quit.confirm"), StringTextComponent.EMPTY, new TranslationTextComponent("deathScreen.titleScreen"), new TranslationTextComponent("deathScreen.respawn"));
                this.minecraft.displayGuiScreen(confirmscreen);
                confirmscreen.setButtonDelay(20);
            }
        }));
        if (!this.isHardcoreMode && this.minecraft.getSession() == null) {
            button.active = false;
        }

        for(Widget widget : this.buttons) {
            widget.active = false;
        }

        this.field_243285_p = (new TranslationTextComponent("deathScreen.score")).appendString(": ").append((new StringTextComponent(Integer.toString(this.minecraft.player.getScore()))).mergeStyle(TextFormatting.YELLOW));
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    //Whether or not player chooses to respawn?
    private void confirmCallback(boolean p_213022_1_) {
        if (p_213022_1_) {
            this.func_228177_a_();
        } else {
            this.minecraft.player.respawnPlayer();
            this.minecraft.displayGuiScreen((Screen)null);
            Minecraft.getInstance().getSoundHandler().stop(bruhh);
        }

    }

    private void func_228177_a_() {
        if (this.minecraft.world != null) {
            this.minecraft.world.sendQuittingDisconnectingPacket();
        }

        this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        this.minecraft.displayGuiScreen(new MainMenuScreen());
        Minecraft.getInstance().getSoundHandler().stop(bruhh);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Frame yeet = null;
        if (this.firstRenderTime == 0L) {
            lastpartialticks = -2;
            this.firstRenderTime = Util.milliTime();
            try {
                fff.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            fff.setAudioChannels(0);
            try {
                yeet = fff.grabImage();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            //fff.setPixelFormat();
        }
        if (needstorestart) {
            try {
                fff.restart();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            lastpartialticks = 0;
            needstorestart = false;
        }
        if (lastpartialticks > (20f / framerate)) {
            if (!Minecraft.getInstance().getSoundHandler().isPlaying(bruhh)) {
                Minecraft.getInstance().getSoundHandler().play(bruhh);
            }
            try {
                yeet = fff.grabImage();
                if (fff.getFrameNumber() > fff.getLengthInFrames() - 5) {
                    needstorestart = true;
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            lastpartialticks -= (20f/framerate);
        }
        if (yeet != null) {
            if (yeet.image != null) {
                if (yeet.imageHeight != 0) {
                    handler.updateTextureData(Java2DFrameUtils.toBufferedImage(yeet));
                }
            }
        }
        float f = (float)(Util.milliTime() - this.firstRenderTime) / 1000.0F;
        Minecraft.getInstance().getTextureManager().bindTexture(handler.getTextureLocation());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, (float) MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)));
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

        this.fillGradient(matrixStack, 0, 0, this.width, this.height, 1615855616, -1602211792);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2 / 2, 30, 16777215);
        RenderSystem.popMatrix();
        if (this.causeOfDeath != null) {
            drawCenteredString(matrixStack, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
        }

        drawCenteredString(matrixStack, this.font, this.field_243285_p, this.width / 2, 100, 16777215);
        if (this.causeOfDeath != null && mouseY > 85 && mouseY < 85 + 9) {
            Style style = this.func_238623_a_(mouseX);
            this.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        lastpartialticks += partialTicks;
    }

    @Nullable
    private Style func_238623_a_(int p_238623_1_) {
        if (this.causeOfDeath == null) {
            return null;
        } else {
            int i = this.minecraft.fontRenderer.getStringPropertyWidth(this.causeOfDeath);
            int j = this.width / 2 - i / 2;
            int k = this.width / 2 + i / 2;
            return p_238623_1_ >= j && p_238623_1_ <= k ? this.minecraft.fontRenderer.getCharacterManager().func_238357_a_(this.causeOfDeath, p_238623_1_ - j) : null;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.causeOfDeath != null && mouseY > 85.0D && mouseY < (double)(85 + 9)) {
            Style style = this.func_238623_a_((int)mouseX);
            if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
                this.handleComponentClicked(style);
                return false;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().getSoundHandler().stop(bruhh);
    }

    public boolean isPauseScreen() {
        return false;
    }


    public void tick() {
        super.tick();
        ++this.enableButtonsTimer;
        if (this.enableButtonsTimer == 20) {
            for(Widget widget : this.buttons) {
                widget.active = true;
            }
        }

    }
}