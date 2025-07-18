package fuzs.armoredfoes.world.level.storage.loot.functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.armoredfoes.init.ModRegistry;
import fuzs.armoredfoes.world.level.storage.loot.predicates.EffectiveDifficultyCheck;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

/**
 * Apply enchantments from an {@link EnchantmentProvider}.
 *
 * @see net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction
 * @see net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction
 */
public class ApplyEnchantmentProviderFunction extends LootItemConditionalFunction {
    public static final MapCodec<ApplyEnchantmentProviderFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(
            instance).and(instance.group(RegistryFixedCodec.create(Registries.ENCHANTMENT_PROVIDER)
                    .fieldOf("provider")
                    .forGetter((ApplyEnchantmentProviderFunction function) -> function.provider)).t1())
            .apply(instance, ApplyEnchantmentProviderFunction::new));

    private final Holder<EnchantmentProvider> provider;

    public ApplyEnchantmentProviderFunction(List<LootItemCondition> predicates, Holder<EnchantmentProvider> provider) {
        super(predicates);
        this.provider = provider;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return ModRegistry.APPLY_ENCHANTMENT_PROVIDER_LOOT_FUNCTION_TYPE.value();
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext context) {
        RegistryAccess registryAccess = context.getLevel().registryAccess();
        DifficultyInstance difficulty = EffectiveDifficultyCheck.getDifficulty(context);
        EnchantmentHelper.enchantItemFromProvider(itemStack,
                registryAccess,
                this.provider.unwrapKey().orElseThrow(),
                difficulty,
                context.getRandom());
        return itemStack;
    }

    public static ApplyEnchantmentProviderFunction.Builder fromProvider(HolderLookup.Provider registries, ResourceKey<EnchantmentProvider> resourceKey) {
        return new ApplyEnchantmentProviderFunction.Builder(registries.lookupOrThrow(Registries.ENCHANTMENT_PROVIDER)
                .getOrThrow(resourceKey));
    }

    public static class Builder extends LootItemConditionalFunction.Builder<ApplyEnchantmentProviderFunction.Builder> {
        private final Holder<EnchantmentProvider> provider;

        public Builder(Holder<EnchantmentProvider> provider) {
            this.provider = provider;
        }

        @Override
        protected ApplyEnchantmentProviderFunction.Builder getThis() {
            return this;
        }

        @Override
        public LootItemFunction build() {
            return new ApplyEnchantmentProviderFunction(this.getConditions(), this.provider);
        }
    }
}
