package fuzs.armoredfoes.fabric.mixin;

import fuzs.armoredfoes.fabric.server.level.CurrentlyLoadingChunkHolder;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GenerationChunkHolder.class)
abstract class GenerationChunkHolderFabricMixin implements CurrentlyLoadingChunkHolder {
    @Unique
    @Nullable
    private LevelChunk armoredfoes$currentlyLoadingChunk;

    @Override
    public @Nullable LevelChunk armoredfoes$getCurrentlyLoadingChunk() {
        return this.armoredfoes$currentlyLoadingChunk;
    }

    @Override
    public void armoredfoes$setCurrentlyLoadingChunk(@Nullable LevelChunk levelChunk) {
        this.armoredfoes$currentlyLoadingChunk = levelChunk;
    }
}
