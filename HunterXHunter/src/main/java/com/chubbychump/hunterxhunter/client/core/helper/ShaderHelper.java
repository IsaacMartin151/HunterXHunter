package com.chubbychump.hunterxhunter.client.core.helper;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.handler.ClientTickHandler;
import com.chubbychump.hunterxhunter.client.core.handler.ConfigHandler;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public final class ShaderHelper {
    public static final String PREFIX_SHADER = "shader/";
    public static final String SHADER_FILM_GRAIN_FRAG = PREFIX_SHADER + "film_grain.frag";
    public static final String SHADER_PASSTHROUGH_VERT = PREFIX_SHADER + "passthrough.vert";

    public enum BotaniaShader {
        FILM_GRAIN(SHADER_PASSTHROUGH_VERT, SHADER_FILM_GRAIN_FRAG);

        public final String vertexShaderPath;
        public final String fragmentShaderPath;

        BotaniaShader(String vertexShaderPath, String fragmentShaderPath) {
            this.vertexShaderPath = vertexShaderPath;
            this.fragmentShaderPath = fragmentShaderPath;
        }
    }

    // Scratch buffer to use for uniforms
    public static final FloatBuffer FLOAT_BUF = MemoryUtil.memAllocFloat(1);
    private static final Map<BotaniaShader, ShaderProgram> PROGRAMS = new EnumMap<>(BotaniaShader.class);

    private static boolean hasIncompatibleMods = false;
    private static boolean checkedIncompatibility = false;

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(HunterXHunter.MOD_ID, path);
    }


    @SuppressWarnings("deprecation")
    public static void initShaders() {
        // Can be null when running datagenerators due to the unfortunate time we call this
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

    public static void useShader(BotaniaShader shader, @Nullable ShaderCallback callback) {
        if (!useShaders()) {
            return;
        }

        ShaderProgram prog = PROGRAMS.get(shader);
        if (prog == null) {
            return;
        }

        int program = prog.getProgram();
        ShaderLinkHelper.func_227804_a_(program);

        int time = GlStateManager.getUniformLocation(program, "time");
        GlStateManager.uniform1i(time, ClientTickHandler.ticksInGame);

        if (callback != null) {
            callback.call(program);
        }
    }

    public static void useShader(BotaniaShader shader) {
        useShader(shader, null);
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

