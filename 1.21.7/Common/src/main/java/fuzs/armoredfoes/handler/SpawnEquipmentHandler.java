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
        @Nullable EntitySpawnReason entitySpawnReason = EntityHelper.getMobSpawnReason(entity);
        if (isNewlySpawned && entitySpawnReason != null && entitySpawnReason != EntitySpawnReason.COMMAND
                && entity instanceof Mob mob) {
            ResourceKey<LootTable> resourceKey = ModLootTables.createEntityEquipmentTable(entity.getType());
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourceKey);
            if (lootTable != LootTable.EMPTY) {
                for (EquipmentSlot equipmentSlot : ArmoredFoes.CONFIG.get(ServerConfig.class).slotsToClearBeforeApplying) {
                    mob.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                }
                mob.equip(resourceKey, Collections.emptyMap());
            }
        }

        return EventResult.PASS;
    }
}
