package team.h2syj.fish.core;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;

import team.h2syj.fish.utils.Utils;

/**
 * 渲染器
 */
public class Renderer {

    public Renderer() {
    }

    public Renderer(String title) {
        Ansi content = Ansi.ansi()
                .fgRgb(ColorList.blue_西子)
                .a(String.format("============%s============", title))
                .reset();
        System.out.println(content);
    }

    public Line newLine() {
        return new Line();
    }

    public Line print(String template, Object... args) {
        return new Line(template, args);
    }

    public Line print(Object obj) {
        return new Line(obj.toString());
    }

    public static void eraseScreen() {
        System.out.println(Ansi.ansi().eraseScreen());
    }

    public Renderer slowPrintln(long time, String template, Object... args) {
        if (template == null)
            return this;
        String content = String.format(template, args);
        int length = content.length();
        long sleep = time / length;
        for (int i = 0; i < length; i++) {
            System.out.print(content.charAt(i));
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println();
        return this;
    }

    public static class Line {
        private final Ansi content;

        public Line() {
            this.content = Ansi.ansi();
        }

        public Line(String content) {
            this.content = Ansi.ansi(new StringBuilder(content));
        }

        public Line(String content, Object... args) {
            this.content = Ansi.ansi(new StringBuilder(String.format(content, args)));
        }

        public Line print(Object object) {
            this.content.a(object.toString());
            return this;
        }

        public Line print(String content, Object... args) {
            this.content.a(String.format(content, args));
            return this;
        }

        public Line print(int color, String content, Object... args) {
            this.content.bgRgb(color).a(String.format(content, args)).reset();
            return this;
        }

        public Line color(int color) {
            this.content.fgRgb(color);
            return this;
        }

        public Line bgColor(int color) {
            this.content.bgRgb(color);
            return this;
        }

        public Line attribute(Attribute attribute) {
            this.content.a(attribute);
            return this;
        }

        public Line reset() {
            this.content.reset();
            return this;
        }

        public void end() {
            System.out.println(this.content.reset());
        }
    }

    public static class ColorList {
        public static final int blue_西子 = getColorByHex("#87c0ca"); // 西子
        public static final int green_春辰 = getColorByHex("#a9be7b"); // 春辰
        public static final int red_胭脂虫 = getColorByHex("#ab1d22"); // 胭脂虫
        public static final int gray_月魄 = getColorByHex("#b2b6b6"); // 月魄
        public static final int purple_木槿 = getColorByHex("#ba79b1"); // 木槿
        public static final int gold_松花 = getColorByHex("#ffee6f"); // 松花
        public static final int gold_黄栗留 = getColorByHex("#fedc5e"); // 黄栗留

        public static int getColorByHex(String hex) {
            return Utils.hexToInt(hex.substring(1));
        }
    }

}
