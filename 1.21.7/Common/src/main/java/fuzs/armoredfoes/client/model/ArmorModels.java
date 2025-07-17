package fuzs.armoredfoes.client.model;

import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ArmorModels {

    public static LayerDefinition createArmorLayer(CubeDeformation cubeDeformation) {
        return ZombieVillagerModel.createArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyMesh(meshDefinition, cubeDeformation);
        });
    }

    public static LayerDefinition createVillagerArmorLayer(CubeDeformation cubeDeformation) {
        return createArmorLayer(cubeDeformation).apply(ArmorModels::modifyVillagerMesh);
    }

    public static LayerDefinition createInnerVillagerArmorLayer(CubeDeformation cubeDeformation) {
        return createVillagerArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyInnerVillagerMesh(meshDefinition, cubeDeformation);
        });
    }

    public static LayerDefinition createWitchArmorLayer(CubeDeformation cubeDeformation) {
        return createVillagerArmorLayer(cubeDeformation).apply(ArmorModels::modifyWitchMesh);
    }

    public static LayerDefinition createInnerWitchArmorLayer(CubeDeformation cubeDeformation) {
        return createWitchArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyInnerVillagerMesh(meshDefinition, cubeDeformation);
        });
    }

    private static MeshDefinition modifyMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation.extend(0.1F, 0.1F, 0.6F)),
                PartPose.ZERO);
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("arms",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partDefinition3.addOrReplaceChild("right_shoulder",
                CubeListBuilder.create().texOffs(40, 16).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation),
                PartPose.ZERO);
        partDefinition3.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation),
                PartPose.ZERO);
        return meshDefinition;
    }

    private static MeshDefinition modifyVillagerMesh(MeshDefinition meshDefinition) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.clearChild("right_arm");
        partDefinition.clearChild("left_arm");
        return meshDefinition;
    }

    private static MeshDefinition modifyWitchMesh(MeshDefinition meshDefinition) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        // witches already wear a hat
        PartDefinition partDefinition2 = partDefinition.clearChild("head");
        partDefinition2.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);
        return meshDefinition;
    }

    private static MeshDefinition modifyInnerVillagerMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.2F, 0.1F, 0.1F)),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        return meshDefinition;
    }
}
