package uniks.accounting;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class StudentOfficeRestServer {

   public static final String CONFIG_STUDENT_OFFICE_EVENTS_YAML = "config/studentOfficeEvents.yaml";

   private int i = 0;
   private StudentOfficeBuilder ob;
   public static StudentOfficeRestServer server = null;

   public static void main(String[] args)
   {
      server = new StudentOfficeRestServer();
      // threadPool(1);
      get("/pull", (req, res) -> server.pull(req, res));
      post("/push", (req, res) -> server.push(req, res));

      server.run();
   }

   public int push(Request req, Response res)
   {
      QueryParamsMap params = req.queryMap();
      String caller = params.get("caller").value();

      String yaml = params.get("yaml").value();

      ob.sync(yaml);

      System.out.println(yaml);
      return 0;
   }

   public void run()
   {
      // open event file
      byte[] bytes = new byte[0];
      try
      {
         bytes = Files.readAllBytes(Paths.get(CONFIG_STUDENT_OFFICE_EVENTS_YAML));
      }
      catch (IOException e)
      {
         Logger.getGlobal().log(Level.SEVERE, "could not load initial data from " + CONFIG_STUDENT_OFFICE_EVENTS_YAML, e);
      }

      String yaml = new String(bytes);

      ob = new StudentOfficeBuilder();
      ob.sync(yaml);



   }

   private String pull(Request req, Response res)
   {
      QueryParamsMap params = req.queryMap();
      String lastKnownNumberString = params.get("lastKnownNumber").value();
      int lastKnownNumber = Integer.parseInt(lastKnownNumberString);
      String caller = params.get("caller").value();

      EventSource eventSource = ob.getEventSource();
      SortedMap<Integer, LinkedHashMap<String, String>> map = eventSource.pull(lastKnownNumber,
            StudentOfficeBuilder.STUDENT_CREATED + " " + StudentOfficeBuilder.STUDENT_ENROLLED);
      String yaml = EventSource.encodeYaml(map);

      return  yaml;
   }
}
