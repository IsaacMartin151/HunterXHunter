package com.registry;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.hunterxhunter.HunterXHunter.MODID;
import static com.registry.Blocks.EXAMPLE_BLOCK;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> OSU = SOUNDS.register("osu", () -> new SoundEvent(new ResourceLocation(MODID, "osu")));
    public static final RegistryObject<SoundEvent> COOKIECHAN = SOUNDS.register("cookiechan", () -> new SoundEvent(new ResourceLocation(MODID, "cookiechan")));
    public static final RegistryObject<SoundEvent> EMBARK_ADVENTURE = SOUNDS.register("embarkadventure", () -> new SoundEvent(new ResourceLocation(MODID, "embarkadventure")));
    public static final RegistryObject<SoundEvent> WORLD_OF_ADVENTURES = SOUNDS.register("worldofadventures", () -> new SoundEvent(new ResourceLocation(MODID, "worldofadventures")));
    public static final RegistryObject<SoundEvent> DEPARTURE = SOUNDS.register("departure", () -> new SoundEvent(new ResourceLocation(MODID, "departure")));
    public static final RegistryObject<SoundEvent> HISOKA = SOUNDS.register("hisoka", () -> new SoundEvent(new ResourceLocation(MODID, "hisoka")));
    public static final RegistryObject<SoundEvent> STONESLIDE = SOUNDS.register("stoneslide", () -> new SoundEvent(new ResourceLocation(MODID, "stoneslide")));
    public static final RegistryObject<SoundEvent> OPEN_BOOK = SOUNDS.register("openbook", () -> new SoundEvent(new ResourceLocation(MODID, "openbook")));
    public static final RegistryObject<SoundEvent> LOUNGE_MUSIC = SOUNDS.register("loungemusic", () -> new SoundEvent(new ResourceLocation(MODID, "loungemusic")));
    public static final RegistryObject<SoundEvent> WIND = SOUNDS.register("wind", () -> new SoundEvent(new ResourceLocation(MODID, "wind")));
}
