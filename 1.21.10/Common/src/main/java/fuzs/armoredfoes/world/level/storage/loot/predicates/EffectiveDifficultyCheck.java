package fuzs.armoredfoes.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

import java.util.Set;

/**
 * A random condition based on {@link DifficultyInstance#getSpecialMultiplier()}.
 *
 * @see net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
 */
public record EffectiveDifficultyCheck(NumberProvider chance) implements LootItemCondition {
    public static final MapCodec<EffectiveDifficultyCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    NumberProviders.CODEC.fieldOf("chance").forGetter(EffectiveDifficultyCheck::chance))
            .apply(instance, EffectiveDifficultyCheck::new));

    @Override
    public LootItemConditionType getType() {
        return ModRegistry.EFFECTIVE_DIFFICULTY_CHECK_LOOT_ITEM_CONDITION_TYPE.value();
    }

    @Override
    public boolean test(LootContext context) {
        DifficultyInstance difficulty = getDifficulty(context);
        float f = this.chance.getFloat(context) * difficulty.getSpecialMultiplier();
        return context.getRandom().nextFloat() < f;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Sets.union(this.chance.getReferencedContextParams(), ImmutableSet.of(LootContextParams.ORIGIN));
    }

    public static DifficultyInstance getDifficulty(LootContext context) {
        BlockPos blockPos = BlockPos.containing(context.getParameter(LootContextParams.ORIGIN));
        return context.getLevel().getCurrentDifficultyAt(blockPos);
    }

    public static LootItemCondition.Builder randomChance(float chance) {
        return () -> new EffectiveDifficultyCheck(ConstantValue.exactly(chance));
    }

    public static LootItemCondition.Builder randomChance(NumberProvider chance) {
        return () -> new EffectiveDifficultyCheck(chance);
    }
}
