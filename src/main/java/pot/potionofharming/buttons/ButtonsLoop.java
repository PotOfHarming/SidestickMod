package pot.potionofharming.buttons;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import pot.potionofharming.SidestickMod;

import java.nio.ByteBuffer;

import static pot.potionofharming.SidestickMod.*;
import static pot.potionofharming.buttons.stickbuttons.ChPeBtn.changePerspective;
import static pot.potionofharming.buttons.stickbuttons.MovementBtns.movement;
import static pot.potionofharming.buttons.stickbuttons.PDBtn.clickButton;
import static pot.potionofharming.buttons.stickbuttons.PauseBtn.pauseGame;

public class ButtonsLoop {
    private static int currentLoopID;
    public static void initButtonsLoop() {
        currentLoopID = loopID;
        Thread placeDestroyLoop = new Thread(() -> {
            try {
                buttonsLoop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        placeDestroyLoop.setDaemon(true);
        placeDestroyLoop.start();
    }

    public static void buttonsLoop() throws InterruptedException {
        LOGGER.info("STARTING BUTTONS LOOP!");
        while (true) {
            if (currentLoopID < loopID) break;
            if (player == null) player = MinecraftClient.getInstance().player;
            if (player == null) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            ByteBuffer buttonsBuffer = GLFW.glfwGetJoystickButtons(joystickID);
            if (buttonsBuffer != null) {
                StringBuilder buttonsString = new StringBuilder("Joystick Buttons: [");
                byte[] buttons = new byte[buttonsBuffer.limit()];
                for (int i = 0; i < buttonsBuffer.limit(); i++) {
                    buttonsString.append(buttonsBuffer.get(i));
                    buttons[i] = buttonsBuffer.get(i);
                    if (i < buttonsBuffer.limit() - 1) {
                        buttonsString.append(", ");
                    }
                }
                buttonsString.append("]");
                // place destroy Perspective Pause? X X X X X X X X X X X X X UP RIGHT DOWN LEFT
                // LOGGER.info(buttonsString.toString());
                pauseGame(buttons[3]); // crashes??
                //if (MinecraftClient.getInstance().currentScreen != null) LOGGER.info(MinecraftClient.getInstance().currentScreen.getTitle().toString());
                if (!MinecraftClient.getInstance().isPaused()) {
                    clickButton(buttons[0], buttons[1]);
                    changePerspective(buttons[2]);
                    movement(buttons[17], buttons[18], buttons[19], buttons[20]);
                }
            }

            try {
                Thread.sleep(SidestickMod.fpsNum);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
