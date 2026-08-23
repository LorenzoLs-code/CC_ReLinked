package com.LorenzoL.CC_ReLinked.block.custom;

import com.LorenzoL.CC_ReLinked.Config;
import com.LorenzoL.CC_ReLinked.block.entity.CableHubBlockEntity;
import com.mojang.serialization.MapCodec;
import edn.stratodonut.drivebywire.wire.MultiChannelWireSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CableHubBlock extends BaseEntityBlock implements MultiChannelWireSource {
    public CableHubBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<CableHubBlock> CODEC = simpleCodec(CableHubBlock::new);
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
        return new CableHubBlockEntity(blockPos, blockState);
    }

    /* DRIVE BY WIRE stuff */
    @Override
    public List<String> wire$getChannels() {
        List<String> toreturn = new ArrayList<>();
        for (int i = 0; i <= Config.MAX_CABLEHUB_CHANNELS.get(); i++) {
            toreturn.add(Integer.toString(i));
        }
        return toreturn;
    }

    @Override
    public String wire$nextChannel(String s, boolean b) {
        int i;
        try { i = Integer.parseInt(s);
        } catch (NumberFormatException e) { return "0"; }

        if (!b) { // moving up

            if (i >= Config.MAX_CABLEHUB_CHANNELS.get()) { return "0"; }
            else { return Integer.toString(i+1); }

        } else { // moving down

            if (i <= 0) { return Integer.toString(Config.MAX_CABLEHUB_CHANNELS.get()); }
            else { return Integer.toString(i-1); }
        }
    }
}
