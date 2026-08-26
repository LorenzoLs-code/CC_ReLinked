package com.LorenzoL.CC_ReLinked.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

public record GlassesData (UUID senderId) {

    public static final Codec<GlassesData> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
                UUIDUtil.CODEC.fieldOf("SenderId").forGetter(GlassesData::senderId)
        ).apply(instance, GlassesData::new));

    public static final StreamCodec<ByteBuf, GlassesData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, GlassesData::senderId,
            GlassesData::new
    );
}
