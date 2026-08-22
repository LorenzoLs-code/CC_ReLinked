package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.block.entity.CableHubBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;

public class CableHubPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:cablehub_peripheral";
    }

    /* LUA FUNCTIONS */
    @LuaFunction
    public boolean getChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        return false;
    }

    @LuaFunction
    public boolean setChannel(CableHubBlockEntity myCableHub, int channel_ID, boolean value) {
        return false;
    }
    // ----
    @LuaFunction
    public int getAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID) {
        return -1;
    }

    @LuaFunction
    public boolean setAnalogChannel(CableHubBlockEntity myCableHub, int channel_ID, int value) {
        return false;
    }
}
