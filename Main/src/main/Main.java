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


public class Main {
  private static BinarySearchTree<Integer> root = new BinarySearchTree<>();
  private final static  int NTHREADS = 50;
  
  public static final void main(String[] args) throws IOException {
    String name = "main";
    
    //create the thread pool with dynamic number of threads
    ExecutorService executor = Executors.newCachedThreadPool();
    //create and execute NTHREAD
    for(int i =0; i<NTHREADS; i++){
        executor.execute(new AddNode());
    }
    //no more threads created
    executor.shutdown();
    
    //draw the pdf
    PrintWriter writer = new PrintWriter(name + ".dot");
    writer.println(root.toDOT(name));
    writer.close();
    ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name + ".pdf", name + ".dot");
    builder.start();
  }
  
  //implement the runnable
  private static class AddNode implements Runnable{
    //private Timer timer = new Timer(1000, new TimerListener());
     @Override
     public void run(){
         /*timer.start();
        
         System.out.println("Time: "+timer.getDelay());
          timer.stop();*/
           root.add((int)(20*Math.random()));
     }
     
     /*class TimerListener implements ActionListener{
         @Override
         public void actionPerformed(ActionEvent event){
           
         }
     }*/
     
  }
  
}
