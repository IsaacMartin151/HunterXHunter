package com.chubbychump.hunterxhunter.init;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import static com.chubbychump.hunterxhunter.init.ModBlocks.NENLIGHT;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.TILE_ENTITY_TYPES;

@ObjectHolder(HunterXHunter.MOD_ID)
public class ModTileEntityTypes {
    public static final TileEntityType<?> NENLIGHT = null; //I think this part is right, just need to do the eventhandler portion correctly.
    //public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, HunterXHunter.MOD_ID);
    //public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT = TILE_ENTITY_TYPES.register("nenlight", () -> TileEntityType.Builder.create(TileEntityNenLight::new, ModBlocks.PERSONLIGHT).build(null));
}