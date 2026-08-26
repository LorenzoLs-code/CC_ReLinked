package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.component.ModDataComponents;
import com.LorenzoL.CC_ReLinked.component.custom.GlassesData;
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

import java.util.*;

public class DataGlassesPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:dataglasses_peripheral";
    }

    // ===== OVERLAY =====
    // ===== Data Storage & Utils
    public static final Map<UUID, Map<Integer, List<List<Object>>>> OverlayData = new HashMap<>();;
    public static Map<Integer, List<List<Object>>> NullRenderData() {
        Map<Integer, List<List<Object>>> M = new HashMap<>();
        List<List<Object>> N = new ArrayList<List<Object>>();
        M.put(0, N);

        return M;}
    // ===== Rendering
    public static void RenderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // ====== checks =====
        // check if the player is in the menu or something like that
        Player player = Minecraft.getInstance().player;
        if (player == null) { return; }

        // check if the glasses are on
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() !=  ModItems.DataGlasses.get()) { return; }

        // check if the glasses are connected
        if (!helmet.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) { guiGraphics.drawString(
                Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return; }

        // ====== Actual Code ======
        UUID senderId;
        try {
            senderId = helmet.get(ModDataComponents.GLASSES_DATA.get()).senderId();
        } catch (Exception e) { guiGraphics.drawString(
                Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return; }

        Map<Integer, List<List<Object>>> RenderData =
                OverlayData.get(senderId);

        int count = 0;
        try {
            for (List<Object> l : RenderData.get(0)) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font,           // font
                        (String) l.get(0),                      // text (0)
                        (Integer) l.get(1), (Integer) l.get(2), // x (1), y (2)
                        0xFFFFFF);                              // color
                count++;
            }
        } catch (Exception e) {
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "ERROR: False RenderData", 5, 5, 0xFF0000);
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "please report this error to CC: ReLinked", 5, 15, 0xFF0000);
        }

        if (count == 0) {
            guiGraphics.drawString(
                Minecraft.getInstance().font, "Linked", 5, 5, 0xFFFFFF);
        }
    }
}
