package com.LorenzoL.CC_ReLinked.block.entity;

import com.LorenzoL.CC_ReLinked.CC_Relinked;
import com.LorenzoL.CC_ReLinked.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CC_Relinked.MOD_ID);


    public static final Supplier<BlockEntityType<CableHubBlockEntity>> CableHub_BE =
            BLOCK_ENTITYS.register("cablehub_be", () -> BlockEntityType.Builder.of(
                    CableHubBlockEntity::new, ModBlocks.CableHub.get()).build(null));

    public static final Supplier<BlockEntityType<DataSenderBlockEntity>> DataSender_BE =
            BLOCK_ENTITYS.register("datasender_be", () -> BlockEntityType.Builder.of(
                    DataSenderBlockEntity::new, ModBlocks.DataSender.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITYS.register(eventBus);
    }
}
