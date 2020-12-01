package com.chubbychump.hunterxhunter.client.core.handler;

import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.common.core.IProxy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
    public static KeyBinding nenControl = new KeyBinding("Toggle Nen", 67, "Nen Abilities");
    public static KeyBinding increaseNen = new KeyBinding("Increase Nen", 86, "Nen Abilities");
    public static KeyBinding gyo = new KeyBinding("Toggle Gyo", 89, "Nen Abilities");
    public static KeyBinding nenPower1 = new KeyBinding("Nen Power 1", 88, "Nen Abilities");
    public static KeyBinding nenPower2 = new KeyBinding("Nen Power 2", 90, "Nen Abilities");

    @Override
    public void registerHandlers() {
        // This is the only place it works, but mods are constructed in parallel (brilliant idea) so this
        // *could* end up blowing up if it races with someone else. Let's pray that doesn't happen.
        ShaderHelper.initShaders();

    }
}
