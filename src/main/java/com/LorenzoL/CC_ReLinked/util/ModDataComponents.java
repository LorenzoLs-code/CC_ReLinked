package com.LorenzoL.CC_ReLinked.util;

import com.LorenzoL.CC_ReLinked.util.Data.OverlayLayerData;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "cc_relinked");


     public static final Supplier<DataComponentType<Boolean>> GLASSES_CONNECTED =
            DATA_COMPONENT_TYPES.register("dataglasses_connected", () ->
                    DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
                            .build()
            );

    public static final Supplier<DataComponentType<UUID>> CONNECTED_BLOCK =
            DATA_COMPONENT_TYPES.register("connected_block", () ->
                    DataComponentType.<UUID>builder()
                            .persistent(UUIDUtil.CODEC)
                            .networkSynchronized(UUIDUtil.STREAM_CODEC)
                            .build()
            );


    public static final Supplier<DataComponentType<OverlayLayerData>> OVERLAY_LAYER_DATA =
            DATA_COMPONENT_TYPES.register("overlay_layerdata", () ->
                    DataComponentType.<OverlayLayerData>builder()
                            .persistent(OverlayLayerData.CODEC)
                            .networkSynchronized(OverlayLayerData.STREAM_CODEC)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
