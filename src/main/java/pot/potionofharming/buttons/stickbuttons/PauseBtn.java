package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;

public class PauseBtn {
    private static boolean toggledPause = false;
    public static void pauseGame(byte pause) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!toggledPause && pause == 1) {
            client.execute(() -> {
                if (client.currentScreen == null) client.setScreen(new GameMenuScreen(true));
                else client.setScreen(null);
                toggledPause = true;
            });
        }
        if (pause == 0) toggledPause = false;
    }
}
