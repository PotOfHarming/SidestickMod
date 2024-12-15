package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import pot.potionofharming.SidestickMod;

import static pot.potionofharming.SidestickMod.player;

public class MovementBtns {
    private static boolean fw = false;
    private static boolean bw = false;
    private static boolean lf = false;
    private static boolean rg = false;
    public static void movement(float x, float z) {
        MinecraftClient client = MinecraftClient.getInstance();
//        if (client.player!=null && client.getNetworkHandler()!=null) {
//            System.out.println("X: "+x+", Z: "+z);
//            double yaw = Math.toRadians(client.player.getYaw());
//
//            double dX = (-Math.sin(yaw)*x)+(Math.cos(yaw)*z);
//            double dY = player.getVelocity().getY();
//            double dZ = (Math.cos(yaw)*x)+(Math.sin(yaw)*z);
//
//            Vec3d movement = new Vec3d(player.getMovementSpeed()*-dX, dY, player.getMovementSpeed()*dZ);
//            player.move(MovementType.PLAYER, movement);
//            client.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(player.getX(), player.getY(), player.getZ(), player.isOnGround()));
//            ClientPlayerEntity p = (ClientPlayerEntity) (PlayerEntity) player;
//
//        }

        Key fwk = client.options.forwardKey.getDefaultKey();
        Key bwk = client.options.backKey.getDefaultKey();
        Key lfk = client.options.leftKey.getDefaultKey();
        Key rgk = client.options.rightKey.getDefaultKey();

        fw = x==1;
        bw = x==-1;
        lf = z==-1;
        rg = z==1;
            if (!fw || !bw) {
                KeyBinding.setKeyPressed(fwk, fw);
                KeyBinding.setKeyPressed(bwk, bw);
            }
            if (!lf || !rg) {
                KeyBinding.setKeyPressed(lfk, lf);
                KeyBinding.setKeyPressed(rgk, rg);
            }
    }
}

