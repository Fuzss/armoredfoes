package fuzs.armoredfoes.neoforge;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.data.loot.ModEquipmentLootProvider;
import fuzs.armoredfoes.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(ArmoredFoes.MOD_ID)
public class ArmoredFoesNeoForge {

    public ArmoredFoesNeoForge() {
        ModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoes::new);
        DataProviderHelper.registerDataProviders(ArmoredFoes.MOD_ID,
                ModRegistry.REGISTRY_SET_BUILDER,
                ModEquipmentLootProvider::new);
    }
}
