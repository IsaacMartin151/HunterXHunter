package com.chubbychump.hunterxhunter.client.screens;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.Power;
import com.chubbychump.hunterxhunter.client.gui.select.*;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser.updateServer;

@OnlyIn(Dist.CLIENT)
public class NenEffectSelect extends Screen {
    public static NenEffectSelect instance = new NenEffectSelect();
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private float visibility = 0.0f;
    public int selectedPower = 0;
    public ResourceLocation[] icons = new ResourceLocation[5];

    protected NenEffectSelect()
    {
        super(new StringTextComponent("Menu"));
        icons[0] = new ResourceLocation( "hunterxhunter", "icons/gon.png");
        icons[1] = new ResourceLocation( "hunterxhunter", "icons/hisoka.png");
        icons[2] = new ResourceLocation( "hunterxhunter", "icons/killua.png");
        icons[3] = new ResourceLocation( "hunterxhunter", "icons/morel.png");
        icons[4] = new ResourceLocation( "hunterxhunter", "icons/razor.png");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public Minecraft getMinecraft()
    {
        return Minecraft.getInstance();
    }

    static class MenuRegion {
        public Power power;
        public double x1, x2;
        public double y1, y2;
        public boolean highlighted;

        public MenuRegion(Power power) {
            this.power = power;
        }

    };

    public ResourceLocation getIconForMode(final Power mode) {
        ResourceLocation yah = icons[mode.ordinal()];
        return yah;
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        matrixStack.push();
        final int start = (int) ( visibility * 98 ) << 24;
        final int end = (int) ( visibility * 128 ) << 24;

        fillGradient(matrixStack, 0, 0, width, height, start, end );

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.blendFuncSeparate( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0 );
        RenderSystem.shadeModel( GL11.GL_SMOOTH );
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR );

        final double vecX = mouseX - width / 2;
        final double vecY = (mouseY - height / 2);
        double radians = Math.atan2( vecY, vecX );

        final double ring_inner_edge = 20;
        final double ring_outer_edge = 50;
        final double text_distnace = 65;
        final double quarterCircle = Math.PI / 2.0;

        if ( radians < -quarterCircle ) {
            radians = radians + Math.PI * 2;
        }

        final double middle_x = width / 2;
        final double middle_y = height / 2;

        final ArrayList<MenuRegion> powers = new ArrayList<MenuRegion>();
        final NenPassiveSelection[] orderedModes = { NenPassiveSelection.POWER_ONE, NenPassiveSelection.POWER_TWO, NenPassiveSelection.POWER_THREE, NenPassiveSelection.POWER_FOUR, NenPassiveSelection.POWER_FIVE };


        for (final NenPassiveSelection nenTypes : orderedModes) {
            powers.add( new MenuRegion(nenTypes) );
        }

        if (!powers.isEmpty()) {
            final int totalModes = Math.max(3, powers.size());
            int currentMode = 0;
            final double fragment = Math.PI * 0.005;
            final double fragment2 = Math.PI * 0.0025;
            final double perObject = 2.0 * Math.PI / totalModes;

            for ( final MenuRegion mnuRgn : powers ) {
                final double begin_rad = currentMode * perObject - quarterCircle;
                final double end_rad = ( currentMode + 1 ) * perObject - quarterCircle;

                mnuRgn.x1 = Math.cos( begin_rad );
                mnuRgn.x2 = Math.cos( end_rad );
                mnuRgn.y1 = Math.sin( begin_rad );
                mnuRgn.y2 = Math.sin( end_rad );

                final double x1m1 = Math.cos( begin_rad + fragment ) * ring_inner_edge;
                final double x2m1 = Math.cos( end_rad - fragment ) * ring_inner_edge;
                final double y1m1 = Math.sin( begin_rad + fragment ) * ring_inner_edge;
                final double y2m1 = Math.sin( end_rad - fragment ) * ring_inner_edge;

                final double x1m2 = Math.cos( begin_rad + fragment2 ) * ring_outer_edge;
                final double x2m2 = Math.cos( end_rad - fragment2 ) * ring_outer_edge;
                final double y1m2 = Math.sin( begin_rad + fragment2 ) * ring_outer_edge;
                final double y2m2 = Math.sin( end_rad - fragment2 ) * ring_outer_edge;

                final float a = 0.5f;
                float f = 0f;

                final boolean quad = inTriangle(
                        x1m1, y1m1,
                        x2m2, y2m2,
                        x2m1, y2m1,
                        vecX, vecY ) || inTriangle(
                        x1m1, y1m1,
                        x1m2, y1m2,
                        x2m2, y2m2,
                        vecX, vecY );

                if ( begin_rad <= radians && radians <= end_rad && quad || mnuRgn.power.ordinal() == selectedPower) {
                    f = 1;
                    mnuRgn.highlighted = true;
                    selectedPower = mnuRgn.power.ordinal();
                }

                buffer.pos( middle_x + x1m1, middle_y + y1m1, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x2m1, middle_y + y2m1, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x2m2, middle_y + y2m2, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x1m2, middle_y + y1m2, 0 ).color( f, f, f, a ).endVertex();

                currentMode++;
            }
        }

        tessellator.draw();

        RenderSystem.shadeModel( GL11.GL_FLAT );

        //matrixStack.translate( 0.0F, 0.0F, 5.0F );
        RenderSystem.enableTexture();
        RenderSystem.color4f( 1, 1, 1, 1.0f );
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();



        //Drawing texture icons
        for ( final MenuRegion mnuRgn : powers ) {
            buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR );
            final double x = ( mnuRgn.x1 + mnuRgn.x2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );
            final double y = ( mnuRgn.y1 + mnuRgn.y2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );
            ResourceLocation target = getIconForMode(mnuRgn.power);
            yo.bindTexture(target);
            RenderSystem.bindTexture(yo.getTexture(target).getGlTextureId());

            final double scalex = 15 * 1 * 0.5;
            final double scaley = 15 * 1 * 0.5;
            final double x1 = x - scalex;
            final double x2 = x + scalex;
            final double y1 = y - scaley;
            final double y2 = y + scaley;

            buffer.pos( middle_x + x1, middle_y + y1, 0 ).tex( 0, 0).color(1f, 1f, 1f, 1f).endVertex();
            buffer.pos( middle_x + x1, middle_y + y2, 0 ).tex( 0, 1 ).color(1f, 1f, 1f, 1f).endVertex();
            buffer.pos( middle_x + x2, middle_y + y2, 0 ).tex( 1, 1).color(1f, 1f, 1f, 1f).endVertex();
            buffer.pos( middle_x + x2, middle_y + y1, 0 ).tex( 1, 0).color(1f, 1f, 1f, 1f).endVertex();

            tessellator.draw();
        }



