package fuzs.armoredfoes.client.model.geom;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

/**
 * TODO 1.21.1 does not use separate baby model layers, they might be problematic as the scaling will apply twice
 */
public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(ArmoredFoes.MOD_ID);
    public static final ModelLayerLocation HUMANOID_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("humanoid");
    public static final ModelLayerLocation HUMANOID_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("humanoid");
    public static final ModelLayerLocation HUMANOID_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("humanoid_baby");
    public static final ModelLayerLocation HUMANOID_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("humanoid_baby");
    public static final ModelLayerLocation ILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("illager");
    public static final ModelLayerLocation ILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("illager");
    public static final ModelLayerLocation ILLAGER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("illager_baby");
    public static final ModelLayerLocation ILLAGER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("illager_baby");
    public static final ModelLayerLocation WITCH_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("witch");
    public static final ModelLayerLocation WITCH_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("witch");
    public static final ModelLayerLocation WITCH_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("witch_baby");
    public static final ModelLayerLocation WITCH_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("witch_baby");
    public static final ModelLayerLocation VILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("villager");
    public static final ModelLayerLocation VILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("villager");
    public static final ModelLayerLocation VILLAGER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmor("villager_baby");
    public static final ModelLayerLocation VILLAGER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmor("villager_baby");
}
