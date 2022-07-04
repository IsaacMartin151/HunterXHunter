package com.chubbychump.hunterxhunter;

import com.chubbychump.hunterxhunter.client.core.handler.ClientInit;
import com.chubbychump.hunterxhunter.client.gui.ContainerScreenGreedIsland;
import com.chubbychump.hunterxhunter.client.rendering.ShiftyTERenderer;
import com.chubbychump.hunterxhunter.packets.PacketManager;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookItemStackHandler;
import com.chubbychump.hunterxhunter.server.abilities.greedislandbook.BookStorage;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealth;
import com.chubbychump.hunterxhunter.server.abilities.heartstuff.MoreHealthStorage;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenStorage;
import com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenUser;
import com.chubbychump.hunterxhunter.server.entities.projectiles.ManipulatorTpProjectile;
import com.chubbychump.hunterxhunter.server.entities.renderers.*;
import com.chubbychump.hunterxhunter.server.items.ItemVariants;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import com.chubbychump.hunterxhunter.util.VillagerUtil;
import com.mojang.blaze3d.platform.ScreenManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

import static com.chubbychump.hunterxhunter.server.abilities.nenstuff.NenProvider.NENUSER;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

@Mod("hunterxhunter")
public class HunterXHunter {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hunterxhunter";
    private static final UUID MODIFIER_ID = UUID.fromString("81f27f52-c8bb-403a-a1a4-b356d2f7a0f0");
    public static ItemVariants itemVariants;

    //TODO: Chess board - Crafting table plus 4 bonemeal plus 4 inksac
    //TODO: Villager character that beats your ass if you lose at chess, takes an entry fee
    //TODO: Custom animation, Grows in size and oneshots if lose, get "Fine Gold" if win
    //TODO: Chess board block can be profession block? Trade to get the entry fee item, or maybe just a card of certain rarity?

    //TODO:

    //Phantom Troupe final boss battle for last item - use theme music

    //Add Neferpitou animation overlay for BloodLust

    //Custom recipe screen for cooking (Pork chops, wheat, sugar cane)
    //Transmuter - Kennen E? Look at spot, become ball of lightning and zoom to it
    //Advancements - pop up on screen, global announcements for Card advancements

    //Add custom screen for every type? Kinda an update screen for current nen capacity + abilities

    //Create animation for adding card to book
    // Separate categories for the types of hunters
    // Beast Hunters, Blacklist Hunters - killing strong mobs, Botanical Hunters, Sea Hunters, Treasure Hunters, Ruins Hunters
    // - 5 food
    // - 20 usable items
    // - 30 crafting ingredients
        // 5 regular mob drops - DONE
        // 2 minerals -
        // 3 world gen - new tree (World tree?), other overworld structure/flowers
        // 1 boss drop?
        //

    // Waila shows if block is harvestable
    // - 10 placeable
    // - 15 tools

    // - Biome structure loot
    // - Animals in Tundra, Badlands - ant, Swamp - giant lizard, Jungle, Plains - foxbear

    // New biome with tough monster, add "Bomber" underground entity
    //custom biome/structure for zoldyck house?
    //custom feature/structure for puzzle structures and intro puzzle structures

    //Room where player can see entity from afar and control them, has to navigate them through a simple maze to reach the end and give rewards
    //Room/biome where Motion TNTs drop from the ceiling and push players into lava

    //custom biome for "home" - 'Mom' npc that gives an item

    // Loot chests from dungeons, abandoned mineshafts, strongholds can hold treasure items

    // - 30 vanilla items

    //Transmuter
    // - Button 1: Dash Move - add ton of velocity and ignore gravity, then set velocity to 0 and reinstate gravity
    // - Button 2: Enchant
    //Enhancer
    // - Button 1: Maybe Regen? Select Jump type? Already has extra hearts
    // - Button 1: Aerial Attack
    //Emitter = create explosions at range, explosion based on power
    // - Button 1: Select Projectile Type - Slowness, levitation, explosion - all knock self back a little
    // - Button 2: Fire Projectile
    //Conjurer
    // - Button 1: Select Structure Type/width/height
    // shield entity?
    // - Button 2: Create Structure
    //Manipulator
    // - Button 1: Move to next person render view
    // - Button 2: Select Post-Damage effect - levitation, extreme knockback, fire, slowness - or choose to halt their own motion?
    // - OR: Throw a projectile, swap position on recast if not null

