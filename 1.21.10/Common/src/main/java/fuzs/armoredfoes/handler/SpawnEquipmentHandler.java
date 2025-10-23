package fuzs.armoredfoes.handler;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.config.ServerConfig;
import fuzs.armoredfoes.init.ModLootTables;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.util.v1.EntityHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class SpawnEquipmentHandler {

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        if (isNewlySpawned && entity instanceof Mob mob) {
            // We cannot determine here when summoned via command if any specific nbt was specified which we should not interfere with (like purposefully setting empty equipment slots).
            // So, to circumvent any issues, we better do nothing when spawned from commands.
            @Nullable EntitySpawnReason entitySpawnReason = EntityHelper.getMobSpawnReason(entity);
            if (entitySpawnReason != null) {
                ResourceKey<LootTable> resourceKey = ModLootTables.createEntityEquipmentTable(entity.getType());
                LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourceKey);
                if (lootTable != LootTable.EMPTY) {
                    for (EquipmentSlot equipmentSlot : ArmoredFoes.CONFIG.get(ServerConfig.class).clearedEquipmentSlots) {
                        mob.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                    }

                    mob.equip(resourceKey, Collections.emptyMap());
                }
            }
        }

        return EventResult.PASS;
    }
}
