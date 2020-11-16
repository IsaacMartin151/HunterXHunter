package com.chubbychump.hunterxhunter.client.core.helper;

import net.minecraft.client.renderer.RenderType;

import javax.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ShaderWrapper extends RenderType {
    private final RenderType delegate;
    private final ShaderHelper.BotaniaShader shader;

    @Nullable
    private final int cb;

    public ShaderWrapper(ShaderHelper.BotaniaShader shader, @Nullable int cb, RenderType delegate) {
        super("hunterxhunter:" + delegate.toString() + "_with_" + shader.name(), delegate.getVertexFormat(), delegate.getDrawMode(), delegate.getBufferSize(), delegate.isUseDelegate(), true,
                () -> {
                    delegate.setupRenderState();
                    ShaderHelper.useShader(shader, cb);
                },
                () -> {
                    ShaderHelper.releaseShader();
                    delegate.clearRenderState();
                });
        this.delegate = delegate;
        this.shader = shader;
        this.cb = cb;
    }

    @Override
    public Optional<RenderType> getOutline() {
        return delegate.getOutline();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return other instanceof ShaderWrapper
                && delegate.equals(((ShaderWrapper) other).delegate)
                && shader == ((ShaderWrapper) other).shader
                && Objects.equals(cb, ((ShaderWrapper) other).cb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delegate, shader, cb);
    }
}
