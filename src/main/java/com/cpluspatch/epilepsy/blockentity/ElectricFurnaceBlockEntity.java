package com.cpluspatch.epilepsy.blockentity;

import com.cpluspatch.epilepsy.Epilepsy;
import com.cpluspatch.epilepsy.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class ElectricFurnaceBlockEntity extends BlockEntity {
    private int energy = 1000;
    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ELECTRIC_FURNACE_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("energy", energy);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        energy = nbt.getInt("energy");
    }
}
