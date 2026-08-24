package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.item.ModArmorMaterials;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DataGlassesPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:dataglasses_peripheral";
    }

    public static void RenderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // ====== checks =====
        // check if the player is in the menu or something like that
        Player player = Minecraft.getInstance().player;
        if (player == null) { return; }

        // check if the glasses are on
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() !=  ModItems.DataGlasses.get()) { return; }

        // ====== Actual Code ======
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                "Hello World",
                10, 10,
                0xFFFFFF
        );

    }
}
