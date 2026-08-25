package com.LorenzoL.CC_ReLinked.item;

import com.LorenzoL.CC_ReLinked.CC_Relinked;
import com.LorenzoL.CC_ReLinked.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CC_Relinked.MOD_ID);


    public static final Supplier<CreativeModeTab> CC_Relinked_Tab = CREATIVE_MODE_TAB.register("cc_relinked_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.CableHub.get()))
            .title(Component.translatable("creativtab.cc_relinked.cc_relinked_tab"))

            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModBlocks.CableHub);

                output.accept(ModItems.RedstoneLens);
                output.accept(ModItems.DataGlasses);
                output.accept(ModBlocks.DataSender);
            }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
