package com.chubbychump.hunterxhunter.init;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(HunterXHunter.MOD_ID)
public class ModTileEntityTypes {
    public static final TileEntityType<?> PERSONLIGHT = null;
    //public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, HunterXHunter.MOD_ID);
    //public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT = TILE_ENTITY_TYPES.register("nenlight", () -> TileEntityType.Builder.create(TileEntityNenLight::new, ModBlocks.PERSONLIGHT).build(null));
}