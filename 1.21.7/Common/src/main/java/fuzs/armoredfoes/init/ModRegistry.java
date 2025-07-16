package fuzs.armoredfoes.init;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.world.level.storage.loot.entries.NaturalEquipmentEntry;
import fuzs.armoredfoes.world.level.storage.loot.entries.SimpleSequenceEntry;
import fuzs.armoredfoes.world.level.storage.loot.predicates.DifficultyCheck;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder();
    static final RegistryManager REGISTRIES = RegistryManager.from(ArmoredFoes.MOD_ID);
    public static final Holder.Reference<LootPoolEntryType> NATURAL_EQUIPMENT_LOOT_POOL_ENTRY_TYPE = REGISTRIES.register(
            Registries.LOOT_POOL_ENTRY_TYPE,
            "natural_equipment",
            () -> new LootPoolEntryType(NaturalEquipmentEntry.CODEC));
    public static final Holder.Reference<LootPoolEntryType> SIMPLE_SEQUENCE_LOOT_POOL_ENTRY_TYPE = REGISTRIES.register(
            Registries.LOOT_POOL_ENTRY_TYPE,
            "simple_sequence",
            () -> new LootPoolEntryType(SimpleSequenceEntry.CODEC));
    public static final Holder.Reference<LootItemConditionType> DIFFICULTY_CHECK_LOOT_ITEM_CONDITION_TYPE = REGISTRIES.register(
            Registries.LOOT_CONDITION_TYPE,
            "difficulty_check",
            () -> new LootItemConditionType(DifficultyCheck.CODEC));

    public static void bootstrap() {
        ModLootTables.bootstrap();
    }
}
