package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.block.entity.DataSenderBlockEntity;
import com.LorenzoL.CC_ReLinked.util.Data.*;
import com.LorenzoL.CC_ReLinked.util.ModDataComponents;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

    // ===== LUA =====
    // ===== Functions
    private OverlayDataStorage dataStorage = new OverlayDataStorage(new HashMap<>());

    @LuaFunction
    public int CreateText(DataSenderBlockEntity blockEntity, String text, int x, int y) {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<TextCommand> updatedTextCommands = new ArrayList<>(current.textCommands());
        updatedTextCommands.add(new TextCommand(text, x, y));

        OverlayLayerData updated = new OverlayLayerData(updatedTextCommands, current.fillCommands());
        dataStorage.data().put(blockEntity.getId(), updated);

        return  updatedTextCommands.size() - 1;
    }

    @LuaFunction
    public void DeleteText(DataSenderBlockEntity blockEntity, int text_index) throws LuaException {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<TextCommand> updatedTextCommands = new ArrayList<>(current.textCommands());
        if (text_index < 0 || text_index >= updatedTextCommands.size()) { throw new LuaException("Invalid text index: " + text_index); }
        updatedTextCommands.remove(text_index);

        OverlayLayerData updated = new OverlayLayerData(updatedTextCommands, current.fillCommands());
        dataStorage.data().put(blockEntity.getId(), updated);
    }

    @LuaFunction
    public void Clear(DataSenderBlockEntity blockEntity) {
        dataStorage.data().put(blockEntity.getId(), NullRenderData());
    }

    @LuaFunction
    public void Send(DataSenderBlockEntity blockEntity) {
        for (Player player : blockEntity.getLevel().players()) {
            ItemStack item = player.getItemBySlot(EquipmentSlot.HEAD);

            if (item.getItem() != ModItems.DataGlasses.get())                                    continue;
            if (!item.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) continue;
            if (!blockEntity.getId().equals(item.get(ModDataComponents.CONNECTED_BLOCK.get())))             continue;

            item.set(ModDataComponents.OVERLAY_LAYER_DATA.get(), dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData()));
            player.setItemSlot(EquipmentSlot.HEAD, item);
        }
    }


    // ===== Util
    private ArrayList<ItemStack> getDataGlasses(Level level, UUID block_id) {
        ArrayList<ItemStack> DataGlasses = new ArrayList<>();

        for (Player player : level.players()) {
            ItemStack item = player.getItemBySlot(EquipmentSlot.HEAD);

            if (item.getItem() != ModItems.DataGlasses.get())                                    continue;
            if (!item.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) continue;
            if (!block_id.equals(item.get(ModDataComponents.CONNECTED_BLOCK.get())))             continue;

            DataGlasses.add(item);
        }

        return DataGlasses;
    }




    // ===== OVERLAY =====
    // ===== Data Storage & Utils
    public static final OverlayData overlayData = new OverlayData(new OverlayDataStorage(new HashMap<>()));
    public static final OverlayLayerData NullRenderData() {
        return new OverlayLayerData(new ArrayList<>(), new ArrayList<>());
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

        // check if the glasses are connected
        if (!helmet.getOrDefault(ModDataComponents.GLASSES_CONNECTED.get(), false)) { guiGraphics.drawString(
                Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return; }

        // ====== Actual Code ======
        UUID senderId;
        try {
            senderId = helmet.get(ModDataComponents.CONNECTED_BLOCK.get());
        } catch (Exception e) { guiGraphics.drawString(
                Minecraft.getInstance().font, "Not Linked", 5, 5, 0xFFFFFF); return; }

        OverlayLayerData RenderData =
                helmet.get(ModDataComponents.OVERLAY_LAYER_DATA.get());

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
