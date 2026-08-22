package com.LorenzoL.CC_ReLinked.block;

import com.LorenzoL.CC_ReLinked.CC_Relinked;
import com.LorenzoL.CC_ReLinked.block.custom.CableHubBlock;
import com.LorenzoL.CC_ReLinked.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CC_Relinked.MOD_ID);


    public static final DeferredBlock<Block> CableHub = registerBlock(
            "cable_hub_block",
            () -> new CableHubBlock(BlockBehaviour.Properties.of()));


    // helper funcs
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return  toReturn;}
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    } // ---

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
