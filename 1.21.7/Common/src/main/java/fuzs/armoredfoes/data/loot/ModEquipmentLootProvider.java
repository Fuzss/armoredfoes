package fuzs.armoredfoes.data.loot;

import fuzs.armoredfoes.init.ModLootTables;
import fuzs.armoredfoes.world.level.storage.loot.entries.NaturalEquipmentEntry;
import fuzs.armoredfoes.world.level.storage.loot.entries.SimpleSequenceEntry;
import fuzs.armoredfoes.world.level.storage.loot.predicates.DifficultyCheck;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModEquipmentLootProvider extends AbstractLootProvider.Simple {

    public ModEquipmentLootProvider(DataProviderContext context) {
        super(LootContextParamSets.EQUIPMENT, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModLootTables.LEATHER_ARMOR_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SimpleSequenceEntry.Builder(LootItem.lootTableItem(Items.LEATHER_HELMET),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.LEATHER_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.LEATHER_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.LEATHER_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.LEATHER_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.LEATHER_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD))))))));
        this.add(ModLootTables.GOLDEN_ARMOR_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SimpleSequenceEntry.Builder(LootItem.lootTableItem(Items.GOLDEN_HELMET),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD))))))));
        this.add(ModLootTables.CHAINMAIL_ARMOR_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SimpleSequenceEntry.Builder(LootItem.lootTableItem(Items.CHAINMAIL_HELMET),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.CHAINMAIL_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.CHAINMAIL_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD))))))));
        this.add(ModLootTables.IRON_ARMOR_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SimpleSequenceEntry.Builder(LootItem.lootTableItem(Items.IRON_HELMET),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.IRON_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.IRON_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD))))))));
        this.add(ModLootTables.DIAMOND_ARMOR_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new SimpleSequenceEntry.Builder(LootItem.lootTableItem(Items.DIAMOND_HELMET),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.DIAMOND_LEGGINGS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD)))),
                                        AlternativesEntry.alternatives(LootItem.lootTableItem(Items.DIAMOND_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.9F))
                                                        .when(DifficultyCheck.difficulty(Difficulty.HARD)),
                                                LootItem.lootTableItem(Items.DIAMOND_BOOTS)
                                                        .when(LootItemRandomChanceCondition.randomChance(0.75F))
                                                        .when(InvertedLootItemCondition.invert(DifficultyCheck.difficulty(
                                                                Difficulty.HARD))))))));
        this.add(ModLootTables.WITHER_SKELETON_EQUIPMENT_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(new NaturalEquipmentEntry.Builder(NestedLootTable.lootTableReference(ModLootTables.LEATHER_ARMOR_EQUIPMENT_LOOT_TABLE),
                                        NestedLootTable.lootTableReference(ModLootTables.GOLDEN_ARMOR_EQUIPMENT_LOOT_TABLE),
                                        NestedLootTable.lootTableReference(ModLootTables.CHAINMAIL_ARMOR_EQUIPMENT_LOOT_TABLE),
                                        NestedLootTable.lootTableReference(ModLootTables.IRON_ARMOR_EQUIPMENT_LOOT_TABLE),
                                        NestedLootTable.lootTableReference(ModLootTables.DIAMOND_ARMOR_EQUIPMENT_LOOT_TABLE))))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GOLDEN_SWORD)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.SHIELD))));
    }
}
