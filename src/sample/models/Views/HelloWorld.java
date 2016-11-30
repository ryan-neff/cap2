/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.models.Views;

/**
 *
 * @author derek
 */
import sample.models.Views.SessionController;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import sample.models.Views.SessionController.helloWorld;


/**
 * A simple HelloWorld demo showing a simple speech application 
 * built using Sphinx-4. This application uses the Sphinx-4 endpointer,
 * which automatically segments incoming audio into utterances and silences.
 */
public class HelloWorld {

    helloWorld hw;
    
    public HelloWorld(helloWorld HW){
        this.hw = HW;
    }
    public String run(){
        try {
            URL url;
            
            url = HelloWorld.class.getResource("helloworld.config.xml");
            

            System.out.println("Loading...");

            ConfigurationManager cm = new ConfigurationManager(url);

	    Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
	    Microphone microphone = (Microphone) cm.lookup("microphone");


            /* allocate the resource necessary for the recognizer */
            recognizer.allocate();

            /* the microphone will keep recording until the program exits */
	    if (microphone.startRecording()) {

		
                 ScheduledService<Void> svc = new ScheduledService<Void>() {
                        @Override
                        protected Task<Void> createTask() {
                            return new Task<Void>() {           
                                @Override
                                protected Void call() throws Exception {
                                    System.out.println("Start speaking when ready");
                                    Result result = recognizer.recognize();
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(new Runnable() {                          
                                        @Override
                                        public void run() {
                                            try{
                                                 if (result != null) {
                                                    String resultText = result.getBestFinalResultNoFiller();
                                                    if(resultText.equals("flip")){
                                                        hw.flipcard();

                                                    }
                                                    else if(resultText.equals("next")){
                                                        hw.nextcard();

                                                    }
                                                    else{
                                                        hw.prevcard();

                                                    }


                                                    } else {
                                                        System.out.println("I can't hear what you said.\n");
                                                    }


                                            }finally{
                                                latch.countDown();
                                            }
                                        }
                                    });
                                    latch.await();                      
                                    //Keep with the background work
                                    return null;
                                }
                            };
                        }
                    };
                        svc.start();
                
                
		/*while (true) {
		    System.out.println
			("Start speaking. Press Ctrl-C to quit.\n");

                   
		    Result result = recognizer.recognize();
		    
		    if (result != null) {
			String resultText = result.getBestFinalResultNoFiller();
                        return resultText;
			
                       
		    } else {
			System.out.println("I can't hear what you said.\n");
		    }
		}*/
	    } else {
		System.out.println("Cannot start microphone.");
		recognizer.deallocate();
		System.exit(1);
	    }
        } catch (IOException e) {
            System.err.println("Problem when loading HelloWorld: " + e);
            e.printStackTrace();
        } catch (PropertyException e) {
            System.err.println("Problem configuring HelloWorld: " + e);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Problem creating HelloWorld: " + e);
            e.printStackTrace();
        }
        
       return null;
    }
}


