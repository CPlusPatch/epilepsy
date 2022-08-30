package com.cpluspatch.epilepsy.groups;

import com.cpluspatch.epilepsy.Epilepsy;
import com.cpluspatch.epilepsy.blocks.ModBlocks;
import com.cpluspatch.epilepsy.items.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final FabricItemGroupBuilder ITEM_GROUP = FabricItemGroupBuilder
            .create(new Identifier(Epilepsy.MOD_ID, "item_group"))
            .icon(() -> new ItemStack(ModItems.MALICIOUS_ORE))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModItems.MALICIOUS_ORE));
                stacks.add(new ItemStack(ModBlocks.MALICIOUS_BLOCK));
            });

    public static void registerItemGroups() {
        ITEM_GROUP.build();
    }
}
