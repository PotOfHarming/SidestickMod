package pot.potionofharming.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pot.potionofharming.SidestickMod;

import java.util.concurrent.atomic.AtomicInteger;

import static pot.potionofharming.SidestickMod.joystickID;
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
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Back"), button -> {
                            this.client.setScreen(parent);
                        })
                        .dimensions(5, 5, 100, 20)
                        .build()
        );
        AtomicInteger w = new AtomicInteger(100);
        String txt = "Current sidestick: "+GLFW.glfwGetJoystickName(joystickID);
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Current sidestick: "+GLFW.glfwGetJoystickName(joystickID)), button -> {
                            int id = joystickID;
                            int limit = GLFW.GLFW_JOYSTICK_16;
                            int newId = id+1;
                            if (newId>limit) newId=GLFW.GLFW_JOYSTICK_1;
                            String name = GLFW.glfwGetJoystickName(newId);
                            button.setMessage(Text.literal("Current sidestick: "+name));
                            joystickID = newId;
                            w.set(textRenderer.getWidth(button.getMessage()));
                            button.setWidth(w.get()+10);
                            button.setX(this.width / 2 - textRenderer.getWidth(button.getMessage())/2-5);
                            reloadLoops();
                        })
                        .dimensions(this.width / 2 - textRenderer.getWidth(txt)/2-5, this.height / 2 - 10, textRenderer.getWidth(txt)+10, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext drawContext) {
        super.renderBackground(drawContext);
    }
}
