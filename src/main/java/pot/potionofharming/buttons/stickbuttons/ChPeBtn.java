package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;

public class ChPeBtn {
    private static boolean perspectiveSwitched = false;
    public static void changePerspective(byte perspective) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!perspectiveSwitched && perspective==1) {
            client.options.setPerspective(client.options.getPerspective().next());
            perspectiveSwitched=true;
        }
        if (perspective == 0) perspectiveSwitched = false;
    }
}
