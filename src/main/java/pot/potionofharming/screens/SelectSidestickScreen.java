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
                        .dimensions(this.width / 2 - 155, this.height - 25, 150, 20)
                        .build()
        );
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Current sidestick: "+GLFW.glfwGetJoystickName(joystickID)), button -> {
                            int id = joystickID;
                            int limit = GLFW.GLFW_JOYSTICK_LAST;
                            int newId = id+1;
                            if (newId>limit||newId<0) newId=GLFW.GLFW_JOYSTICK_1;
                            System.out.println(newId);
                            String name = GLFW.glfwGetJoystickName(id);
                            button.setMessage(Text.literal("Current sidestick: "+name));
                            joystickID = newId;
                            reloadLoops();
                        })
                        .dimensions(this.width / 2 + 5, this.height - 25, 150, 20)
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
