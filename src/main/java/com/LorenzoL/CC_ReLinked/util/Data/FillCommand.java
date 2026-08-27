package com.LorenzoL.CC_ReLinked.util.Data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FillCommand(int x1, int y1, int x2, int y2, int color) {
    public static final Codec<FillCommand> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x1").forGetter(FillCommand::x1),
            Codec.INT.fieldOf("x2").forGetter(FillCommand::x2),
            Codec.INT.fieldOf("y1").forGetter(FillCommand::y1),
            Codec.INT.fieldOf("y2").forGetter(FillCommand::y2),
            Codec.INT.fieldOf("color").forGetter(FillCommand::color)
    ).apply(instance, FillCommand::new));

    public static final StreamCodec<ByteBuf, FillCommand> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, FillCommand::x1,
            ByteBufCodecs.INT, FillCommand::x2,
            ByteBufCodecs.INT, FillCommand::y1,
            ByteBufCodecs.INT, FillCommand::y2,
            ByteBufCodecs.INT, FillCommand::color,
            FillCommand::new
    );
}
