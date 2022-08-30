package com.cpluspatch.epilepsy.items;

import com.cpluspatch.epilepsy.Epilepsy;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item MALICIOUS_ORE = new MaliciousOre(new FabricItemSettings().group(ItemGroup.MISC));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(Epilepsy.MOD_ID, "malicious_ore"), MALICIOUS_ORE);
    }
}
