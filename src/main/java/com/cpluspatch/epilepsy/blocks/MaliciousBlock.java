package com.cpluspatch.epilepsy.blocks;

import com.cpluspatch.epilepsy.sounds.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MaliciousBlock extends Block {
    public MaliciousBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
       if (placer.isPlayer()) {
           placer.playSound(ModSounds.BABABOOEY, 1.0f, 1.0f);
       }
    }
}
