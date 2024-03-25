package team.h2syj.fish.core;

public class Renderer {
    public Renderer(String title) {
        System.out.printf("============%s============%n", title);
    }

    public Renderer print(String template, Object... args) {
        System.out.printf((template) + "%n", args);
        return this;
    }

    public Renderer print(Object obj) {
        System.out.println(obj);
        return this;
    }

    public Renderer slowPrint(long time, String template, Object... args) {
        if (template == null)
            return this;
        String content = String.format(template, args);
        int length = content.length();
        long sleep = time / length;
        for (int i = 0; i < length; i++) {
            System.out.print(content.indexOf(i));
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println();
        return this;
    }

}
