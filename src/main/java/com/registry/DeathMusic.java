package com.registry;

import com.example.hunterxhunter.HunterXHunter;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public class DeathMusic implements SoundInstance {
    ResourceLocation location = new ResourceLocation(HunterXHunter.MODID, "hisoka");

    @Override
    public ResourceLocation getLocation() {
        return location;
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager p_119841_) {
        return null;
    }

    @Override
    public Sound getSound() {
        return null;
    }

    @Override
    public SoundSource getSource() {
        return null;
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
        return 0;
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public float getPitch() {
        return 0;
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
        return null;
    }
}

