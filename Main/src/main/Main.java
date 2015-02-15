/* Copyright (c) 2015, Frédéric Fauberteau
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.util.concurrent.*;//to add a thread pool
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.*;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
  private static final BinarySearchTree<Integer> root = new BinarySearchTree<>();
  public final static int NTHREADS = 50;   
  public static long addTime = 0 ;
    
    
 public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Thread");
        yAxis.setLabel("Adding time");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Graphe du temps d'ajout des noeuds");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("");
        //populating the series with data
        series.getData().add(new XYChart.Data(NTHREADS, addTime));
        /*series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));*/
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
 
 
   

    public static final void main(String[] args) throws IOException {
        String name = "main";

        //create the thread pool with dynamic number of threads
        ExecutorService executor = Executors.newCachedThreadPool();
        //create and execute NTHREAD
        int k = 0;
       
        for (int i = 0; i < NTHREADS; i++) {
            k++;
            executor.execute(new AddNode());
        }
        //no more threads created
        executor.shutdown();

        //draw the pdf
        try{
        PrintWriter writer = new PrintWriter(name + ".dot");
        writer.println(root.toDOT(name));
        writer.close();
        }
        
        catch(Exception e){
            e.getStackTrace();
        }
        try{
        ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name + ".pdf", name + ".dot");
        builder.start();
        }
        catch(Exception e ){
            e.getStackTrace();
        }
        
        System.out.println("Bonjour");
       
        launch();
       
    }

    
    
    
    
    //implement the runnable
    private static class AddNode implements Runnable {

        //private Timer timer = new Timer(1000, new TimerListener());
        @Override
        public void run() {
            long startTime = System.nanoTime();
            root.add((int) (100 * Math.random()));
            //stop timerstartTime = System.nanoTime();
            long endTime = System.nanoTime();
            long delay = endTime - startTime;
            addTime += delay;
            //display delay
            System.out.println("Time to add a node: " + delay * Math.pow(10, -9) + " seconds.");

        }

        /*class TimerListener implements ActionListener{
         @Override
         public void actionPerformed(ActionEvent event){
           
         }
         }*/
    }

}
