package com.LorenzoL.CC_ReLinked.item;

import com.LorenzoL.CC_ReLinked.CC_Relinked;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CC_Relinked.MOD_ID);

    public static final DeferredItem<Item> RedstoneLens = ITEMS.register(
            "redstone_lens",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
