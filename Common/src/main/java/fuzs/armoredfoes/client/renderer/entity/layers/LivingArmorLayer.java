package fuzs.armoredfoes.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;

/**
 * @see HumanoidArmorLayer
 */
public abstract class LivingArmorLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T>> extends RenderLayer<T, M> {
    private final A innerModel;
    private final A outerModel;
    private final A innerModelBaby;
    private final A outerModelBaby;
    private final TextureAtlas armorTrimAtlas;

    public LivingArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        this(renderer, innerModel, outerModel, innerModel, outerModel, modelManager);
    }

    public LivingArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, A innerModelBaby, A outerModelBaby, ModelManager modelManager) {
        super(renderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.innerModelBaby = innerModelBaby;
        this.outerModelBaby = outerModelBaby;
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderArmorPiece(poseStack,
                bufferSource,
                lightCoords,
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                EquipmentSlot.CHEST,
                this.getArmorModel(entity, EquipmentSlot.CHEST));
        this.renderArmorPiece(poseStack,
                bufferSource,
                lightCoords,
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                EquipmentSlot.LEGS,
                this.getArmorModel(entity, EquipmentSlot.LEGS));
        this.renderArmorPiece(poseStack,
                bufferSource,
                lightCoords,
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                EquipmentSlot.FEET,
                this.getArmorModel(entity, EquipmentSlot.FEET));
        this.renderArmorPiece(poseStack,
                bufferSource,
                lightCoords,
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                EquipmentSlot.HEAD,
                this.getArmorModel(entity, EquipmentSlot.HEAD));
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, EquipmentSlot slot, A model) {
        ItemStack itemStack = entity.getItemBySlot(slot);
        if (itemStack.getItem() instanceof ArmorItem item) {
            if (item.getEquipmentSlot() == slot) {
                model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                this.getParentModel().copyPropertiesTo(model);
                this.setAllVisible(model, false);
                this.setPartVisibility(entity, model, slot);
                boolean usesInnerModel = this.usesInnerModel(slot);
                ArmorMaterial armorMaterial = (ArmorMaterial) item.getMaterial().value();
                int dyedColor = itemStack.is(ItemTags.DYEABLE) ?
                        FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, -6265536)) : -1;
                for (ArmorMaterial.Layer layer : armorMaterial.layers()) {
                    int color = layer.dyeable() ? dyedColor : -1;
                    this.renderModel(poseStack, bufferSource, lightCoords, model, color, layer.texture(usesInnerModel));
                }

                ArmorTrim armorTrim = (ArmorTrim) itemStack.get(DataComponents.TRIM);
                if (armorTrim != null) {
                    this.renderTrim(item.getMaterial(),
                            poseStack,
                            bufferSource,
                            lightCoords,
                            armorTrim,
                            model,
                            usesInnerModel);
                }

                if (itemStack.hasFoil()) {
                    this.renderGlint(poseStack, bufferSource, lightCoords, model);
                }
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model, int dyeColor, ResourceLocation textureLocation) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(textureLocation));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, dyeColor);
    }

    private void renderTrim(Holder<ArmorMaterial> armorMaterial, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ArmorTrim trim, A model, boolean innerTexture) {
        TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(
                innerTexture ? trim.innerTexture(armorMaterial) : trim.outerTexture(armorMaterial));
        VertexConsumer vertexConsumer = textureAtlasSprite.wrap(bufferSource.getBuffer(Sheets.armorTrimsSheet(trim.pattern()
                .value()
                .decal())));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model) {
        model.renderToBuffer(poseStack,
                bufferSource.getBuffer(RenderType.armorEntityGlint()),
                packedLight,
                OverlayTexture.NO_OVERLAY);
    }

    protected abstract void setAllVisible(A model, boolean visible);

    protected abstract void setPartVisibility(T entity, A model, EquipmentSlot equipmentSlot);

    private A getArmorModel(T entity, EquipmentSlot slot) {
        if (this.usesInnerModel(slot)) {
            return entity.isBaby() ? this.innerModelBaby : this.innerModel;
        } else {
            return entity.isBaby() ? this.outerModelBaby : this.outerModel;
        }
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
