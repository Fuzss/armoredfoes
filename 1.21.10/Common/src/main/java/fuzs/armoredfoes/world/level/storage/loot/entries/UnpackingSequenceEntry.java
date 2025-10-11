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

/**
 * Like {@link SequentialEntry}, meaning all entries until the first that fails are returned.
 * <p>
 * In contrast, here they are also unpacked directly, meaning all loot from the valid entries is generated, not just
 * from one single entry picked randomly later on.
 */
public class UnpackingSequenceEntry extends SequentialEntry {
    public static final MapCodec<UnpackingSequenceEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    LootPoolEntries.CODEC.listOf()
                            .optionalFieldOf("children", List.of())
                            .forGetter((UnpackingSequenceEntry entry) -> entry.children))
            .and(singletonFields(instance))
            .apply(instance, UnpackingSequenceEntry::new));

    protected final int weight;
    protected final int quality;
    protected final List<LootItemFunction> functions;
    final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;

    public UnpackingSequenceEntry(List<LootPoolEntryContainer> children, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(children, conditions);
        this.weight = weight;
        this.quality = quality;
        this.functions = functions;
        this.compositeFunction = LootItemFunctions.compose(functions);
    }

    /**
     * @see LootPoolSingletonContainer#singletonFields(RecordCodecBuilder.Instance)
     */
    protected static Products.P4<RecordCodecBuilder.Mu<UnpackingSequenceEntry>, Integer, Integer, List<LootItemCondition>, List<LootItemFunction>> singletonFields(RecordCodecBuilder.Instance<UnpackingSequenceEntry> instance) {
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
                            UnpackingSequenceEntry.this.weight + UnpackingSequenceEntry.this.quality * luck), 0);
                }

                @Override
                public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext) {
                    for (LootPoolEntry lootPoolEntry : lootPoolEntries) {
                        lootPoolEntry.createItemStack(LootItemFunction.decorate(UnpackingSequenceEntry.this.compositeFunction,
                                stackConsumer,
                                lootContext), lootContext);
                    }
                }
            });
            return UnpackingSequenceEntry.super.compose(children).expand(context, lootPoolEntries::add);
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
        return ModRegistry.UNPACKING_SEQUENCE_LOOT_POOL_ENTRY_TYPE.value();
    }

    public static class Builder extends LootPoolEntryContainer.Builder<UnpackingSequenceEntry.Builder> implements FunctionUserBuilder<UnpackingSequenceEntry.Builder> {
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
        protected UnpackingSequenceEntry.Builder getThis() {
            return this;
        }

        @Override
        public UnpackingSequenceEntry.Builder apply(LootItemFunction.Builder functionBuilder) {
            this.functions.add(functionBuilder.build());
            return this.getThis();
        }

        public UnpackingSequenceEntry.Builder and(LootPoolEntryContainer.Builder<?> childBuilder) {
            this.entries.add(childBuilder.build());
            return this.getThis();
        }

        protected List<LootItemFunction> getFunctions() {
            return this.functions.build();
        }

        public UnpackingSequenceEntry.Builder setWeight(int weight) {
            this.weight = weight;
            return this.getThis();
        }

        public UnpackingSequenceEntry.Builder setQuality(int quality) {
            this.quality = quality;
            return this.getThis();
        }

        @Override
        public LootPoolEntryContainer build() {
            return new UnpackingSequenceEntry(this.entries.build(),
                    this.weight,
                    this.quality,
                    this.getConditions(),
                    this.getFunctions());
        }
    }
}
