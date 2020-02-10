import java.io.*;
import java.net.*;
import response.*;
import com.sun.net.httpserver.*;
import java.nio.file.*;
import dataAccess.authDAO;
import model.authTokenModel;
import handler.*;

/*
	This example demonstrates the basic structure of the Family Map Server
	(although it is for a fictitious "Ticket to Ride" game, not Family Map).
	The example is greatly simplfied to help you more easily understand the
	basic elements of the server.
	
	The Server class is the "main" class for the server (i.e., it contains the 
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which 
		the server should accept incoming client connections.
*/
public class Server {

    // The maximum number of waiting incoming connections to queue.
    // While this value is necessary, for our purposes it is unimportant.
    // Take CS 460 for a deeper understanding of what it means.
    private static final int MAX_WAITING_CONNECTIONS = 12;

    // Java provides an HttpServer class that can be used to embed
    // an HTTP server in any Java program.
    // Using the HttpServer class, you can easily make a Java
    // program that can receive incoming HTTP requests, and respond
    // with appropriate HTTP responses.
    // HttpServer is the class that actually implements the HTTP network
    // protocol (be glad you don't have to).
    // The "server" field contains the HttpServer instance for this program,
    // which is initialized in the "run" method below.
    private HttpServer server;

    // This method initializes and runs the server.
    // The "portNumber" parameter specifies the port number on which the
    // server should accept incoming client connections.
    private void run(String portNumber) {

        // Since the server has no "user interface", it should display "log"
        // messages containing information about its internal activities.
        // This allows a system administrator (or you) to know what is happening
        // inside the server, which can be useful for diagnosing problems
        // that may occur.
        System.out.println("Initializing HTTP Server");

        try {
            // Create a new HttpServer object.
            // Rather than calling "new" directly, we instead create
            // the object by calling the HttpServer.create static factory method.
            // Just like "new", this method returns a reference to the new object.
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        // Log message indicating that the server is creating and installing
        // its HTTP handlers.
        // The HttpServer class listens for incoming HTTP requests.  When one
        // is received, it looks at the URL path inside the HTTP request, and
        // forwards the request to the handler for that URL path.
        System.out.println("Creating contexts");

        // Create and install the HTTP handler for the "/games/list" URL path.
        // When the HttpServer receives an HTTP request containing the
        // "/games/list" URL path, it will forward the request to ListGamesHandler
        // for processing.
        server.createContext("/person", personHandler);
        server.createContext("/clear/", clearHandler );
        server.createContext("/event", eventHandler);
        server.createContext("/user/login", loginHandler);
        server.createContext("/user/register", registerHandler);
        server.createContext("/fill", fillHandler);
        server.createContext("/load", loadHandler);
        server.createContext("/",  defaultHandler);

        // Log message indicating that the HttpServer is about the start accepting
        // incoming client connections.
        System.out.println("Starting server");

        // Tells the HttpServer to start accepting incoming client connections.
        // This method call will return immediately, and the "main" method
        // for the program will also complete.
        // Even though the "main" method has completed, the program will continue
        // running because the HttpServer object we created is still running
        // in the background.
        server.start();

        // Log message indicating that the server has successfully started.
        System.out.println("Server started");
    }

    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = "8080";
        System.out.println("server listening on port " + 8080);
        new Server().run(portNumber);
    }

    private HttpHandler personHandler = new HttpHandler(){


        public void handle(HttpExchange exchange) throws IOException{

            Headers reqHeaders = exchange.getRequestHeaders();
            // Check to see if an "Authorization" header is present
            if (!(reqHeaders.containsKey("Authorization"))) {
                response r = new response("Auth token is required");
                sendResponseBody(r, exchange);
                return;
            }
                // Extract the auth token from the "Authorization" header
                String authToken = reqHeaders.getFirst("Authorization");
                if(!(validate(authToken)))
                {
                    response r = new response("Invalid auth token!");
                    sendResponseBody(r, exchange);
                    return;
                }

            URI url =exchange.getRequestURI();
            String urlData = url.toString();
            handler.personHandler p = new personHandler();
            sendResponseBody(p.handlePerson(urlData, authToken), exchange);
            //System.out.println("complete!");
        }
    };

