package pot.potionofharming.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pot.potionofharming.screens.SelectSidestickScreen;

@Mixin(TitleScreen.class)
public class SelectStick extends Screen {

    protected SelectStick(Text title) {
        super(title);
    }


    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addBtn(CallbackInfo ci) {
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Sidestick options"), button -> {
                            this.client.setScreen(new SelectSidestickScreen(this.client.currentScreen));
                        })
                        .dimensions(5, 5, 100, 20)
                        .build()
        );
    }
}
