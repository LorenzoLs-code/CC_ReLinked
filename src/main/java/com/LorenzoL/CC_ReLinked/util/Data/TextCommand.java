package com.LorenzoL.CC_ReLinked.util.Data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

// Command typs
public record TextCommand(String text, int x, int y) {
    public static final Codec<TextCommand> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("text").forGetter(TextCommand::text),
            Codec.INT.fieldOf("x").forGetter(TextCommand::x),
            Codec.INT.fieldOf("y").forGetter(TextCommand::y)
    ).apply(instance, TextCommand::new));

    public static final StreamCodec<ByteBuf, TextCommand> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TextCommand::text,
            ByteBufCodecs.INT, TextCommand::x,
            ByteBufCodecs.INT, TextCommand::y,
            TextCommand::new
    );
}
