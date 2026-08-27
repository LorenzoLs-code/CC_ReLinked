package com.LorenzoL.CC_ReLinked.block.custom;

import com.LorenzoL.CC_ReLinked.block.entity.DataSenderBlockEntity;
import com.LorenzoL.CC_ReLinked.util.ModDataComponents;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

import java.util.UUID;

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
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
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

        boolean connected = stack.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), true);
        UUID senderId;
        try {
            senderId = stack.get(ModDataComponents.CONNECTED_BLOCK.get());
        } catch (Exception e) {
            if (level.getBlockEntity(pos) instanceof DataSenderBlockEntity blockEntity) {
                stack.set(ModDataComponents.GLASSES_CONNECTED.get(), true);
                stack.set(ModDataComponents.CONNECTED_BLOCK.get(), blockEntity.getId());
                // - message
                player.displayClientMessage(Component.literal("Linked"), true);
            } return ItemInteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof DataSenderBlockEntity blockEntity) {

            if (blockEntity.getId() == senderId
            ) {// same block as before
                stack.set(ModDataComponents.GLASSES_CONNECTED.get(),
                        !connected);
                // - message
                if (!connected) {
                    player.displayClientMessage(Component.literal("Linked"), true);
                } else {
                    player.displayClientMessage(Component.literal("Unlinked"), true);
                }

            } else { // other block then before
                stack.set(ModDataComponents.GLASSES_CONNECTED.get(), true);
                stack.set(ModDataComponents.CONNECTED_BLOCK.get(), blockEntity.getId());
                // - message
                player.displayClientMessage(Component.literal("Linked"), true);

                return ItemInteractionResult.SUCCESS;
            }}
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

}