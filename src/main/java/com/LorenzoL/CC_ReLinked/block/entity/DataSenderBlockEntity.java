package com.LorenzoL.CC_ReLinked.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class DataSenderBlockEntity extends BlockEntity {
    private  UUID id;

    public DataSenderBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntitys.DataSender_BE.get(), pos, blockState);
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return  id;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putUUID("PeripheralId", id);
    }
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("PeripheralId")) {
            this.id = tag.getUUID("PeripheralId");
        }
    }

}
