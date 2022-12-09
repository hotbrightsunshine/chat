module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens client to javafx.fxml;

    exports it.fi.meucci;
    exports it.fi.meucci.utils;
}