    /*
    - Custom dimension for after all items are collected
    // Boss battle - 4 islands, 1 starting island and 3 opponent islands
    // ShiaPouf creates phantom entities, 2 per player, in order to satisfy bloodlust
    // Youpi detonates random locations
    // Neferpitou causes bloodlust, ranged attack

    // Add a blindness thing to get Gyo to actually do something - maybe ren?

    - "Cards" need to be able to be converted to and from item form, so each card needs corresponding item (Not all need to be new items)
    - If card of certain rarity, play sound
    - Cool Animation for adding card to the book feat. transforming into card texture vs regular texture
    -
    - Custom cutscene for when someone beats the game, gets something cool and permanent card
     */

    //Add burnout potion effect that sets nen to 0?

    //add a tamable flying mount that sucks, tameable ground mount? Maybe the lizard? or something?

    //Add sound indicators for bloodlust removal, staffs,

    //Custom structure at start to get "Hunter License" - use NBT data from woodland mansion, build custom setup, then scan it
    // - 1. Beating Satotz in a race - Race while rendering an entity
    // - 2. Foggy Maze
    // - 3. Tower of traps
    // - 4. Press button to get one book

    //Grenade item

    //Custom kitchen blocks to cook stuff - Living furnace - uses hostile mob drops as fuel
    //Custom armor types - lizardleg boots, foxbear leggings, carapace chestplate, helmet TBD - set bonus = nen bonus?
    //Maybe the new giant fish from fishing can be last part


    public HunterXHunter() {
        ClientInit clientInit = new ClientInit();

        clientInit.initClient();
        RegistryHandler.init(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupServer);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupServer(final FMLCommonSetupEvent event) {
        PacketManager.register(); //32.0.63
        CapabilityManager.INSTANCE.register(INenUser.class, new NenStorage(), () -> new NenUser());
        CapabilityManager.INSTANCE.register(IMoreHealth.class, new MoreHealthStorage(), () -> new MoreHealth());
        CapabilityManager.INSTANCE.register(ItemStackHandler.class, new BookStorage(), () -> new BookItemStackHandler(100));

        VillagerUtil.fixPOITypeBlockStates(MASADORIAN_POI.get());

        GlobalEntityTypeAttributes.put(AMONG_US_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D)
                .create());

        GlobalEntityTypeAttributes.put(OBAMA_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D)
                .create());

        GlobalEntityTypeAttributes.put(MIDDLE_FINGER_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D)
                .create());

        GlobalEntityTypeAttributes.put(SHIAPOUF_CLONE_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 1.0D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D)
                .create());

        GlobalEntityTypeAttributes.put(YOUPI_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.8F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 100.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(NEFERPITOU_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.6F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(SHIAPOUF_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)1.0F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(CHIMERA_ANT_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 40.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double).3F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D).create());

        GlobalEntityTypeAttributes.put(FOXBEAR_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)1F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 30.0D)
                .createMutableAttribute(Attributes.ARMOR, 3.0D).create());

        GlobalEntityTypeAttributes.put(GIANT_LIZARD_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 16.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)1F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 30.0D).create());

        GlobalEntityTypeAttributes.put(CONJURER_MOUNT.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double).9F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D).create());

        GlobalEntityTypeAttributes.put(CAMERA_ENTITY.get(), Mob.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double).5F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D).create());

        //BrewingRecipeRegistry.addRecipe(new BloodLustRecipe());

        RegistryHandler.setupStructures();
        RegistryHandler.registerConfiguredStructures();

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(SPIDER_EAGLE_KEY, 10));
        BiomeDictionary.addTypes(SPIDER_EAGLE_KEY, BiomeDictionary.Type.SPARSE);
        BiomeDictionary.addTypes(SPIDER_EAGLE_KEY, BiomeDictionary.Type.DRY);
        BiomeDictionary.addTypes(SPIDER_EAGLE_KEY, BiomeDictionary.Type.PLATEAU);

        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(WORLD_TREE_KEY, 10));
        BiomeDictionary.addTypes(WORLD_TREE_KEY, BiomeDictionary.Type.SPOOKY);
        BiomeDictionary.addTypes(WORLD_TREE_KEY, BiomeDictionary.Type.MAGICAL);
        BiomeDictionary.addTypes(WORLD_TREE_KEY, BiomeDictionary.Type.OVERWORLD);
    }

    private static class MagicProjectileRendererFactory implements IRenderFactory<ManipulatorTpProjectile> {
        @Override
        public EntityRenderer<? super ManipulatorTpProjectile> createRenderFor(EntityRendererManager manager) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            return new SpriteRenderer<>(manager, itemRenderer);
        }
    }

