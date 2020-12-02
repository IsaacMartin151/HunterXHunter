package com.chubbychump.hunterxhunter.client.gui;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.rendering.TextureHandler;
import com.chubbychump.hunterxhunter.client.sounds.MenuMusic;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;

import static com.chubbychump.hunterxhunter.HunterXHunter.eff;
import static net.minecraft.client.gui.widget.Widget.WIDGETS_LOCATION;


public class HunterXHunterMainMenu extends MainMenuScreen {
    private boolean needstorestart = false;
    private boolean start = false;
    private Button startbutton = new Button(0 + 80, 0 + 20, 80, 20, ITextComponent.getTextComponentOrEmpty("go fk urself"), (p_213094_1_) -> {
        this.start = true;
    });

    private float framerate = 30f;
    private float lastpartialticks;
    private MenuMusic bruhh = new MenuMusic();
    private TextureHandler handler;
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation(HunterXHunter.MOD_ID, "textures/gui/title/title.png");
    private long firstRenderTime;

    public HunterXHunterMainMenu() {
        super(false);
        this.firstRenderTime = 0L;
        this.handler = new TextureHandler(Minecraft.getInstance().getTextureManager(), new DynamicTexture(640, 360, true));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        int j = 24;
        this.addSingleplayerMultiplayerButtons(j, 0);
        this.addButton(new ImageButton(0, j * 4, 20, 20, 0, 106, 20, WIDGETS_LOCATION, 256, 256, (p_213090_1_) -> {
            this.minecraft.displayGuiScreen(new LanguageScreen(this, this.minecraft.gameSettings, this.minecraft.getLanguageManager()));
        }, new TranslationTextComponent("narrator.button.language")));
        this.addButton(new Button(0, j * 2, 100, 20, new TranslationTextComponent("menu.options"), (p_213096_1_) -> {
            this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
        }));
        this.addButton(startbutton);
        this.addButton(new Button(0, j *3, 100, 20, new TranslationTextComponent("menu.quit"), (p_213094_1_) -> {
            try {
                eff.stop();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            this.minecraft.shutdown();
        }));
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int yIn, int rowHeightIn) {
        this.addButton(new Button(0, rowHeightIn*0, 100, 20, new TranslationTextComponent("menu.singleplayer"), (p_213089_1_) -> {
            this.minecraft.displayGuiScreen(new WorldSelectionScreen(this));
        }));
        boolean flag = this.minecraft.isMultiplayerEnabled();
        Button.ITooltip button$itooltip = flag ? Button.field_238486_s_ : (p_238659_1_, p_238659_2_, p_238659_3_, p_238659_4_) -> {
            if (!p_238659_1_.active) {
                this.renderTooltip(p_238659_2_, this.minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), p_238659_3_, p_238659_4_);
            }
        };
        (this.addButton(new Button(0, yIn + rowHeightIn * 1, 100, 20, new TranslationTextComponent("menu.multiplayer"), (p_213095_1_) -> {
            Screen screen = (Screen)(this.minecraft.gameSettings.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this));
            this.minecraft.displayGuiScreen(screen);
        }, button$itooltip))).active = flag;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Frame yeet = null;
        if (this.firstRenderTime == 0L) {
            lastpartialticks = -100;
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
            //eff.setPixelFormat();
        }
        if (needstorestart) {
            Minecraft.getInstance().getSoundHandler().play(bruhh);
            try {
                eff.restart();
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
                yeet = eff.grabImage();
                if (eff.getFrameNumber() > eff.getLengthInFrames() - 5) {
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
        Minecraft.getInstance().getTextureManager().bindTexture(handler.getTextureLocation());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        this.minecraft.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
        //net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, matrixStack, this.font, this.width, this.height);

        for (Widget widget : this.buttons) {
            widget.setAlpha(.8F);
        }
        startbutton.setAlpha(.8F);

        if (start == true) {
            for (int i = 0; i < this.buttons.size(); ++i) {
                if (buttons.get(i) != startbutton) {
                    buttons.get(i).render(matrixStack, mouseX, mouseY, partialTicks);
                }
            }
        }
        else {
            this.startbutton.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        lastpartialticks += partialTicks;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            if (mouseY > (double)(this.height - 10) && mouseY < (double)this.height) {
                //super.minecraft.displayGuiScreen(new WinGameScreen(false, Runnables.doNothing()));
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
        needstorestart = true;
        Minecraft.getInstance().getSoundHandler().stop(bruhh);
    }
}