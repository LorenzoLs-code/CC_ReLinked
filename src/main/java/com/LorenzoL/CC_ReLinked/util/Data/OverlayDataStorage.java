package com.LorenzoL.CC_ReLinked.util.Data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.Map;
import java.util.UUID;

// OverlayDataStorage
public record OverlayDataStorage(Map<UUID, OverlayLayerData> data) {
    public static final Codec<OverlayDataStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(UUIDUtil.STRING_CODEC, OverlayLayerData.CODEC).fieldOf("data").forGetter(OverlayDataStorage::data)
    ).apply(instance, OverlayDataStorage::new));
}
