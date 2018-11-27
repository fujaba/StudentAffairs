package uniks.accounting.view.tapool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fulib.yaml.Yamler;
import spark.Request;
import spark.Response;
import uniks.accounting.EventSource;
import uniks.accounting.StudentOfficeBuilder;
import uniks.accounting.TAPoolBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;
import static uniks.accounting.view.tapool.TAPoolApplication.tb;

public class TAPoolRestService {
    
    private static final Logger logger = LogManager.getLogger();
    
    public TAPoolRestService() {
        port(3001);
        
        path("/api/pool", () -> {
            before("/*", (q, a) -> logger.debug(q.requestMethod() + " on " + q.uri() + " from " + q.host()));
            get("/get", this::getSharedEvents);
            post("/put", this::putSharedEvents);
        });
        
        logger.info("Rest service listening on " + port());
    }
    
    public void stopServer() {
        stop();
    }
    
    private String getSharedEvents(Request req, Response res) {
        String since = req.queryParams("since");
        String caller = req.queryParams("caller");
        if (since == null) {
            res.status(400);
            return "Param 'since' need to be set";
        }

        if (caller == null) {
            res.status(400);
            return "Param 'caller' need to be set";
        }
        long timestamp = 0;
        try {
            timestamp = Long.parseLong(since);
        } catch (NumberFormatException e) {
            res.status(400);
            return "Param 'since' have to be a timestamp number";
        }

        EventSource eventSource = tb.getEventSource();
        SortedMap<Long, LinkedHashMap<String, String>> map = eventSource.pull(timestamp,
                TAPoolBuilder.STUDENT_HIRED_AS_TA);
        List<LinkedHashMap<String, String>> filteredEvents = map.values().stream().filter(event -> event.get(TAPoolBuilder.TEACHING_ASSISTANT_FOR).equals(caller)).collect(Collectors.toList());

        String result = EventSource.encodeYaml(filteredEvents);


        res.status(200);
        return result;
    }
    
    private String putSharedEvents(Request req, Response res) {
        String caller = req.queryParams("caller");
        String body = req.body();
        if (caller == null) {
            res.status(400);
            return "Param 'caller' need to be set";
        }

        if (body == null || body.isEmpty()) {
            res.status(400);
            return "Body is required";
        }

        Yamler yamler = new Yamler();
        ArrayList<LinkedHashMap<String, String>> allowedEvents;
        String result = "OK";
        switch (caller) {
            case "segroup":
                allowedEvents = filterAllowedEventTypes(yamler.decodeList(body),
                    TAPoolBuilder.STUDENT_HIRED_AS_TA);
                ob.applyEvents(allowedEvents);
                break;

            case "theorygroup":
                allowedEvents = filterAllowedEventTypes(yamler.decodeList(body),
                    TAPoolBuilder.STUDENT_HIRED_AS_TA);
                ob.applyEvents(allowedEvents);
                break;

            default:
                break;
        }

        res.status(200);
        return result;
    }
    
    private ArrayList<LinkedHashMap<String, String>> filterAllowedEventTypes(ArrayList<LinkedHashMap<String, String>> events, String... types) {
        ArrayList<LinkedHashMap<String, String>> result = new ArrayList<>();
        List<String> allowedTypes = Arrays.asList(types);
        for (LinkedHashMap<String, String> event : events) {
            if (allowedTypes.contains(event.get(StudentOfficeBuilder.EVENT_TYPE))) {
                result.add(event);
            }
        }
        return result;
    }
}
