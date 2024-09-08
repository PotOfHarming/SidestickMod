package pot.potionofharming;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.lwjgl.glfw.GLFW;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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
			Thread pitchYawLoop = new Thread(() -> {
				try {
					pitchAndYawLoop();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
			pitchYawLoop.setDaemon(true);
			pitchYawLoop.start();

			Thread placeDestroyLoop = new Thread(() -> {
				try {
					placeAndDestroyLoop();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
			placeDestroyLoop.setDaemon(true);
			placeDestroyLoop.start();
		}
	}
	public static void pitchAndYawLoop() throws InterruptedException {
		while (true) {
			if (player == null) player = MinecraftClient.getInstance().player;
			if (MinecraftClient.getInstance().isPaused() || player == null) {
				continue;
			}
			FloatBuffer axesBuffer = GLFW.glfwGetJoystickAxes(joystickID);
			if (axesBuffer != null) {
				StringBuilder axesString = new StringBuilder("Joystick Axes: [");
				float[] axes = new float[axesBuffer.limit()];
				for (int i = 0; i < axesBuffer.limit(); i++) {
					if (axesBuffer.get(i) < 0.25f && axesBuffer.get(i) > -0.25f) {
						axesString.append("0");
						axes[i] = 0f;
					} else {
						axesString.append(axesBuffer.get(i) * 10);
						axes[i] = axesBuffer.get(i) * 10;
					}
					if (i < axesBuffer.limit() - 1) {
						axesString.append(", ");
					}
				}
				axesString.append("]");
				LOGGER.info(axesString.toString());
				changePaY(-axes[1], axes[0]);
				// YAW PITCH RUDDER THRUSTLEVER
				// LEFTRIGHT UPDOWN IGNORE WALK FORWARDBACKWARD
			}

			try {
				Thread.sleep(Math.round(MinecraftClient.getInstance().getLastFrameDuration()*100));
				LOGGER.info("LASTFRAMETIME: "+MinecraftClient.getInstance().getLastFrameDuration()*100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void placeAndDestroyLoop() throws InterruptedException {
		while (true) {
			if (player == null) player = MinecraftClient.getInstance().player;
			if (MinecraftClient.getInstance().isPaused() || player == null) {
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
				LOGGER.info(buttonsString.toString());
				clickButton(buttons[0], buttons[1]);
				changePerspective(buttons[2]);
				pauseGame(buttons[3]);
				movement(buttons[17], buttons[18], buttons[19], buttons[20]);
			}

			try {
				Thread.sleep(Math.round(MinecraftClient.getInstance().getLastFrameDuration()*100));
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static PlayerEntity player = MinecraftClient.getInstance().player;
	public static void changePaY(float pitch, float yaw) {
		float sensitivity = 0.6f/Math.round(MinecraftClient.getInstance().getLastFrameDuration()*5);
		LOGGER.info(MinecraftClient.getInstance().options.getMouseSensitivity().getValue().toString());
		if (player.getPitch()+(pitch*sensitivity) > 90) player.setPitch(90);
		else player.setPitch(player.getPitch() + (pitch*sensitivity));
		if (player.getPitch()+(pitch*sensitivity) < -90) player.setPitch(-90);
		else player.setPitch(player.getPitch() + (pitch*sensitivity));

		player.setYaw(player.getYaw() + (yaw*sensitivity));
	}


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

	private static boolean perspectiveSwitched = false;
	private static boolean toggledPause = false;
	public static void changePerspective(byte perspective) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!perspectiveSwitched && perspective == 1) {
			if (client.options.getPerspective()==Perspective.FIRST_PERSON) client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
			else if (client.options.getPerspective()==Perspective.THIRD_PERSON_BACK) client.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
			else if (client.options.getPerspective()==Perspective.THIRD_PERSON_FRONT) client.options.setPerspective(Perspective.FIRST_PERSON);
			perspectiveSwitched = true;
			LOGGER.info("PERSPECTIVE SWITCHED TO: "+client.options.getPerspective());
		}
		if (perspective == 0) perspectiveSwitched = false;
	}

	public static void pauseGame(byte pause) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!toggledPause && pause == 1) {
			client.openPauseMenu(!client.isPaused());
			toggledPause = true;
		}
		if (pause == 0) toggledPause = false;
	}

	public static void movement(byte forward, byte right, byte backward, byte left) {
//		if (forward!=1 || backward!=1) {
//			if (forward == 1) {
//				player.move(MovementType.PLAYER, new Vec3d(-player.getMovementSpeed(), 0, 0));
//			}
//			if (backward == 1) {
//				player.move(MovementType.PLAYER, new Vec3d(player.getMovementSpeed(), 0, 0));
//			}
//		}
//		if (right!=1 || left!=1) {
//			if (left == 1) {
//				player.move(MovementType.PLAYER, new Vec3d(0, 0, player.getMovementSpeed()));
//			}
//			if (right == 1) {
//				player.move(MovementType.PLAYER, new Vec3d(0, 0, -player.getMovementSpeed()));
//			}
//		}
	}
}