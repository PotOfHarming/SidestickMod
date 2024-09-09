package pot.potionofharming.direction;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.nio.FloatBuffer;

import static pot.potionofharming.SidestickMod.*;

public class PYLoop {

    public static void initPaYLoop() {
        Thread pitchYawLoop = new Thread(() -> {
            paYLoop();
        });
        pitchYawLoop.setDaemon(true);
        pitchYawLoop.start();
    }
    public static void paYLoop() {
        while (true) {
            if (player == null) player = MinecraftClient.getInstance().player;
            if (MinecraftClient.getInstance().isPaused() || player == null) {
                try {
                    Thread.sleep(Math.round(MinecraftClient.getInstance().getLastFrameDuration()*100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
                // LOGGER.info(axesString.toString());
                changePaY(-axes[1], axes[0]);
                // YAW PITCH RUDDER THRUSTLEVER
                // LEFTRIGHT UPDOWN IGNORE WALK FORWARDBACKWARD
            }

            try {
                Thread.sleep(Math.round(MinecraftClient.getInstance().getLastFrameDuration()*100));
                // LOGGER.info("LASTFRAMETIME: "+MinecraftClient.getInstance().getLastFrameDuration()*100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void changePaY(float pitch, float yaw) {
        float sensitivity = 0.6f/Math.round(MinecraftClient.getInstance().getLastFrameDuration()*5);
        // LOGGER.info("MOUSESENSITIVITY: "+MinecraftClient.getInstance().options.getMouseSensitivity().getValue().toString());
        if (player.getPitch()+(pitch*sensitivity) > 90) player.setPitch(90);
        else player.setPitch(player.getPitch() + (pitch*sensitivity));
        if (player.getPitch()+(pitch*sensitivity) < -90) player.setPitch(-90);
        else player.setPitch(player.getPitch() + (pitch*sensitivity));

        player.setYaw(player.getYaw() + (yaw*sensitivity));
    }
}
