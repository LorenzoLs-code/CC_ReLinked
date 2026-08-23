package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.Config;
import com.LorenzoL.CC_ReLinked.block.entity.CableHubBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import edn.stratodonut.drivebywire.wire.WireNetworkManager;

import java.util.Map;

public class CableHubPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:cablehub_peripheral";
    }

    /* LUA FUNCTIONS */
    @LuaFunction
    public boolean getChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        int value = getAnalogChannel(myCableHub, channel_ID);

        if (value > 0) { return true; }
        else { return false; }

    }

    @LuaFunction
    public boolean setChannel(CableHubBlockEntity myCableHub, int channel_ID, boolean value) {
        int r_value;
        if (value) { r_value = 15; } else { r_value = 0; }

        return setAnalogChannel(myCableHub, channel_ID, r_value);
    }
    // ----
    @LuaFunction
    public int getAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        try {
            return WireNetworkManager
                    .get(myCableHub.getLevel())
                    .getSourceSignals(myCableHub.getBlockPos())
                    .get(Integer.toString(channel_ID));
        } catch (Exception e) {
            return 0;
        }
    }

    @LuaFunction
    public boolean setAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID, int value) {
        if (channel_ID > Config.MAX_CABLEHUB_CHANNELS.get()) { return false; }

        WireNetworkManager.trySetSignalAt(
                myCableHub.getLevel(),
                myCableHub.getBlockPos(),
                Integer.toString(channel_ID),
                value
        );
        return true;
    }
}
