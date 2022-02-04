package com.example.particleeffectengine;


import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class StartScene implements Initializable {

    public ObservableList<String> choices = FXCollections.observableArrayList("Burst","Over time");
    @FXML
    private TextField particlesemittedps;
    @FXML
    private TextField ttl;
    @FXML
    private TextField posx;
    @FXML
    private TextField posy;
    @FXML
    private CheckBox sequential;
    @FXML
    private CheckBox parallel;
    @FXML
    private TextField nop;
    @FXML
    private TextField scenewidth;
    @FXML
    private TextField sceneheight;
    @FXML
    private ChoiceBox choicebox;

    @FXML
    protected void selection(){
        if (choicebox.getValue() == "Burst") {
            particlesemittedps.setDisable(choicebox.getValue() == "Burst");
            particlesemittedps.setText(nop.getText());
        }else{
            particlesemittedps.setDisable(false);
        }
    }

    @FXML
    protected void pressSequential(){
        parallel.setSelected(!sequential.isSelected());
    }

    @FXML
    protected void pressParallel(){
        sequential.setSelected(!parallel.isSelected());
    }

    @FXML
    protected void start() throws Exception {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        Main.totalParticles = Integer.parseInt(nop.getText());
        if ((Integer.parseInt(sceneheight.getText()) > screenSize.getHeight()) && (Integer.parseInt(scenewidth.getText()) > screenSize.getWidth())) Main.fullscreen=true;
        if ((Integer.parseInt(sceneheight.getText()) > screenSize.getHeight())) Main.windowHeight = (int) screenSize.getHeight();
        else Main.windowHeight = Integer.parseInt(sceneheight.getText());
        if ((Integer.parseInt(scenewidth.getText()) > screenSize.getWidth())) Main.windowWidth = (int) screenSize.getWidth();
        else Main.windowWidth = Integer.parseInt(scenewidth.getText());
        Main.timeToLive = Integer.parseInt(ttl.getText());
        Main.emitPerS = Integer.parseInt(particlesemittedps.getText());
        Main.emitterX = Integer.parseInt(posx.getText());
        Main.emitterY = Integer.parseInt(posy.getText());
        if (sequential.isSelected()) Main.animationOption = Main.AnimationOption.SEQUENTIAL;
        else Main.animationOption = Main.AnimationOption.PARALLEL;

        Main.animation();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicebox.setItems(choices);
        choicebox.setValue("Over time");
        AnimationTimer listener = new AnimationTimer(){
            @Override
            public void handle(long l) {
                selection();
            }
        };
        listener.start();
    }
}
