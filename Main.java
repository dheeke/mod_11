package application;
	
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Main extends Application {
	@Override // Override the start method in the Application class
	  public void start(Stage primaryStage) {
	    // Text area for displaying contents
	    TextArea ta = new TextArea();
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
	    primaryStage.setTitle("Server"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	    new Thread( () -> {
	      try {
	        // Create a server socket
	        ServerSocket serverSocket = new ServerSocket(8000);
	        Platform.runLater(() ->
	          ta.appendText("Server started at " + new Date() + "\n Standing by for number input\n"));
	  
	        // Listen for a connection request
	        Socket socket = serverSocket.accept();
	  
	        // Create data input and output streams
	        DataInputStream inputFromClient = new DataInputStream(
	          socket.getInputStream());
	        DataOutputStream outputToClient = new DataOutputStream(
	          socket.getOutputStream());
	  
	        while (true) {
	          // Receive input number from the client
	          int inputNumber = inputFromClient.readInt();
	          System.out.println("Input number received is: " + inputNumber + "\n");
	          ta.appendText("The number recieved is:"+inputNumber+"\n  Sending result...\n");
	          int flag = 1, n;
	          n = inputNumber/2;
	  
	          // Check for prime
	          if(inputNumber==0||inputNumber==1)
	          {  
	        	flag=0;      
	          }
	          else
	        	  {  
	        	   for(int i=2;i<=n;i++)
	        	   {      
	        	    if(inputNumber%i==0)
	        	    {            
	        	     flag=0;
	        	     break;
	        	    }      
	        	   }    
	        	  }  
	   
	          // Send result back to the client
	          outputToClient.writeInt(flag);
	        }
	      }
	      catch(IOException ex) {
	        ex.printStackTrace();
	      }
	    }).start();
	    new Client().start(new Stage());
 }
	  /**
	   * The main method is only needed for the IDE with limited
	   * JavaFX support. Not needed for running from the command line.
	   */
	  public static void main(String[] args) {
	    launch(args);
	    
	  }
}
