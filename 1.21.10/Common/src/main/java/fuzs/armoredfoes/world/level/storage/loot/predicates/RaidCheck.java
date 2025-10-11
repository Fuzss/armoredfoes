package fuzs.armoredfoes.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Optional;
import java.util.Set;

public record RaidCheck(Optional<IntRange> waves) implements LootItemCondition {
    public static final MapCodec<RaidCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(IntRange.CODEC.optionalFieldOf(
            "waves").forGetter(RaidCheck::waves)).apply(instance, RaidCheck::new));

    @Override
    public LootItemConditionType getType() {
        return ModRegistry.RAID_CHECK_LOOT_ITEM_CONDITION_TYPE.value();
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
