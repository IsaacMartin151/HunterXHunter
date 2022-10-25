package com.screens;

import com.example.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.registry.MenuMusic;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ModListScreen;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;

import java.io.File;

import static net.minecraft.client.gui.components.AbstractWidget.WIDGETS_LOCATION;

@OnlyIn(Dist.CLIENT)
public class MainMenu extends Screen {
    private static File file = new File(Minecraft.getInstance().getResourcePackDirectory().getAbsolutePath()+"/departure.avi");
    public static FFmpegFrameGrabber frameGrabber= new FFmpegFrameGrabber(file);
    public DynamicTexture dynamicTexture = new DynamicTexture(640, 360, true);
    private boolean needstorestart = false;
    private boolean start = false;
    private Button startbutton = new Button(0, 0, 60, 20, Component.literal("Start"), (p_213094_1_) -> {
        this.start = true;
    });

    private float framerate = 30f;
    private float lastpartialticks;
    private MenuMusic departure = new MenuMusic();
    private TextureManager textureManager;
    private static final ResourceLocation TITLE = new ResourceLocation(HunterXHunter.MODID, "textures/gui/title/title.png");
    private long firstRenderTime;

    public MainMenu() {
        super(Component.literal("Main Menu"));
        this.firstRenderTime = 0L;
        this.textureManager = new TextureManager(Minecraft.getInstance().getResourceManager());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        int j = 20;
        this.addRenderableWidget(new Button(0, j * 4, 100, 20, Component.literal("Mods"), button -> {
            this.minecraft.setScreen(new ModListScreen(this));
        }));
        this.addRenderableWidget(new Button(0, j * 2, 100, 20, Component.literal("Options"), (p_213096_1_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));
        this.addRenderableWidget(startbutton);
        this.addRenderableWidget(new Button(0, j*0, 100, 20, Component.literal("Singleplayer"), (p_213089_1_) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }));
        this.addRenderableWidget(new Button(0, 0 + j * 1, 100, 20, Component.literal("Multiplayer"), (p_213095_1_) -> {
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Frame yeet = null;
        if (this.firstRenderTime == 0L) {
            lastpartialticks = -100;
            this.firstRenderTime = Util.getEpochMillis();
            try {
                frameGrabber.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            frameGrabber.setAudioChannels(0);
            try {
                yeet = frameGrabber.grabImage();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
        if (lastpartialticks < 0) {
            lastpartialticks += partialTicks;
            return;
        }
        if (needstorestart) {
            Minecraft.getInstance().getSoundManager().play(departure);
            try {
                frameGrabber.restart();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            lastpartialticks = 0;
            needstorestart = false;
        }

        if (lastpartialticks > (20f / framerate)) {
            if (!Minecraft.getInstance().getSoundManager().isActive(departure)) {
                Minecraft.getInstance().getSoundManager().play(departure);
            }
            try {
                yeet = frameGrabber.grabImage();
                if (frameGrabber.getFrameNumber() > frameGrabber.getLengthInFrames() - 3) {
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
                    dynamicTexture.setPixels(dynamicTexture.getPixels());
                }
            }
        }
        new ResourceLocation(dynamicTexture.toString());
        textureManager.bindForSetup(dynamicTexture);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        textureManager.bindForSetup(TITLE);

        startbutton.setAlpha(.8F);

        if (start) {
            for (Widget renderable : this.renderables) {
                if (renderable != startbutton) {
                    renderable.render(matrixStack, mouseX, mouseY, partialTicks);
                }
            }
        }
        else {
            this.startbutton.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        lastpartialticks += partialTicks;
    }

    @Override
    public void onClose() {
        try {
            frameGrabber.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        needstorestart = true;
        Minecraft.getInstance().getSoundManager().stop();
    }
}
