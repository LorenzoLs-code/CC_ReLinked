package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.Config;
import com.LorenzoL.CC_ReLinked.block.entity.CableHubBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import dan200.computercraft.api.peripheral.PeripheralType;
import edn.stratodonut.drivebywire.wire.WireNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CableHubPeripheral implements GenericPeripheral {
    @Override
    public String id() { return "cc_relinked:cablehub_peripheral"; }

    @Override
    public PeripheralType getType() { return PeripheralType.ofType("ccrl_cablehub"); }

    /* LUA FUNCTIONS */

    @LuaFunction(mainThread = true)
    public boolean getChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        return getAnalogChannel(myCableHub, channel_ID) > 0;
    }

    @LuaFunction(mainThread = true)
    public boolean setChannel(CableHubBlockEntity myCableHub, int channel_ID, boolean value) {
        return setAnalogChannel(myCableHub, channel_ID, value ? 15 : 0);
    }

    @LuaFunction(mainThread = true)
    public int getAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        if (myCableHub == null || myCableHub.getLevel() == null) return 0;
        try {
            Integer val = WireNetworkManager
                    .get(myCableHub.getLevel())
                    .getSourceSignals(myCableHub.getBlockPos())
                    .get(Integer.toString(channel_ID));
            return val != null ? val : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @LuaFunction(mainThread = true)
    public boolean setAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID, int value) {
        if (myCableHub == null) return false;
        Level level = myCableHub.getLevel();
        if (level == null) return false;

        if (channel_ID > Config.MAX_CABLEHUB_CHANNELS.get() || channel_ID < 0) {
            return false;
        }

        BlockPos pos = myCableHub.getBlockPos();
        String channelStr = Integer.toString(channel_ID);

        WireNetworkManager.trySetSignalAt(
                level,
                pos,
                channelStr,
                value
        );

        myCableHub.setChanged();
        BlockState state = myCableHub.getBlockState();
        level.sendBlockUpdated(pos, state, state, 3);
        level.updateNeighborsAt(pos, state.getBlock());

        return true;
    }
}