//    public static void registerPropertyOverride() {
//        ItemModelsProperties.registerProperty(itemVariants, new ResourceLocation("activated"), ItemVariants::getFullnessPropertyOverride);
//        // use lambda function to link the NBT fullness value to a suitable property override value
//    }

    private void setupClient(final FMLClientSetupEvent event) {
        //event.enqueueWork(HunterXHunter::registerPropertyOverride);

        RenderingRegistry.registerEntityRenderingHandler(AMONG_US_ENTITY.get(), AmongUsRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(OBAMA_ENTITY.get(), ObamaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MIDDLE_FINGER_ENTITY.get(), MiddleFingerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SHIAPOUF_ENTITY.get(), ShiapoufRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(YOUPI_ENTITY.get(), YoupiRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NEFERPITOU_ENTITY.get(), NeferpitouRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SHIAPOUF_CLONE_ENTITY.get(), ShiapoufCloneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CHIMERA_ANT_ENTITY.get(), ChimeraAntRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FOXBEAR_ENTITY.get(), FoxBearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(GIANT_LIZARD_ENTITY.get(), GiantLizardRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(CONJURER_MOUNT.get(), ConjurerMountRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(BASE_MAGIC_PROJECTILE.get(), new MagicProjectileRendererFactory());
        RenderingRegistry.registerEntityRenderingHandler(NO_GRAVITY_PROJECTILE.get(), NoGravityProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CAMERA_ENTITY.get(), CameraEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(SUPER_COBWEB.get(), RenderType.getTranslucent());

        //RenderTypeLookup.setRenderLayer(SHIFTY_BLOCK.get(), RenderType.getTranslucent());
        ClientRegistry.bindTileEntityRenderer(SHIFTY_TILE_ENTITY.get(), ShiftyTERenderer::new);

        MinecraftForge.EVENT_BUS.register(new IngameGui(Minecraft.getInstance()));
        ScreenManager.registerFactory(GREED_ISLAND_CONTAINER.get(), ContainerScreenGreedIsland::new);
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("hunterxhunter") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GON_FISHING_POLE.get());
        }
    };

    public static void applyHealthModifier(Player player, double amount) {
        AttributeModifier modifier = new AttributeModifier(MODIFIER_ID, "levelHearts.healthModifier", amount, AttributeModifier.Operation.ADDITION);
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        attribute.removeModifier(modifier);
        attribute.applyPersistentModifier(modifier); //Maybe 769?
    }

    public static void recalcPlayerHealth(Player player, int level) {
        // Capability, Config Files, & Local Variables
        IMoreHealth cap = MoreHealth.getFromPlayer(player);
        ArrayList<Integer> ramp = Config.levelRamp.get();
        int max = Config.maxHealth.get() > 0 ? Config.maxHealth.get() : 1024;
        boolean changed = false;

        // Validate information
        if (cap.getRampPosition() < 0) {
            cap.setRampPosition((short) 0);
        }

        LazyOptional<INenUser> yo = player.getCapability(NENUSER, null);
        int Type = yo.orElseThrow(null).getNenType();
        while (cap.getRampPosition() < ramp.size() && (player.getMaxHealth() < max || Type == 1)) {
            changed = true;

            // Increase ramp position, add two half hearts, and notify the player
            cap.addRampPosition();
            cap.setModifier(cap.getModifier() + 2); // Add two-half hearts

            // Apply the health modifier so player.getMaxHealth() returns the correct amount
            if (Type == 1) {
                applyHealthModifier(player, cap.getEnhancerModifier());
            }
            else {
                applyHealthModifier(player, cap.getTrueModifier());
            }
        }

        if (changed) {
            MoreHealth.updateClient((ServerPlayer) player, cap);
            player.setHealth(player.getMaxHealth());
        }
    }
}
