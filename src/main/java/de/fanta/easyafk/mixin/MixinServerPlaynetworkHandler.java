package de.fanta.easyafk.mixin;

import de.fanta.easyafk.AFKHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlaynetworkHandler {

    @Shadow
    @Final
    static Logger LOGGER;
    @Shadow
    public ServerPlayerEntity player;

    /**
     * @author fantahund
     */
    @Inject(method = "onPlayerMove", at = @At("HEAD"))
    public void onPlayerMove(PlayerMoveC2SPacket packetplayinflying, CallbackInfo ci) {

        if (!player.isSleeping() && !player.isDead()) {
            Vec3d oldpos = player.getPos();
            new Thread(() -> {
                try {
                    Thread.sleep(50);
                    boolean test = true;
                    if (player.getPos() != oldpos && test) {
                        test = false;
                        AFKHandler.handleMoved(player);
                        test = true;
                    }
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            }).start();
        }
    }
}
