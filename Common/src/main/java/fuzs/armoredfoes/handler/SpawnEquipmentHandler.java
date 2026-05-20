package fuzs.armoredfoes.handler;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.config.ServerConfig;
import fuzs.armoredfoes.init.ModLootTables;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class SpawnEquipmentHandler {
    private static final EquipmentSlot[] EQUIPMENT_SLOT_VALUES = EquipmentSlot.values();

    public static void onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isLoadedFromDisk, @Nullable MobSpawnType spawnReason) {
        // We cannot determine here when summoned via command if any specific nbt was specified which we should not interfere with (like purposefully setting empty equipment slots).
        // In that case, though, the spawn reason will be null, so we can filter that out like so.
        if (!isLoadedFromDisk && entity instanceof Mob mob && spawnReason != null) {
            ResourceKey<LootTable> resourceKey = ModLootTables.createEntityEquipmentTable(entity.getType());
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourceKey);
            if (lootTable != LootTable.EMPTY && prepareEquipmentSlots(mob)) {
                mob.equip(resourceKey, Collections.emptyMap());
            }
        }
    }

    private static boolean prepareEquipmentSlots(Mob mob) {
        // Mobs in this tag have their equipment cleared before trying to apply equipment from the loot table.
        EquipmentSlotGroup equipmentSlotGroup = ArmoredFoes.CONFIG.get(ServerConfig.class).clearedEquipmentSlots;
        if (mob.getType().is(ModRegistry.DISCARDS_ORIGINAL_EQUIPMENT_ENTITY_TAG)) {
            for (EquipmentSlot equipmentSlot : EQUIPMENT_SLOT_VALUES) {
                if (equipmentSlotGroup.test(equipmentSlot)) {
                    mob.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                }
            }
        } else {
            // All other mobs must have empty equipment slots.
            for (EquipmentSlot equipmentSlot : EQUIPMENT_SLOT_VALUES) {
                if (equipmentSlotGroup.test(equipmentSlot)) {
                    if (!mob.getItemBySlot(equipmentSlot).isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
