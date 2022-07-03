package com.chubbychump.hunterxhunter.client.core.handler;

import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class ClientInit {
    public static final KeyMapping nenControl    =  new KeyMapping("Toggle Nen", 67, "Nen Abilities");
    public static final KeyMapping increaseNen   =  new KeyMapping("Increase Nen", 86, "Nen Abilities");
    public static final KeyMapping gyo           =  new KeyMapping("Toggle Gyo", 89, "Nen Abilities");
    public static final KeyMapping nenPower1     =  new KeyMapping("Nen Power 1", 88, "Nen Abilities");
    public static final KeyMapping nenPower2     =  new KeyMapping("Nen Power 2", 90, "Nen Abilities");
    public static final KeyMapping devTesting    =  new KeyMapping("Dev Testing", 93, "Nen Abilities");
    public static final KeyMapping book          =  new KeyMapping("Book", 91, "Nen Abilities");
    public static final KeyMapping transformCard =  new KeyMapping("Transform Card", 92, "Nen Abilities");

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
