package de.fanta.easyafk.util;

import de.fanta.easyafk.AFKHandler;
import de.fanta.easyafk.EasyAFK;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    public static long maxIdleTime = 10;

    static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("easyafk.properties");

    static void serialize() {
        Properties prop = new Properties();
        prop.setProperty("maxafkminutes", String.valueOf(maxIdleTime));
        try {
            OutputStream s = Files.newOutputStream(configPath);
            prop.store(s, "EasyAFK Config");
            s.close();
        } catch (IOException e) {
            EasyAFK.LOGGER.warn("Failed to write config!");
        }
    }

    public static void deserialize() {
        Properties prop = new Properties();
        try {
            InputStream s = Files.newInputStream(configPath);
            prop.load(s);
            maxIdleTime = Integer.parseInt(prop.getProperty("maxafkminutes", String.valueOf(maxIdleTime)));
        } catch (IOException e) {
            EasyAFK.LOGGER.warn("Failed to read config!");
        }
        Config.serialize();
    }
}
