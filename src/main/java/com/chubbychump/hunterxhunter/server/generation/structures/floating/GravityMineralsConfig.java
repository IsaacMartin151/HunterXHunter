package com.chubbychump.hunterxhunter.server.generation.structures.floating;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class GravityMineralsConfig implements FeatureConfiguration {
        public static final Codec<GravityMineralsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("tube_radius").forGetter(GravityMineralsConfig::getTubeRadius),
                Codec.INT.fieldOf("shape_radius_a").forGetter(GravityMineralsConfig::getShapeRadiusA),
                Codec.INT.fieldOf("shape_radius_b").forGetter(GravityMineralsConfig::getShapeRadiusB)
        ).apply(instance, GravityMineralsConfig::new));

        private final int tubeRadius;
        private final int shapeRadiusA;
        private final int shapeRadiusB;

        public GravityMineralsConfig(int tubeRadius, int shapeRadiusA, int shapeRadiusB) {
            this.tubeRadius = tubeRadius;
            this.shapeRadiusA = shapeRadiusA;
            this.shapeRadiusB = shapeRadiusB;
        }

        public int getTubeRadius() {
            return tubeRadius;
        }

        public int getShapeRadiusA() {
            return shapeRadiusA;
        }

        public int getShapeRadiusB() {
            return shapeRadiusB;
        }
}
