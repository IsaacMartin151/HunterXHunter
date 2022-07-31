package com.chubbychump.hunterxhunter.server.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.SimpleCriterion;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.loot.DeserializationContext;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class HalfwayTrigger extends SimpleCriterion<HalfwayTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "halfway");
    public static final HalfwayTrigger INSTANCE = new HalfwayTrigger();

    private HalfwayTrigger() {}

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
