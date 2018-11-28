package uniks.accounting.view.theorygroup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uniks.accounting.EventSource;
import uniks.accounting.TheoryGroupBuilder;
import uniks.accounting.theorygroup.Presentation;
import uniks.accounting.theorygroup.Seminar;
import uniks.accounting.theorygroup.TheoryGroup;
import uniks.accounting.theorygroup.TheoryStudent;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;
import uniks.accounting.view.theorygroup.subController.TheoryGroupController;
import uniks.accounting.view.theorygroup.subView.ModifyPresentation;
import uniks.accounting.view.theorygroup.subView.ModifySeminar;
import uniks.accounting.view.theorygroup.subView.ModifyTheoryGroup;
import uniks.accounting.view.theorygroup.subView.ModifyTheoryStudent;

import java.util.LinkedHashMap;
import java.util.SortedMap;

import static uniks.accounting.view.theorygroup.TheoryGroupApplication.gb;
import static uniks.accounting.view.theorygroup.TheoryGroupApplication.modelView;

public class MainController {

    private static Logger logger = LogManager.getLogger();
    
    private static final String STUDENT_OFFICE_URL = "http://localhost:3000/api/office";
    private static final String TA_POOL_URL = "http://localhost:3001/api/pool";
    
    private MainView view;

    private long lastGetRequestTimestamp;
    private long lastPutRequestTimestamp;
    
    public MainController(MainView view) {
        this.view = view;
    }
    
    public void init() {
        this.view.getCreateGroup().setOnAction(this::onCreateGroup);
        this.view.getUpdate().setOnAction(this::onUpdate);
        this.view.getPut().setOnAction(this::onPut);
        this.view.getGet().setOnAction(this::onGet);
        this.lastGetRequestTimestamp = 0;
        this.lastPutRequestTimestamp = 0;
    }
    
    private void onGroupOverviewDoubleClick(MouseEvent evt) {
        if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
            this.onUpdate(null);    
        }
    }
    
    private void onCreateGroup(ActionEvent evt) {
        TheoryGroup group = gb.getOrCreateTheoryGroup();
        OfficeTreeItem rootItem = new OfficeTreeItem("Theory Group - " + group.getHead());

        TheoryGroupController groupCon = new TheoryGroupController(rootItem, group);
        groupCon.init();
        
        modelView.put(rootItem.getId(), groupCon);
        
        this.view.addRootItem(rootItem);
        
        this.view.getGroupOverview().setOnMousePressed(this::onGroupOverviewDoubleClick);
        this.view.getCreateGroup().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
        
        this.view.getUpdate().disableProperty().bind(this.view.getGroupOverview().getSelectionModel().selectedItemProperty().isNull());
        this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
    }

    private void onPut(ActionEvent evt) {
        try {
            SortedMap<Long, LinkedHashMap<String, String>> events = gb.getEventSource().pull(lastPutRequestTimestamp, TheoryGroupBuilder.EXAMINATION_GRADED);
            String yaml = EventSource.encodeYaml(events);
            HttpResponse<String> res = Unirest.post(STUDENT_OFFICE_URL + "/put")
                    .queryString("caller", gb.getTheoryGroup().getHead())
                    .body(yaml)
                    .asString();
            if (res.getStatus() != 200) {
                logger.error("Put request for " + STUDENT_OFFICE_URL + "/put failed with " + res.getStatus() + " " + res.getStatusText());
            }

            events = gb.getEventSource().pull(lastPutRequestTimestamp, TheoryGroupBuilder.STUDENT_HIRED_AS_TA);
            yaml = EventSource.encodeYaml(events);
            res = Unirest.post(TA_POOL_URL + "/put")
                    .queryString("caller", gb.getTheoryGroup().getHead())
                    .body(yaml)
                    .asString();
            if (res.getStatus() != 200) {
                logger.error("Put request for " + STUDENT_OFFICE_URL + "/put failed with " + res.getStatus() + " " + res.getStatusText());
            }

            lastPutRequestTimestamp = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Put request failed");
        }
    }

    private void onGet(ActionEvent evt) {
        try {
            HttpResponse<String> res = Unirest.get(STUDENT_OFFICE_URL + "/get")
                    .queryString("since", lastGetRequestTimestamp)
                    .queryString("caller", gb.getTheoryGroup().getHead())
                    .asString();
            if (res.getStatus() == 200) {
                String yaml = res.getBody();
                gb.applyEvents(yaml);
                logger.info("Apply events from student office:\n" + yaml);
            } else {
                logger.error("Get request for " + STUDENT_OFFICE_URL + "/get failed with " + res.getStatus() + " " + res.getStatusText());
            }

            res = Unirest.get(TA_POOL_URL + "/get")
                    .queryString("since", lastGetRequestTimestamp)
                    .queryString("caller", gb.getTheoryGroup().getHead())
                    .asString();
            if (res.getStatus() == 200) {
                String yaml = res.getBody();
                gb.applyEvents(yaml);
                logger.info("Apply events from ta pool:\n" + yaml);
            } else {
                logger.error("Get request for " + TA_POOL_URL + "/get failed with " + res.getStatus() + " " + res.getStatusText());
            }

            lastGetRequestTimestamp = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Get request failed");
        }
    }

    private void onUpdate(ActionEvent evt) {
        OfficeTreeItem selectedItem = (OfficeTreeItem) this.view.getGroupOverview().getSelectionModel().getSelectedItem();
        SubController con = modelView.get(selectedItem.getId());
        if (con != null) {
            Object modelItem = con.getModel();
            if (modelItem instanceof TheoryGroup) {
                TheoryGroup group = (TheoryGroup) modelItem;
                new ModifyTheoryGroup(group).showAndWait();
            } else if (modelItem instanceof TheoryStudent) {
                TheoryStudent student = (TheoryStudent) modelItem;
                new ModifyTheoryStudent(student).showAndWait();
            } else if (modelItem instanceof Seminar) {
                Seminar seminar = (Seminar) modelItem;
                new ModifySeminar(seminar).showAndWait();
            } else if (modelItem instanceof Presentation) {
                Presentation presentation = (Presentation) modelItem;
                new ModifyPresentation(presentation).showAndWait();
            }
        }
    }
}
