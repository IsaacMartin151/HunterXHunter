package com.chubbychump.hunterxhunter.common.containers;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.gui.ButtonAction;
import com.chubbychump.hunterxhunter.client.gui.IToolMode;
import com.chubbychump.hunterxhunter.client.gui.NenPassiveSelection;
import com.google.common.base.Stopwatch;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;

public class NenEffectSelect extends Screen {
    public static NenEffectSelect instance = new NenEffectSelect();
    private static TextureManager yo = Minecraft.getInstance().getTextureManager();
    private float visibility = 0.0f;
    private boolean canRaise = true;
    public IToolMode switchTo = null;
    public ButtonAction doAction = null;
    public boolean actionUsed = false;
    public ResourceLocation[] icons = new ResourceLocation[4];

    protected NenEffectSelect()
    {
        super(new StringTextComponent("Menu"));
        for ( final NenPassiveSelection mode : NenPassiveSelection.values() ) {
            ResourceLocation sprite = new ResourceLocation( "hunterxhunter", "icons/" + mode.name().toLowerCase() + ".png");
            icons[mode.ordinal()] = sprite;
        }
    }

    @Override
    public Minecraft getMinecraft()
    {
        return Minecraft.getInstance();
    }

    private static class MenuButton {

        public double x1, x2;
        public double y1, y2;
        public boolean highlighted;

        public final ButtonAction action;
        public TextureAtlasSprite icon;
        public int color;
        public String name;
        public Direction textSide;

        public MenuButton(
                final String name,
                final ButtonAction action,
                final double x,
                final double y,
                final TextureAtlasSprite ico,
                final Direction textSide )
        {
            this.name = name;
            this.action = action;
            x1 = x;
            x2 = x + 18;
            y1 = y;
            y2 = y + 18;
            icon = ico;
            color = 0xffffff;
            this.textSide = textSide;
        }
    };

    static class MenuRegion {
        public IToolMode mode;
        //public ResourceLocation regionTexture;
        public double x1, x2;
        public double y1, y2;
        public boolean highlighted;

        public MenuRegion(IToolMode mode) {
            this.mode = mode;
        }

    };

