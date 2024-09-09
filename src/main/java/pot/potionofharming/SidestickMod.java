package pot.potionofharming;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.lwjgl.glfw.GLFW;
import pot.potionofharming.buttons.ButtonsLoop;
import pot.potionofharming.direction.PYLoop;

public class SidestickMod implements ModInitializer {
	public static final String MOD_ID = "sidestickmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int joystickID;
	public static boolean joystickFound = false;
	@Override
	public void onInitialize() {
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Get Joystick connected
		for (int i = 0; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
			if (GLFW.glfwJoystickPresent(i)) {
				LOGGER.info("Joystick found: " + GLFW.glfwGetJoystickName(i));
				joystickID = i;
				joystickFound = true;
			}
		}

		// Optionally set up a callback for joystick events
		GLFW.glfwSetJoystickCallback((jid, event) -> {
			if (event == GLFW.GLFW_CONNECTED) {
				LOGGER.info("Joystick connected: " + GLFW.glfwGetJoystickName(jid));
			} else if (event == GLFW.GLFW_DISCONNECTED) {
				LOGGER.info("Joystick disconnected.");
			}
		});

		if (!joystickFound) {
			LOGGER.warn("NO JOYSTICK FOUND /!\\");
		} else {
			LOGGER.info("AXIS: "+GLFW.glfwGetJoystickAxes(SidestickMod.joystickID).toString());
			if (!GLFW.glfwJoystickPresent(joystickID)) {
				LOGGER.warn("JOYSTICK IS NOT PRESENT!");
				return;
			}

			// loops
			ButtonsLoop.initButtonsLoop();
			PYLoop.initPaYLoop();
		}
	}
	public static PlayerEntity player = MinecraftClient.getInstance().player;
}