        //Drawing strings
        for ( final MenuRegion mnuRgn : powers ) {
            if ( mnuRgn.highlighted ) {
                final double x = ( mnuRgn.x1 + mnuRgn.x2 ) * 0.5;
                final double y = ( mnuRgn.y1 + mnuRgn.y2 ) * 0.5;

                int fixed_x = (int) ( x * text_distnace );
                final int fixed_y = (int) ( y * text_distnace );
                final String text = mnuRgn.power.getName();

                if ( x <= -0.2 ) {
                    fixed_x -= font.getStringWidth( text );
                }
                else if ( -0.2 <= x && x <= 0.2 ) {
                    fixed_x -= font.getStringWidth( text ) / 2;
                }

                font.drawStringWithShadow(matrixStack, text, (int) middle_x + fixed_x, (int) middle_y + fixed_y, 0xffffffff );
            }
        }

        matrixStack.pop();
    }
    private boolean inTriangle(
            final double x1,
            final double y1,
            final double x2,
            final double y2,
            final double x3,
            final double y3,
            final double x,
            final double y )
    {
        final double ab = ( x1 - x ) * ( y2 - y ) - ( x2 - x ) * ( y1 - y );
        final double bc = ( x2 - x ) * ( y3 - y ) - ( x3 - x ) * ( y2 - y );
        final double ca = ( x3 - x ) * ( y1 - y ) - ( x1 - x ) * ( y3 - y );
        return sign( ab ) == sign( bc ) && sign( bc ) == sign( ca );
    }

    private int sign(final double n ) {
        return n > 0 ? 1 : -1;
    }

    @Override
    public void onClose() {
        HunterXHunter.LOGGER.info("Setting nen type to "+(selectedPower+1));
        INenUser yo = NenUser.getFromPlayer(minecraft.player);
        yo.setNenType(selectedPower + 1);
        updateServer(minecraft.player, yo);
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.visibility = 0f;
        this.minecraft.displayGuiScreen( null );

        if ( this.minecraft.currentScreen == null )
        {
            this.minecraft.setGameFocused(true);
        }
        HunterXHunter.LOGGER.info("Setting nen type to "+(selectedPower+1));
        INenUser yo = NenUser.getFromPlayer(minecraft.player);
        yo.setNenType(selectedPower + 1);
        updateServer(minecraft.player, yo);
        return true;
    }

}