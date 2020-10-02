package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.common.abilities.NenAbilities;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventsHandling {
    @SubscribeEvent
    public static void keyPress(InputEvent.KeyInputEvent event) {
        System.out.println("Key "+event+" was pressed");
        System.out.println("Key c is "+'c'+", comparing to "+event.getKey());
        NenAbilities Yo = new NenAbilities();
        if (67 == event.getKey()) {
            Yo.increaseNenPower();
        }

    }
}
