module com.example.distributedpee {
    requires javafx.controls;
    requires javafx.fxml;
    requires mpj;


    opens com.example.distributedpee to javafx.fxml;
    exports com.example.distributedpee;
}