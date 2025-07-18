package fuzs.armoredfoes.fabric;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class ArmoredFoesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoes::new);
    }
}
