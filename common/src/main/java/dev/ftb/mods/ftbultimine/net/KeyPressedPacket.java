package dev.ftb.mods.ftbultimine.net;

import dev.ftb.mods.ftblibrary.platform.network.PacketContext;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record KeyPressedPacket(boolean pressed, boolean autoShapelessOnOre) implements CustomPacketPayload {
    public static final Type<KeyPressedPacket> TYPE = new Type<>(FTBUltimineAPI.id("key_pressed_packet"));

    public static final StreamCodec<FriendlyByteBuf, KeyPressedPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, KeyPressedPacket::pressed,
            ByteBufCodecs.BOOL, KeyPressedPacket::autoShapelessOnOre,
            KeyPressedPacket::new
    );

    @Override
    public Type<KeyPressedPacket> type() {
        return TYPE;
    }

    public static void handle(KeyPressedPacket message, PacketContext context) {
        FTBUltimine.getInstance().setKeyPressed(
            (ServerPlayer) context.player(),
            message.pressed(),
            message.autoShapelessOnOre()
        );
    }
}