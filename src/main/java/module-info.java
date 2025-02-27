module Kulki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens pl.karatesan.gfx to javafx.fxml;
    exports pl.karatesan.gfx;
}