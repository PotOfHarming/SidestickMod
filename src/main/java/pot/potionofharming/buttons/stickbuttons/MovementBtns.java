package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;

public class MovementBtns {
    private static boolean fw = false;
    private static boolean bw = false;
    private static boolean lf = false;
    private static boolean rg = false;
    public static void movement(byte forward, byte right, byte backward, byte left) {
        MinecraftClient client = MinecraftClient.getInstance();
        Key fwk = client.options.forwardKey.getDefaultKey();
        Key bwk = client.options.backKey.getDefaultKey();
        Key lfk = client.options.leftKey.getDefaultKey();
        Key rgk = client.options.rightKey.getDefaultKey();

        fw = forward==1;
        bw = backward==1;
        lf = left==1;
        rg = right==1;
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

