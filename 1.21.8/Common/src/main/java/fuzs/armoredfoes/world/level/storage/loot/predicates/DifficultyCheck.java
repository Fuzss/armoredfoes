package fuzs.armoredfoes.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

/**
 * A simple {@link Difficulty} check.
 *
 * @see net.minecraft.world.level.storage.loot.predicates.WeatherCheck
 */
public record DifficultyCheck(Difficulty difficulty) implements LootItemCondition {
    public static final MapCodec<DifficultyCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Difficulty.CODEC.fieldOf("difficulty").forGetter(DifficultyCheck::difficulty))
            .apply(instance, DifficultyCheck::new));

    @Override
    public LootItemConditionType getType() {
        return ModRegistry.DIFFICULTY_CHECK_LOOT_ITEM_CONDITION_TYPE.value();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getLevel().getDifficulty() == this.difficulty;
    }

    public static LootItemCondition.Builder difficulty(Difficulty difficulty) {
        return () -> new DifficultyCheck(difficulty);
    }
}
