package fuzs.armoredfoes.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A simple {@link Difficulty} check.
 *
 * @see net.minecraft.world.level.storage.loot.predicates.WeatherCheck
 */
public record DifficultyCheck(Difficulty difficulty) implements LootItemCondition {
    public static final MapCodec<DifficultyCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Difficulty.CODEC.fieldOf("difficulty").forGetter(DifficultyCheck::difficulty))
            .apply(instance, DifficultyCheck::new));

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getLevel().getDifficulty() == this.difficulty;
    }

    public static LootItemCondition.Builder difficulty(Difficulty difficulty) {
        return () -> new DifficultyCheck(difficulty);
    }
}
