package team.h2syj.fish.core;

public class Renderer {
    public Renderer(String title) {
        System.out.printf("============%s============%n", title);
    }

    public Renderer print(String content, Object... args) {
        System.out.printf((content) + "%n", args);
        return this;
    }

    public Renderer print(Object obj) {
        System.out.println(obj);
        return this;
    }
}
