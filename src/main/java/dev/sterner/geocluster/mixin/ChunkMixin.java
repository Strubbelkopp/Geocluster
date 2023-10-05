package dev.sterner.geocluster.mixin;

import dev.sterner.geocluster.api.deposits.IChunk;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.StructureHolder;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.BlendingData;
import net.minecraft.world.tick.ChunkTickScheduler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Chunk.class)
public abstract class ChunkMixin implements BlockView,
        BiomeAccess.Storage,
        StructureHolder, IChunk {

    private ArrayList<Identifier> deposits;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectManager(ChunkPos pos, UpgradeData upgradeData, HeightLimitView heightLimitView, Registry biome, long inhabitedTime, ChunkSection[] sectionArrayInitializer, BlendingData blendingData, CallbackInfo ci) {
        deposits = new ArrayList<>();
    }

    public ArrayList<Identifier> getDeposits() {
        return this.deposits;
    }

    public void addDeposit(Identifier deposit) {
        this.deposits.add(deposit);
    }
}