package fuzs.armoredfoes.common.client.model;

import com.google.common.collect.Maps;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.model.monster.zombie.ZombieVillagerModel;
import net.minecraft.client.model.npc.BabyVillagerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Map;
import java.util.Set;

public class LivingArmorModel {
    /**
     * @see LayerDefinitions#createRoots()
     */
    private static final MeshTransformer VILLAGER_LIKE_TRANSFORMER = MeshTransformer.scaling(0.9375F);
    /**
     * @see HumanoidModel#ADULT_ARMOR_PARTS_PER_SLOT
     */
    private static final Map<EquipmentSlot, Set<String>> ARMED_ADULT_ARMOR_PARTS_PER_SLOT = Maps.newEnumMap(Map.of(
            EquipmentSlot.HEAD,
            Set.of("head"),
            EquipmentSlot.CHEST,
            Set.of("body", "right_shoulder", "left_shoulder"),
            EquipmentSlot.LEGS,
            Set.of("left_leg", "right_leg", "body"),
            EquipmentSlot.FEET,
            Set.of("left_leg", "right_leg")));
    /**
     * @see HumanoidModel#BABY_ARMOR_PARTS_PER_SLOT
     */
    private static final Map<EquipmentSlot, Set<String>> ARMED_BABY_ARMOR_PARTS_PER_SLOT = Maps.newEnumMap(Map.of(
            EquipmentSlot.HEAD,
            Set.of("head"),
            EquipmentSlot.CHEST,
            Set.of("body", "right_shoulder", "left_shoulder"),
            EquipmentSlot.LEGS,
            Set.of("left_leg", "right_leg", "waist"),
            EquipmentSlot.FEET,
            Set.of("left_foot", "right_foot")));

    public static ArmorModelSet<LayerDefinition> createHumanoidAdultArmorLayerSet() {
        return HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    /**
     * @see LayerDefinitions#createRoots()
     */
    public static ArmorModelSet<LayerDefinition> createHumanoidBabyArmorLayerSet() {
        return HumanoidModel.createBabyArmorMeshSet(LayerDefinitions.BABY_INNER_ARMOR_DEFORMATION,
                LayerDefinitions.BABY_OUTER_ARMOR_DEFORMATION,
                PartPose.ZERO).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 64);
        });
    }

    public static ArmorModelSet<LayerDefinition> createArmedAdultArmorLayerSet() {
        return createArmedAdultArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_LIKE_TRANSFORMER);
        });
    }

    /**
     * @see LayerDefinitions#createRoots()
     */
    public static ArmorModelSet<LayerDefinition> createArmedBabyArmorLayerSet() {
        return createArmedBabyArmorLayerSet(LayerDefinitions.BABY_INNER_ARMOR_DEFORMATION,
                LayerDefinitions.BABY_OUTER_ARMOR_DEFORMATION);
    }

    public static ArmorModelSet<LayerDefinition> createIllagerArmorLayerSet() {
        return createIllagerArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_LIKE_TRANSFORMER);
        });
    }

    public static ArmorModelSet<LayerDefinition> createWitchArmorLayerSet() {
        return createWitchArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_LIKE_TRANSFORMER);
        });
    }

    private static ArmorModelSet<LayerDefinition> createArmedAdultArmorLayerSet(CubeDeformation innerDeformation, CubeDeformation outerDeformation) {
        return HumanoidModel.createArmorMeshSet(LivingArmorModel::createArmedAdultArmorMesh,
                ARMED_ADULT_ARMOR_PARTS_PER_SLOT,
                innerDeformation,
                outerDeformation).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    private static ArmorModelSet<LayerDefinition> createArmedBabyArmorLayerSet(CubeDeformation innerDeformation, CubeDeformation outerDeformation) {
        return HumanoidModel.createArmorMeshSet(LivingArmorModel::createArmedBabyArmorMesh,
                ARMED_BABY_ARMOR_PARTS_PER_SLOT,
                innerDeformation,
                outerDeformation).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 64);
        });
    }

    private static ArmorModelSet<LayerDefinition> createIllagerArmorLayerSet(CubeDeformation innerDeformation, CubeDeformation outerDeformation) {
        return HumanoidModel.createArmorMeshSet(LivingArmorModel::createArmedAdultArmorMesh,
                HumanoidModel.ADULT_ARMOR_PARTS_PER_SLOT,
                innerDeformation,
                outerDeformation).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    private static ArmorModelSet<LayerDefinition> createWitchArmorLayerSet(CubeDeformation innerDeformation, CubeDeformation outerDeformation) {
        return createArmedAdultArmorLayerSet(innerDeformation,
                outerDeformation).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(LivingArmorModel::modifyWitchMesh);
        });
    }

    private static MeshDefinition createArmedAdultArmorMesh(CubeDeformation cubeDeformation) {
        return ZombieVillagerModel.createBaseArmorMesh(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            modifyArmedAdultArmorMesh(meshDefinition, cubeDeformation);
            return meshDefinition;
        });
    }

    /**
     * @see HumanoidModel#createBaseArmorMesh(CubeDeformation)
     * @see ZombieVillagerModel#createBaseArmorMesh(CubeDeformation)
     * @see IllagerModel#createBodyLayer()
     */
    private static void modifyArmedAdultArmorMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation.extend(0.1F, 0.1F, 0.6F)),
                PartPose.ZERO);
        PartDefinition arms = root.addOrReplaceChild("arms",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        arms.addOrReplaceChild("right_shoulder",
                CubeListBuilder.create().texOffs(40, 16).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation),
                PartPose.ZERO);
        arms.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation),
                PartPose.ZERO);
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(2.0F, 12.0F, 0.0F));
    }

    private static MeshDefinition createArmedBabyArmorMesh(CubeDeformation cubeDeformation) {
        return HumanoidModel.createBabyArmorMesh(cubeDeformation, PartPose.ZERO)
                .apply((MeshDefinition meshDefinition) -> {
                    return modifyArmedBabyArmorMesh(meshDefinition, cubeDeformation);
                });
    }

    /**
     * @see HumanoidModel#createBabyArmorMesh(CubeDeformation, PartPose)
     * @see BabyVillagerModel#createBodyModel()
     */
    private static MeshDefinition modifyArmedBabyArmorMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition arms = root.addOrReplaceChild("arms",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 17.5F, 0.0F));
        arms.addOrReplaceChild("right_shoulder",
                CubeListBuilder.create()
                        .texOffs(30, 25)
                        .addBox(-1.0F, -2.4925F, -1.8401F, 2.0F, 5.0F, 3.0F, cubeDeformation.extend(0.5F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(-3.0F, 1.0F, -0.9599F, -1.0472F, 0.0F, 0.0F));
        arms.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create()
                        .texOffs(30, 17)
                        .addBox(5.0F, -2.4925F, -1.8401F, 2.0F, 5.0F, 3.0F, cubeDeformation.extend(0.5F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(-3.0F, 1.0F, -0.9599F, -1.0472F, 0.0F, 0.0F));
        return meshDefinition;
    }

    private static MeshDefinition modifyWitchMesh(MeshDefinition meshDefinition) {
        PartDefinition root = meshDefinition.getRoot();
        root.getChild("head").addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);
        return meshDefinition;
    }
}
