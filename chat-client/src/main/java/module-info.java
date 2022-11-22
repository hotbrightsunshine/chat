module it.fi.meucci {
    requires javafx.controls;
    requires javafx.fxml;

    opens it.fi.meucci to javafx.fxml;
    exports it.fi.meucci;
}
