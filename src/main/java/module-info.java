module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens project to javafx.fxml;
    exports project;
}
