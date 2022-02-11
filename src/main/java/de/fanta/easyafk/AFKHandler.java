package de.fanta.easyafk;

import de.fanta.easyafk.util.ChatUtil;
import de.fanta.easyafk.util.Config;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class AFKHandler {



    public static HashMap<ServerPlayerEntity, PlayerInfo> playerTimes;

    public AFKHandler() {
        playerTimes = new HashMap<>();
    }

    public static void handleMoved(ServerPlayerEntity p) {
        PlayerInfo playerInfo = playerTimes.get(p);
        if (playerInfo != null) {
            playerInfo.last_move_time = System.currentTimeMillis();
            if (playerInfo.afk) {
                playerInfo.afk = false;
                ChatUtil.sendBrodcastMessage(p,p.getName().asString() + " is no longer AFK");
                playerInfo.last_check_time = playerInfo.last_move_time;
            }
        }
    }

    public static void handleJoin(final ServerPlayerEntity p) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerTimes.put(p, playerInfo);
    }

    public static void onTimer(MinecraftServer server) {
        long t = System.currentTimeMillis();
        for (ServerPlayerEntity p : PlayerLookup.all(server)) {
            handleTimer(p, t);
        }
    }

    private static void handleTimer(ServerPlayerEntity p, long time) {
        PlayerInfo playerInfo = playerTimes.get(p);
        if (playerInfo != null && !playerInfo.afk) {
            long addTime;
            if (time < playerInfo.last_move_time + EasyAFK.getMaxIdleTime() * 60 * 1000) {
                addTime = time - playerInfo.last_check_time;
            } else {
                addTime = (playerInfo.last_move_time + EasyAFK.getMaxIdleTime() * 60 * 1000) - playerInfo.last_check_time;
                playerInfo.afk = true;
                ChatUtil.sendBrodcastMessage(p, p.getName().asString() + " is now AFK");
            }
            playerInfo.accounted_time += Math.max(addTime, 0);
            playerInfo.last_check_time = time;
        }
    }

    public static void setPlayerAFK(ServerPlayerEntity player) {
        PlayerInfo playerInfo = playerTimes.get(player);
        if (playerInfo != null && !playerInfo.afk) {
            playerInfo.afk = true;
            ChatUtil.sendBrodcastMessage(player, player.getName().asString() + " is now AFK");
        }
    }

    public static void handleLogout(ServerPlayerEntity p) {
        playerTimes.remove(p);
    }
}
