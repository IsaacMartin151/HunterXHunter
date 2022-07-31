package com.chubbychump.hunterxhunter.server.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class ItemVariants extends Item {
    public ItemVariants() {
        super(new Item.Properties().group(HunterXHunter.TAB));
    }

    public static final String NBT_TAG_NAME_FLAVOUR = "colour";
    public static final String NBT_TAG_NAME_ACTIVATED = "activated";

    public static ToggledOn getActivated(ItemStack stack) {
        CompoundTag compoundNBT = stack.getOrCreateTag();
        return ToggledOn.fromNBT(compoundNBT, NBT_TAG_NAME_ACTIVATED);
    }

    public static float getFullnessPropertyOverride(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
        ToggledOn enumBottleFullness = getActivated(itemStack);
        return enumBottleFullness.getPropertyOverrideValue();
    }

    public enum ToggledOn implements IStringSerializable {
        OFF(0, "0pc", "off"),
        ON(1, "100pc", "on");

        @Override
        public String toString() {
            return this.getDescription();
        }

        @Override
        public String getString() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public float getPropertyOverrideValue() {
            return nbtID;
        }

        public ToggledOn toggle() {
            if (nbtID == 0) return this;
            for (ToggledOn activated : ToggledOn.values()) {
                if (activated.nbtID == nbtID - 1) return activated;
            }
            // error... return default
            return this;
        }

        /**
         * Read the NBT tag for bottle fullness and create the corresponding Enum from it.
         * Do not trust NBT values! They can be set to illegal values by factors outside your code's control.
         * Always check them for correctness (within range, logical consistency with other tags, etc)
         *
         * @param compoundNBT
         * @return
         */
        public static ToggledOn fromNBT(CompoundTag compoundNBT, String tagname) {
            byte activatedID = 0;  // default in case of error
            if (compoundNBT != null && compoundNBT.contains(tagname)) {
                activatedID = compoundNBT.getByte(tagname);
            }
            Optional<ToggledOn> activated = getActivatedFromID(activatedID);
            return activated.orElse(ON);
        }

        public void putIntoNBT(CompoundTag compoundNBT, String tagname) {
            compoundNBT.putByte(tagname, nbtID);
        }

        private final byte nbtID;
        private final String name;
        private final String description;

        ToggledOn(int i_NBT_ID, String i_name, String i_description) {
            this.nbtID = (byte) i_NBT_ID;
            this.name = i_name;
            this.description = i_description;
        }

        private static Optional<ToggledOn> getActivatedFromID(byte ID) {
            for (ToggledOn activated : ToggledOn.values()) {
                if (activated.nbtID == ID) return Optional.of(activated);
            }
            return Optional.empty();
        }
    }
}
