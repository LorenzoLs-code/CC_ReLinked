package com.LorenzoL.CC_ReLinked.peripheral;

import com.LorenzoL.CC_ReLinked.block.entity.DataSenderBlockEntity;
import com.LorenzoL.CC_ReLinked.util.Data.*;
import com.LorenzoL.CC_ReLinked.util.ModDataComponents;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import dan200.computercraft.api.peripheral.PeripheralType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;


import java.util.*;

public class DataGlassesPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return "cc_relinked:dataglasses_peripheral";
    }
    @Override
    public PeripheralType getType() { return PeripheralType.ofType("ccrl_data_sender"); }

    // ===== LUA =====
    // ===== Functions
    private OverlayDataStorage dataStorage = new OverlayDataStorage(new HashMap<>());


    // == Text
    @LuaFunction(mainThread = true)
    public int createText(DataSenderBlockEntity blockEntity, String text, int x, int y) {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<TextCommand> updatedTextCommands = new ArrayList<>(current.textCommands());
        updatedTextCommands.add(new TextCommand(text, x, y));

        OverlayLayerData updated = new OverlayLayerData(updatedTextCommands, current.fillCommands());
        dataStorage.data().put(blockEntity.getId(), updated);

        return  updatedTextCommands.size() - 1;
    }

    @LuaFunction(mainThread = true)
    public void deleteText(DataSenderBlockEntity blockEntity, int text_index) throws LuaException {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<TextCommand> updatedTextCommands = new ArrayList<>(current.textCommands());
        if (text_index < 0 || text_index >= updatedTextCommands.size()) { throw new LuaException("Invalid text index: " + text_index); }
        updatedTextCommands.remove(text_index);

        OverlayLayerData updated = new OverlayLayerData(updatedTextCommands, current.fillCommands());
        dataStorage.data().put(blockEntity.getId(), updated);
    }

    // == Fill
    @LuaFunction(mainThread = true)
    public int createFill(DataSenderBlockEntity blockEntity, int x1, int y1, int x2, int y2, int color) {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<FillCommand> updatedFillCommands = new ArrayList<>(current.fillCommands());
        updatedFillCommands.add(new FillCommand(x1, y1, x2, y2, color));

        OverlayLayerData updated = new OverlayLayerData(current.textCommands(), updatedFillCommands);
        dataStorage.data().put(blockEntity.getId(), updated);

        return  updatedFillCommands.size() - 1;
    }

    @LuaFunction(mainThread = true)
    public void deleteFill(DataSenderBlockEntity blockEntity, int fill_index) throws LuaException {
        OverlayLayerData current = dataStorage.data().getOrDefault(blockEntity.getId(), NullRenderData());

        List<FillCommand> updatedFillCommands = new ArrayList<>(current.fillCommands());
        if (fill_index < 0 || fill_index >= updatedFillCommands.size()) { throw new LuaException("Invalid fill index: " + fill_index); }
        updatedFillCommands.remove(fill_index);

        OverlayLayerData updated = new OverlayLayerData(current.textCommands(), updatedFillCommands);
        dataStorage.data().put(blockEntity.getId(), updated);
    }


    // == Util
    @LuaFunction(mainThread = true)
    public void clear(DataSenderBlockEntity blockEntity) {
        dataStorage.data().put(blockEntity.getId(), NullRenderData());
    }

    @LuaFunction(mainThread = true)
    public void send(DataSenderBlockEntity blockEntity) {
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

}
