package dev.sterner.geocluster.mixin;

import dev.sterner.geocluster.api.deposits.IChunk;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin extends DrawableHelper {

    @Shadow @Nullable protected abstract WorldChunk getChunk();
    @Shadow @Final
    private MinecraftClient client;

    @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
    public void addToDebug(CallbackInfoReturnable<List<String>> cir) {
        if (!client.hasReducedDebugInfo()) {
            Chunk chunk = this.getChunk();
            if (chunk != null) {
                ArrayList<Identifier> deposits = ((IChunk) chunk).getDeposits();
                if (deposits != null) {
                    ArrayList<String> list = (ArrayList<String>) cir.getReturnValue();
                    list.add("Deposits" + deposits);
                    cir.setReturnValue(list);
                }
            }
        }
    }
}
