package fuzs.armoredfoes.data.loot;

import fuzs.armoredfoes.init.ModLootTables;
import fuzs.armoredfoes.world.level.storage.loot.entries.SelectOneEntry;
import fuzs.armoredfoes.world.level.storage.loot.entries.UnpackingSequenceEntry;
import fuzs.armoredfoes.world.level.storage.loot.functions.ApplyEnchantmentProviderFunction;
import fuzs.armoredfoes.world.level.storage.loot.predicates.DifficultyCheck;
import fuzs.armoredfoes.world.level.storage.loot.predicates.EffectiveDifficultyCheck;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModEquipmentLootProvider extends AbstractLootProvider.Simple {

    public ModEquipmentLootProvider(DataProviderContext context) {
        super(LootContextParamSets.EQUIPMENT, context);
    }

    @Override
    public void addLootTables() {
        this.addNaturalArmorTables(ModLootTables.NATURAL_ARMOR_EQUIPMENT,
                ModLootTables.LEATHER_ARMOR_EQUIPMENT,
                ModLootTables.GOLDEN_ARMOR_EQUIPMENT,
                ModLootTables.CHAINMAIL_ARMOR_EQUIPMENT,
                ModLootTables.IRON_ARMOR_EQUIPMENT,
                ModLootTables.DIAMOND_ARMOR_EQUIPMENT,
                false);
        this.addNaturalArmorTables(ModLootTables.ENCHANTED_NATURAL_ARMOR_EQUIPMENT,
                ModLootTables.ENCHANTED_LEATHER_ARMOR_EQUIPMENT,
                ModLootTables.ENCHANTED_GOLDEN_ARMOR_EQUIPMENT,
                ModLootTables.ENCHANTED_CHAINMAIL_ARMOR_EQUIPMENT,
                ModLootTables.ENCHANTED_IRON_ARMOR_EQUIPMENT,
                ModLootTables.ENCHANTED_DIAMOND_ARMOR_EQUIPMENT,
                true);
        this.addNaturalArmorTable(EntityType.WITHER_SKELETON);
        this.addNaturalArmorTable(EntityType.DROWNED);
        this.addGoldenArmorTable(EntityType.PIGLIN);
        this.addGoldenArmorTable(EntityType.PIGLIN_BRUTE);
        this.addGoldenArmorTable(EntityType.ZOMBIFIED_PIGLIN);
    }

    private void addNaturalArmorTable(EntityType<?> entityType) {
        this.add(ModLootTables.createEntityEquipmentTable(entityType),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(ModLootTables.ENCHANTED_NATURAL_ARMOR_EQUIPMENT))));
    }

    private void addGoldenArmorTable(EntityType<?> entityType) {
        this.add(ModLootTables.createEntityEquipmentTable(entityType),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(NestedLootTable.lootTableReference(ModLootTables.ENCHANTED_GOLDEN_ARMOR_EQUIPMENT))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .flags(EntityFlagsPredicate.Builder.flags().setIsBaby(false))))));
    }

    private void addNaturalArmorTables(ResourceKey<LootTable> armorTable, ResourceKey<LootTable> leatherTable, ResourceKey<LootTable> goldenTable, ResourceKey<LootTable> chainmailTable, ResourceKey<LootTable> ironTable, ResourceKey<LootTable> diamondTable, boolean enchanted) {
        this.add(armorTable,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SelectOneEntry.Builder(NestedLootTable.lootTableReference(leatherTable),
                                        NestedLootTable.lootTableReference(goldenTable),
                                        NestedLootTable.lootTableReference(chainmailTable),
                                        NestedLootTable.lootTableReference(ironTable),
                                        NestedLootTable.lootTableReference(diamondTable)).when(EffectiveDifficultyCheck.randomChance(
                                        0.15F)))));
        this.add(leatherTable,
                this.createArmorEquipment(Items.LEATHER_HELMET,
                        Items.LEATHER_CHESTPLATE,
                        Items.LEATHER_LEGGINGS,
                        Items.LEATHER_BOOTS,
                        enchanted));
        this.add(goldenTable,
                this.createArmorEquipment(Items.GOLDEN_HELMET,
                        Items.GOLDEN_CHESTPLATE,
                        Items.GOLDEN_LEGGINGS,
                        Items.GOLDEN_BOOTS,
                        enchanted));
        this.add(chainmailTable,
                this.createArmorEquipment(Items.CHAINMAIL_HELMET,
                        Items.CHAINMAIL_CHESTPLATE,
                        Items.CHAINMAIL_LEGGINGS,
                        Items.CHAINMAIL_BOOTS,
                        enchanted));
        this.add(ironTable,
                this.createArmorEquipment(Items.IRON_HELMET,
                        Items.IRON_CHESTPLATE,
                        Items.IRON_LEGGINGS,
                        Items.IRON_BOOTS,
                        enchanted));
        this.add(diamondTable,
                this.createArmorEquipment(Items.DIAMOND_HELMET,
                        Items.DIAMOND_CHESTPLATE,
                        Items.DIAMOND_LEGGINGS,
                        Items.DIAMOND_BOOTS,
                        enchanted));
    }

    private LootTable.Builder createArmorEquipment(Item headItem, Item chestItem, Item legsItem, Item feetItem, boolean enchant) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(new UnpackingSequenceEntry.Builder(
                                enchant ? this.createEnchantedSpawnedArmor(headItem) : LootItem.lootTableItem(headItem),
                                this.createArmorEquipmentPiece(chestItem, enchant),
                                this.createArmorEquipmentPiece(legsItem, enchant),
                                this.createArmorEquipmentPiece(feetItem, enchant))));
    }

    /**
     * @see net.minecraft.world.entity.Mob#populateDefaultEquipmentSlots(RandomSource, DifficultyInstance)
     */
    private LootPoolEntryContainer.Builder<?> createArmorEquipmentPiece(Item item, boolean enchant) {
        return AlternativesEntry.alternatives((enchant ? this.createEnchantedSpawnedArmor(item) :
                        LootItem.lootTableItem(item)).when(LootItemRandomChanceCondition.randomChance(0.9F))
                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                (enchant ? this.createEnchantedSpawnedArmor(item) : LootItem.lootTableItem(item)).when(
                                LootItemRandomChanceCondition.randomChance(0.75F))
                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(Difficulty.HARD))));
    }

    /**
     * @see net.minecraft.world.entity.Mob#enchantSpawnedWeapon(ServerLevelAccessor, RandomSource,
     *         DifficultyInstance)
     */
    private LootPoolSingletonContainer.Builder<?> createEnchantedSpawnedWeapon(Item item) {
        return this.createSpawnedEquipment(item, 0.25F);
    }

    /**
     * @see net.minecraft.world.entity.Mob#enchantSpawnedArmor(ServerLevelAccessor, RandomSource, EquipmentSlot,
     *         DifficultyInstance)
     */
    private LootPoolSingletonContainer.Builder<?> createEnchantedSpawnedArmor(Item item) {
        return this.createSpawnedEquipment(item, 0.5F);
    }

    /**
     * @see net.minecraft.world.entity.Mob#enchantSpawnedEquipment(ServerLevelAccessor, EquipmentSlot, RandomSource,
     *         float, DifficultyInstance)
     */
    private LootPoolSingletonContainer.Builder<?> createSpawnedEquipment(Item item, float randomChance) {
        return LootItem.lootTableItem(item)
                .apply(ApplyEnchantmentProviderFunction.fromProvider(this.registries(),
                                VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT)
                        .when(EffectiveDifficultyCheck.randomChance(randomChance)));
    }
}
