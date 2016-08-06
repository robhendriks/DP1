package co.robhendriks.dp1.command;

import co.robhendriks.dp1.App;

public abstract class CommandCollection {
    private App app;

    public void init(App app) {
        if (this.app == null) {
            this.app = app;
        }
    }

    public App getApp() {
        return app;
    }
}
