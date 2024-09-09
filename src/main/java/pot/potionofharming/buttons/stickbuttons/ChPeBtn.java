package pot.potionofharming.buttons.stickbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;

public class ChPeBtn {
    private static boolean perspectiveSwitched = false;
    public static void changePerspective(byte perspective) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!perspectiveSwitched && perspective == 1) {
            if (client.options.getPerspective()== Perspective.FIRST_PERSON) client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            else if (client.options.getPerspective()==Perspective.THIRD_PERSON_BACK) client.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            else if (client.options.getPerspective()==Perspective.THIRD_PERSON_FRONT) client.options.setPerspective(Perspective.FIRST_PERSON);
            perspectiveSwitched = true;
            // LOGGER.info("PERSPECTIVE SWITCHED TO: "+client.options.getPerspective());
        }
        if (perspective == 0) perspectiveSwitched = false;
    }
}
