package re.domi.fastbento.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import re.domi.fastbento.config.Config;
import re.domi.fastbento.config.PerformanceStats;

@Mixin(BlockEntityRenderManager.class)
public class BlockEntityRenderManagerMixin {
    
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity, S extends BlockEntityRenderState> void removeBlockEntityRenderer(
            E blockEntity, CallbackInfoReturnable<BlockEntityRenderer<E, S>> cir) {
        if (Config.simplifiedChests) {
            if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof EnderChestBlockEntity) {
                PerformanceStats.incrementBlockEntityRemoved();
                cir.setReturnValue(null);
            }
        }
    }
}
