package fr.razi.blocknote.GUI;

import fr.razi.blocknote.Handler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class BlockNoteGUI extends Application {

    private final TextArea textArea= new TextArea();
    private final Handler handler = new Handler(this);

    private Stage primaryStage;
    private File currentFile = null;

    private Menu menuFile;

    @Override
    public void start(Stage stage) {

        MenuBar menuBar = new MenuBar();

        primaryStage = stage;
        primaryStage.setTitle("Bloc note - Nouveau fichier");

        initFileMenu();

        menuBar.getMenus().add(menuFile);

        VBox root = new VBox(menuBar, textArea);

        VBox.setVgrow(textArea, Priority.ALWAYS);
        Scene mainScene = new Scene(root, 1080, 500);

        primaryStage.centerOnScreen();
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    public void setCurrentFile(File file){
        currentFile = file;
        if(file != null) primaryStage.setTitle("Bloc note - " + file.getName());
        else primaryStage.setTitle("Bloc note - Nouveau fichier");
    }


    private void initFileMenu(){
        if(menuFile != null) return;
        menuFile = new Menu("Fichier");

        MenuItem[] fileMenuItems = getFileMenuItems();
        menuFile.getItems().add(new SeparatorMenuItem());

        for (MenuItem item : fileMenuItems) {
            menuFile.getItems().add(item);
        }
    }

    private MenuItem[] getFileMenuItems(){
        MenuItem newFile = new MenuItem("Nouveau");
        newFile.setOnAction(event -> handler.newFile());
        MenuItem openFile = new MenuItem("Ouvrir");
        openFile.setOnAction(event -> handler.openFile());
        MenuItem saveFile = new MenuItem("Enregistrer");
        saveFile.setOnAction(event -> handler.saveFile(textArea.getText()));
        MenuItem exitApp = new MenuItem("Quitter");
        exitApp.setOnAction(event -> getPrimaryStage().close());

        return new MenuItem[]{newFile, openFile, saveFile, exitApp};
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public File getCurrentFile(){
        return currentFile;
    }
}
