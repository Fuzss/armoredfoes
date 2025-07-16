package fuzs.armoredfoes.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.function.Consumer;

public class NaturalEquipmentEntry extends CompositeEntryBase {
    public static final MapCodec<NaturalEquipmentEntry> CODEC = RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<NaturalEquipmentEntry> instance) -> instance.group(
                    LootPoolEntries.CODEC.listOf()
                            .optionalFieldOf("children", List.of())
                            .forGetter((NaturalEquipmentEntry entry) -> entry.children))
            .and(commonFields(instance).t1())
            .and(NumberProviders.CODEC.optionalFieldOf("equipment_chance", ConstantValue.exactly(0.15F))
                    .forGetter((NaturalEquipmentEntry entry) -> entry.equipmentChance))
            .and(NumberProviders.CODEC.optionalFieldOf("max_base_tier", UniformGenerator.between(0.0F, 1.0F))
                    .forGetter((NaturalEquipmentEntry entry) -> entry.maxBaseTier))
            .and(NumberProviders.CODEC.optionalFieldOf("per_tier_after_first", ConstantValue.exactly(0.095F))
                    .forGetter((NaturalEquipmentEntry entry) -> entry.perTierAfterFirst))
            .apply(instance, NaturalEquipmentEntry::new));

    private final NumberProvider equipmentChance;
    private final NumberProvider maxBaseTier;
    private final NumberProvider perTierAfterFirst;

    public NaturalEquipmentEntry(List<LootPoolEntryContainer> children, List<LootItemCondition> conditions) {
        this(children,
                conditions,
                ConstantValue.exactly(0.15F),
                UniformGenerator.between(0.0F, 1.0F),
                ConstantValue.exactly(0.095F));
    }

    public NaturalEquipmentEntry(List<LootPoolEntryContainer> children, List<LootItemCondition> conditions, NumberProvider equipmentChance, NumberProvider maxBaseTier, NumberProvider perTierAfterFirst) {
        super(children, conditions);
        this.equipmentChance = equipmentChance;
        this.maxBaseTier = maxBaseTier;
        this.perTierAfterFirst = perTierAfterFirst;
    }

    @Override
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> children) {
        if (children.isEmpty()) {
            return ALWAYS_FALSE;
        } else {
            return (LootContext context, Consumer<LootPoolEntry> consumer) -> {
                BlockPos blockPos = BlockPos.containing(context.getParameter(LootContextParams.ORIGIN));
                DifficultyInstance difficulty = context.getLevel().getCurrentDifficultyAt(blockPos);
                int equipmentTier = this.getEquipmentTier(context, difficulty, children.size());
                return equipmentTier != -1 && children.get(equipmentTier).expand(context, consumer);
            };
        }
    }

    protected int getEquipmentTier(LootContext context, DifficultyInstance difficulty, int tiers) {
        if (context.getRandom().nextFloat()
                < this.equipmentChance.getFloat(context) * difficulty.getSpecialMultiplier()) {
            int equipmentTier = this.maxBaseTier.getInt(context);
            for (int i = 0; i < tiers; i++) {
                if (context.getRandom().nextFloat() < this.perTierAfterFirst.getFloat(context)) {
                    equipmentTier++;
                }
            }

            return Math.min(equipmentTier, tiers);
        } else {
            return -1;
        }
    }

    @Override
    public LootPoolEntryType getType() {
        return ModRegistry.NATURAL_EQUIPMENT_LOOT_POOL_ENTRY_TYPE.value();
    }

    public static class Builder extends LootPoolEntryContainer.Builder<NaturalEquipmentEntry.Builder> {
        private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();

        public Builder(LootPoolEntryContainer.Builder<?>... children) {
            for (LootPoolEntryContainer.Builder<?> builder : children) {
                this.entries.add(builder.build());
            }
        }

        @Override
        protected NaturalEquipmentEntry.Builder getThis() {
            return this;
        }

        public NaturalEquipmentEntry.Builder and(LootPoolEntryContainer.Builder<?> childBuilder) {
            this.entries.add(childBuilder.build());
            return this.getThis();
        }

        @Override
        public LootPoolEntryContainer build() {
            return new NaturalEquipmentEntry(this.entries.build(), this.getConditions());
        }
    }
}
