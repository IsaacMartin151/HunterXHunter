package com.screens;

import com.example.hunterxhunter.HunterXHunter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.registry.MenuMusic;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ModListScreen;

@OnlyIn(Dist.CLIENT)
public class MainMenu extends Screen {
//    public FrameGrabber frameGrabber;
//    public DynamicTexture dynamicTexture = new DynamicTexture(640, 360, true);
    private boolean needstorestart = false;
    private boolean start = false;
    private float framerate = 30f;
    private float lastpartialticks = -100;
    private MenuMusic departure = new MenuMusic();
    private TextureManager textureManager;
    private final ResourceLocation TITLE = new ResourceLocation(HunterXHunter.MODID, "textures/gui/title/title.png");
    private long firstRenderTime;
    public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);

    public MainMenu() {
        super(Component.literal("Hunter X Hunter Main Menu"));
        this.firstRenderTime = 0L;
        this.textureManager = Minecraft.getInstance().textureManager;
//        HunterXHunter.LOGGER.info("Trying to get departure at: " + Minecraft.getInstance().getResourcePackDirectory().getAbsolutePath());
//        File departure = new File(Minecraft.getInstance().getResourcePackDirectory().getAbsolutePath()+"/departure.mp4");
//        frameGrabber = new FFmpegFrameGrabber(departure);
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
        int verticalSpacing = 20;
        this.addRenderableWidget(new Button(0, verticalSpacing * 0, 100, verticalSpacing, Component.literal("Singleplayer"), (p_213089_1_) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }));
        this.addRenderableWidget(new Button(0, verticalSpacing * 1, 100, verticalSpacing, Component.literal("Multiplayer"), (p_213095_1_) -> {
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }));
        this.addRenderableWidget(new Button(0, verticalSpacing * 2, 100, verticalSpacing, Component.literal("Options"), (p_213096_1_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));
        this.addRenderableWidget(new Button(0, verticalSpacing * 3, 100, verticalSpacing, Component.literal("Mods"), button -> {
            this.minecraft.setScreen(new ModListScreen(this));
        }));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        Frame yeet = null;
        if (this.firstRenderTime == 0L) {
            Minecraft.getInstance().getSoundManager().stop();
            this.firstRenderTime = Util.getEpochMillis();
//            try {
//                frameGrabber.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            frameGrabber.setAudioChannels(0);
//            try {
//                yeet = frameGrabber.grabFrame();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        if (lastpartialticks < 0) {
            lastpartialticks += partialTicks;
            return;
        }
        if (needstorestart) {
            Minecraft.getInstance().getSoundManager().play(departure);
//            try {
//                frameGrabber.restart();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            lastpartialticks = 0;
            needstorestart = false;
        }

        if (lastpartialticks > (20f / framerate)) {
            if (!Minecraft.getInstance().getSoundManager().isActive(departure)) {
                Minecraft.getInstance().getSoundManager().play(departure);
            }
//            try {
//                yeet = frameGrabber.grabFrame();
//                if (frameGrabber.getFrameNumber() > frameGrabber.getLengthInFrames() - 3) {
//                    needstorestart = true;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            lastpartialticks -= (20f/framerate);
        }

//        if (yeet != null) {
//            if (yeet.image != null) {
//                if (yeet.imageHeight != 0) {
//                    dynamicTexture.setPixels(dynamicTexture.getPixels());
//                }
//            }
//        }
//        ResourceLocation image = new ResourceLocation(dynamicTexture.toString());
//        textureManager.bindForSetup(image);
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
//        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        this.panorama.render(partialTicks, Mth.clamp(1.0F, 0.0F, 1.0F));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

        RenderSystem.setShaderTexture(0, TITLE);
        // X pos on screen, y pos on screen, wi, x repetitions, y repititions
        int width = this.width*2/4;
        int height = width * 216/384;
        blit(matrixStack, this.width/3, this.height/7, Util.getMillis() / 20, 0, width, height, width, height);
        for (Widget renderable : this.renderables) {
            renderable.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        lastpartialticks += partialTicks;
    }

    @Override
    public void onClose() {
//        try {
//            frameGrabber.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        needstorestart = true;
//        Minecraft.getInstance().getSoundManager().stop();
    }
}
