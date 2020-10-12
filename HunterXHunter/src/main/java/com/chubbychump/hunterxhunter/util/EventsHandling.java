package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.NenProvider;
import com.chubbychump.hunterxhunter.common.abilities.NenUser;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//import com.chubbychump.hunterxhunter.common.blocks.NenLight;

@Mod.EventBusSubscriber
public class EventsHandling {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(HunterXHunter.MOD_ID, "nenuser"), new NenProvider());
        }
    }

    @SubscribeEvent
    public static void keyPress(InputEvent.KeyInputEvent event) {

        if (67 == event.getKey()) {
            LazyOptional<NenUser> yo = Minecraft.getInstance().player.getCapability(NenProvider.MANA_CAP, null);
            yo.orElseThrow(null).toggleNen();
            processLightPlacementForEntities(Minecraft.getInstance().world);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(TickEvent.WorldTickEvent event)
    {
        World theWorld = event.world;
        if (event.phase == TickEvent.Phase.START ) // && !theWorld.isRemote)
        {
            processLightPlacementForEntities(theWorld);
        }
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(TileEntityNenLight::new, ModBlocks.PERSONLIGHT).build(null).setRegistryName("personlight"));
    }

    private static void processLightPlacementForEntities(World theWorld)
    {
//        for (Entity theEntity : Collections.unmodifiableList(theWorld.loadedEntityList))
        //for (Entity theEntity : theWorld.getClosestPlayer(0, 0, 0))
        //{
            Entity theEntity = Minecraft.getInstance().player;
            // place light near entity where there is space to do so
            BlockPos blockLocation = new BlockPos(
                    MathHelper.floor(theEntity.getPosX()),
                    MathHelper.floor(theEntity.getPosY() - 0.2D - theEntity.getYOffset()),
                    MathHelper.floor(theEntity.getPosZ())).up();
            Block blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
            NenLight lightBlockToPlace = new NenLight();
            if (blockAtLocation == Blocks.AIR) {
                placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace, false);
            }
            else if (blockAtLocation instanceof NenLight) {
                if (blockAtLocation.getDefaultState().getLightValue() != lightBlockToPlace.getDefaultState().getLightValue()) {
                    return;//placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace, false);
                }
            }
            else {
                blockLocation.up();
                blockAtLocation = theWorld.getBlockState(blockLocation).getBlock();
                if (blockAtLocation == Blocks.AIR) {
                    placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace, false);
                }
                else if (blockAtLocation instanceof NenLight) {
                    if (blockAtLocation.getDefaultState().getLightValue() != lightBlockToPlace.getDefaultState().getLightValue()) {
                        placeLightSourceBlock(theEntity, blockLocation, lightBlockToPlace, false);
                    }
                }
            }
    }
    private static void placeLightSourceBlock(Entity theEntity, BlockPos blockLocation, NenLight theLightBlock, boolean isFlashlight)
    {
        theEntity.world.setBlockState(blockLocation, theLightBlock.getDefaultState());
        TileEntity theTileEntity = theEntity.world.getTileEntity(blockLocation);
        if (theTileEntity instanceof TileEntityNenLight)
        {
            TileEntityNenLight theTileEntityMovingLightSource = (TileEntityNenLight) theTileEntity;
            theTileEntityMovingLightSource.theEntity = theEntity;
            theTileEntityMovingLightSource.typeFlashlight = isFlashlight;
        }
    }


}
