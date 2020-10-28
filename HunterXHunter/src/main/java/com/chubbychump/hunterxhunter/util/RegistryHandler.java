package com.chubbychump.hunterxhunter.util;

import com.chubbychump.hunterxhunter.common.blocks.BlockItemBase;
import com.chubbychump.hunterxhunter.common.blocks.NenLight;
import com.chubbychump.hunterxhunter.common.blocks.RubyBlock;
import com.chubbychump.hunterxhunter.common.entities.EntityRayBeam;
import com.chubbychump.hunterxhunter.common.items.Crystal_Nen;
import com.chubbychump.hunterxhunter.common.items.ItemBase;
import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.chubbychump.hunterxhunter.HunterXHunter.MOD_ID;


public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    //Items
    public static final RegistryObject<Item> RUBY = ITEMS.register( "ruby", ItemBase::new);
    public static final RegistryObject<Item> CRYSTALLIZEDNEN = ITEMS.register( "crystal_nen", Crystal_Nen::new);

    //Blocks
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", RubyBlock::new);
    public static final RegistryObject<Block> NEN_LIGHT = BLOCKS.register("nenlight", NenLight::new);
    //Block Items
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItemBase(RUBY_BLOCK.get()));
    public static final RegistryObject<Item> NEN_LIGHT_ITEM = ITEMS.register("nen_light", () -> new BlockItemBase(NEN_LIGHT.get()));

    //Tile Entities
    public static final RegistryObject<TileEntityType<TileEntityNenLight>> NEN_LIGHT_TILE_ENTITY = TILE_ENTITY_TYPES.register("nenlight", () -> TileEntityType.Builder.create(TileEntityNenLight::new, ModBlocks.NENLIGHT).build(null));

    //Entities
    public static final RegistryObject<EntityType<EntityRayBeam>> RAY_BEAM_ENTITY = ENTITY_TYPES.register("raybeam", () -> EntityType.Builder.<EntityRayBeam>create(EntityRayBeam::new, EntityClassification.MISC)
            .size(.9f, .9f)
            .setTrackingRange(32)
            .setUpdateInterval(40)
            .setShouldReceiveVelocityUpdates(false)
            .build(""));

}