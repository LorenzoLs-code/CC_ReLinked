package com.LorenzoL.CC_ReLinked.block.custom;

import com.LorenzoL.CC_ReLinked.block.entity.DataSenderBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class DataSenderBlock extends BaseEntityBlock {
    public DataSenderBlock(Properties properties) { super(properties); }

    // CODEC
    public static final MapCodec<DataSenderBlock> CODEC = simpleCodec(DataSenderBlock::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    /* BLOCK ENTITY stuff */
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DataSenderBlockEntity(blockPos, blockState);
    }
}
