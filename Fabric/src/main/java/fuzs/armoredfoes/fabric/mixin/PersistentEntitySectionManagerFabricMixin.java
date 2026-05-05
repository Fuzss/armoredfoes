package fuzs.armoredfoes.fabric.mixin;

import fuzs.armoredfoes.fabric.world.entity.EntityLoadData;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
abstract class PersistentEntitySectionManagerFabricMixin<T extends EntityAccess> {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void addEntity(T entity, boolean loaded, CallbackInfoReturnable<Boolean> callback) {
        ((EntityLoadData) entity).armoredfoes$setLoadedFromDisk(loaded);
    }
}
