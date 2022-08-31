package com.cpluspatch.epilepsy.blocks;

import com.cpluspatch.epilepsy.Epilepsy;
import com.cpluspatch.epilepsy.blockentity.ElectricFurnaceBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block MALICIOUS_BLOCK = new MaliciousBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block ELECTRIC_FURNACE = new ElectricFurnace(FabricBlockSettings.of(Material.METAL).strength(4.0f));

    public static final BlockEntityType<ElectricFurnaceBlockEntity> ELECTRIC_FURNACE_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(Epilepsy.MOD_ID, "electric_furnace_block_entity"), FabricBlockEntityTypeBuilder.create(ElectricFurnaceBlockEntity::new, ELECTRIC_FURNACE)
                    .build()
        );

    private static void registerBlock(Block block, Identifier id) {
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        registerBlock(MALICIOUS_BLOCK, new Identifier(Epilepsy.MOD_ID, "malicious_block"));
        registerBlock(ELECTRIC_FURNACE, new Identifier(Epilepsy.MOD_ID, "electric_furnace"));
    }
}
