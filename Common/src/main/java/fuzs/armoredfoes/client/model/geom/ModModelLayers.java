package fuzs.armoredfoes.client.model.geom;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(ArmoredFoes.MOD_ID);
    public static final ModelLayerLocation HUMANOID_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("humanoid");
    public static final ModelLayerLocation HUMANOID_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("humanoid");
    public static final ModelLayerLocation HUMANOID_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "humanoid_baby");
    public static final ModelLayerLocation HUMANOID_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "humanoid_baby");
    public static final ModelLayerLocation ILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("illager");
    public static final ModelLayerLocation ILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("illager");
    public static final ModelLayerLocation ILLAGER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "illager_baby");
    public static final ModelLayerLocation ILLAGER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "illager_baby");
    public static final ModelLayerLocation WITCH_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("witch");
    public static final ModelLayerLocation WITCH_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("witch");
    public static final ModelLayerLocation WITCH_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "witch_baby");
    public static final ModelLayerLocation WITCH_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "witch_baby");
    public static final ModelLayerLocation VILLAGER_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer("villager");
    public static final ModelLayerLocation VILLAGER_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer("villager");
    public static final ModelLayerLocation VILLAGER_BABY_INNER_ARMOR = MODEL_LAYERS.registerInnerArmorModelLayer(
            "villager_baby");
    public static final ModelLayerLocation VILLAGER_BABY_OUTER_ARMOR = MODEL_LAYERS.registerOuterArmorModelLayer(
            "villager_baby");
}
