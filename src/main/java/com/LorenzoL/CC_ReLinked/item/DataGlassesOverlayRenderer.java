package com.LorenzoL.CC_ReLinked.item;

import com.LorenzoL.CC_ReLinked.util.Data.FillCommand;
import com.LorenzoL.CC_ReLinked.util.Data.OverlayLayerData;
import com.LorenzoL.CC_ReLinked.util.Data.TextCommand;
import com.LorenzoL.CC_ReLinked.util.ModDataComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

public class DataGlassesOverlayRenderer {
    public static <slotInventory> void RenderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // ====== checks =====
        // check if the player is in the menu or something like that
        Player player = Minecraft.getInstance().player;
        if (player == null) { return; }

        ItemStack DataGlasses;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() !=  ModItems.DataGlasses.get()) {
            boolean hasDataGlasses =false;
            if(ModList.get().isLoaded("curios")) {
                hasDataGlasses = CuriosApi.getCuriosInventory(player).map(curiosInventory -> curiosInventory.getStacksHandler("head").map(slotInventory -> {
                    for (int i = 0; i < slotInventory.getSlots(); i++) {
                        if (slotInventory.getStacks().getStackInSlot(i)
                                .is(ModItems.DataGlasses.get())) {
                            return true;
                        }
                    }
                    return false;
                }).orElse(false)).orElse(false);
            }
            if (!hasDataGlasses) {return;}}

        // check if the glasses are connected
        if (!helmet.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) {
            ItemStack maybeDataGlasses = helmet;
            if(ModList.get().isLoaded("curios")) {
                maybeDataGlasses = CuriosApi.getCuriosInventory(player).map(curiosInventory -> curiosInventory.getStacksHandler("head").map(slotInventory -> {
                    for (int i = 0; i < slotInventory.getSlots(); i++) {
                        ItemStack item = slotInventory.getStacks().getStackInSlot(i);
                        if (item.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) {

                            return item;
                        }}
                    return helmet;}).orElse(helmet)).orElse(helmet);}
            if (helmet == maybeDataGlasses) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return;
            } else { DataGlasses = maybeDataGlasses;}
        } else {DataGlasses = helmet;}

        // ====== Actual Code ======
        UUID senderId;
        try {
            senderId = DataGlasses.get(ModDataComponents.CONNECTED_BLOCK.get());
        } catch (Exception e) { guiGraphics.drawString(
                Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return; }

        OverlayLayerData RenderData =
                DataGlasses.get(ModDataComponents.OVERLAY_LAYER_DATA.get());

        boolean count = true;
        try {
            for (TextCommand l : RenderData.textCommands()) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font,           // font
                        (String) l.text(),                      // text (0)
                        (Integer) l.x(), (Integer) l.y(), // x (1), y (2)
                        0xFFFFFF);                              // color
                count=false;
            }
            for (FillCommand f : RenderData.fillCommands()) {
                guiGraphics.fill(
                        f.x1(), f.x2(),
                        f.y1(), f.x2(),
                        f.color());
                count=false;
            }

        } catch (Exception e) {
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "BUG: False RenderData", 5, 5, 0xFF0000);
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    "please report this bug to CC: ReLinked", 5, 15, 0xFF0000);
        }

        if (count) {
            guiGraphics.drawString(
                    Minecraft.getInstance().font, "Linked", 5, 5, 0xFFFFFF);
        }
    }
}
