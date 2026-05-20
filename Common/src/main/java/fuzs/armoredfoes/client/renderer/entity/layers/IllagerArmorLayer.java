package fuzs.armoredfoes.client.renderer.entity.layers;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;

public class IllagerArmorLayer<T extends AbstractIllager> extends LivingArmorLayer<T, IllagerModel<T>, IllagerModel<T>> {

    public IllagerArmorLayer(RenderLayerParent<T, IllagerModel<T>> renderer, IllagerModel<T> innerModel, IllagerModel<T> outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
    }

    public IllagerArmorLayer(RenderLayerParent<T, IllagerModel<T>> renderer, IllagerModel<T> innerModel, IllagerModel<T> outerModel, IllagerModel<T> innerModelBaby, IllagerModel<T> outerModelBaby, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, innerModelBaby, outerModelBaby, modelManager);
    }

    @Override
    protected void setAllVisible(IllagerModel<T> model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        this.getBody(model).visible = visible;
        model.arms.visible = visible;
        model.rightArm.visible = visible;
        model.leftArm.visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    @Override
    protected void setPartVisibility(T entity, IllagerModel<T> model, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                this.getBody(model).visible = true;
                model.arms.visible = entity.getArmPose() == AbstractIllager.IllagerArmPose.CROSSED;
                model.rightArm.visible = !model.arms.visible;
                model.leftArm.visible = !model.arms.visible;
                break;
            case LEGS:
                this.getBody(model).visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }
    }

    private ModelPart getBody(IllagerModel<T> model) {
        return model.root().getChild("body");
    }
}
