package re.domi.fastbento.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SuppressWarnings("CallToPrintStackTrace")
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger("FastBento-Config");
    
    // Version info - now targeting Minecraft 26.x series
    public static final String VERSION = "3.0.0+26.x";
    public static final String MC_VERSION = "26.1-26.3";
    
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("fast-bento.properties");
    private static final String CONFIG_COMMENT = "Fast Bento config file - Optimizes chest rendering for better performance\nCompatible with Minecraft 26.1, 26.2, and 26.3";

    // Main toggle
    public static boolean simplifiedChests = true;
    
    // Fine-grained toggles for different chest types
    public static boolean optimizeRegularChests = true;
    public static boolean optimizeTrappedChests = true;
    public static boolean optimizeEnderChests = true;
    public static boolean optimizeCopperChests = true;
    
    // Advanced options
    public static boolean removeBlockEntityTickers = true;
    public static boolean preloadChunkGeometry = true;
    public static boolean batchRenderOptimization = false;
    
    // Stats tracking
    public static boolean trackPerformance = false;

    private static void read() {
        try {
            if (!Files.exists(CONFIG_FILE)) {
                write();
                return;
            }

            try (FileInputStream configInputStream = new FileInputStream(CONFIG_FILE.toFile())) {
                Properties properties = new Properties();
                properties.load(configInputStream);

                simplifiedChests = parseBoolean(properties, "simplifiedChests", true);
                optimizeRegularChests = parseBoolean(properties, "optimizeRegularChests", true);
                optimizeTrappedChests = parseBoolean(properties, "optimizeTrappedChests", true);
                optimizeEnderChests = parseBoolean(properties, "optimizeEnderChests", true);
                optimizeCopperChests = parseBoolean(properties, "optimizeCopperChests", true);
                removeBlockEntityTickers = parseBoolean(properties, "removeBlockEntityTickers", true);
                preloadChunkGeometry = parseBoolean(properties, "preloadChunkGeometry", true);
                batchRenderOptimization = parseBoolean(properties, "batchRenderOptimization", false);
                trackPerformance = parseBoolean(properties, "trackPerformance", false);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to read config file: {}", e.getMessage());
        }
    }

    static void write() {
        try {
            Files.createDirectories(CONFIG_DIR);
            try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.toFile())) {
                Properties properties = new Properties();
                properties.setProperty("simplifiedChests", Boolean.toString(simplifiedChests));
                properties.setProperty("optimizeRegularChests", Boolean.toString(optimizeRegularChests));
                properties.setProperty("optimizeTrappedChests", Boolean.toString(optimizeTrappedChests));
                properties.setProperty("optimizeEnderChests", Boolean.toString(optimizeEnderChests));
                properties.setProperty("optimizeCopperChests", Boolean.toString(optimizeCopperChests));
                properties.setProperty("removeBlockEntityTickers", Boolean.toString(removeBlockEntityTickers));
                properties.setProperty("preloadChunkGeometry", Boolean.toString(preloadChunkGeometry));
                properties.setProperty("batchRenderOptimization", Boolean.toString(batchRenderOptimization));
                properties.setProperty("trackPerformance", Boolean.toString(trackPerformance));
                properties.store(outputStream, CONFIG_COMMENT);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to write config file: {}", e.getMessage());
        }
    }

    private static boolean parseBoolean(Properties props, String key, boolean defaultValue) {
        String value = props.getProperty(key);
        if (value == null) return defaultValue;
        return Boolean.parseBoolean(value.trim());
    }

    static {
        read();
    }

    /**
     * Check if a specific chest type should be optimized
     */
    public static boolean shouldOptimizeChest(ChestType type) {
        if (!simplifiedChests) return false;
        
        switch (type) {
            case REGULAR:
                return optimizeRegularChests;
            case TRAPPED:
                return optimizeTrappedChests;
            case ENDER:
                return optimizeEnderChests;
            case COPPER:
                return optimizeCopperChests;
            default:
                return simplifiedChests;
        }
    }

    public enum ChestType {
        REGULAR, TRAPPED, ENDER, COPPER
    }
}
