package re.domi.fastbento.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import re.domi.fastbento.config.Config;
import re.domi.fastbento.config.PerformanceStats;

import java.util.function.Supplier;

/**
 * Mixin for copper chest blocks - optimizes rendering by using static models
 * Covers all copper variants: regular, exposed, weathered, oxidized (waxed and unwaxed)
 */
@Mixin(value = CopperChestBlock.class, priority = 1500)
public abstract class CopperChestBlockMixin extends AbstractChestBlock<ChestBlockEntity> {
    
    @Override
    @Unique(silent = true)
    protected BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }

    @SuppressWarnings({ "MixinAnnotationTarget", "UnresolvedMixinReference" })
    @Inject(method = {"getRenderType", "method_9604"}, at = @At("HEAD"), cancellable = true, remap = false)
    private void replaceRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        if (!Config.shouldOptimizeChest(Config.ChestType.COPPER)) {
            cir.setReturnValue(BlockRenderType.INVISIBLE);
        } else {
            PerformanceStats.incrementChestOptimized();
        }
    }

    @Inject(method = "getTicker", at = @At("HEAD"), cancellable = true)
    private <T extends BlockEntity> void removeTicker(World world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> cir) {
        if (Config.removeBlockEntityTickers && Config.shouldOptimizeChest(Config.ChestType.COPPER)) {
            cir.setReturnValue(null);
        }
    }

    @SuppressWarnings({"DataFlowIssue", "unused"})
    protected CopperChestBlockMixin(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> entityTypeRetriever) {
        super(null, null);
    }
}
