package wtf.wooly.hide_arrows.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import wtf.wooly.hide_arrows.config.HideArrowsConfig;

import java.util.function.IntConsumer;

public class HideArrowsConfigScreen extends Screen {
    // Slider stops: 0 (hidden), 1..MAX_VISIBLE, then Unlimited at the far right.
    private static final int MAX_VISIBLE = 20;
    private static final int STOPS = MAX_VISIBLE + 1; // slider index STOPS == Unlimited

    private final Screen parent;

    public HideArrowsConfigScreen(Screen parent) {
        super(Component.literal("Hide Arrows"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        HideArrowsConfig config = HideArrowsConfig.get();

        int width = 220;
        int x = this.width / 2 - width / 2;
        int top = this.height / 4;
        int gap = 24;

        int titleWidth = this.font.width(this.title);
        this.addRenderableWidget(new StringWidget(this.width / 2 - titleWidth / 2, top - 30, titleWidth, 20, this.title, this.font));

        this.addRenderableWidget(new MaxSlider(x, top, width, 20, "Max arrows",
                config.maxArrows, value -> config.maxArrows = value));

        this.addRenderableWidget(new MaxSlider(x, top + gap, width, 20, "Max bee stingers",
                config.maxStingers, value -> config.maxStingers = value));

        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> this.onClose())
                .bounds(x, top + gap * 2 + 12, width, 20)
                .build());
    }

    @Override
    public void onClose() {
        HideArrowsConfig.get().save();
        this.minecraft.setScreen(this.parent);
    }

    /** Vanilla slider mapped to discrete stops; the far-right stop reads "Unlimited". */
    private static class MaxSlider extends AbstractSliderButton {
        private final String label;
        private final IntConsumer setter;

        MaxSlider(int x, int y, int width, int height, String label, int initial, IntConsumer setter) {
            super(x, y, width, height, Component.empty(), toSliderValue(initial));
            this.label = label;
            this.setter = setter;
            this.updateMessage();
        }

        private static double toSliderValue(int configValue) {
            if (configValue < 0) {
                return 1.0; // Unlimited sits at the far right.
            }
            return Math.min(configValue, MAX_VISIBLE) / (double) STOPS;
        }

        private int currentValue() {
            int index = (int) Math.round(this.value * STOPS);
            return index >= STOPS ? HideArrowsConfig.UNLIMITED : index;
        }

        @Override
        protected void updateMessage() {
            int value = currentValue();
            String text = value < 0 ? "Unlimited" : value == 0 ? "Hidden" : String.valueOf(value);
            this.setMessage(Component.literal(label + ": " + text));
        }

        @Override
        protected void applyValue() {
            this.setter.accept(currentValue());
        }
    }
}
