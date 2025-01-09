module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;

    requires junit;

    opens com.cgvsu to javafx.fxml; // Открываем пакет com.cgvsu для JavaFX
    opens com.cgvsu.math to junit; // Открываем пакет com.cgvsu.math для JUnit

    exports com.cgvsu; // Экспортируем основной пакет
    exports com.cgvsu.math; // Экспортируем пакет com.cgvsu.math
}
