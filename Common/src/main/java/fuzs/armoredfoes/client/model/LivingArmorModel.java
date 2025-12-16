package fuzs.armoredfoes.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.monster.zombie.ZombieVillagerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;

import java.util.Set;
import java.util.function.Function;

public class LivingArmorModel {
    private static final MeshTransformer VILLAGER_TRANSFORMER = MeshTransformer.scaling(0.9375F);

    public static ArmorModelSet<LayerDefinition> createHumanoidArmorLayerSet() {
        return HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    public static ArmorModelSet<LayerDefinition> createArmedArmorLayerSet() {
        return createArmedArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_TRANSFORMER);
        });
    }

    public static ArmorModelSet<LayerDefinition> createWitchArmorLayerSet() {
        return createWitchArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_TRANSFORMER);
        });
    }

    public static ArmorModelSet<LayerDefinition> createIllagerArmorLayerSet() {
        return createIllagerArmorLayerSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                LayerDefinitions.OUTER_ARMOR_DEFORMATION).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(VILLAGER_TRANSFORMER);
        });
    }

    public static ArmorModelSet<LayerDefinition> createIllagerArmorLayerSet(CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        return HumanoidModel.createArmorMeshSet(LivingArmorModel::createArmedArmorMesh,
                innerCubeDeformation,
                outerCubeDeformation).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    public static ArmorModelSet<LayerDefinition> createArmedArmorLayerSet(CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        return createArmedArmorMeshSet(LivingArmorModel::createArmedArmorMesh,
                innerCubeDeformation,
                outerCubeDeformation).map((MeshDefinition meshDefinition) -> {
            return LayerDefinition.create(meshDefinition, 64, 32);
        });
    }

    public static ArmorModelSet<LayerDefinition> createWitchArmorLayerSet(CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        return createArmedArmorLayerSet(innerCubeDeformation,
                outerCubeDeformation).map((LayerDefinition layerDefinition) -> {
            return layerDefinition.apply(LivingArmorModel::modifyWitchMesh);
        });
    }

    /**
     * @see HumanoidModel#createArmorMeshSet(Function, CubeDeformation, CubeDeformation)
     */
    private static ArmorModelSet<MeshDefinition> createArmedArmorMeshSet(Function<CubeDeformation, MeshDefinition> meshCreator, CubeDeformation innerCubeDeformation, CubeDeformation outerCubeDeformation) {
        MeshDefinition meshDefinition = meshCreator.apply(outerCubeDeformation);
        meshDefinition.getRoot().retainPartsAndChildren(Set.of("head"));
        MeshDefinition meshDefinition2 = meshCreator.apply(outerCubeDeformation);
        meshDefinition2.getRoot().retainPartsAndChildren(Set.of("body", "arms"));
        MeshDefinition meshDefinition3 = meshCreator.apply(innerCubeDeformation);
        meshDefinition3.getRoot().retainExactParts(Set.of("left_leg", "right_leg", "body"));
        MeshDefinition meshDefinition4 = meshCreator.apply(outerCubeDeformation);
        meshDefinition4.getRoot().retainExactParts(Set.of("left_leg", "right_leg"));
        return new ArmorModelSet<>(meshDefinition, meshDefinition2, meshDefinition3, meshDefinition4);
    }

    private static MeshDefinition createArmedArmorMesh(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = ZombieVillagerModel.createBaseArmorMesh(cubeDeformation);
        modifyOuterMesh(meshDefinition, cubeDeformation);
        modifyInnerMesh(meshDefinition, cubeDeformation);
        return meshDefinition;
    }

    private static MeshDefinition modifyWitchMesh(MeshDefinition meshDefinition) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.getChild("head").addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);
        return meshDefinition;
    }

    private static void modifyOuterMesh(MeshDefinition meshDefinition, CubeDeformation outerCubeDeformation) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, outerCubeDeformation.extend(0.1F, 0.1F, 0.6F)),
                PartPose.ZERO);
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("arms",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partDefinition3.addOrReplaceChild("right_shoulder",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, outerCubeDeformation),
                PartPose.ZERO);
        partDefinition3.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, outerCubeDeformation),
                PartPose.ZERO);
    }

    private static void modifyInnerMesh(MeshDefinition meshDefinition, CubeDeformation innerCubeDeformation) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, innerCubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, innerCubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(2.0F, 12.0F, 0.0F));
    }
}
