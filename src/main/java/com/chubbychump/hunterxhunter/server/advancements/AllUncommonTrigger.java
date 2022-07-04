package com.chubbychump.hunterxhunter.server.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;

public class AllUncommonTrigger extends AbstractCriterionTrigger<AllUncommonTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "all_uncommon");
    public static final AllUncommonTrigger INSTANCE = new AllUncommonTrigger();

    private AllUncommonTrigger() {}

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

    public void trigger(ServerPlayer player) {
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
