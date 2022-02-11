package de.fanta.easyafk;

import de.fanta.easyafk.util.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyAFK implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("EasyAFK");

    @Override
    public void onInitialize() {
        Config.deserialize();
        Listener listener = new Listener();
        listener.init();

        AFKHandler afkHandler = new AFKHandler();
    }
}
