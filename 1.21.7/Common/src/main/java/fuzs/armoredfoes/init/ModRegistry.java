package fuzs.armoredfoes.init;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.world.level.storage.loot.entries.SelectOneEntry;
import fuzs.armoredfoes.world.level.storage.loot.entries.UnpackingSequenceEntry;
import fuzs.armoredfoes.world.level.storage.loot.functions.ApplyEnchantmentProviderFunction;
import fuzs.armoredfoes.world.level.storage.loot.predicates.DifficultyCheck;
import fuzs.armoredfoes.world.level.storage.loot.predicates.EffectiveDifficultyCheck;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder();
    static final RegistryManager REGISTRIES = RegistryManager.from(ArmoredFoes.MOD_ID);
    public static final Holder.Reference<LootPoolEntryType> SELECT_ONE_LOOT_POOL_ENTRY_TYPE = REGISTRIES.register(
            Registries.LOOT_POOL_ENTRY_TYPE,
            "select_one",
            () -> new LootPoolEntryType(SelectOneEntry.CODEC));
    public static final Holder.Reference<LootPoolEntryType> UNPACKING_SEQUENCE_LOOT_POOL_ENTRY_TYPE = REGISTRIES.register(
            Registries.LOOT_POOL_ENTRY_TYPE,
            "unpacking_sequence",
            () -> new LootPoolEntryType(UnpackingSequenceEntry.CODEC));
    public static final Holder.Reference<LootItemConditionType> DIFFICULTY_CHECK_LOOT_ITEM_CONDITION_TYPE = REGISTRIES.register(
            Registries.LOOT_CONDITION_TYPE,
            "difficulty_check",
            () -> new LootItemConditionType(DifficultyCheck.CODEC));
    public static final Holder.Reference<LootItemConditionType> EFFECTIVE_DIFFICULTY_CHECK_LOOT_ITEM_CONDITION_TYPE = REGISTRIES.register(
            Registries.LOOT_CONDITION_TYPE,
            "effective_difficulty_check",
            () -> new LootItemConditionType(EffectiveDifficultyCheck.CODEC));
    public static final Holder.Reference<LootItemFunctionType<ApplyEnchantmentProviderFunction>> APPLY_ENCHANTMENT_PROVIDER_LOOT_FUNCTION_TYPE = REGISTRIES.register(
            Registries.LOOT_FUNCTION_TYPE,
            "apply_enchantment_provider",
            () -> new LootItemFunctionType<>(ApplyEnchantmentProviderFunction.CODEC));

    static final TagFactory TAGS = TagFactory.make(ArmoredFoes.MOD_ID);
    public static final TagKey<EntityType<?>> SHOWS_WORN_ARMOR_ENTITY_TAG = TAGS.registerEntityTypeTag(
            "shows_worn_armor");

    public static void bootstrap() {
        ModLootTables.bootstrap();
    }
}
