package com.cpluspatch.epilepsy.blocks;

import com.cpluspatch.epilepsy.Epilepsy;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block MALICIOUS_BLOCK = new MaliciousBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BlockItem MALICIOUS_BLOCK_ITEM = new BlockItem(MALICIOUS_BLOCK, new FabricItemSettings());

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(Epilepsy.MOD_ID, "malicious_block"), MALICIOUS_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(Epilepsy.MOD_ID, "malicious_block"), MALICIOUS_BLOCK_ITEM);
    }
}
