package re.domi.fastbento;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import re.domi.fastbento.config.Config;
import re.domi.fastbento.config.PerformanceStats;

public class FastBentoMod implements ClientModInitializer {
    public static final String MOD_ID = "fastbento";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Fast Bento v{} loading for Minecraft {}", Config.VERSION, Config.MC_VERSION);
        PerformanceStats.reset();
    }
}
