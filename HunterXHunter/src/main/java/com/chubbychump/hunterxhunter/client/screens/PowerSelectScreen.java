package com.chubbychump.hunterxhunter.client.screens;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.Power;
import com.chubbychump.hunterxhunter.client.gui.select.*;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
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

import static com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser.updateServer;

@OnlyIn(Dist.CLIENT)
public class PowerSelectScreen extends Screen {
    public static PowerSelectScreen instance = new PowerSelectScreen();
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private float visibility = 0.0f;
    public int type = NenUser.getFromPlayer(Minecraft.getInstance().player).getNenType();
    public int selectedPower = -1;
    public ResourceLocation[] enhancer = new ResourceLocation[4];
    public ResourceLocation[] manipulator = new ResourceLocation[4];
    public ResourceLocation[] transmuter = new ResourceLocation[4];
    public ResourceLocation[] conjurer = new ResourceLocation[4];
    public ResourceLocation[] emitter = new ResourceLocation[4];

    protected PowerSelectScreen()
    {
        super(new StringTextComponent("PowerMenu"));
        for (final EnhancerPowers list : EnhancerPowers.values()) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "textures/icons/" + list.name().toLowerCase() + ".png");
            enhancer[list.ordinal()] = sprite;
        }
        for (final ManipulatorPowers list : ManipulatorPowers.values()) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "textures/icons/" + list.name().toLowerCase() + ".png");
            manipulator[list.ordinal()] = sprite;
        }
        for (final TransmuterPowers list : TransmuterPowers.values()) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "textures/icons/" + list.name().toLowerCase() + ".png");
            transmuter[list.ordinal()] = sprite;
        }
        for (final ConjurerPowers list : ConjurerPowers.values()) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "textures/icons/" + list.name().toLowerCase() + ".png");
            conjurer[list.ordinal()] = sprite;
        }
        for (final EmitterPowers list : EmitterPowers.values()) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "textures/icons/" + list.name().toLowerCase() + ".png");
            emitter[list.ordinal()] = sprite;
        }
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
        ResourceLocation yah = null;
        switch (type) {
            case 1:
                yah = instance.enhancer[mode.ordinal()];
                break;
            case 2:
                yah = instance.manipulator[mode.ordinal()];
                break;
            case 3:
                yah = instance.transmuter[mode.ordinal()];
                break;
            case 4:
                yah = instance.conjurer[mode.ordinal()];
                break;
            case 5:
                yah = instance.emitter[mode.ordinal()];
                break;
        }
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
        final EnhancerPowers[] enhancer = { EnhancerPowers.POWER_ONE, EnhancerPowers.POWER_TWO, EnhancerPowers.POWER_THREE, EnhancerPowers.POWER_FOUR };
        final ManipulatorPowers[] manipulator = { ManipulatorPowers.POWER_ONE, ManipulatorPowers.POWER_TWO, ManipulatorPowers.POWER_THREE, ManipulatorPowers.POWER_FOUR };
        final TransmuterPowers[] transmuter = { TransmuterPowers.POWER_ONE, TransmuterPowers.POWER_TWO, TransmuterPowers.POWER_THREE, TransmuterPowers.POWER_FOUR };
        final ConjurerPowers[] conjurer = { ConjurerPowers.POWER_ONE, ConjurerPowers.POWER_TWO, ConjurerPowers.POWER_THREE, ConjurerPowers.POWER_FOUR };
        final EmitterPowers[] emitter = { EmitterPowers.POWER_ONE, EmitterPowers.POWER_TWO, EmitterPowers.POWER_THREE, EmitterPowers.POWER_FOUR };

        switch (type) {
            case 1:
                for (final EnhancerPowers nenTypes : enhancer) {
                    powers.add( new MenuRegion(nenTypes) );
                }
                break;
            case 2:
                for (final ManipulatorPowers nenTypes : manipulator) {
                    powers.add( new MenuRegion(nenTypes) );
                }
                break;
            case 3:
                for (final TransmuterPowers nenTypes : transmuter) {
                    powers.add( new MenuRegion(nenTypes) );
                }
                break;
            case 4:
                for (final ConjurerPowers nenTypes : conjurer) {
                    powers.add( new MenuRegion(nenTypes) );
                }
                break;
            case 5:
                for (final EmitterPowers nenTypes : emitter) {
                    powers.add( new MenuRegion(nenTypes) );
                }
                break;
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


        buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR );

        //Drawing texture icons
        for ( final MenuRegion mnuRgn : powers ) {
            final double x = ( mnuRgn.x1 + mnuRgn.x2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );
            final double y = ( mnuRgn.y1 + mnuRgn.y2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );
            HunterXHunter.LOGGER.info("checking for handler "+getIconForMode(mnuRgn.power));
            yo.bindTexture(getIconForMode(mnuRgn.power));
            RenderSystem.bindTexture(yo.getTexture(getIconForMode(mnuRgn.power)).getGlTextureId());

            final double scalex = 15 * 1 * 0.5;
            final double scaley = 15 * 1 * 0.5;
            final double x1 = x - scalex;
            final double x2 = x + scalex;
            final double y1 = y - scaley;
            final double y2 = y + scaley;

            buffer.pos( middle_x + x1, middle_y + y1, 0 ).tex( 0, 0).color(1, 1, 1, 1).endVertex();
            buffer.pos( middle_x + x1, middle_y + y2, 0 ).tex( 0, 1 ).color(1, 1, 1, 1).endVertex();
            buffer.pos( middle_x + x2, middle_y + y2, 0 ).tex( 1, 1).color(1, 1, 1, 1).endVertex();
            buffer.pos( middle_x + x2, middle_y + y1, 0 ).tex( 1, 0).color(1, 1, 1, 1).endVertex();
        }

        tessellator.draw();

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
        HunterXHunter.LOGGER.info("Setting passivepower to "+(selectedPower));
        INenUser yo = NenUser.getFromPlayer(minecraft.player);
        yo.setPassivePower(selectedPower);
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
        return true;
    }

}