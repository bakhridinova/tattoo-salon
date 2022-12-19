package com.example.demo.controller.navigation;

import static com.example.demo.controller.navigation.PageNavigation.HOME;

public class Router {
    private String page = HOME;
    private Type type = Type.FORWARD;

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Type getType() {
        return type;
    }

    public void setRedirect() {
        this.type = Type.REDIRECT;
    }

    public enum Type {
        FORWARD,
        REDIRECT
    }
}
