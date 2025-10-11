package fuzs.armoredfoes.client.renderer.entity.layers;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class IllagerArmorLayer<S extends IllagerRenderState> extends LivingArmorLayer<S, IllagerModel<S>, IllagerModel<S>> {

    public IllagerArmorLayer(RenderLayerParent<S, IllagerModel<S>> renderer, ArmorModelSet<IllagerModel<S>> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, equipmentRenderer);
    }

    public IllagerArmorLayer(RenderLayerParent<S, IllagerModel<S>> renderer, ArmorModelSet<IllagerModel<S>> modelSet, ArmorModelSet<IllagerModel<S>> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, babyModelSet, equipmentRenderer);
    }

    @Override
    protected ItemStack getItem(S renderState, EquipmentSlot slot) {
        return RenderStateExtraData.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_KEY,
                Collections.emptyMap()).getOrDefault(slot, ItemStack.EMPTY);
    }
}
