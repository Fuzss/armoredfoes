package fuzs.armoredfoes.client.model;

import fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.LayerDefinition;
import fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.MeshDefinition;
import fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.PartDefinition;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;

public class LivingArmorModel {

    public static LayerDefinition createArmorLayer(CubeDeformation cubeDeformation) {
        return new LayerDefinition(ZombieVillagerModel.createArmorLayer(cubeDeformation)).apply((MeshDefinition meshDefinition) -> {
            return modifyMesh(meshDefinition, cubeDeformation);
        });
    }

    public static LayerDefinition createInnerArmorLayer(CubeDeformation cubeDeformation) {
        return createArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyInnerMesh(meshDefinition, cubeDeformation);
        });
    }

    public static LayerDefinition createVillagerArmorLayer(CubeDeformation cubeDeformation) {
        return createArmorLayer(cubeDeformation).apply(LivingArmorModel::modifyVillagerMesh);
    }

    public static LayerDefinition createInnerVillagerArmorLayer(CubeDeformation cubeDeformation) {
        return createVillagerArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyInnerMesh(meshDefinition, cubeDeformation);
        });
    }

    public static LayerDefinition createWitchArmorLayer(CubeDeformation cubeDeformation) {
        return createVillagerArmorLayer(cubeDeformation).apply(LivingArmorModel::modifyWitchMesh);
    }

    public static LayerDefinition createInnerWitchArmorLayer(CubeDeformation cubeDeformation) {
        return createWitchArmorLayer(cubeDeformation).apply((MeshDefinition meshDefinition) -> {
            return modifyInnerMesh(meshDefinition, cubeDeformation);
        });
    }

    private static MeshDefinition modifyMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
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
        root.getChild("head").addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        return meshDefinition;
    }

    private static MeshDefinition modifyVillagerMesh(MeshDefinition meshDefinition) {
        PartDefinition root = meshDefinition.getRoot();
        root.clearChild("right_arm");
        root.clearChild("left_arm");
        PartDefinition head = root.getChild("head");
        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        hat.addOrReplaceChild("hat_rim", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);
        return meshDefinition;
    }

    private static MeshDefinition modifyWitchMesh(MeshDefinition meshDefinition) {
        PartDefinition root = meshDefinition.getRoot();
        // Witches already wear a hat.
        root.clearChild("head");
        return meshDefinition;
    }

    private static MeshDefinition modifyInnerMesh(MeshDefinition meshDefinition, CubeDeformation cubeDeformation) {
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
