/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main.AddNode;
import static main.Main.NTHREADS;
/**
 *
 * @author slou
 */
public class ui extends Application {
    private int nbWords ;
    private int nbThreads ;
  
    
    public int getNbWords(){
        return nbWords;
    }
    
    public int getNbThreads(){
        return nbThreads;
    }
    public void StartUi() {
        this.launch();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("JavaFX Welcome");
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Defining the Name text field
        final TextField nbWords = new TextField();
        nbWords.setPromptText("Number of words");
        GridPane.setConstraints(nbWords, 0, 0);
        grid.getChildren().add(nbWords);

        //Defining the Last Name text field
        final TextField nbThreads = new TextField();
        nbThreads.setPromptText("Number of threads");
        GridPane.setConstraints(nbThreads, 0, 1);
        grid.getChildren().add(nbThreads);

        //Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 4);
        grid.getChildren().add(clear);
        
        //Defining the Submit button
        Button go = new Button("Go");
        GridPane.setConstraints(go, 2, 4);
        grid.getChildren().add(go);

        //Adding a Label
        final Label label = new Label();
        GridPane.setConstraints(label, 0, 5);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);
        
        //Add all
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        //
        go.setOnAction((ActionEvent e) -> {
            if (nbThreads.getText() != null && !nbThreads.getText().isEmpty() && nbWords.getText() != null && !nbWords.getText().isEmpty()) {
                label.setText(nbWords.getText() + " words with " + nbThreads.getText() + " threads.");
                this.nbThreads = Integer.parseInt(nbThreads.getText());
                this.nbWords = Integer.parseInt(nbWords.getText());
                
               
                 
                //create and execute NTHREAD
                int k = 0;

                for (int i = 0; i < this.nbThreads; i++) {
                    
                    //create the thread pool with dynamic number of threads
                 ExecutorService executor = Executors.newFixedThreadPool(i+1);
                    
                    for(int j =0;j<this.nbWords;j++)
                    executor.execute(new AddNode());
                    
                    //no more threads created
                executor.shutdown();
                
                }
                

                
                
                
                
                
            } else {
                label.setText("You must fill the fields above");
            }
        });

        clear.setOnAction((ActionEvent e) -> {
            nbWords.clear();
            nbThreads.clear();
            label.setText(null);
        });
        primaryStage.show();
    }
}
