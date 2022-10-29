package com.registry;

import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class DeathMusic implements SoundInstance {
    ResourceLocation location = new ResourceLocation(HunterXHunter.MODID, "hisoka");
    Sound sound;

    @Override
    public ResourceLocation getLocation() {
        return location;
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager soundManager) {
        sound = soundManager.getSoundEvent(location).getSound(RandomSource.create());
        return soundManager.getSoundEvent(location);
    }

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public SoundSource getSource() {
        return SoundSource.MUSIC;
    }

    @Override
    public boolean isLooping() {
        return true;
    }

    @Override
    public boolean isRelative() {
        return false;
    }

    @Override
    public int getDelay() {
        return 200;
    }

    @Override
    public float getVolume() {
        return 1;
    }

    @Override
    public float getPitch() {
        return 1;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }
}

