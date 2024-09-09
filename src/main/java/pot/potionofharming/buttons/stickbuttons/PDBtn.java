package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PDBtn {
    public static void clickButton(byte place, byte destroy) {
    if (place == 0 && destroy == 0) return;
    MinecraftClient client = MinecraftClient.getInstance();
    BlockHitResult bhr = (BlockHitResult) client.crosshairTarget;
    if (bhr == null) return;
    BlockPos pos = bhr.getBlockPos();
    Direction dir = bhr.getSide();

    if (place == 1) {

    }

    if (destroy == 1) {

    }
}
}
