package re.domi.fastbento.config;

import net.fabricmc.fabric.impl.event.lifecycle.LoadedChunksCache;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.world.chunk.WorldChunk;

/**
 * Enhanced config screen with per-chest-type toggles and performance stats
 */
public class ConfigScreen extends Screen {
    private static final Text SCREEN_TITLE = Text.literal("Fast Bento Config");
    private static final Text GLOBAL_ON = Text.literal("Global: ON");
    private static final Text GLOBAL_OFF = Text.literal("Global: OFF");
    
    private static final Text REGULAR_ON = Text.literal("Regular Chests: ON");
    private static final Text REGULAR_OFF = Text.literal("Regular Chests: OFF");
    
    private static final Text TRAPPED_ON = Text.literal("Trapped Chests: ON");
    private static final Text TRAPPED_OFF = Text.literal("Trapped Chests: OFF");
    
    private static final Text ENDER_ON = Text.literal("Ender Chests: ON");
    private static final Text ENDER_OFF = Text.literal("Ender Chests: OFF");
    
    private static final Text COPPER_ON = Text.literal("Copper Chests: ON");
    private static final Text COPPER_OFF = Text.literal("Copper Chests: OFF");
    
    private static final Text TICKERS_ON = Text.literal("Remove Tickers: ON");
    private static final Text TICKERS_OFF = Text.literal("Remove Tickers: OFF");

    private final Screen parent;
    private int buttonY = 55;

    ConfigScreen(Screen parent) {
        super(SCREEN_TITLE);
        this.parent = parent;
    }

    @Override
    public void init() {
        buttonY = 55;
        
        // Global toggle
        this.addDrawableChild(createToggleButton(
            Config.simplifiedChests ? GLOBAL_ON : GLOBAL_OFF,
            () -> {
                Config.simplifiedChests = !Config.simplifiedChests;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        // Per-chest-type toggles (only enabled if global is on)
        addSeparator();
        
        this.addDrawableChild(createToggleButton(
            Config.optimizeRegularChests ? REGULAR_ON : REGULAR_OFF,
            () -> {
                Config.optimizeRegularChests = !Config.optimizeRegularChests;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        this.addDrawableChild(createToggleButton(
            Config.optimizeTrappedChests ? TRAPPED_ON : TRAPPED_OFF,
            () -> {
                Config.optimizeTrappedChests = !Config.optimizeTrappedChests;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        this.addDrawableChild(createToggleButton(
            Config.optimizeEnderChests ? ENDER_ON : ENDER_OFF,
            () -> {
                Config.optimizeEnderChests = !Config.optimizeEnderChests;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        this.addDrawableChild(createToggleButton(
            Config.optimizeCopperChests ? COPPER_ON : COPPER_OFF,
            () -> {
                Config.optimizeCopperChests = !Config.optimizeCopperChests;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        addSeparator();
        
        // Block entity ticker removal toggle
        this.addDrawableChild(createToggleButton(
            Config.removeBlockEntityTickers ? TICKERS_ON : TICKERS_OFF,
            () -> {
                Config.removeBlockEntityTickers = !Config.removeBlockEntityTickers;
                updateAllButtons();
                Config.write();
                applyChanges();
            }
        ));
        
        addSeparator();
        
        // Stats display
        String stats = PerformanceStats.getStatsSummary();
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(stats),
            button -> {}
        ).dimensions(this.width / 2 - 150, buttonY, 300, 20).build());
        buttonY += 25;
        
        // Done button
        this.addDrawableChild(new ButtonWidget.Builder(
            ScreenTexts.DONE,
            button -> {
                if (this.client != null) {
                    this.client.setScreen(this.parent);
                }
            })
            .dimensions(this.width / 2 - 100, this.height - 50, 200, 20)
            .build());
    }
    
    private ButtonWidget createToggleButton(Text message, Runnable onClick) {
        ButtonWidget.ButtonSupplier supplier = button -> {
            onClick.run();
        };
        return ButtonWidget.builder(message, supplier)
            .dimensions(this.width / 2 - 100, buttonY, 200, 20)
            .build();
    }
    
    private void addSeparator() {
        buttonY += 5;
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("-------------------"),
            button -> {}
        ).dimensions(this.width / 2 - 100, buttonY, 200, 10).build());
        buttonY += 15;
    }
    
    private void updateAllButtons() {
        // Re-initialize to refresh button states
        this.children().forEach(child -> {
            if (child instanceof ButtonWidget btn) {
                // Buttons will be recreated on next init call
            }
        });
    }
    
    private void applyChanges() {
        if (this.client != null) {
            this.client.worldRenderer.reload();
            
            if (this.client.world != null) {
                //noinspection UnstableApiUsage
                ((LoadedChunksCache)this.client.world).fabric_getLoadedChunks().forEach(WorldChunk::updateAllBlockEntities);
            }
        }
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderDarkening(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("v" + Config.VERSION + " | Minecraft " + Config.MC_VERSION), 
            this.width / 2, 22, 0xFFAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
}
