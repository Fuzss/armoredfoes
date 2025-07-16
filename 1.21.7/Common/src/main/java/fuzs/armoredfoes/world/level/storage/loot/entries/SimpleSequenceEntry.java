package fuzs.armoredfoes.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SimpleSequenceEntry extends SequentialEntry {
    public static final MapCodec<SimpleSequenceEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    LootPoolEntries.CODEC.listOf()
                            .optionalFieldOf("children", List.of())
                            .forGetter((SimpleSequenceEntry entry) -> entry.children))
            .and(singletonFields(instance))
            .apply(instance, SimpleSequenceEntry::new));

    protected final int weight;
    protected final int quality;
    protected final List<LootItemFunction> functions;
    final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;

    public SimpleSequenceEntry(List<LootPoolEntryContainer> children, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(children, conditions);
        this.weight = weight;
        this.quality = quality;
        this.functions = functions;
        this.compositeFunction = LootItemFunctions.compose(functions);
    }

    /**
     * @see LootPoolSingletonContainer#singletonFields(RecordCodecBuilder.Instance)
     */
    protected static Products.P4<RecordCodecBuilder.Mu<SimpleSequenceEntry>, Integer, Integer, List<LootItemCondition>, List<LootItemFunction>> singletonFields(RecordCodecBuilder.Instance<SimpleSequenceEntry> instance) {
        return instance.group(Codec.INT.optionalFieldOf("weight", 1).forGetter(container -> container.weight),
                        Codec.INT.optionalFieldOf("quality", 0)
                                .forGetter(lootPoolSingletonContainer -> lootPoolSingletonContainer.quality))
                .and(commonFields(instance).t1())
                .and(LootItemFunctions.ROOT_CODEC.listOf()
                        .optionalFieldOf("functions", List.of())
                        .forGetter(container -> container.functions));
    }

    @Override
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> children) {
        return (LootContext context, Consumer<LootPoolEntry> consumer) -> {
            List<LootPoolEntry> lootPoolEntries = new ArrayList<>();
            consumer.accept(new LootPoolEntry() {
                @Override
                public int getWeight(float luck) {
                    return Math.max(Mth.floor(
                            SimpleSequenceEntry.this.weight + SimpleSequenceEntry.this.quality * luck), 0);
                }

                @Override
                public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext) {
                    for (LootPoolEntry lootPoolEntry : lootPoolEntries) {
                        lootPoolEntry.createItemStack(LootItemFunction.decorate(SimpleSequenceEntry.this.compositeFunction,
                                stackConsumer,
                                lootContext), lootContext);
                    }
                }
            });
            return SimpleSequenceEntry.super.compose(children).expand(context, lootPoolEntries::add);
        };
    }

    @Override
    public void validate(ValidationContext validationContext) {
        super.validate(validationContext);

        for (int i = 0; i < this.functions.size(); i++) {
            this.functions.get(i)
                    .validate(validationContext.forChild(new ProblemReporter.IndexedFieldPathElement("functions", i)));
        }
    }

    @Override
    public LootPoolEntryType getType() {
        return ModRegistry.SIMPLE_SEQUENCE_LOOT_POOL_ENTRY_TYPE.value();
    }

    public static class Builder extends LootPoolEntryContainer.Builder<SimpleSequenceEntry.Builder> implements FunctionUserBuilder<SimpleSequenceEntry.Builder> {
        private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
        protected int weight = 1;
        protected int quality = 0;
        private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();

        public Builder(LootPoolEntryContainer.Builder<?>... children) {
            for (LootPoolEntryContainer.Builder<?> builder : children) {
                this.entries.add(builder.build());
            }
        }

        @Override
        protected SimpleSequenceEntry.Builder getThis() {
            return this;
        }

        @Override
        public SimpleSequenceEntry.Builder apply(LootItemFunction.Builder functionBuilder) {
            this.functions.add(functionBuilder.build());
            return this.getThis();
        }

        public SimpleSequenceEntry.Builder and(LootPoolEntryContainer.Builder<?> childBuilder) {
            this.entries.add(childBuilder.build());
            return this.getThis();
        }

        protected List<LootItemFunction> getFunctions() {
            return this.functions.build();
        }

        public SimpleSequenceEntry.Builder setWeight(int weight) {
            this.weight = weight;
            return this.getThis();
        }

        public SimpleSequenceEntry.Builder setQuality(int quality) {
            this.quality = quality;
            return this.getThis();
        }

        @Override
        public LootPoolEntryContainer build() {
            return new SimpleSequenceEntry(this.entries.build(),
                    this.weight,
                    this.quality,
                    this.getConditions(),
                    this.getFunctions());
        }
    }
}
