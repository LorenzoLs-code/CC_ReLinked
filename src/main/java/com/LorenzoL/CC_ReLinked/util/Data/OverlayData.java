package com.LorenzoL.CC_ReLinked.util.Data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* main class */
public class OverlayData extends SavedData {
    private OverlayDataStorage storage;

    public OverlayData(OverlayDataStorage storage) {
        this.storage = storage;
    }

    public static final Factory<OverlayData> FACTORY = new Factory<>(
            () -> new OverlayData(new OverlayDataStorage(new HashMap<>())),
            (tag, registries) -> new OverlayData(
                    OverlayDataStorage.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(new OverlayDataStorage(new HashMap<>()))
            ),
            null
    );

    public static OverlayData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, "cc_relinked_overlay_data");
    }

    public Map<UUID, OverlayLayerData> getData(UUID uuid) {
        return storage.data();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        OverlayDataStorage.CODEC.encodeStart(NbtOps.INSTANCE, storage).result().ifPresent(encoded -> tag.merge((CompoundTag) encoded));
        return tag;
    }
}
