package com.cpluspatch.epilepsy.blocks;

import com.cpluspatch.epilepsy.Epilepsy;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block MALICIOUS_BLOCK = new MaliciousBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block ELECTRIC_FURNACE = new ElectricFurnace(FabricBlockSettings.of(Material.METAL).strength(4.0f));

    private static void registerBlock(Block block, String name) {
        Registry.register(Registry.BLOCK, new Identifier(Epilepsy.MOD_ID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Epilepsy.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        registerBlock(MALICIOUS_BLOCK, "malicious_block");
        registerBlock(ELECTRIC_FURNACE, "electric_furnace");
    }
}
