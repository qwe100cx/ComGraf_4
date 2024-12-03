module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires org.testng;
    requires junit;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}