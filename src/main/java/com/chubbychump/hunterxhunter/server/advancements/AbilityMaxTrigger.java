package com.chubbychump.hunterxhunter.server.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class AbilityMaxTrigger extends SimpleCriterionTrigger<AbilityMaxTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "ability_max");
    public static final AbilityMaxTrigger INSTANCE = new AbilityMaxTrigger();

    private AbilityMaxTrigger() {}

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Nonnull
    @Override
    public Instance deserializeTrigger(@Nonnull JsonObject json, EntityPredicate.Composite playerPred, DeserializationContext conditions) {
        return new Instance(playerPred);
    }

    public void trigger(ServerPlayer player) {
        triggerListeners(player, instance -> instance.test());
    }

    static class Instance extends CriterionInstance {

        Instance(EntityPredicate.Composite playerPred) {
            super(ID, playerPred);
        }

        @Nonnull
        @Override
        public ResourceLocation getId() {
            return ID;
        }

        boolean test() {
            return true;
        }
    }
}
