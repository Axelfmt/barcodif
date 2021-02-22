package com.amtSoftware.vistaControlador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage escenario) throws Exception {
        escenario.setTitle("barcodif");
        escenario.setScene(new Scene(new VentanaPrincipal(), 300, 300));
        escenario.show();
    }
}
