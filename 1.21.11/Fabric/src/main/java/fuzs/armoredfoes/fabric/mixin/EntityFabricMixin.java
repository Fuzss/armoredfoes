package fuzs.armoredfoes.fabric.mixin;

import fuzs.armoredfoes.fabric.world.entity.EntityLoadData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
abstract class EntityFabricMixin implements EntityLoadData {
    @Unique
    private boolean armoredfoes$isLoadedFromDisk;

    @Override
    public boolean armoredfoes$isLoadedFromDisk() {
        return this.armoredfoes$isLoadedFromDisk;
    }

    @Override
    public void armoredfoes$setLoadedFromDisk(boolean isLoadedFromDisk) {
        this.armoredfoes$isLoadedFromDisk = isLoadedFromDisk;
    }
}
