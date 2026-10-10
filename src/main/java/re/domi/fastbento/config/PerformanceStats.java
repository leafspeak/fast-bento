package re.domi.fastbento.config;

/**
 * Tracks performance statistics for the mod
 */
public class PerformanceStats {
    private static int chestsOptimized = 0;
    private static int blockEntitiesRemoved = 0;
    private static long lastUpdate = 0;

    public static synchronized void incrementChestOptimized() {
        chestsOptimized++;
    }

    public static synchronized void incrementBlockEntityRemoved() {
        blockEntitiesRemoved++;
    }

    public static synchronized int getChestsOptimized() {
        return chestsOptimized;
    }

    public static synchronized int getBlockEntitiesRemoved() {
        return blockEntitiesRemoved;
    }

    public static synchronized void reset() {
        chestsOptimized = 0;
        blockEntitiesRemoved = 0;
        lastUpdate = System.currentTimeMillis();
    }

    public static synchronized String getStatsSummary() {
        long elapsed = System.currentTimeMillis() - lastUpdate;
        return String.format(
            "Fast Bento Stats | Chests: %d | BlockEntities: %d | Time: %ds",
            chestsOptimized,
            blockEntitiesRemoved,
            elapsed / 1000
        );
    }
}
