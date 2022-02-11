package de.fanta.easyafk;

import de.fanta.easyafk.util.ChatUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class Listener {

    public Listener() {

    }

    public void init() {
        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            if (ChatUtil.getServer() == null) {
                ChatUtil.setServer(server);
            }
                AFKHandler.handleJoin(handler.getPlayer());
        }));

        ServerPlayConnectionEvents.DISCONNECT.register(((handler, server) -> {
            AFKHandler.handleLogout(handler.getPlayer());
        }));


        AttackBlockCallback.EVENT.register(((player, world, hand, pos, direction) -> {
            AFKHandler.handleMoved((ServerPlayerEntity) player);
            return ActionResult.PASS;
        }));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server != null) {
                AFKHandler.onTimer(server);
            }
        });
    }
}
