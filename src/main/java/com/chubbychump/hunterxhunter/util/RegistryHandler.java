package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.server.advancements.*;
import com.chubbychump.hunterxhunter.server.entities.entityclasses.CameraEntity;
import com.chubbychump.hunterxhunter.server.blocks.*;
import com.chubbychump.hunterxhunter.server.entities.entityclasses.*;
import com.chubbychump.hunterxhunter.server.entities.projectiles.EmitterBaseProjectile;
import com.chubbychump.hunterxhunter.server.entities.projectiles.ManipulatorTpProjectile;
import com.chubbychump.hunterxhunter.server.generation.BaseWorldTreeFeatureConfig;
import com.chubbychump.hunterxhunter.server.generation.SpiderEagleCarver;
import com.chubbychump.hunterxhunter.server.generation.WorldTreeFeature;
import com.chubbychump.hunterxhunter.server.generation.structures.floating.GravityMinerals;
import com.chubbychump.hunterxhunter.server.generation.structures.floating.GravityMineralsConfig;
import com.chubbychump.hunterxhunter.server.generation.structures.worldtree.ComponentTrunk;
import com.chubbychump.hunterxhunter.server.generation.structures.worldtree.WorldTreeConfig2;
import com.chubbychump.hunterxhunter.server.generation.structures.worldtree.WorldTreeFeature2;
import com.chubbychump.hunterxhunter.server.generation.structures.worldtree.WorldTreeStructure;
import com.chubbychump.hunterxhunter.server.items.ItemBase;
import com.chubbychump.hunterxhunter.server.items.StaffBase;
import com.chubbychump.hunterxhunter.server.items.devtools.Clearing;
import com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic.*;
import com.chubbychump.hunterxhunter.server.items.thehundred.crafting.*;
import com.chubbychump.hunterxhunter.server.items.thehundred.food.PotatoSoup;
import com.chubbychump.hunterxhunter.server.items.thehundred.food.RoastedPorkDish;
import com.chubbychump.hunterxhunter.server.items.thehundred.food.SpiderEagleEgg;
import com.chubbychump.hunterxhunter.server.items.thehundred.food.TastyFood;
import com.chubbychump.hunterxhunter.server.items.thehundred.onetimeuse.Crystal_Nen;
import com.chubbychump.hunterxhunter.server.items.thehundred.onetimeuse.Duplicator;
import com.chubbychump.hunterxhunter.server.items.thehundred.placeable.SaturationStand;
import com.chubbychump.hunterxhunter.server.items.thehundred.tools.*;
import com.chubbychump.hunterxhunter.server.potions.BloodLust;
import com.chubbychump.hunterxhunter.server.potions.BloodLustEffect;
import com.chubbychump.hunterxhunter.server.recipes.VatRecipeSerializer;
import com.chubbychump.hunterxhunter.server.recipes.VatRecipes;
import com.chubbychump.hunterxhunter.server.tileentities.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);
    public static final DeferredRegister<WorldCarver<?>> CARVER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, MOD_ID);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, MOD_ID);
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTION_TYPES, MOD_ID);
    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static void init(IEventBus bus) {
        CONTAINER.register(bus);
        ITEMS.register(bus);
        BLOCKS.register(bus);
        RECIPES.register(bus);
        TILE_ENTITY_TYPES.register(bus);

        ENTITY_TYPES.register(bus);
        SOUNDS.register(bus);
        POTION_TYPES.register(bus);
        POTIONS.register(bus);
        POINT_OF_INTEREST.register(bus);
        PROFESSIONS.register(bus);

        FOLIAGE_PLACER_TYPES.register(bus);
        CARVER.register(bus);
        STRUCTURES.register(bus);
        FEATURES.register(bus);
        BIOMES.register(bus);

        registerCriteriaTriggers();
    }

    public static Method GETCODEC_METHOD;

    public static void setupStructures() {
        StructureSeparationSettings bruh = new StructureSeparationSettings(10, /* average distance apart in chunks between spawn attempts */
                5 /* minimum distance apart in chunks between spawn attempts */,
                1234567890);
        Structure.NAME_STRUCTURE_BIMAP.put(WORLD_TREE_STRUCTURE.get().getRegistryName().toString(), WORLD_TREE_STRUCTURE.get());
        //Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structure);
        //HXHConfiguredStructures.registerStructureFeatures();
        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_)
                .put(WORLD_TREE_STRUCTURE.get(),  bruh/* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */)
                .build();

        WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

            /*
             * Pre-caution in case a mod makes the structure map immutable like datapacks do.
             * I take no chances myself. You never know what another mods does...
             *
             * structureConfig requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
             */
            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(WORLD_TREE_STRUCTURE.get(), bruh);
                settings.getValue().getStructures().field_236193_d_ = tempMap;
            }
            else{
                structureMap.put(WORLD_TREE_STRUCTURE.get(), bruh);
            }
        });
    }

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        //Registry.register(registry, new ResourceLocation(MOD_ID, "wttrunk"), CONFIGURED_WORLD_TREE);
        //FlatGenerationSettings.STRUCTURES.put(WORLD_TREE_STRUCTURE.get(), CONFIGURED_WORLD_TREE);
    }

    public static IStructurePieceType registerPiece(String name, IStructurePieceType piece) {
        return Registry.register(Registry.STRUCTURE_PIECE, name, piece);
    }

    public static void registerCriteriaTriggers() {
        CriteriaTriggers.register(AbilityUseTrigger.INSTANCE);
        CriteriaTriggers.register(AbilityMaxTrigger.INSTANCE);
        CriteriaTriggers.register(CardCollectorTrigger.INSTANCE);
        CriteriaTriggers.register(AllCommonTrigger.INSTANCE);
        CriteriaTriggers.register(AllUncommonTrigger.INSTANCE);
        CriteriaTriggers.register(AllEpicTrigger.INSTANCE);
        CriteriaTriggers.register(AllLegendaryTrigger.INSTANCE);
        CriteriaTriggers.register(HalfwayTrigger.INSTANCE);
        CriteriaTriggers.register(SingleLegendaryTrigger.INSTANCE);
        //CriteriaTriggers.register(AbilityMaxTrigger.INSTANCE);
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
    public static final RegistryObject<SoundEvent> AMONG_US = SOUNDS.register("amongus", () -> new SoundEvent(new ResourceLocation(MOD_ID, "amongus")));
    public static final RegistryObject<SoundEvent> AMOOGUS = SOUNDS.register("amoogus", () -> new SoundEvent(new ResourceLocation(MOD_ID, "amoogus")));
    public static final RegistryObject<SoundEvent> ROLE_REVEAL = SOUNDS.register("rolereveal", () -> new SoundEvent(new ResourceLocation(MOD_ID, "rolereveal")));
    public static final RegistryObject<SoundEvent> VENTING = SOUNDS.register("venting", () -> new SoundEvent(new ResourceLocation(MOD_ID, "venting")));
    public static final RegistryObject<SoundEvent> OSU = SOUNDS.register("osu", () -> new SoundEvent(new ResourceLocation(MOD_ID, "osu")));
    public static final RegistryObject<SoundEvent> COOKIECHAN = SOUNDS.register("cookiechan", () -> new SoundEvent(new ResourceLocation(MOD_ID, "cookiechan")));
    public static final RegistryObject<SoundEvent> WORLD_OF_ADVENTURES = SOUNDS.register("worldofadventures", () -> new SoundEvent(new ResourceLocation(MOD_ID, "worldofadventures")));
    public static final RegistryObject<SoundEvent> DEPARTURE = SOUNDS.register("departure", () -> new SoundEvent(new ResourceLocation(MOD_ID, "departure")));
    public static final RegistryObject<SoundEvent> HISOKA = SOUNDS.register("hisoka", () -> new SoundEvent(new ResourceLocation(MOD_ID, "hisoka")));
    public static final RegistryObject<SoundEvent> STONESLIDE = SOUNDS.register("stoneslide", () -> new SoundEvent(new ResourceLocation(MOD_ID, "stoneslide")));
    public static final RegistryObject<SoundEvent> OPEN_BOOK = SOUNDS.register("openbook", () -> new SoundEvent(new ResourceLocation(MOD_ID, "openbook")));
    public static final RegistryObject<SoundEvent> LOUNGE_MUSIC = SOUNDS.register("loungemusic", () -> new SoundEvent(new ResourceLocation(MOD_ID, "loungemusic")));
    public static final RegistryObject<SoundEvent> EMBARK_ADVENTURE = SOUNDS.register("embarkadventure", () -> new SoundEvent(new ResourceLocation(MOD_ID, "embarkadventure")));
    public static final RegistryObject<SoundEvent> WIND = SOUNDS.register("wind", () -> new SoundEvent(new ResourceLocation(MOD_ID, "wind")));
    public static final RegistryObject<SoundEvent> URAWIZARD = SOUNDS.register("urawizard", () -> new SoundEvent(new ResourceLocation(MOD_ID, "urawizard")));


    //Items

    //Non-Hundred items
    public static final RegistryObject<Item> EXPERIENCE_ITEM = ITEMS.register("experience_item", ItemBase::new);


    //Hundred items
        //Crafting
        public static final RegistryObject<Item> BEAR_CLAW = ITEMS.register( "bear_claw", BearClaw::new);
        public static final RegistryObject<Item> EXPLOSIVE_ASHES = ITEMS.register( "explosive_ashes", ExplosiveAshes::new);
        public static final RegistryObject<Item> LIZARD_LEG = ITEMS.register( "lizard_leg", LizardLeg::new);
        public static final RegistryObject<Item> SUPER_STRING = ITEMS.register( "super_string", SuperString::new);
        public static final RegistryObject<Item> CARAPACE = ITEMS.register( "carapace", Carapace::new);
        public static final RegistryObject<Item> BAT_WING = ITEMS.register( "bat_wing", ItemBase::new);
        public static final RegistryObject<Item> POTENT_AURA = ITEMS.register( "potent_aura", ItemBase::new);
        public static final RegistryObject<Item> STAFF_BASE = ITEMS.register( "staff_base", StaffBase::new);

        public static final RegistryObject<Item> ASPECT_OF_ANT = ITEMS.register( "aspect_of_ant", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_BAT = ITEMS.register( "aspect_of_bat", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_BEAR = ITEMS.register( "aspect_of_bear", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_BLAZE = ITEMS.register( "aspect_of_blaze", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_CREEPER = ITEMS.register( "aspect_of_creeper", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_GHAST = ITEMS.register( "aspect_of_ghast", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_GUARDIAN = ITEMS.register( "aspect_of_guardian", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_PHANTOM = ITEMS.register( "aspect_of_phantom", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_SPIDER = ITEMS.register( "aspect_of_spider", ItemBase::new);
        public static final RegistryObject<Item> ASPECT_OF_VILLAGER = ITEMS.register( "aspect_of_villager", ItemBase::new);


        //Cosmetics
        public static final RegistryObject<Item> CAMONG_US = ITEMS.register("camong_us", CAmongUs::new);
        public static final RegistryObject<Item> CCRASH_BANDICOOT = ITEMS.register("ccrash_bandicoot", CCrashBandicoot::new);
        public static final RegistryObject<Item> CDREAM = ITEMS.register("cdream", CDream::new);
        public static final RegistryObject<Item> CGIRL = ITEMS.register("cgirl", CGirl::new);
        public static final RegistryObject<Item> CMIDDLE_FINGER = ITEMS.register("cmiddle_finger", CMiddleFinger::new);
        public static final RegistryObject<Item> CNETERO = ITEMS.register("cnetero", CNetero::new);
        public static final RegistryObject<Item> COBAMIUM_PYRAMID = ITEMS.register("cobamium_pyramid", CObamiumPyramid::new);

        //One-time use
        public static final RegistryObject<Item> RUBY = ITEMS.register( "ruby", ItemBase::new);
        public static final RegistryObject<Item> CRYSTALLIZEDNEN = ITEMS.register( "crystal_nen", Crystal_Nen::new);
        public static final RegistryObject<Item> DUPLICATOR = ITEMS.register( "duplicator", Duplicator::new);

        //Repeatable use
        public static final RegistryObject<Item> SELF_DESTRUCT_BUTTON = ITEMS.register( "self_destruct_button", SelfDestructButton::new);
        public static final RegistryObject<Item> GON_FISHING_POLE = ITEMS.register( "gon_fishing_pole", GonFishingPole::new);

            //Staffs
            public static final RegistryObject<Item> BAT_STAFF = ITEMS.register( "bat_staff", BatStaff::new);
            public static final RegistryObject<Item> BLAZE_STAFF = ITEMS.register( "blaze_staff", BlazeStaff::new);
            public static final RegistryObject<Item> CHIMERA_STAFF = ITEMS.register( "chimera_staff", ChimeraAntStaff::new);
            public static final RegistryObject<Item> CREEPER_STAFF = ITEMS.register( "creeper_staff", CreeperStaff::new);
            public static final RegistryObject<Item> FOXBEAR_STAFF = ITEMS.register( "foxbear_staff", FoxbearStaff::new);
            public static final RegistryObject<Item> GHAST_STAFF = ITEMS.register( "ghast_staff", GhastStaff::new);
            public static final RegistryObject<Item> GUARDIAN_STAFF = ITEMS.register( "guardian_staff", GuardianStaff::new);
            public static final RegistryObject<Item> PHANTOM_STAFF = ITEMS.register( "phantom_staff", PhantomStaff::new);
            public static final RegistryObject<Item> SPIDER_STAFF = ITEMS.register( "spider_staff", SpiderStaff::new);
            public static final RegistryObject<Item> VILLAGER_STAFF = ITEMS.register( "villager_staff", VillagerStaff::new);

        //Food
        public static final RegistryObject<Item> TASTY_FOOD = ITEMS.register( "tasty_food", TastyFood::new);
        public static final RegistryObject<Item> ROASTED_PORK_DISH = ITEMS.register( "roasted_pork_dish", RoastedPorkDish::new);
        public static final RegistryObject<Item> POTATO_SOUP = ITEMS.register( "potato_soup", PotatoSoup::new);
        public static final RegistryObject<Item> SPIDER_EAGLE_EGG = ITEMS.register( "spider_eagle_egg", SpiderEagleEgg::new);

    //Cards
    public static final RegistryObject<Item> CARD_1 = ITEMS.register("card_1", ItemBase::new);
    public static final RegistryObject<Item> CARD_2 = ITEMS.register("card_2", ItemBase::new);
    public static final RegistryObject<Item> CARD_3 = ITEMS.register("card_3", ItemBase::new);
    public static final RegistryObject<Item> CARD_4 = ITEMS.register("card_4", ItemBase::new);
    public static final RegistryObject<Item> CARD_5 = ITEMS.register("card_5", ItemBase::new);
    public static final RegistryObject<Item> CARD_6 = ITEMS.register("card_6", ItemBase::new);
    public static final RegistryObject<Item> CARD_7 = ITEMS.register("card_7", ItemBase::new);
    public static final RegistryObject<Item> CARD_8 = ITEMS.register("card_8", ItemBase::new);
    public static final RegistryObject<Item> CARD_9 = ITEMS.register("card_9", ItemBase::new);
    public static final RegistryObject<Item> CARD_10 = ITEMS.register("card_10", ItemBase::new);

    public static final RegistryObject<Item> CARD_11 = ITEMS.register("card_11", ItemBase::new);
    public static final RegistryObject<Item> CARD_12 = ITEMS.register("card_12", ItemBase::new);
    public static final RegistryObject<Item> CARD_13 = ITEMS.register("card_13", ItemBase::new);
    public static final RegistryObject<Item> CARD_14 = ITEMS.register("card_14", ItemBase::new);
    public static final RegistryObject<Item> CARD_15 = ITEMS.register("card_15", ItemBase::new);
    public static final RegistryObject<Item> CARD_16 = ITEMS.register("card_16", ItemBase::new);
    public static final RegistryObject<Item> CARD_17 = ITEMS.register("card_17", ItemBase::new);
    public static final RegistryObject<Item> CARD_18 = ITEMS.register("card_18", ItemBase::new);
    public static final RegistryObject<Item> CARD_19 = ITEMS.register("card_19", ItemBase::new);
    public static final RegistryObject<Item> CARD_20 = ITEMS.register("card_20", ItemBase::new);

    public static final RegistryObject<Item> CARD_21 = ITEMS.register("card_21", ItemBase::new);
    public static final RegistryObject<Item> CARD_22 = ITEMS.register("card_22", ItemBase::new);
    public static final RegistryObject<Item> CARD_23 = ITEMS.register("card_23", ItemBase::new);
    public static final RegistryObject<Item> CARD_24 = ITEMS.register("card_24", ItemBase::new);
    public static final RegistryObject<Item> CARD_25 = ITEMS.register("card_25", ItemBase::new);
    public static final RegistryObject<Item> CARD_26 = ITEMS.register("card_26", ItemBase::new);
    public static final RegistryObject<Item> CARD_27 = ITEMS.register("card_27", ItemBase::new);
    public static final RegistryObject<Item> CARD_28 = ITEMS.register("card_28", ItemBase::new);
    public static final RegistryObject<Item> CARD_29 = ITEMS.register("card_29", ItemBase::new);
    public static final RegistryObject<Item> CARD_30 = ITEMS.register("card_30", ItemBase::new);

    public static final RegistryObject<Item> CARD_31 = ITEMS.register("card_31", ItemBase::new);
    public static final RegistryObject<Item> CARD_32 = ITEMS.register("card_32", ItemBase::new);
    public static final RegistryObject<Item> CARD_33 = ITEMS.register("card_33", ItemBase::new);
    public static final RegistryObject<Item> CARD_34 = ITEMS.register("card_34", ItemBase::new);
    public static final RegistryObject<Item> CARD_35 = ITEMS.register("card_35", ItemBase::new);
    public static final RegistryObject<Item> CARD_36 = ITEMS.register("card_36", ItemBase::new);
    public static final RegistryObject<Item> CARD_37 = ITEMS.register("card_37", ItemBase::new);
    public static final RegistryObject<Item> CARD_38 = ITEMS.register("card_38", ItemBase::new);
    public static final RegistryObject<Item> CARD_39 = ITEMS.register("card_39", ItemBase::new);
    public static final RegistryObject<Item> CARD_40 = ITEMS.register("card_40", ItemBase::new);

    public static final RegistryObject<Item> CARD_41 = ITEMS.register("card_41", ItemBase::new);
    public static final RegistryObject<Item> CARD_42 = ITEMS.register("card_42", ItemBase::new);
    public static final RegistryObject<Item> CARD_43 = ITEMS.register("card_43", ItemBase::new);
    public static final RegistryObject<Item> CARD_44 = ITEMS.register("card_44", ItemBase::new);
    public static final RegistryObject<Item> CARD_45 = ITEMS.register("card_45", ItemBase::new);
    public static final RegistryObject<Item> CARD_46 = ITEMS.register("card_46", ItemBase::new);
    public static final RegistryObject<Item> CARD_47 = ITEMS.register("card_47", ItemBase::new);
    public static final RegistryObject<Item> CARD_48 = ITEMS.register("card_48", ItemBase::new);
    public static final RegistryObject<Item> CARD_49 = ITEMS.register("card_49", ItemBase::new);
    public static final RegistryObject<Item> CARD_50 = ITEMS.register("card_50", ItemBase::new);

    public static final RegistryObject<Item> CARD_51 = ITEMS.register("card_51", ItemBase::new);
    public static final RegistryObject<Item> CARD_52 = ITEMS.register("card_52", ItemBase::new);
    public static final RegistryObject<Item> CARD_53 = ITEMS.register("card_53", ItemBase::new);
    public static final RegistryObject<Item> CARD_54 = ITEMS.register("card_54", ItemBase::new);
    public static final RegistryObject<Item> CARD_55 = ITEMS.register("card_55", ItemBase::new);
    public static final RegistryObject<Item> CARD_56 = ITEMS.register("card_56", ItemBase::new);
    public static final RegistryObject<Item> CARD_57 = ITEMS.register("card_57", ItemBase::new);
    public static final RegistryObject<Item> CARD_58 = ITEMS.register("card_58", ItemBase::new);
    public static final RegistryObject<Item> CARD_59 = ITEMS.register("card_59", ItemBase::new);
    public static final RegistryObject<Item> CARD_60 = ITEMS.register("card_60", ItemBase::new);

    public static final RegistryObject<Item> CARD_61 = ITEMS.register("card_61", ItemBase::new);
    public static final RegistryObject<Item> CARD_62 = ITEMS.register("card_62", ItemBase::new);
    public static final RegistryObject<Item> CARD_63 = ITEMS.register("card_63", ItemBase::new);
    public static final RegistryObject<Item> CARD_64 = ITEMS.register("card_64", ItemBase::new);
    public static final RegistryObject<Item> CARD_65 = ITEMS.register("card_65", ItemBase::new);
    public static final RegistryObject<Item> CARD_66 = ITEMS.register("card_66", ItemBase::new);
    public static final RegistryObject<Item> CARD_67 = ITEMS.register("card_67", ItemBase::new);
    public static final RegistryObject<Item> CARD_68 = ITEMS.register("card_68", ItemBase::new);
    public static final RegistryObject<Item> CARD_69 = ITEMS.register("card_69", ItemBase::new);
    public static final RegistryObject<Item> CARD_70 = ITEMS.register("card_70", ItemBase::new);

    public static final RegistryObject<Item> CARD_71 = ITEMS.register("card_71", ItemBase::new);
    public static final RegistryObject<Item> CARD_72 = ITEMS.register("card_72", ItemBase::new);
    public static final RegistryObject<Item> CARD_73 = ITEMS.register("card_73", ItemBase::new);
    public static final RegistryObject<Item> CARD_74 = ITEMS.register("card_74", ItemBase::new);
    public static final RegistryObject<Item> CARD_75 = ITEMS.register("card_75", ItemBase::new);
    public static final RegistryObject<Item> CARD_76 = ITEMS.register("card_76", ItemBase::new);
    public static final RegistryObject<Item> CARD_77 = ITEMS.register("card_77", ItemBase::new);
    public static final RegistryObject<Item> CARD_78 = ITEMS.register("card_78", ItemBase::new);
    public static final RegistryObject<Item> CARD_79 = ITEMS.register("card_79", ItemBase::new);
    public static final RegistryObject<Item> CARD_80 = ITEMS.register("card_80", ItemBase::new);

    public static final RegistryObject<Item> CARD_81 = ITEMS.register("card_81", ItemBase::new);
    public static final RegistryObject<Item> CARD_82 = ITEMS.register("card_82", ItemBase::new);
    public static final RegistryObject<Item> CARD_83 = ITEMS.register("card_83", ItemBase::new);
    public static final RegistryObject<Item> CARD_84 = ITEMS.register("card_84", ItemBase::new);
    public static final RegistryObject<Item> CARD_85 = ITEMS.register("card_85", ItemBase::new);
    public static final RegistryObject<Item> CARD_86 = ITEMS.register("card_86", ItemBase::new);
    public static final RegistryObject<Item> CARD_87 = ITEMS.register("card_87", ItemBase::new);
    public static final RegistryObject<Item> CARD_88 = ITEMS.register("card_88", ItemBase::new);
    public static final RegistryObject<Item> CARD_89 = ITEMS.register("card_89", ItemBase::new);
    public static final RegistryObject<Item> CARD_90 = ITEMS.register("card_90", ItemBase::new);

    public static final RegistryObject<Item> CARD_91 = ITEMS.register("card_91", ItemBase::new);
    public static final RegistryObject<Item> CARD_92 = ITEMS.register("card_92", ItemBase::new);
    public static final RegistryObject<Item> CARD_93 = ITEMS.register("card_93", ItemBase::new);
    public static final RegistryObject<Item> CARD_94 = ITEMS.register("card_94", ItemBase::new);
    public static final RegistryObject<Item> CARD_95 = ITEMS.register("card_95", ItemBase::new);
    public static final RegistryObject<Item> CARD_96 = ITEMS.register("card_96", ItemBase::new);
    public static final RegistryObject<Item> CARD_97 = ITEMS.register("card_97", ItemBase::new);
    public static final RegistryObject<Item> CARD_98 = ITEMS.register("card_98", ItemBase::new);
    public static final RegistryObject<Item> CARD_99 = ITEMS.register("card_99", ItemBase::new);
    public static final RegistryObject<Item> CARD_100 = ITEMS.register("card_100", ItemBase::new);

    //Dev Tools
    public static final RegistryObject<Item> CLEARING_TOOL = ITEMS.register("clearing", Clearing::new);

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> CHESS_TABLE_BLOCK = BLOCKS.register("chess_table_block", ChessTable::new);
    public static final RegistryObject<Block> NEN_LIGHT = BLOCKS.register("nenlight", NenLight::new);
    public static final RegistryObject<Block> GREED_ISLAND_PORTAL = BLOCKS.register("twilight_portal", PortalBlock::new);
    public static final RegistryObject<Block> SATURATION_STAND = BLOCKS.register("saturation_stand", SaturationStand::new);
    public static final RegistryObject<Block> VAT = BLOCKS.register("vat", Vat::new);
    public static final RegistryObject<Block> CONJURER_BLOCK = BLOCKS.register("conjurer_block", ConjurerBlock::new);
    public static final RegistryObject<Block> SUPER_COBWEB = BLOCKS.register("super_cobweb", () -> new SuperCobweb(AbstractBlock.Properties.create(Material.WEB).doesNotBlockMovement().setRequiresTool().hardnessAndResistance(4.0F).variableOpacity().notSolid()));
    public static final RegistryObject<Block> AURA_STONE = BLOCKS.register("aura_stone", AuraStone::new);
    public static final RegistryObject<Block> UR_A_WIZARD = BLOCKS.register("ur_a_wizard", UrAWizardHarry::new);
    public static final RegistryObject<Block> SHIFTY_BLOCK = BLOCKS.register("shifty_block", ShiftyBlock::new);
    public static final RegistryObject<Block> SPIDER_EAGLE_EGG_BLOCK = BLOCKS.register("spider_eagle_egg_block", () -> new SpiderEagleEggBlock(AbstractBlock.Properties.create(Material.DRAGON_EGG, MaterialColor.SAND).hardnessAndResistance(0.5F).sound(SoundType.METAL).notSolid()));

    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> TWILIGHT_PORTAL_ITEM = ITEMS.register("twilight_portal", () -> new BlockItemBase(GREED_ISLAND_PORTAL.get()));
    public static final RegistryObject<Item> SATURATION_STAND_ITEM = ITEMS.register("saturation_stand", () -> new BlockItemBase(SATURATION_STAND.get()));
    public static final RegistryObject<Item> SUPER_COBWEB_ITEM = ITEMS.register("super_cobweb", () -> new BlockItemBase(SUPER_COBWEB.get()));
    public static final RegistryObject<Item> SPIDER_EAGLE_EGG_ITEM = ITEMS.register("spider_eagle_egg_item", () -> new BlockItemBase(SPIDER_EAGLE_EGG_BLOCK.get()));
    public static final RegistryObject<Item> AURA_STONE_ITEM = ITEMS.register("aura_stone", () -> new BlockItemBase(AURA_STONE.get()));
    public static final RegistryObject<Item> VAT_ITEM = ITEMS.register("vat", () -> new BlockItemBase(VAT.get()));
    public static final RegistryObject<Item> SHIFTY_BLOCK_ITEM = ITEMS.register("shifty_block", () -> new BlockItemBase(SHIFTY_BLOCK.get()));


    //Recipes
    public static final RegistryObject<VatRecipeSerializer> VAT_RECIPE_SERIALIZER = RECIPES.register("vat", () -> new VatRecipeSerializer(VatRecipes::new));

    //Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT_TILE_ENTITY = TILE_ENTITY_TYPES.register("nen_light_tile_entity", () -> TileEntityType.Builder.create(TileEntityNenLight::new, NEN_LIGHT.get()).build(null));
    public static final RegistryObject<TileEntityType<ShiftyTileEntity>> SHIFTY_TILE_ENTITY = TILE_ENTITY_TYPES.register("shifty_tile_entity", () -> TileEntityType.Builder.create(ShiftyTileEntity::new, SHIFTY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityConjurerBlock>> CONJURER_BLOCK_TILE_ENTITY = TILE_ENTITY_TYPES.register("conjurer_tile_entity", () -> TileEntityType.Builder.create(TileEntityConjurerBlock::new, CONJURER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<SaturationStandTileEntity>> SATURATION_STAND_TILE_ENTITY = TILE_ENTITY_TYPES.register("saturation_stand", () -> TileEntityType.Builder.create(SaturationStandTileEntity::new, SATURATION_STAND.get()).build(null));
    public static final RegistryObject<TileEntityType<VatTileEntity>> VAT_TILE_ENTITY = TILE_ENTITY_TYPES.register("vat_tile_entity", () -> TileEntityType.Builder.create(() -> new VatTileEntity(), VAT.get()).build(null));

    //Containers
    public static final RegistryObject<ContainerType<GreedIslandContainer>> GREED_ISLAND_CONTAINER = CONTAINER.register("greedislandbook", () -> IForgeContainerType.create(GreedIslandContainer::createContainerClientSide));

    //Entities
    public static final RegistryObject<EntityType<Obama>> OBAMA_ENTITY = ENTITY_TYPES.register("obama", () -> EntityType.Builder.<Obama>create(Obama::new, EntityClassification.MONSTER)
            .size(.5f, .5f)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<AmongUs>> AMONG_US_ENTITY = ENTITY_TYPES.register("among_us", () -> EntityType.Builder.<AmongUs>create(AmongUs::new, EntityClassification.MONSTER)
            .size(.5f, .5f)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<MiddleFinger>> MIDDLE_FINGER_ENTITY = ENTITY_TYPES.register("middle_finger", () -> EntityType.Builder.<MiddleFinger>create(MiddleFinger::new, EntityClassification.MONSTER)
            .size(5.5f, 5.5f)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<ShiapoufClone>> SHIAPOUF_CLONE_ENTITY = ENTITY_TYPES.register("shiapouf_clone", () -> EntityType.Builder.<ShiapoufClone>create(ShiapoufClone::new, EntityClassification.MONSTER)
            .size(.5f, .5f)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Youpi>> YOUPI_ENTITY = ENTITY_TYPES.register("youpi", () -> EntityType.Builder.<Youpi>create(Youpi::new, EntityClassification.MONSTER)
            .size(3.2f, 6.5f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Neferpitou>> NEFERPITOU_ENTITY = ENTITY_TYPES.register("neferpitou", () -> EntityType.Builder.<Neferpitou>create(Neferpitou::new, EntityClassification.MONSTER)
            .size(1.8f, 3.7f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Shiapouf>> SHIAPOUF_ENTITY = ENTITY_TYPES.register("shiapouf", () -> EntityType.Builder.<Shiapouf>create(Shiapouf::new, EntityClassification.MONSTER)
            .size(2f, 5f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<ChimeraAnt>> CHIMERA_ANT_ENTITY = ENTITY_TYPES.register("chimera_ant", () -> EntityType.Builder.<ChimeraAnt>create(ChimeraAnt::new, EntityClassification.MONSTER)
            .size(1.6f, 3.4f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<FoxBear>> FOXBEAR_ENTITY = ENTITY_TYPES.register("foxbear", () -> EntityType.Builder.<FoxBear>create(FoxBear::new, EntityClassification.MONSTER)
            .size(2f, 2.2f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<GiantLizard>> GIANT_LIZARD_ENTITY = ENTITY_TYPES.register("giant_lizard", () -> EntityType.Builder.<GiantLizard>create(GiantLizard::new, EntityClassification.CREATURE)
            .size(1.5f, 2f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));


    public static final RegistryObject<EntityType<ConjurerMount>> CONJURER_MOUNT = ENTITY_TYPES.register("conjurer_mount", () -> EntityType.Builder.<ConjurerMount>create(ConjurerMount::new, EntityClassification.CREATURE)
            .size(2.2f, 3f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<ManipulatorTpProjectile>> BASE_MAGIC_PROJECTILE = ENTITY_TYPES.register("base_magic_projectile", () -> EntityType.Builder.<ManipulatorTpProjectile>create(ManipulatorTpProjectile::new, EntityClassification.MISC)
            .size(.25f, .25f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .func_233608_b_(10)
            .build(""));

    public static final RegistryObject<EntityType<EmitterBaseProjectile>> NO_GRAVITY_PROJECTILE = ENTITY_TYPES.register("no_gravity_projectile", () -> EntityType.Builder.<EmitterBaseProjectile>create(EmitterBaseProjectile::new, EntityClassification.MISC)
            .size(.25f, .25f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .func_233608_b_(10)
            .build(""));

    public static final RegistryObject<EntityType<CameraEntity>> CAMERA_ENTITY = ENTITY_TYPES.register("invisible_camera_entity", () -> EntityType.Builder.<CameraEntity>create(CameraEntity::new, EntityClassification.CREATURE)
            .size(.25f, .25f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));


    //Point Of Interests
    public static final RegistryObject<PointOfInterestType> MASADORIAN_POI = POINT_OF_INTEREST.register("masadorian_poi", () -> new PointOfInterestType("masadorian_poi", ImmutableSet.copyOf(RUBY_BLOCK.get().getStateContainer().getValidStates()), 1, 1));
    public static final RegistryObject<PointOfInterestType> CHESS_MASTER_POI = POINT_OF_INTEREST.register("chess_master_poi", () -> new PointOfInterestType("chess_master_poi", ImmutableSet.copyOf(CHESS_TABLE_BLOCK.get().getStateContainer().getValidStates()), 1, 1));

    //Professions
    public static final RegistryObject<VillagerProfession> MASADORIAN = PROFESSIONS.register("masadorian", () -> new VillagerProfession("masadorian", MASADORIAN_POI.get(), VillagerProfession.CARTOGRAPHER.getSpecificItems(),  VillagerProfession.CARTOGRAPHER.getRelatedWorldBlocks(), COOKIECHAN.get()));
    public static final RegistryObject<VillagerProfession> CHESS_MASTER = PROFESSIONS.register("chess_master", () -> new VillagerProfession("chess_master", CHESS_MASTER_POI.get(), VillagerProfession.CARTOGRAPHER.getSpecificItems(),  VillagerProfession.CARTOGRAPHER.getRelatedWorldBlocks(), COOKIECHAN.get()));

    //FoliagePlacer

    //Features
    public static final Feature<WorldTreeConfig2> WORLD_TREE_FEATURE = new WorldTreeFeature2();
    public static final Feature<GravityMineralsConfig> GRAVITY_MINERALS_FEATURE = new GravityMinerals();

    //Configured Features
    public static final ConfiguredFeature<?, ?> WORLD_TREE_FEATURE_CONFIG = WORLD_TREE_FEATURE
            .withConfiguration(new WorldTreeConfig2(23, 4, 65, 0.05))
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(20)));
    public static final ConfiguredFeature<?, ?> GRAVITY_MINERALS_FEATURE_CONFIG = GRAVITY_MINERALS_FEATURE
            .withConfiguration(new GravityMineralsConfig(4, 18, 18))
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(10)));

    //Older feature stuff here
    public static final RegistryObject<Feature<BaseWorldTreeFeatureConfig>> WORLD_TREE = FEATURES.register("world_tree", () -> new WorldTreeFeature(BaseWorldTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<OreFeatureConfig>> ORE_AURA = FEATURES.register("aura_stone", () -> new OreFeature(OreFeatureConfig.CODEC));



    //Structures
    public static final RegistryObject<Structure<NoFeatureConfig>> WORLD_TREE_STRUCTURE = STRUCTURES.register("wttrunk",() -> new WorldTreeStructure(NoFeatureConfig.field_236558_a_));
    //public static final StructureFeature<?, ?> CONFIGURED_WORLD_TREE = WORLD_TREE_STRUCTURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static final IStructurePieceType WTTRUNK = RegistryHandler.registerPiece(new ResourceLocation(MOD_ID, "wttrunk").toString(), ComponentTrunk::new);

    //Carver
    public static final RegistryObject<WorldCarver<ProbabilityConfig>> SPIDER_EAGLE_CARVER = CARVER.register("spider_eagle_carver", () -> new SpiderEagleCarver(ProbabilityConfig.CODEC, 256));

    //Biomes
    public static final RegistryKey<Biome> WORLD_TREE_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(MOD_ID, "world_tree_biome"));
    public static final RegistryKey<Biome> SPIDER_EAGLE_KEY = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(MOD_ID, "spider_eagle_biome"));

    public static final RegistryObject<Biome> WORLD_TREE_BIOME = BIOMES.register("world_tree_biome", () -> new Biome.Builder().
            precipitation(Biome.RainType.RAIN).
            category(Biome.Category.SAVANNA).
            depth(0.1F).
            scale(0.2F).
            temperature(0.8F).
            downfall(0.8F).
            withGenerationSettings(new BiomeGenerationSettings.Builder().
                    //withFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(), () -> Features.SPRING_OPEN).
                    withSurfaceBuilder(() -> SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.GRASS_DIRT_SAND_CONFIG)).build()).
            withMobSpawnSettings(MobSpawnInfo.EMPTY).
            setEffects((new BiomeAmbience.Builder()).
                    setWaterColor(7098023).
                    setMusic(new BackgroundMusicSelector(SoundEvents.MUSIC_CREATIVE, 0, 50, true)).
                    setWaterFogColor(14733798).
                    setFogColor(16627961).
                    withSkyColor(14733798).
                    build()).
            build());

    public static final RegistryObject<Biome> SPIDER_EAGLE_BIOME = BIOMES.register("spider_eagle_biome", () -> new Biome.Builder().
            precipitation(Biome.RainType.NONE).
            category(Biome.Category.DESERT).
            depth(.1F).
            scale(0.001F).
            temperature(1.4F).
            downfall(0.3F).
            withGenerationSettings(new BiomeGenerationSettings.Builder().
                    withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION.ordinal(), () -> Features.FOSSIL).
                    withSurfaceBuilder(() -> SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.TERRACOTTA.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState()))).build()).
            withMobSpawnSettings(MobSpawnInfo.EMPTY).
            setEffects((new BiomeAmbience.Builder()).
                    setWaterColor(5098023).
                    setMusic(new BackgroundMusicSelector(SoundEvents.MUSIC_CREATIVE, 0, 50, true)).
                    setWaterFogColor(9733798).
                    setFogColor(6627961).
                    withSkyColor(4733798).
                    build()).
            build());
}