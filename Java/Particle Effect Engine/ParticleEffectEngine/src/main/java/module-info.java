module com.example.particleeffectengine {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.particleeffectengine to javafx.fxml;
    exports com.example.particleeffectengine;
}