    public ResourceLocation getIconForMode(final IToolMode mode) {
        ResourceLocation yah = instance.icons[mode.ordinal()];
        return yah;
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        matrixStack.push();
        //matrixStack.translate( 0.0F, 0.0F, 200.0F );

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
        HunterXHunter.LOGGER.info("In render loop. Radians is "+radians);

        final double middle_x = width / 2;
        final double middle_y = height / 2;

        final ArrayList<MenuRegion> modes = new ArrayList<MenuRegion>();
        final ArrayList<MenuButton> btns = new ArrayList<MenuButton>();

        final ArrayList<NenPassiveSelection> nenPassiveSelections = new ArrayList<NenPassiveSelection>();

        final NenPassiveSelection[] orderedModes = { NenPassiveSelection.POWER_ONE, NenPassiveSelection.POWER_TWO, NenPassiveSelection.POWER_THREE, NenPassiveSelection.POWER_FOUR };

        for (final NenPassiveSelection mode : orderedModes) {
            modes.add( new MenuRegion( mode ) );
            HunterXHunter.LOGGER.info("Adding new MenuRegion");
        }

        HunterXHunter.LOGGER.info("Finished adding menuregions");

        switchTo = null;
        doAction = null;

        if ( !modes.isEmpty() ) {
            final int totalModes = Math.max( 3, modes.size() );
            int currentMode = 0;
            final double fragment = Math.PI * 0.005;
            final double fragment2 = Math.PI * 0.0025;
            final double perObject = 2.0 * Math.PI / totalModes;

            for ( final MenuRegion mnuRgn : modes ) {
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

                if ( begin_rad <= radians && radians <= end_rad && quad ) {
                    f = 1;
                    mnuRgn.highlighted = true;
                    switchTo = mnuRgn.mode;
                }

                buffer.pos( middle_x + x1m1, middle_y + y1m1, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x2m1, middle_y + y2m1, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x2m2, middle_y + y2m2, 0 ).color( f, f, f, a ).endVertex();
                buffer.pos( middle_x + x1m2, middle_y + y1m2, 0 ).color( f, f, f, a ).endVertex();

                currentMode++;
            }
        }

        for (final MenuButton btn : btns) {
            final float a = 0.5f;
            float f = 0f;

            if (btn.x1 <= vecX && btn.x2 >= vecX && btn.y1 <= vecY && btn.y2 >= vecY) {
                f = 1;
                btn.highlighted = true;
                doAction = btn.action;
            }

            buffer.pos( middle_x + btn.x1, middle_y + btn.y1, 0 ).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + btn.x1, middle_y + btn.y2, 0 ).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + btn.x2, middle_y + btn.y2, 0 ).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + btn.x2, middle_y + btn.y1, 0 ).color( f, f, f, a ).endVertex();
        }

        tessellator.draw();

        RenderSystem.shadeModel( GL11.GL_FLAT );

        //matrixStack.translate( 0.0F, 0.0F, 5.0F );
        RenderSystem.enableTexture();
        RenderSystem.color4f( 1, 1, 1, 1.0f );
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();


        buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR );

        for ( final MenuRegion mnuRgn : modes ) {
            final double x = ( mnuRgn.x1 + mnuRgn.x2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );
            final double y = ( mnuRgn.y1 + mnuRgn.y2 ) * 0.5 * ( ring_outer_edge * 0.6 + 0.4 * ring_inner_edge );

            yo.bindTexture(getIconForMode(mnuRgn.mode));
            RenderSystem.bindTexture(yo.getTexture(getIconForMode(mnuRgn.mode)).getGlTextureId());


            final double scalex = 15 * 1 * 0.5;
            final double scaley = 15 * 1 * 0.5;
            final double x1 = x - scalex;
            final double x2 = x + scalex;
            final double y1 = y - scaley;
            final double y2 = y + scaley;

            final float f = 1.0f;
            final float a = 1.0f;

            final float u1 = 0 * 16;
            final float u2 = ( 0 + 1 ) * 16;
            final float v1 = 0 * 16;
            final float v2 = ( 0 + 1 ) * 16;

            buffer.pos( middle_x + x1, middle_y + y1, 0 ).tex( u1, v1 ).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + x1, middle_y + y2, 0 ).tex( u1, v2 ).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + x2, middle_y + y2, 0 ).tex( u2, v2).color( f, f, f, a ).endVertex();
            buffer.pos( middle_x + x2, middle_y + y1, 0 ).tex( u2, v1).color( f, f, f, a ).endVertex();
        }

        for ( final MenuButton btn : btns ) {
            final float f = switchTo == null ? 1.0f : 0.5f;
            final float a = 1.0f;

            final double u1 = 0;
            final double u2 = 16;
            final double v1 = 0;
            final double v2 = 16;

            final TextureAtlasSprite sprite = btn.icon;

            final double btnx1 = btn.x1 + 1;
            final double btnx2 = btn.x2 - 1;
            final double btny1 = btn.y1 + 1;
            final double btny2 = btn.y2 - 1;

            final float red = f * ( ( btn.color >> 16 & 0xff ) / 255.0f );
            final float green = f * ( ( btn.color >> 8 & 0xff ) / 255.0f );
            final float blue = f * ( ( btn.color & 0xff ) / 255.0f );

            buffer.pos( middle_x + btnx1, middle_y + btny1, 0 ).tex( sprite.getInterpolatedU( u1 ), sprite.getInterpolatedV( v1 ) ).color( red, green, blue, a ).endVertex();
            buffer.pos( middle_x + btnx1, middle_y + btny2, 0 ).tex( sprite.getInterpolatedU( u1 ), sprite.getInterpolatedV( v2 ) ).color( red, green, blue, a ).endVertex();
            buffer.pos( middle_x + btnx2, middle_y + btny2, 0 ).tex( sprite.getInterpolatedU( u2 ), sprite.getInterpolatedV( v2 ) ).color( red, green, blue, a ).endVertex();
            buffer.pos( middle_x + btnx2, middle_y + btny1, 0 ).tex( sprite.getInterpolatedU( u2 ), sprite.getInterpolatedV( v1 ) ).color( red, green, blue, a ).endVertex();
        }

        tessellator.draw();

        for ( final MenuRegion mnuRgn : modes ) {
            if ( mnuRgn.highlighted ) {
                final double x = ( mnuRgn.x1 + mnuRgn.x2 ) * 0.5;
                final double y = ( mnuRgn.y1 + mnuRgn.y2 ) * 0.5;

                int fixed_x = (int) ( x * text_distnace );
                final int fixed_y = (int) ( y * text_distnace );
                final String text = mnuRgn.mode.getName();

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

    private int sign(
            final double n )
    {
        return n > 0 ? 1 : -1;
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button)
    {
        this.visibility = 0f;
        canRaise = false;
        this.minecraft.displayGuiScreen( null );

        if ( this.minecraft.currentScreen == null )
        {
            this.minecraft.setGameFocused(true);
        }
        return true;
    }

}