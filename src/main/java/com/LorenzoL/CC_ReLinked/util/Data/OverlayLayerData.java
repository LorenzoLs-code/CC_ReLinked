package com.LorenzoL.CC_ReLinked.util.Data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

// OverlayLayerData
public record OverlayLayerData(List<TextCommand> textCommands, List<FillCommand> fillCommands) {
    public static final Codec<OverlayLayerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextCommand.CODEC.listOf().fieldOf("text_commands").forGetter(OverlayLayerData::textCommands),
            FillCommand.CODEC.listOf().fieldOf("fill_commands").forGetter(OverlayLayerData::fillCommands)
    ).apply(instance, OverlayLayerData::new));

    public static final StreamCodec<ByteBuf, OverlayLayerData> STREAM_CODEC = StreamCodec.composite(
            TextCommand.STREAM_CODEC.apply(ByteBufCodecs.list()), OverlayLayerData::textCommands,
            FillCommand.STREAM_CODEC.apply(ByteBufCodecs.list()), OverlayLayerData::fillCommands,
            OverlayLayerData::new
    );
}
