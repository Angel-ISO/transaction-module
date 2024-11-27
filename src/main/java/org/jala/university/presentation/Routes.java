package org.jala.university.presentation;

import lombok.Getter;

@Getter
public enum Routes {
    DEVELOP("develop"),
    MAIN("main"),
    HOME("home"),
    ADMIN_HOME("adminHome"),
    ADMIN_SETTINGS("adminSettings"),
    ADMIN_MESSAGE("adminMessage"),
    ADMIN_SCAN_QR_CODE("adminCodeqrScan"),
    ADMIN_SEND_MONEY("adminUser"),
    ADMIN_HISTORIAL_TRANSACTION("adminhistorialTransaction"),
    SETTINGS("settings"),
    MESSAGE("message"),
    SCAN_QR_CODE("codeqrScan"),
    SEND_MONEY("sendMoney"),
    LOG_IN("logInUsers"),
    ADMIN_VIEW("adminView");

    private final String name;
    

    Routes(String name) {
        this.name = name;
    }

}


