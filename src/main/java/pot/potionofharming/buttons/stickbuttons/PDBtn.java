package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import pot.potionofharming.SidestickMod;

public class PDBtn {
    public static byte ds;
    public static byte pl;

    public static boolean startedPLoop = false;
    public static boolean startedDLoop = false;
    public static void clickButton(byte destroy, byte place) {
        MinecraftClient client = MinecraftClient.getInstance();
        ds = destroy;
        pl = place;
        if (pl==1 && !startedPLoop) {
            Thread placeLoop = new Thread(PDBtn::loopPlace);
            placeLoop.setDaemon(true);
            placeLoop.start();
            startedPLoop = true;
        }
        if (ds==1 && !startedDLoop) {
            Thread destroyLoop = new Thread(PDBtn::loopDestroy);
            destroyLoop.setDaemon(true);
            destroyLoop.start();
            startedDLoop = true;
        }
    }

    public static void loopPlace() {
        KeyBinding.setKeyPressed(MinecraftClient.getInstance().options.useKey.getDefaultKey(), true);
        while (pl == 1) {
            try {
                Thread.sleep(SidestickMod.fpsNum);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        KeyBinding.setKeyPressed(MinecraftClient.getInstance().options.useKey.getDefaultKey(), false);
        startedPLoop = false;
    }

    public static void loopDestroy() {
        KeyBinding.setKeyPressed(MinecraftClient.getInstance().options.attackKey.getDefaultKey(), true);
        while (ds == 1) {
            try {
                Thread.sleep(SidestickMod.fpsNum);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        KeyBinding.setKeyPressed(MinecraftClient.getInstance().options.attackKey.getDefaultKey(), false);
        startedDLoop = false;
    }
}
