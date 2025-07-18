package fuzs.armoredfoes.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.function.Consumer;

/**
 * Picks a single entry from a list.
 *
 * @see net.minecraft.world.entity.Mob#populateDefaultEquipmentSlots(RandomSource, DifficultyInstance)
 */
public class SelectionEntry extends CompositeEntryBase {
    public static final MapCodec<SelectionEntry> CODEC = RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<SelectionEntry> instance) -> instance.group(
                    LootPoolEntries.CODEC.listOf()
                            .optionalFieldOf("children", List.of())
                            .forGetter((SelectionEntry entry) -> entry.children))
            .and(commonFields(instance).t1())
            .and(NumberProviders.CODEC.fieldOf("base").forGetter((SelectionEntry entry) -> entry.base))
            .and(NumberProviders.CODEC.fieldOf("per_value_above_first")
                    .forGetter((SelectionEntry entry) -> entry.perValueAboveFirst))
            .apply(instance, SelectionEntry::new));

    private final NumberProvider base;
    private final NumberProvider perValueAboveFirst;

    public SelectionEntry(List<LootPoolEntryContainer> children, List<LootItemCondition> conditions) {
        this(children, conditions, UniformGenerator.between(0.0F, 1.0F), ConstantValue.exactly(0.095F));
    }

    public SelectionEntry(List<LootPoolEntryContainer> children, List<LootItemCondition> conditions, NumberProvider base, NumberProvider perValueAboveFirst) {
        super(children, conditions);
        this.base = base;
        this.perValueAboveFirst = perValueAboveFirst;
    }

    @Override
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> children) {
        if (children.isEmpty()) {
            return ALWAYS_FALSE;
        } else {
            return (LootContext context, Consumer<LootPoolEntry> consumer) -> {
                int equipmentTier = this.getEquipmentTier(context, children.size());
                return children.get(equipmentTier).expand(context, consumer);
            };
        }
    }

    protected int getEquipmentTier(LootContext context, int tiers) {
        int equipmentTier = this.base.getInt(context);
        // slightly different from vanilla with more runs for increasing equipment tier
        for (int i = 0; i < tiers; i++) {
            if (context.getRandom().nextFloat() < this.perValueAboveFirst.getFloat(context)) {
                equipmentTier++;
            }
        }

        return Math.min(equipmentTier, tiers);
    }

    @Override
    public LootPoolEntryType getType() {
        return ModRegistry.SELECTION_LOOT_POOL_ENTRY_TYPE.value();
    }

    public static class Builder extends LootPoolEntryContainer.Builder<SelectionEntry.Builder> {
        private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();

        public Builder(LootPoolEntryContainer.Builder<?>... children) {
            for (LootPoolEntryContainer.Builder<?> builder : children) {
                this.entries.add(builder.build());
            }
        }

        @Override
        protected SelectionEntry.Builder getThis() {
            return this;
        }

        public SelectionEntry.Builder and(LootPoolEntryContainer.Builder<?> childBuilder) {
            this.entries.add(childBuilder.build());
            return this.getThis();
        }

        @Override
        public LootPoolEntryContainer build() {
            return new SelectionEntry(this.entries.build(), this.getConditions());
        }
    }
}
