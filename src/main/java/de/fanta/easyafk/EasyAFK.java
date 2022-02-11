package de.fanta.easyafk;

import de.fanta.easyafk.util.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyAFK implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("EasyAFK");

    private static long maxIdleTime = 10;

    public static long getMaxIdleTime() {
        return maxIdleTime;
    }

    public static void setMaxIdleTime(long maxIdleTime) {
        EasyAFK.maxIdleTime = maxIdleTime;
    }

    @Override
    public void onInitialize() {
        Config.deserialize();
        maxIdleTime = Config.maxIdleTime;

        Listener listener = new Listener();
        listener.init();

        AFKHandler afkHandler = new AFKHandler();

    }
}
