module com.example.lotterycopy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lotterycopy to javafx.fxml;
    exports com.example.lotterycopy;
}