package com.chubbychump.hunterxhunter.client.sounds;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public class DeathMusic implements ISound {
    private ResourceLocation location;
    private SoundEventAccessor soundEvent;
    private Sound sound;

    public DeathMusic() {
        location = new ResourceLocation(HunterXHunter.MOD_ID, "hisoka");
        try {
            createAccessor(Minecraft.getInstance().getSoundHandler());
        } catch (Throwable e) {}
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return location;
    }

    @Override
    public SoundEventAccessor createAccessor(SoundHandler handler) {
        soundEvent = handler.getAccessor(location);

        if (soundEvent == null)
            sound = SoundHandler.MISSING_SOUND;
        else
            sound = soundEvent.cloneEntry();

        return soundEvent;
    }

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public SoundCategory getCategory() {
        return SoundCategory.PLAYERS;
    }

    @Override
    public boolean canRepeat() {
        return false;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public int getRepeatDelay() {
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
    public AttenuationType getAttenuationType() {
        return AttenuationType.LINEAR;
    }
}
