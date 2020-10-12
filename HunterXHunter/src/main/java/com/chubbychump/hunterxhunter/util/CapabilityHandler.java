package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.NenProvider;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Capability handler
 *
 * This class is responsible for attaching our capabilities
 */
@Mod.EventBusSubscriber
public class CapabilityHandler
{
    public static final ResourceLocation MANA_CAP = new ResourceLocation(HunterXHunter.MOD_ID, "nenuser");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent event)
    {
        if (!(event.getObject() instanceof ClientPlayerEntity)) return;

        event.addCapability(MANA_CAP, new NenProvider());
    }
}
