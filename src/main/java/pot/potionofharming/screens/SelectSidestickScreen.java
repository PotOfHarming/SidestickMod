package pot.potionofharming.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pot.potionofharming.SidestickMod;
import pot.potionofharming.screens.config.Configuration;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static pot.potionofharming.SidestickMod.reloadLoops;

public class SelectSidestickScreen extends Screen {
    private final Screen parent;
    public SelectSidestickScreen(Screen parent) {
        super(Text.literal("Select sidestick"));
        this.parent = parent;
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    protected void init() {
        this.addDrawableChild(new SliderWidget(this.width / 2 - 155, 40, 150, 20, Text.literal("Sensitivity: "+ Configuration.getSensitivity()+"%"), ((double) 1 /500*Configuration.getSensitivity())) {
            @Override
            protected void updateMessage() {
                long val = Math.round(1+this.value*499);

                this.setMessage(Text.literal("Sensitivity: "+val+"%"));
                Configuration.saveConfig();
            }

            @Override
            protected void applyValue() {
                Configuration.setSensitivity(Math.round(1+this.value*499));
            }
        });
        reloadStick();
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Current sidestick: "+GLFW.glfwGetJoystickName(Configuration.getStickId())), button -> {
                            int id = Configuration.getStickId();
                            int limit = GLFW.GLFW_JOYSTICK_LAST;
                            int newId = id+1;
                            if (newId>limit||newId<0) newId=GLFW.GLFW_JOYSTICK_1;
                            System.out.println(newId);
                            String name = GLFW.glfwGetJoystickName(id);
                            button.setMessage(Text.literal("Current sidestick: "+name));
                            Configuration.setStickId(newId);
                            reloadLoops();
                            Configuration.saveConfig();
                        })
                        .dimensions(this.width / 2 + 5, 40, 150, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Done"), button -> {
                            this.client.setScreen(parent);
                        })
                        .dimensions(this.width/2-75, this.height - 50, 150, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
        drawContext.drawText(textRenderer, Text.literal("Sidestick options"), width/2-textRenderer.getWidth("Sidestick options")/2, 25, 0xFFFFFF, false);
    }

    @Override
    public void renderBackground(DrawContext drawContext) {
        super.renderBackground(drawContext);
    }

    public static void reloadStick() {
        if (Configuration.getStickId()==-1||GLFW.glfwGetJoystickName(Configuration.getStickId())==null) {
            for (int i = GLFW.GLFW_JOYSTICK_1; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
                if (GLFW.glfwJoystickPresent(i)) {
                    SidestickMod.LOGGER.info("Joystick found: " + GLFW.glfwGetJoystickName(i));
                    Configuration.setStickId(i);
                }
            }
        }
    }
}
