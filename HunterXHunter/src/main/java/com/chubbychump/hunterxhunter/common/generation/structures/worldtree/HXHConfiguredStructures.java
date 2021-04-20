package com.chubbychump.hunterxhunter.common.generation.structures.worldtree;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.util.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class HXHConfiguredStructures {
    public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> WORLD_TREE_STRUCTURE_FEATURE =
            RegistryHandler.WORLD_TREE_STRUCTURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    private static <FC extends IFeatureConfig, F extends Structure<FC>> StructureFeature<FC, F> register(String name, StructureFeature<FC, F> structureFeature) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, name, structureFeature);
    }

    public static void registerStructureFeatures() {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE,
                new ResourceLocation(HunterXHunter.MOD_ID, "wttrunk"), WORLD_TREE_STRUCTURE_FEATURE);
    }
}