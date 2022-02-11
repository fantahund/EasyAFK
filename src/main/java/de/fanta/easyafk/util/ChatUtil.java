package de.fanta.easyafk.util;

import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

public class ChatUtil {

    public static final TextColor GREEN = TextColor.parse("#52ff9d");
    public static final TextColor ORANGE = TextColor.parse("#ffac4d");
    public static final TextColor RED = TextColor.parse("#ff6b6b");
    public static final TextColor BLUE = TextColor.parse("#87f7ea");
    private static MinecraftServer server;

    public ChatUtil() {
    }

    public static void sendMessage(String message, ServerPlayerEntity player, TextColor color) {
        LiteralText EasyAFKMessage = new LiteralText("");
        EasyAFKMessage.append(getPrefix());
        LiteralText rawmessage = new net.minecraft.text.LiteralText(message);
        rawmessage.setStyle(Style.EMPTY.withColor(color));
        EasyAFKMessage.append(rawmessage);
        player.sendMessage(EasyAFKMessage, false);
    }

    public static void sendNormalMessage(ServerPlayerEntity player, String message) {
        sendMessage(message, player, GREEN);
    }

    public static void sendWarningMessage(ServerPlayerEntity player, String message) {
        sendMessage(message, player, ORANGE);
    }

    public static void sendErrorMessage(ServerPlayerEntity player, String message) {
        sendMessage(message, player, RED);
    }

    public static void sendBrodcastMessage(ServerPlayerEntity player, String message) {
        LiteralText afkmessage = new LiteralText("");
        afkmessage.append(getPrefix());
        LiteralText rawmessage = new net.minecraft.text.LiteralText(message);
        rawmessage.setStyle(Style.EMPTY.withColor(GREEN));
        afkmessage.append(rawmessage);
        server.getPlayerManager().broadcast(afkmessage, MessageType.CHAT, player.getUuid());
    }

    private static LiteralText getPrefix() {
        net.minecraft.text.LiteralText component = new LiteralText("");
        LiteralText prefix = new net.minecraft.text.LiteralText("EasyAFK");
        prefix.setStyle(Style.EMPTY.withColor(ChatUtil.GREEN));
        LiteralText bracket_left = new net.minecraft.text.LiteralText("[");
        bracket_left.setStyle(Style.EMPTY.withColor(ChatUtil.BLUE));
        LiteralText bracket_right = new net.minecraft.text.LiteralText("] ");
        bracket_right.setStyle(Style.EMPTY.withColor(ChatUtil.BLUE));
        component.append(bracket_left);
        component.append(prefix);
        component.append(bracket_right);
        return component;
    }

    public static void setServer(MinecraftServer server) {
        ChatUtil.server = server;
    }

    public static MinecraftServer getServer() {
        return server;
    }
}
