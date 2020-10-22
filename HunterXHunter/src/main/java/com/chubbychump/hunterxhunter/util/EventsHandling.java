package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.NenUser;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.HunterXHunter.*;
import static com.chubbychump.hunterxhunter.init.ModBlocks.PERSONLIGHT;

@Mod.EventBusSubscriber
public class EventsHandling {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "nenuser"), new NenProvider());
            LOGGER.info("Attached capability to player with ID# "+event.getObject().getUniqueID());
        }
    }
    @SubscribeEvent
    public static void keyPress(TickEvent.PlayerTickEvent event) {
        if (nenControl.isPressed() && event.phase == TickEvent.Phase.START) {
            LazyOptional<NenUser> yo = event.player.getCapability(NenProvider.MANA_CAP, null);
            yo.orElseThrow(null).toggleNen();
            boolean erm = yo.orElseThrow(null).isNenActivated();
        }
        if (increaseNen.isPressed() && event.phase == TickEvent.Phase.START) {
            LazyOptional<NenUser> yo = event.player.getCapability(NenProvider.MANA_CAP, null);
            yo.orElseThrow(null).increaseNenPower();
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        processLightPlacementForEntities(event.player.getEntityWorld());
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(TileEntityNenLight::new, PERSONLIGHT).build(null).setRegistryName("hunterxhunter", "nenlighttileentity"));
    }

    private static void processLightPlacementForEntities(World theWorld) {
        for (Entity theEntity : Collections.unmodifiableList(theWorld.getPlayers())) {
            if (theEntity.getCapability(NenProvider.MANA_CAP).orElseThrow(null).isNenActivated()) {
                BlockPos blockLocation = new BlockPos(MathHelper.floor(theEntity.getPosX()), MathHelper.floor(theEntity.getPosY() - 0.1D - theEntity.getYOffset()), MathHelper.floor(theEntity.getPosZ())).up();
                Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                NenLight lightBlockToPlace = new NenLight();
                if ((blockAtLocation == Blocks.AIR) || (blockAtLocation == PERSONLIGHT)) {
                    placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace);
                }
                //if (blockAtLocation == com.chubbychump.hunterxhunter.common.blocks.NenLight) {
                //    blockAtLocation.
                //}
            }
        }
    }
    private static void placeLightSourceBlock(Entity theEntity, BlockPos blockLocation, NenLight theLightBlock) {
        theEntity.world.setBlockState(blockLocation, theLightBlock.getDefaultState());
        TileEntity theTileEntity = theEntity.world.getTileEntity(blockLocation);
        LOGGER.info("Going into placeLightSourceBlock if statement");
        if (theTileEntity instanceof TileEntityNenLight) {
            TileEntityNenLight theTileEntityMovingLightSource = (TileEntityNenLight) theTileEntity;
            LazyOptional<NenUser> yo = theEntity.getCapability(NenProvider.MANA_CAP, null);
            LOGGER.info("Got the tile entity");
            int bro = yo.orElse(null).getNenPower();
            if(bro > 15) {
                bro = 15;
            }
            theTileEntityMovingLightSource.setLevelOfLight(bro);
            theTileEntityMovingLightSource.theEntity = theEntity;
        }
    }
}