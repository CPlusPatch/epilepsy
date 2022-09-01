package com.cpluspatch.epilepsy.blockentity;

import com.cpluspatch.epilepsy.Epilepsy;
import com.cpluspatch.epilepsy.blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {
    public static BlockEntityType<ElectricFurnaceBlockEntity> ELECTRIC_FURNACE;

    public static void registerBlockEntities() {
        ELECTRIC_FURNACE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Epilepsy.MOD_ID, "electric_furnace"),
                FabricBlockEntityTypeBuilder.create(ElectricFurnaceBlockEntity::new,
                        ModBlocks.ELECTRIC_FURNACE).build(null));

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, ELECTRIC_FURNACE);
    }
}
