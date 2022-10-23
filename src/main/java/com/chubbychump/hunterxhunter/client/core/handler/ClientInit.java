package com.chubbychump.hunterxhunter.client.core.handler;

import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientInit {
    public static final KeyBinding nenControl    =  new KeyBinding("Toggle Nen", 67, "Nen Abilities");
    public static final KeyBinding increaseNen   =  new KeyBinding("Increase Nen", 86, "Nen Abilities");
    public static final KeyBinding gyo           =  new KeyBinding("Toggle Gyo", 89, "Nen Abilities");
    public static final KeyBinding nenPower1     =  new KeyBinding("Nen Power 1", 88, "Nen Abilities");
    public static final KeyBinding nenPower2     =  new KeyBinding("Nen Power 2", 90, "Nen Abilities");
    public static final KeyBinding devTesting    =  new KeyBinding("Dev Testing", 93, "Nen Abilities");
    public static final KeyBinding book          =  new KeyBinding("Book", 91, "Nen Abilities");
    public static final KeyBinding transformCard =  new KeyBinding("Transform Card", 92, "Nen Abilities");

    public void initClient() {
        ClientRegistry.registerKeyBinding(nenControl);
        ClientRegistry.registerKeyBinding(increaseNen);
        ClientRegistry.registerKeyBinding(gyo);
        ClientRegistry.registerKeyBinding(nenPower1);
        ClientRegistry.registerKeyBinding(nenPower2);
        ClientRegistry.registerKeyBinding(devTesting);
        ClientRegistry.registerKeyBinding(book);
        ClientRegistry.registerKeyBinding(transformCard);

        ShaderHelper.initShaders();
    }
}