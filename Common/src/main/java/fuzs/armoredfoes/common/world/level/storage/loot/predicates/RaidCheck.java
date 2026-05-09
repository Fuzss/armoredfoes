package fuzs.armoredfoes.common.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;
import java.util.Set;

public record RaidCheck(Optional<IntRange> waves) implements LootItemCondition {
    public static final MapCodec<RaidCheck> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(IntRange.CODEC.optionalFieldOf(
            "waves").forGetter(RaidCheck::waves)).apply(instance, RaidCheck::new));

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean test(LootContext context) {
        if (context.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Raider raider) {
            return raider.getCurrentRaid() != null && (this.waves.isEmpty() || this.waves.get()
                    .test(context, raider.getCurrentRaid().getGroupsSpawned()));
        } else {
            return false;
        }
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return this.waves.map(IntRange::getReferencedContextParams)
                .orElseGet(LootItemCondition.super::getReferencedContextParams);
    }

    public static LootItemCondition.Builder raid() {
        return () -> new RaidCheck(Optional.empty());
    }

    public static LootItemCondition.Builder raid(IntRange waves) {
        return () -> new RaidCheck(Optional.of(waves));
    }
}
