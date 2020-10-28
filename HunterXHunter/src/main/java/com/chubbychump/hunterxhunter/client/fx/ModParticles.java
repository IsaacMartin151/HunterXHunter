package com.chubbychump.hunterxhunter.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static com.chubbychump.hunterxhunter.client.core.helper.ShaderHelper.prefix;

public class ModParticles {

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, prefix(name), thing);
    }
    //public static final ParticleType<WispParticleData> WISP = new WispParticleType();
    public static final ParticleType<SparkleParticleData> SPARKLE = new SparkleParticleType();

    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> evt) {
        //register(evt.getRegistry(), "wisp", WISP);
        register(evt.getRegistry(), "sparkle", SPARKLE);
    }

    public static class FactoryHandler {
        public static void registerFactories(ParticleFactoryRegisterEvent evt) {
            //Minecraft.getInstance().particles.registerFactory(ModParticles.WISP, WispParticleType.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ModParticles.SPARKLE, SparkleParticleType.Factory::new);
        }
    }
}
