import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server (){
        connections=new ArrayList<>();
        done=false;
    }

    //set the server run on port 9999
    //create the thread pool
    //call the connectionhandler, which create a chat room for client and complete the chat room function
    //add connectionhandlers to the thread pool
    @Override
    public void run() {
        try {
            server=new ServerSocket(9999);
            pool=Executors.newCachedThreadPool();

            //create the client endpoint after the server endpoint accpeted it

            while (!done) {
                Socket client=server.accept();
                ConnectionHandler handler=new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);                
            }

        } catch (Exception e) {
            shutDown();
        }
    }
    public void broadcast (String message){
        for (ConnectionHandler ch : connections) {
            if(ch!=null){
                ch.sendMessage(message);
            }
        }
    }
    public void shutDown(){
        try {
            done=true;
            pool.shutdown();
            if(!server.isClosed()){
                server.close();
            }
            for (ConnectionHandler ch : connections) {
                ch.shutDown();
            }
        } catch (Exception e) {
        }
        
    }
        class ConnectionHandler implements Runnable {
            
            private Socket client;
            private BufferedReader in;
            private PrintWriter out;
            private String nickname;
            
            public ConnectionHandler(Socket client){
                this.client=client;
            }

            //the chat room for client
            @Override
            public void run() {
                try {
                    out = new PrintWriter(client.getOutputStream(),true);
                    in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                    out.println("Please enter a nickname");
                    nickname=in.readLine();
                    System.out.println(nickname+" connected");
                    broadcast(nickname+" joined the chat!");
                    String message;
                    while ((message=in.readLine())!=null) {
                        // for the clent changeing the nickname
                        if(message.startsWith("/nick ")){
                            String[]messageSplit =message.split(" ",2);
                            if (messageSplit.length==2) {
                                broadcast(nickname+" renamed themselves to "+messageSplit[1]);
                                System.out.println(nickname+" renamed themselves to "+messageSplit[1]);
                                nickname=messageSplit[1];
                                out.println("Successfully changed nickname to "+nickname);
                            }else{
                                out.println("No nickname provided!");
                            }
                        }
                        // for the client leaving the chat 
                        else if(message.startsWith("/quit")){
                            broadcast(nickname+" left the chat!");
                            shutDown();
                        }
                        // for the client chating
                        else{
                            broadcast(nickname+": "+message);
                        }
                    }
                } catch (IOException e) {
                    shutDown();
                }
            }
            
            public void sendMessage(String message){
                out.println(message);
            }
            public void shutDown(){
                try {
                    in.close();
                    out.close();
                    if(!client.isClosed()){
                        client.close();
                    }
                } catch (IOException e) {
                }
            }
            
        }
    public static void main(String[] args) {
        Server server=new Server();
        server.run();
    }
    
} 