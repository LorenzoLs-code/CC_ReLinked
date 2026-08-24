package com.LorenzoL.CC_ReLinked.peripheral;

import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class DataGlassesPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:dataglasses_peripheral";
    }

    public static void RenderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // code
    }
}
