package fuzs.armoredfoes.client.model.geom;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(ArmoredFoes.MOD_ID);
    public static final ModelLayerLocation HUMANOID_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("humanoid");
    public static final ModelLayerLocation HUMANOID_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("humanoid");
    public static final ModelLayerLocation ILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("illager");
    public static final ModelLayerLocation ILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("illager");
    public static final ModelLayerLocation WITCH_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("witch");
    public static final ModelLayerLocation WITCH_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("witch");
    public static final ModelLayerLocation VILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("villager");
    public static final ModelLayerLocation VILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("villager");
}
