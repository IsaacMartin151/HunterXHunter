package com.chubbychump.hunterxhunter.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class AbilityUseTrigger extends AbstractCriterionTrigger<AbilityUseTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "ability_use");
    public static final AbilityUseTrigger INSTANCE = new AbilityUseTrigger();

    private AbilityUseTrigger() {}

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Nonnull
    @Override
    public Instance deserializeTrigger(@Nonnull JsonObject json, EntityPredicate.AndPredicate playerPred, ConditionArrayParser conditions) {
        return new Instance(playerPred);
    }

    public void trigger(ServerPlayerEntity player) {
        triggerListeners(player, instance -> instance.test());
    }

    static class Instance extends CriterionInstance {

        Instance(EntityPredicate.AndPredicate playerPred) {
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