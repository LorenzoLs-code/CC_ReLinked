package com.LorenzoL.CC_ReLinked.block.custom;

import com.LorenzoL.CC_ReLinked.block.entity.DataSenderBlockEntity;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import com.LorenzoL.CC_ReLinked.peripheral.DataGlassesPeripheral;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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

    /* events stuff */
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.isClientSide) return;
        if (state.getBlock() == oldState.getBlock()) return;

        // create block in OverlayData
        DataGlassesPeripheral.OverlayData.put(
                DataGlassesPeripheral.getOverlayDataKey(pos),
                DataGlassesPeripheral.NullRenderData()
        );}
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.isClientSide) return;

        // remove block form OverlayData
        DataGlassesPeripheral.OverlayData.remove(
                DataGlassesPeripheral.getOverlayDataKey(pos)
        );

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (stack.getItem() != ModItems.DataGlasses.get()) { return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION; }
        
        // code
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
