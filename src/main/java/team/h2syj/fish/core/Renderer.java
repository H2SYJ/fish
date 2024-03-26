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
                .fgRgb(ColorList.blue_xizi)
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
        public static final int blue_xizi = getColorByHex("#87c0ca"); // 西子
        public static final int green_spring = getColorByHex("#a9be7b"); // 春辰
        public static final int red_cochineal = getColorByHex("#ab1d22"); // 胭脂虫
        public static final int gray = getColorByHex("#b2b6b6"); // 月魄
        public static final int purple = getColorByHex("#ba79b1"); // 木槿
        public static final int gold = getColorByHex("#ffee6f"); // 松花

        public static int getColorByHex(String hex) {
            return Utils.hexToInt(hex.substring(1));
        }
    }

}
