package com.chubbychump.hunterxhunter.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class AllCommonTrigger extends AbstractCriterionTrigger<AllCommonTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "all_common");
    public static final AllCommonTrigger INSTANCE = new AllCommonTrigger();

    private AllCommonTrigger() {}

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