    public HttpHandler clearHandler= new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ((exchange.getRequestMethod().toLowerCase().equals("post"))) {
                handler.clearHandler clearer = new clearHandler();
                //sendResponseBody.sendBack()
                sendResponseBody(clearer.handleClear(), exchange);
            }
        }
    };

    public HttpHandler fillHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI url =exchange.getRequestURI();
            String urlData = url.toString();
            String[] separate = urlData.split("/");
            String username = separate[2];
            int numGenerations = 4;
            if(separate.length > 3) {
                numGenerations = Integer.valueOf(separate[3]);
            }
            handler.fillHandler filler = new fillHandler();
            sendResponseBody(filler.handleFill(username, numGenerations), exchange);
            //System.out.println("complete!");
        }
    };

    public HttpHandler eventHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers reqHeaders = exchange.getRequestHeaders();
            // Check to see if an "Authorization" header is present
            if (!(reqHeaders.containsKey("Authorization"))) {
                response r = new response("Auth token is required");
                sendResponseBody(r, exchange);
                return;
            }
            // Extract the auth token from the "Authorization" header
            String authToken = reqHeaders.getFirst("Authorization");
            if(!(validate(authToken)))
            {
                response r = new response("Invalid auth token!");
                sendResponseBody(r, exchange);
                return;
            }

            URI url =exchange.getRequestURI();
            String urlData = url.toString();
            handler.eventHandler eventer = new eventHandler();
            sendResponseBody(eventer.getEvent(urlData, authToken), exchange);
            //System.out.println("complete!");
        }
    };
    public HttpHandler registerHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream reqBody = exchange.getRequestBody();
            // Read JSON string from the input stream
            String reqData = readString(reqBody);
            handler.registerHandler registerer = new registerHandler();
            sendResponseBody(registerer.handleRegister(reqData), exchange);

        }
    };
    public HttpHandler loginHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream reqBody = exchange.getRequestBody();
            // Read JSON string from the input stream
            String reqData = readString(reqBody);
            handler.loginHandler loginer = new loginHandler();
            sendResponseBody(loginer.handleLogin(reqData), exchange);
        }
    };

    public HttpHandler loadHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handler.clearHandler.handleClear();
            InputStream reqBody = exchange.getRequestBody();
            // Read JSON string from the input stream
            String reqData = readString(reqBody);
            handler.loadHandler loader = new loadHandler();
            sendResponseBody(loader.handleLoad(reqData), exchange);
           // System.out.println("complete!");
        }
    };
    public HttpHandler defaultHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers head=exchange.getResponseHeaders();

            URI url=exchange.getRequestURI();
            String urlData=url.toString();
            String path = null;

            //String[] params=urlData.split("/",2);
              if(urlData.equals("/")|| urlData.equals("index.html")){
                  path = "/Server/HTML/index.html";
              }
              else
              {
                  path = "/Server/HTML" + urlData;
              }
             String baseDir = Paths.get("").toAbsolutePath().toString();
                //File newFile = new File(path);
                String fullPath = baseDir+path;
              Path newPath = Paths.get(fullPath);
              File newFile = new File(baseDir+path);
              if(!newFile.exists())
              {
                  newPath = Paths.get(baseDir+"/Server/HTML/404.html");
              }
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Files.copy(newPath, exchange.getResponseBody());
           // System.out.println("hello");
            exchange.getResponseBody().close();

        }
    };

    public void sendResponseBody(response r, HttpExchange exchange)throws IOException{
            OutputStream sendBack = exchange.getResponseBody();
            String json = r.getBody();
            //System.out.println(json);
            //System.out.println("we closing, son!");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            writeString(json, sendBack);
            exchange.getResponseBody().close();
        }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


    private boolean validate(String authToken){
        authDAO auth = new authDAO();
        authTokenModel validated = auth.getAuth(authToken);
        if(validated == null)
        {
            return false;
        }
        return true;
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}