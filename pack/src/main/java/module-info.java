module oriseus.pack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    
    opens oriseus.pack to javafx.fxml;
    opens oriseus.pack.modelsViews to javafx.fxml;
    opens oriseus.pack.controllers to javafx.fxml;
    opens oriseus.pack.dto to com.fasterxml.jackson.databind;
    
    exports oriseus.pack;
    exports oriseus.pack.modelsViews;
    exports oriseus.pack.controllers;
}
