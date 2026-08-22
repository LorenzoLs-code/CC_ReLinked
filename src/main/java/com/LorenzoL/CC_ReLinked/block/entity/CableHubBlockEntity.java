package com.LorenzoL.CC_ReLinked.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CableHubBlockEntity extends BlockEntity {
    public CableHubBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntitys.CableHub_BE.get(), pos, blockState);
    }
}
