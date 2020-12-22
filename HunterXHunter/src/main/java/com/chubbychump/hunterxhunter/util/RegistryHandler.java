package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.common.blocks.BlockItemBase;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.blocks.PortalBlock;
import com.chubbychump.hunterxhunter.common.blocks.RubyBlock;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.chubbychump.hunterxhunter.common.items.*;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.PotatoSoup;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.RoastedPorkDish;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.SpiderEagleEgg;
import com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse.Crystal_Nen;
import com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse.Duplicator;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.TastyFood;
import com.chubbychump.hunterxhunter.common.items.thehundred.placeable.SaturationStand;
import com.chubbychump.hunterxhunter.common.potions.BloodLust;
import com.chubbychump.hunterxhunter.common.potions.BloodLustEffect;
import com.chubbychump.hunterxhunter.common.tileentities.SaturationStandTileEntity;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Method;
import java.util.Set;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, MOD_ID);
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTION_TYPES, MOD_ID);
    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);

    public static void init() {
        CONTAINER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTION_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POINT_OF_INTEREST.register(FMLJavaModLoadingContext.get().getModEventBus());
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //try {
        //    Method func_221052_a = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
        //    func_221052_a.invoke(null, MASADORIAN_POI);
        //} catch (Exception e) {
        //    e.printStackTrace();
       // }
    }

    //Particles
    //public static final RegistryObject<ParticleType<SparkleParticleData>> SPARKLES = PARTICLE_TYPES.register("sparkles", () -> new ParticleT);

    //Dimensions
    public static final RegistryKey<DimensionType> GREED_ISLAND_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(MOD_ID, "greedisland"));
    public static final RegistryKey<World> GREED_ISLAND_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(MOD_ID, "greedisland"));

    //Effects
    public static final RegistryObject<Effect> BLOODLUST_EFFECT = POTIONS.register("bloodlust_effect", () -> new BloodLustEffect(EffectType.HARMFUL, 0xcc0a0a));

    //Potions
    public static final RegistryObject<Potion> BLOODLUST_POTION = POTION_TYPES.register("bloodlust_potion", () -> new BloodLust(new EffectInstance(BLOODLUST_EFFECT.get())));

    //Sounds
    public static final RegistryObject<SoundEvent> OSU = SOUNDS.register("osu", () -> new SoundEvent(new ResourceLocation(MOD_ID, "osu")));
    public static final RegistryObject<SoundEvent> COOKIECHAN = SOUNDS.register("cookiechan", () -> new SoundEvent(new ResourceLocation(MOD_ID, "cookiechan")));
    public static final RegistryObject<SoundEvent> WORLD_OF_ADVENTURES = SOUNDS.register("worldofadventures", () -> new SoundEvent(new ResourceLocation(MOD_ID, "worldofadventures")));
    public static final RegistryObject<SoundEvent> DEPARTURE = SOUNDS.register("departure", () -> new SoundEvent(new ResourceLocation(MOD_ID, "departure")));
    public static final RegistryObject<SoundEvent> HISOKA = SOUNDS.register("hisoka", () -> new SoundEvent(new ResourceLocation(MOD_ID, "hisoka")));

    //Items
    public static final RegistryObject<Item> RUBY = ITEMS.register( "ruby", ItemBase::new);
    public static final RegistryObject<Item> CRYSTALLIZEDNEN = ITEMS.register( "crystal_nen", Crystal_Nen::new);
    public static final RegistryObject<Item> TASTY_FOOD = ITEMS.register( "tasty_food", TastyFood::new);
    public static final RegistryObject<Item> DUPLICATOR = ITEMS.register( "duplicator", Duplicator::new);
    public static final RegistryObject<Item> ROASTED_PORK_DISH = ITEMS.register( "roasted_pork_dish", RoastedPorkDish::new);
    public static final RegistryObject<Item> POTATO_SOUP = ITEMS.register( "potato_soup", PotatoSoup::new);
    public static final RegistryObject<Item> SPIDER_EAGLE_EGG = ITEMS.register( "spider_eagle_egg", SpiderEagleEgg::new);

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> NEN_LIGHT = BLOCKS.register("nenlight", NenLight::new);
    public static final RegistryObject<Block> GREED_ISLAND_PORTAL = BLOCKS.register("twilight_portal", PortalBlock::new);
    public static final RegistryObject<Block> SATURATION_STAND = BLOCKS.register("saturation_stand", SaturationStand::new);

    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> NEN_LIGHT_ITEM = ITEMS.register("nen_light", () -> new BlockItemBase(NEN_LIGHT.get()));
    public static final RegistryObject<Item> TWILIGHT_PORTAL_ITEM = ITEMS.register("twilight_portal", () -> new BlockItemBase(GREED_ISLAND_PORTAL.get()));
    public static final RegistryObject<Item> SATURATION_STAND_ITEM = ITEMS.register("saturation_stand", () -> new BlockItemBase(SATURATION_STAND.get()));

    //Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT_TILE_ENTITY = TILE_ENTITY_TYPES.register("nen_light_tile_entity", () -> TileEntityType.Builder.create(TileEntityNenLight::new, NEN_LIGHT.get()).build(null));
    public static final RegistryObject<TileEntityType<SaturationStandTileEntity>> SATURATION_STAND_TILE_ENTITY = TILE_ENTITY_TYPES.register("saturation_stand", () -> TileEntityType.Builder.create(SaturationStandTileEntity::new, SATURATION_STAND.get()).build(null));

    //Containers
    public static final RegistryObject<ContainerType<GreedIslandContainer>> GREED_ISLAND_CONTAINER = CONTAINER.register("greedislandbook", () -> IForgeContainerType.create(GreedIslandContainer::createContainerClientSide));

    //Entities
    public static final RegistryObject<EntityType<EntityRayBeam>> RAY_BEAM_ENTITY = ENTITY_TYPES.register("raybeam", () -> EntityType.Builder.<EntityRayBeam>create(EntityRayBeam::new, EntityClassification.MISC)
            .size(.9f, .9f)
            .setTrackingRange(32)
            .setUpdateInterval(40)
            .setShouldReceiveVelocityUpdates(false)
            .build(""));

    //Point Of Interests
    public static final RegistryObject<PointOfInterestType> MASADORIAN_POI = POINT_OF_INTEREST.register("masadorianpoi", () -> new PointOfInterestType("masadorianpoi", ImmutableSet.copyOf(RUBY_BLOCK.get().getStateContainer().getValidStates()), 1, 1));

    //Professions
    public static final RegistryObject<VillagerProfession> MASADORIAN = PROFESSIONS.register("masadorian", () -> new VillagerProfession("masadorian", MASADORIAN_POI.get(), VillagerProfession.CARTOGRAPHER.getSpecificItems(),  VillagerProfession.CARTOGRAPHER.getRelatedWorldBlocks(), COOKIECHAN.get()));
}