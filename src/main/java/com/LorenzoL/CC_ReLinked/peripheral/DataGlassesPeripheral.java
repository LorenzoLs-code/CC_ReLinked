package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.item.ModArmorMaterials;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGlassesPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:dataglasses_peripheral";
    }

    // ===== OVERLAY =====
    // ===== Data Storage & Utils
    public static Map<List<Integer>, Map<Integer, List<List<Object>>>> OverlayData;
    public static Map<Integer, List<List<Object>>> NullRenderData() {
        Map<Integer, List<List<Object>>> M = new HashMap<>();
        List<List<Object>> N = new ArrayList<>();
        M.put(0, N);

        return M;}
    public static List<Integer> getOverlayDataKey(BlockPos pos) {
        return List.of(pos.getX(), pos.getY(), pos.getZ());
    }

    // ===== Rendering
    public static void RenderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // ====== checks =====
        // check if the player is in the menu or something like that
        Player player = Minecraft.getInstance().player;
        if (player == null) { return; }

        // check if the glasses are on
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() !=  ModItems.DataGlasses.get()) { return; }

        // ====== Actual Code ======

        try {
            Map<Integer, List<List<Object>>> RenderData = OverlayData.get("");
            for (List<Object> l : RenderData.get(0)) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font,           // font
                        (String) l.get(0),                      // text (0)
                        (Integer) l.get(1), (Integer) l.get(2),  // x (1), y (2)
                        0xFFFFFF);                              // color
            }
        } catch (Exception e) {
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "ERROR: False RenderData", 5, 5, 0xFF0000);
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "please report this error to CC: ReLinked", 5, 15, 0xFF0000);
        }
    }
}
