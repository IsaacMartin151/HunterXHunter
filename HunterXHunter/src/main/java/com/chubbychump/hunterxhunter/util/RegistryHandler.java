package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.client.gui.GreedIslandContainer;
import com.chubbychump.hunterxhunter.common.blocks.*;
import com.chubbychump.hunterxhunter.common.entities.entityclasses.*;
import com.chubbychump.hunterxhunter.common.generation.BaseWorldTreeFeatureConfig;
import com.chubbychump.hunterxhunter.common.generation.SpiderEagleCarver;
import com.chubbychump.hunterxhunter.common.generation.WorldTreeFeature;
import com.chubbychump.hunterxhunter.common.items.ItemBase;
import com.chubbychump.hunterxhunter.common.items.StaffBase;
import com.chubbychump.hunterxhunter.common.items.thehundred.crafting.*;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.PotatoSoup;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.RoastedPorkDish;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.SpiderEagleEgg;
import com.chubbychump.hunterxhunter.common.items.thehundred.food.TastyFood;
import com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse.Crystal_Nen;
import com.chubbychump.hunterxhunter.common.items.thehundred.onetimeuse.Duplicator;
import com.chubbychump.hunterxhunter.common.items.thehundred.placeable.SaturationStand;
import com.chubbychump.hunterxhunter.common.items.thehundred.tools.*;
import com.chubbychump.hunterxhunter.common.potions.BloodLust;
import com.chubbychump.hunterxhunter.common.potions.BloodLustEffect;
import com.chubbychump.hunterxhunter.common.recipes.VatRecipeSerializer;
import com.chubbychump.hunterxhunter.common.recipes.VatRecipes;
import com.chubbychump.hunterxhunter.common.tileentities.SaturationStandTileEntity;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityConjurerBlock;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.common.tileentities.VatTileEntity;
import com.google.common.collect.ImmutableSet;
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
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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

    //TODO: Register shiapouf clone entity

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
        BIOMES.register(bus);
        FEATURES.register(bus);
        STRUCTURES.register(bus);

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
    public static final RegistryObject<SoundEvent> STONESLIDE = SOUNDS.register("stoneslide", () -> new SoundEvent(new ResourceLocation(MOD_ID, "stoneslide")));
    public static final RegistryObject<SoundEvent> OPEN_BOOK = SOUNDS.register("openbook", () -> new SoundEvent(new ResourceLocation(MOD_ID, "openbook")));
    public static final RegistryObject<SoundEvent> LOUNGE_MUSIC = SOUNDS.register("loungemusic", () -> new SoundEvent(new ResourceLocation(MOD_ID, "loungemusic")));
    public static final RegistryObject<SoundEvent> EMBARK_ADVENTURE = SOUNDS.register("embarkadventure", () -> new SoundEvent(new ResourceLocation(MOD_ID, "embarkadventure")));
    public static final RegistryObject<SoundEvent> WIND = SOUNDS.register("wind", () -> new SoundEvent(new ResourceLocation(MOD_ID, "wind")));
    public static final RegistryObject<SoundEvent> URAWIZARD = SOUNDS.register("urawizard", () -> new SoundEvent(new ResourceLocation(MOD_ID, "urawizard")));


    //Items
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

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> NEN_LIGHT = BLOCKS.register("nenlight", NenLight::new);
    public static final RegistryObject<Block> GREED_ISLAND_PORTAL = BLOCKS.register("twilight_portal", PortalBlock::new);
    public static final RegistryObject<Block> SATURATION_STAND = BLOCKS.register("saturation_stand", SaturationStand::new);
    public static final RegistryObject<Block> VAT = BLOCKS.register("vat", Vat::new);
    public static final RegistryObject<Block> CONJURER_BLOCK = BLOCKS.register("conjurer_block", ConjurerBlock::new);
    public static final RegistryObject<Block> SUPER_COBWEB = BLOCKS.register("super_cobweb", () -> new SuperCobweb(AbstractBlock.Properties.create(Material.WEB).doesNotBlockMovement().setRequiresTool().hardnessAndResistance(4.0F).variableOpacity().notSolid()));
    public static final RegistryObject<Block> AURA_STONE = BLOCKS.register("aura_stone", AuraStone::new);
    public static final RegistryObject<Block> UR_A_WIZARD = BLOCKS.register("ur_a_wizard", UrAWizardHarry::new);
    public static final RegistryObject<Block> SPIDER_EAGLE_EGG_BLOCK = BLOCKS.register("spider_eagle_egg_block", () -> new SpiderEagleEggBlock(AbstractBlock.Properties.create(Material.DRAGON_EGG, MaterialColor.SAND).hardnessAndResistance(0.5F).sound(SoundType.METAL).notSolid()));

    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> TWILIGHT_PORTAL_ITEM = ITEMS.register("twilight_portal", () -> new BlockItemBase(GREED_ISLAND_PORTAL.get()));
    public static final RegistryObject<Item> SATURATION_STAND_ITEM = ITEMS.register("saturation_stand", () -> new BlockItemBase(SATURATION_STAND.get()));
    public static final RegistryObject<Item> SUPER_COBWEB_ITEM = ITEMS.register("super_cobweb", () -> new BlockItemBase(SUPER_COBWEB.get()));
    public static final RegistryObject<Item> SPIDER_EAGLE_EGG_ITEM = ITEMS.register("spider_eagle_egg_item", () -> new BlockItemBase(SPIDER_EAGLE_EGG_BLOCK.get()));
    public static final RegistryObject<Item> AURA_STONE_ITEM = ITEMS.register("aura_stone", () -> new BlockItemBase(AURA_STONE.get()));
    public static final RegistryObject<Item> VAT_ITEM = ITEMS.register("vat", () -> new BlockItemBase(VAT.get()));


    //Recipes
    public static final RegistryObject<VatRecipeSerializer> VAT_RECIPE_SERIALIZER = RECIPES.register("vat", () -> new VatRecipeSerializer(VatRecipes::new));

    //Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT_TILE_ENTITY = TILE_ENTITY_TYPES.register("nen_light_tile_entity", () -> TileEntityType.Builder.create(TileEntityNenLight::new, NEN_LIGHT.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityConjurerBlock>> CONJURER_BLOCK_TILE_ENTITY = TILE_ENTITY_TYPES.register("conjurer_tile_entity", () -> TileEntityType.Builder.create(TileEntityConjurerBlock::new, CONJURER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<SaturationStandTileEntity>> SATURATION_STAND_TILE_ENTITY = TILE_ENTITY_TYPES.register("saturation_stand", () -> TileEntityType.Builder.create(SaturationStandTileEntity::new, SATURATION_STAND.get()).build(null));
    public static final RegistryObject<TileEntityType<VatTileEntity>> VAT_TILE_ENTITY = TILE_ENTITY_TYPES.register("vat_tile_entity", () -> TileEntityType.Builder.create(() -> new VatTileEntity(), VAT.get()).build(null));

    //Containers
    public static final RegistryObject<ContainerType<GreedIslandContainer>> GREED_ISLAND_CONTAINER = CONTAINER.register("greedislandbook", () -> IForgeContainerType.create(GreedIslandContainer::createContainerClientSide));

    //Entities
    public static final RegistryObject<EntityType<ShiapoufClone>> SHIAPOUF_CLONE_ENTITY = ENTITY_TYPES.register("shiapoufclone", () -> EntityType.Builder.<ShiapoufClone>create(ShiapoufClone::new, EntityClassification.MONSTER)
            .size(.5f, .5f)
            .setTrackingRange(20)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Youpi>> YOUPI_ENTITY = ENTITY_TYPES.register("youpi", () -> EntityType.Builder.<Youpi>create(Youpi::new, EntityClassification.MONSTER)
            .size(3f, 3f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Neferpitou>> NEFERPITOU_ENTITY = ENTITY_TYPES.register("neferpitou", () -> EntityType.Builder.<Neferpitou>create(Neferpitou::new, EntityClassification.MONSTER)
            .size(2f, 3f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<Shiapouf>> SHIAPOUF_ENTITY = ENTITY_TYPES.register("shiapouf", () -> EntityType.Builder.<Shiapouf>create(Shiapouf::new, EntityClassification.MONSTER)
            .size(2f, 3f)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    public static final RegistryObject<EntityType<ChimeraAnt>> CHIMERA_ANT_ENTITY = ENTITY_TYPES.register("chimeraant", () -> EntityType.Builder.<ChimeraAnt>create(ChimeraAnt::new, EntityClassification.MONSTER)
            .size(1.2f, 2f)
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

    public static final RegistryObject<EntityType<GiantLizard>> GIANT_LIZARD_ENTITY = ENTITY_TYPES.register("giantlizard", () -> EntityType.Builder.<GiantLizard>create(GiantLizard::new, EntityClassification.CREATURE)
            .size(3f, 2f)
            .setTrackingRange(15)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(""));

    //Point Of Interests
    public static final RegistryObject<PointOfInterestType> MASADORIAN_POI = POINT_OF_INTEREST.register("masadorianpoi", () -> new PointOfInterestType("masadorianpoi", ImmutableSet.copyOf(RUBY_BLOCK.get().getStateContainer().getValidStates()), 1, 1));

    //Professions
    public static final RegistryObject<VillagerProfession> MASADORIAN = PROFESSIONS.register("masadorian", () -> new VillagerProfession("masadorian", MASADORIAN_POI.get(), VillagerProfession.CARTOGRAPHER.getSpecificItems(),  VillagerProfession.CARTOGRAPHER.getRelatedWorldBlocks(), COOKIECHAN.get()));

    //FoliagePlacer

    //Features
    public static final RegistryObject<Feature<BaseWorldTreeFeatureConfig>> WORLD_TREE = FEATURES.register("world_tree", () -> new WorldTreeFeature(BaseWorldTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<OreFeatureConfig>> ORE_AURA = FEATURES.register("aura_stone", () -> new OreFeature(OreFeatureConfig.CODEC));

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
                    withFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(), () -> Features.SPRING_OPEN).
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