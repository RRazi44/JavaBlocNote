package fr.razi.blocknote;

import fr.razi.blocknote.GUI.BlockNoteGUI;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.Scanner;


public class Handler {

    public BlockNoteGUI instance;

    public Handler(BlockNoteGUI instance){
        this.instance = instance;
    }

    public void saveFile(String text) {

        if(instance.getCurrentFile() == null){
            File file = openFileChooser(false, "New file");
            if(file == null) return;
            write(file, text);
            instance.setCurrentFile(file);
        }

        else{
            write(instance.getCurrentFile(), text);
        }
    }

    private static void write(File file, String text){
        try (FileWriter writer = new FileWriter(file);) {
            writer.write(text);
        }
        catch (IOException e) {
            System.err.println("Erreur d’écriture : " + e.getMessage());
        }
    }


    public void openFile(){
        File file = openFileChooser(true, "Open a file");
        if(file == null) return;

        StringBuilder text = new StringBuilder();

        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                text.append(myReader.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.\n" + e.getMessage() + "\n" + e.getCause());
        }

        instance.getTextArea().setText(text.toString());
        instance.setCurrentFile(file);
    }


    public void newFile() {
        instance.getTextArea().setText("");
        instance.setCurrentFile(null);
    }

    public File openFileChooser(boolean isOpen, String windowName){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tous les fichiers", "*"));
        fileChooser.setTitle(windowName);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        if(isOpen){
            return fileChooser.showOpenDialog(instance.getPrimaryStage());
        }
        return fileChooser.showSaveDialog(instance.getPrimaryStage());
    }

}
