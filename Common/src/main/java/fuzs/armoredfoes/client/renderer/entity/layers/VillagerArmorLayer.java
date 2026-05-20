package fuzs.armoredfoes.client.renderer.entity.layers;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class VillagerArmorLayer<T extends LivingEntity, M extends VillagerModel<T>, A extends VillagerModel<T>> extends LivingArmorLayer<T, M, A> {

    public VillagerArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
    }

    public VillagerArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, A innerModelBaby, A outerModelBaby, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, innerModelBaby, outerModelBaby, modelManager);
    }

    @Override
    protected void setAllVisible(A model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        this.getBody(model).visible = visible;
        this.getArms(model).visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    @Override
    protected void setPartVisibility(T renderState, A model, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                this.getBody(model).visible = true;
                this.getArms(model).visible = true;
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

    private ModelPart getBody(A model) {
        return model.root().getChild("body");
    }

    private ModelPart getArms(A model) {
        return model.root().getChild("arms");
    }
}
