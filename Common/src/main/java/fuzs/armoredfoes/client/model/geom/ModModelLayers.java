package fuzs.armoredfoes.client.model.geom;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ArmorModelSet;

import java.util.function.Function;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(ArmoredFoes.MOD_ID);
    public static final ArmorModelSet<ModelLayerLocation> HUMANOID_ARMOR = MODEL_LAYERS.registerArmorSet("humanoid");
    public static final ArmorModelSet<ModelLayerLocation> HUMANOID_BABY_ARMOR = MODEL_LAYERS.registerArmorSet(
            "humanoid_baby");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_ARMOR = MODEL_LAYERS.registerArmorSet("illager");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_BABY_ARMOR = MODEL_LAYERS.registerArmorSet(
            "illager_baby");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_CROSSED_ARMOR = MODEL_LAYERS.registerArmorSet(
            "illager/crossed");
    public static final ArmorModelSet<ModelLayerLocation> ILLAGER_BABY_CROSSED_ARMOR = MODEL_LAYERS.registerArmorSet(
            "illager_baby/crossed");
    public static final ArmorModelSet<ModelLayerLocation> WITCH_ARMOR = MODEL_LAYERS.registerArmorSet("witch");
    public static final ArmorModelSet<ModelLayerLocation> WITCH_BABY_ARMOR = MODEL_LAYERS.registerArmorSet("witch_baby");
    public static final ArmorModelSet<ModelLayerLocation> VILLAGER_ARMOR = MODEL_LAYERS.registerArmorSet("villager");
    public static final ArmorModelSet<ModelLayerLocation> VILLAGER_BABY_ARMOR = MODEL_LAYERS.registerArmorSet(
            "villager_baby");

    /**
     * @see ArmorModelSet#bake(ArmorModelSet, EntityModelSet, Function)
     */
    public static <M extends EntityModel<?>> ArmorModelSet<M> bake(ArmorModelSet<ModelLayerLocation> armorModelSet, EntityModelSet entityModelSet, Function<ModelPart, M> baker) {
        return armorModelSet.map((ModelLayerLocation modelLayerLocation) -> {
            return baker.apply(entityModelSet.bakeLayer(modelLayerLocation));
        });
    }
}
