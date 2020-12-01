package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper;
import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.common.blocks.BlockItemBase;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.blocks.PortalBlock;
import com.chubbychump.hunterxhunter.common.blocks.RubyBlock;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.chubbychump.hunterxhunter.common.items.Crystal_Nen;
import com.chubbychump.hunterxhunter.common.items.ItemBase;
import com.chubbychump.hunterxhunter.common.items.ItemFlowerBag;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModBlocks;
import com.google.common.collect.ImmutableSet;
import javafx.stage.Stage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.tree.AbstractLayoutCache;
import java.lang.reflect.Method;
import java.util.Set;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class RegistryHandler {
    //public static Stage bruh = new Stage(); Doesn't work here, need to move somewhere else
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, MOD_ID);



    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER.register(FMLJavaModLoadingContext.get().getModEventBus());
        POINT_OF_INTEREST.register(FMLJavaModLoadingContext.get().getModEventBus());
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        try {
            Method func_221052_a = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
            func_221052_a.invoke(null, MASADORIAN_POI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
    }



    //Particles
    //public static final RegistryObject<ParticleType<SparkleParticleData>> SPARKLES = PARTICLE_TYPES.register("sparkles", () -> new ParticleT);

    //Dimensions
    public static final RegistryKey<DimensionType> GREED_ISLAND_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(MOD_ID, "greedisland"));
    public static final RegistryKey<World> GREED_ISLAND_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(MOD_ID, "greedisland"));

    //Sounds
    public static final RegistryObject<SoundEvent> OSU = SOUNDS.register("osu", () -> new SoundEvent(new ResourceLocation(MOD_ID, "osu")));
    public static final RegistryObject<SoundEvent> COOKIECHAN = SOUNDS.register("cookiechan", () -> new SoundEvent(new ResourceLocation(MOD_ID, "cookiechan")));
    public static final RegistryObject<SoundEvent> WORLD_OF_ADVENTURES = SOUNDS.register("worldofadventures", () -> new SoundEvent(new ResourceLocation(MOD_ID, "worldofadventures")));
    public static final RegistryObject<SoundEvent> DEPARTURE = SOUNDS.register("departure", () -> new SoundEvent(new ResourceLocation(MOD_ID, "departure")));

    //Items
    public static final RegistryObject<Item> RUBY = ITEMS.register( "ruby", ItemBase::new);
    public static final RegistryObject<Item> CRYSTALLIZEDNEN = ITEMS.register( "crystal_nen", Crystal_Nen::new);
    public static final RegistryObject<Item> GREED_ISLAND_BOOK = ITEMS.register( "greed_island_book", ItemFlowerBag::new);

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> NEN_LIGHT = BLOCKS.register("nenlight", NenLight::new);
    public static final RegistryObject<Block> GREED_ISLAND_PORTAL = BLOCKS.register("twilight_portal", PortalBlock::new);

    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> NEN_LIGHT_ITEM = ITEMS.register("nen_light", () -> new BlockItemBase(NEN_LIGHT.get()));
    public static final RegistryObject<Item> TWILIGHT_PORTAL_ITEM = ITEMS.register("twilight_portal", () -> new BlockItemBase(GREED_ISLAND_PORTAL.get()));

    //Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT_TILE_ENTITY = TILE_ENTITY_TYPES.register("nenlight", () -> TileEntityType.Builder.create(TileEntityNenLight::new, ModBlocks.NENLIGHT).build(null));

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
    public static final RegistryObject<PointOfInterestType> MASADORIAN_POI = POINT_OF_INTEREST.register("masadorianpoi", () -> new PointOfInterestType("masadorianpoi", getAllStates(RUBY_BLOCK.get()), 1, 1));

    //Professions
    public static final RegistryObject<VillagerProfession> MASADORIAN = PROFESSIONS.register("masadorian", () -> new VillagerProfession("masadorian", MASADORIAN_POI.get(), VillagerProfession.CARTOGRAPHER.getSpecificItems(),  VillagerProfession.CARTOGRAPHER.getRelatedWorldBlocks(), COOKIECHAN.get()));
    //PointOfInterestType::func_221052_a
}