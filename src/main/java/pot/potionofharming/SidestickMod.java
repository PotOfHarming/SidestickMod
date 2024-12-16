package pot.potionofharming;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.lwjgl.glfw.GLFW;
import pot.potionofharming.buttons.ButtonsLoop;
import pot.potionofharming.direction.PYLoop;
import pot.potionofharming.screens.config.Configuration;

public class SidestickMod implements ModInitializer {
	public static final String MOD_ID = "sidestickmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean joystickFound = false;
	public static int loopID = 0;
	public static int fpsNum = 16;
	@Override
	public void onInitialize() {
		Configuration.loadConfig();
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Get Joystick connected
		if (Configuration.getStickId()==-1||GLFW.glfwGetJoystickName(Configuration.getStickId())==null) {
			for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
				if (GLFW.glfwJoystickPresent(i)) {
					LOGGER.info("Joystick found: " + GLFW.glfwGetJoystickName(i));
					Configuration.setSensitivity(i);
					joystickFound = true;
				}
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
			LOGGER.info("AXIS: "+GLFW.glfwGetJoystickAxes(Configuration.getStickId()).toString());
			if (!GLFW.glfwJoystickPresent(Configuration.getStickId())) {
				LOGGER.warn("JOYSTICK IS NOT PRESENT!");
				return;
			}
		}
		reloadLoops();
	}

	public static void reloadLoops() {
		loopID++;
		// loops
		ButtonsLoop.initButtonsLoop();
		PYLoop.initPaYLoop();
		SidestickMod.LOGGER.info("Reloaded loops");
	}
	public static PlayerEntity player = MinecraftClient.getInstance().player;
}