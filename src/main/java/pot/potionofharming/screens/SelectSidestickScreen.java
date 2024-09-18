package pot.potionofharming.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

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
