package com.LorenzoL.CC_ReLinked.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DataSenderBlockEntity extends BlockEntity {
    public DataSenderBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntitys.DataSender_BE.get(), pos, blockState);
    }
}
