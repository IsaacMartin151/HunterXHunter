package com.chubbychump.hunterxhunter.client.core.helper;

import com.chubbychump.hunterxhunter.HunterXHunter;

import com.chubbychump.hunterxhunter.client.core.handler.ConfigHandler;
import com.mojang.blaze3d.platform.GlStateManager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static org.lwjgl.opengl.GL11.*;

public final class ShaderHelper {
    public static final String PREFIX_SHADER = "shader/";
    public static final String SHADER_FILM_GRAIN_FRAG = PREFIX_SHADER + "film_grain.frag";
    public static final String SHADER_PASSTHROUGH_VERT = PREFIX_SHADER + "passthrough.vert";
    public static final String SHADER_PATTERN_FRAG = PREFIX_SHADER + "pattern.frag";
    public static final String SHADER_PATTERN_VERT = PREFIX_SHADER + "pattern.vert";
    public static final String SHADER_BOOK_FRAG = PREFIX_SHADER + "book.frag";
    public static final String SHADER_BOOK_VERT = PREFIX_SHADER + "book.vert";
    public static final String LIGHTING_FRAG = PREFIX_SHADER + "lighting.frag";
    public static final String LIGHTING_VERT = PREFIX_SHADER + "lighting.vert";
    public static final String GRAYSCALE_FRAG = PREFIX_SHADER + "grayscale.frag";
    public static final String GRAYSCALE_VERT = PREFIX_SHADER + "grayscale.vert";

    public enum BotaniaShader {
        FILM_GRAIN(SHADER_PASSTHROUGH_VERT, SHADER_FILM_GRAIN_FRAG),
        DRAGON(SHADER_PATTERN_VERT, SHADER_PATTERN_FRAG),
        BOOK(SHADER_BOOK_VERT, SHADER_BOOK_FRAG),
        GRAYSCALE(GRAYSCALE_VERT, GRAYSCALE_FRAG),
        LIGHTING(LIGHTING_VERT, LIGHTING_FRAG);

        public final String vertexShaderPath;
        public final String fragmentShaderPath;

        BotaniaShader(String vertexShaderPath, String fragmentShaderPath) {
            this.vertexShaderPath = vertexShaderPath;
            this.fragmentShaderPath = fragmentShaderPath;
        }
    }

    // Scratch buffer to use for uniforms
    public static final FloatBuffer FLOAT_BUF = MemoryUtil.memAllocFloat(3);
    private static final Map<BotaniaShader, ShaderProgram> PROGRAMS = new EnumMap<>(BotaniaShader.class);

    private static boolean hasIncompatibleMods = false;
    private static boolean checkedIncompatibility = false;

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(HunterXHunter.MOD_ID, path);
    }


    @SuppressWarnings("deprecation")
    public static void initShaders() {
        // Can be null when running datagenerators due to the unfortunate time we call this
        LOGGER.info("Initializing shaders");
        if (Minecraft.getInstance() != null
                && Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
                    (IResourceManagerReloadListener) manager -> {
                        PROGRAMS.values().forEach(ShaderLinkHelper::deleteShader);
                        PROGRAMS.clear();
                        loadShaders(manager);
                    });
        }
    }

    private static void loadShaders(IResourceManager manager) {
        if (!useShaders()) {
            return;
        }

        for (BotaniaShader shader : BotaniaShader.values()) {
            createProgram(manager, shader);
        }
    }

    public static void useShader(BotaniaShader shader, @Nullable ShaderCallback callback, int textureID, int[] nencolor) {
        if (!useShaders()) {
            return;
        }

        ShaderProgram prog = PROGRAMS.get(shader);
        if (prog == null) {
            LOGGER.info("Shader program is null");
            //return;
        }

        int program = prog.getProgram();
        ShaderLinkHelper.func_227804_a_(program);

        if (prog != PROGRAMS.get(BotaniaShader.GRAYSCALE)) {
            long time = System.currentTimeMillis();
            double speed = 10.;
            int timer = (int) (time % 1000);
            //LOGGER.info("Timer is now " + timer);
            int bruh = GlStateManager.getUniformLocation(program, "timer");
            int noisetexture = GlStateManager.getUniformLocation(program, "noise"); //IntBuffer will not work use the textureID
            int color1 = GlStateManager.getUniformLocation(program, "uColor1");
            int color2 = GlStateManager.getUniformLocation(program, "uColor2");
            int color3 = GlStateManager.getUniformLocation(program, "uColor3");
            GlStateManager.uniform1i(noisetexture, textureID);
            GlStateManager.uniform1i(bruh, timer);
            GlStateManager.uniform1i(color1, nencolor[0]);
            GlStateManager.uniform1i(color2, nencolor[1]);
            GlStateManager.uniform1i(color3, nencolor[2]);
            //GL20.glBindAttribLocation(program, 0, "texCoords");
        }

        if (callback != null) {
            callback.call(program);
        }
    }

    public static void useShader(BotaniaShader shader, int textureID, int[] nencolor) {
        useShader(shader, null, textureID, nencolor);
    }

    public static void releaseShader() {
        ShaderLinkHelper.func_227804_a_(0);
    }

    public static boolean useShaders() {
        return ConfigHandler.CLIENT.useShaders.get() && checkIncompatibleMods();
    }

    private static boolean checkIncompatibleMods() {
        if (!checkedIncompatibility) {
            hasIncompatibleMods = ModList.get().isLoaded("optifine");
            checkedIncompatibility = true;
        }

        return !hasIncompatibleMods;
    }

    private static void createProgram(IResourceManager manager, BotaniaShader shader) {
        try {
            ShaderLoader vert = createShader(manager, shader.vertexShaderPath, ShaderLoader.ShaderType.VERTEX);
            ShaderLoader frag = createShader(manager, shader.fragmentShaderPath, ShaderLoader.ShaderType.FRAGMENT);
            int progId = ShaderLinkHelper.createProgram();
            ShaderProgram prog = new ShaderProgram(progId, vert, frag);
            ShaderLinkHelper.linkProgram(prog);
            PROGRAMS.put(shader, prog);

        } catch (IOException ex) {
            HunterXHunter.LOGGER.error("Failed to load program {}", shader.name(), ex);
        }
    }

    private static ShaderLoader createShader(IResourceManager manager, String filename, ShaderLoader.ShaderType shaderType) throws IOException {
        ResourceLocation loc = prefix(filename);
        try (InputStream is = new BufferedInputStream(manager.getResource(loc).getInputStream())) {
            return ShaderLoader.func_216534_a(shaderType, loc.toString(), is, shaderType.name().toLowerCase(Locale.ROOT));
        }
    }

    private static class ShaderProgram implements IShaderManager {
        private final int program;
        private final ShaderLoader vert;
        private final ShaderLoader frag;

        private ShaderProgram(int program, ShaderLoader vert, ShaderLoader frag) {
            this.program = program;
            this.vert = vert;
            this.frag = frag;
        }

        @Override
        public int getProgram() {
            return program;
        }

        @Override
        public void markDirty() {
        }

        @Override
        public ShaderLoader getVertexShaderLoader() {
            return vert;
        }

        @Override
        public ShaderLoader getFragmentShaderLoader() {
            return frag;
        }
    }

}