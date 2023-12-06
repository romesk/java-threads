module com.lab.lab7threads {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.lab.gui to javafx.fxml;
    exports org.lab.gui;
    exports org.lab.gui.controllers;
    opens org.lab.gui.controllers to javafx.fxml;
    opens org.lab.models.threading to javafx.base;
}