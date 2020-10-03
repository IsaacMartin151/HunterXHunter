package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.common.abilities.types.Enhancer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//import com.chubbychump.hunterxhunter.common.blocks.NenLight;

@Mod.EventBusSubscriber
public class EventsHandling {
    @SubscribeEvent
    public static void keyPress(InputEvent.KeyInputEvent event) {
        Enhancer Yo = new Enhancer();
        if (67 == event.getKey()) {
            Yo.increaseNenPower();
        }

    }
    /*
    @SubscribeEvent
    public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {
        TileEntityType<?> type = TileEntityType.Builder.create(NenLight).build(null);
        type.setRegistryName("hunterxhunter", "nenlightblock");
        evt.getRegistry().register(type);
    }
     */
/*
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && !event.player.getEntityWorld().isRemote)
        {
            if (event.player. != null)
            {
                if (BlockMovingLightSource.isLightEmittingItem(
                        event.player.getCurrentEquippedItem().getItem()))
                {
                    int blockX = MathHelper.floor_double(event.player.posX);
                    int blockY = MathHelper.floor_double(event.player.posY-0.2D -
                            event.player.getYOffset());
                    int blockZ = MathHelper.floor_double(event.player.posZ);
                    // place light at head level
                    BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
                    if (event.player.worldObj.getBlockState(blockLocation).getBlock() ==
                            Blocks.air)
                    {
                        event.player.worldObj.setBlockState(
                                blockLocation,
                                MovingLightSource.blockMovingLightSource.getDefaultState());
                    }
                    else if (event.player.worldObj.getBlockState(
                            blockLocation.add(
                                    event.player.getLookVec().xCoord,
                                    event.player.getLookVec().yCoord,
                                    event.player.getLookVec().zCoord)).getBlock() == Blocks.air)
                    {
                        event.player.worldObj.setBlockState(
                                blockLocation.add(
                                        event.player.getLookVec().xCoord,
                                        event.player.getLookVec().yCoord,
                                        event.player.getLookVec().zCoord),
                                NenLight.blockMovingLightSource.getDefaultState());
                    }
                }
            }
        }
    } */
}
