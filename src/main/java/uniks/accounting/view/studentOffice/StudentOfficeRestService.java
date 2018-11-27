package uniks.accounting.view.studentOffice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import uniks.accounting.EventSource;
import uniks.accounting.StudentOfficeBuilder;

import java.util.LinkedHashMap;
import java.util.SortedMap;

import static spark.Spark.*;
import static uniks.accounting.view.studentOffice.StudentOfficeApplication.ob;

public class StudentOfficeRestService {
    
    private static final Logger logger = LogManager.getLogger();
    
    public StudentOfficeRestService() {
        port(3000);
        
        path("/api/office", () -> {
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
        
        EventSource eventSource = ob.getEventSource();
        SortedMap<Long, LinkedHashMap<String, String>> map = eventSource.pull(timestamp,
                StudentOfficeBuilder.STUDENT_CREATED, StudentOfficeBuilder.STUDENT_ENROLLED);
        
        String result = EventSource.encodeYaml(map);
        
        res.status(200);
        return result;
    }

    private String putSharedEvents(Request req, Response res) {
        return "OK";
    }
}
