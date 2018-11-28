package uniks.accounting.view.segroup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fulib.yaml.Yamler;
import uniks.accounting.EventSource;
import uniks.accounting.SEGroupBuilder;
import uniks.accounting.segroup.*;
import uniks.accounting.view.segroup.subController.SEGroupController;
import uniks.accounting.view.shared.OfficeTreeItem;
import uniks.accounting.view.shared.SubController;
import uniks.accounting.view.segroup.subView.*;

import java.util.LinkedHashMap;
import java.util.SortedMap;

import static uniks.accounting.view.segroup.SEGroupApplication.gb;
import static uniks.accounting.view.segroup.SEGroupApplication.modelView;

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
        String profName = this.view.getProfName().getText();
        if (profName != null && !profName.isEmpty()) {
            SEGroup group = gb.buildSEGroup(profName);
            OfficeTreeItem rootItem = new OfficeTreeItem("SE Group - " + profName);

            SEGroupController groupCon = new SEGroupController(rootItem, group);
            groupCon.init();
            
            modelView.put(rootItem.getId(), groupCon);
            
            this.view.addRootItem(rootItem);
            
            this.view.getGroupOverview().setOnMousePressed(this::onGroupOverviewDoubleClick);
            this.view.getCreateGroup().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
            this.view.getProfName().disableProperty().bind(this.view.getGroupOverview().rootProperty().isNotNull());
            
            this.view.getUpdate().disableProperty().bind(this.view.getGroupOverview().getSelectionModel().selectedItemProperty().isNull());
            this.view.getUpdate().prefWidthProperty().bind(this.view.widthProperty());
            this.view.getPut().prefWidthProperty().bind(this.view.widthProperty());
            this.view.getGet().prefWidthProperty().bind(this.view.widthProperty());
        }
    }
    
    private void onPut(ActionEvent evt) {
        try {
            SortedMap<Long, LinkedHashMap<String, String>> events = gb.getEventSource().pull(lastPutRequestTimestamp, SEGroupBuilder.EXAMINATION_GRADED);
            String yaml = EventSource.encodeYaml(events);
            HttpResponse<String> res = Unirest.post(STUDENT_OFFICE_URL + "/put")
                    .queryString("caller", gb.getSeGroup().getHead())
                    .body(yaml)
                    .asString();
            if (res.getStatus() != 200) {
                logger.error("Put request for " + STUDENT_OFFICE_URL + "/put failed with " + res.getStatus() + " " + res.getStatusText());
            }
            
            events = gb.getEventSource().pull(lastPutRequestTimestamp, SEGroupBuilder.STUDENT_HIRED_AS_TA);
            yaml = EventSource.encodeYaml(events);
            res = Unirest.post(TA_POOL_URL + "/put")
                    .queryString("caller", gb.getSeGroup().getHead())
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
                    .queryString("caller", gb.getSeGroup().getHead())
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
                    .queryString("caller", gb.getSeGroup().getHead())
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
            if (modelItem instanceof SEGroup) {
                SEGroup group = (SEGroup) modelItem;
                new ModifySEGroup(group).showAndWait();
            } else if (modelItem instanceof SEClass) {
                SEClass seClass = (SEClass) modelItem;
                new ModifySEClass(seClass).showAndWait();
            } else if (modelItem instanceof Assignment) {
                Assignment assignment = (Assignment) modelItem;
                new ModifyAssignment(assignment).showAndWait();
            } else if(modelItem instanceof SEStudent) {
                SEStudent student = (SEStudent) modelItem;
                new ModifySEStudent(student).showAndWait();
            } else if(modelItem instanceof Achievement) {
                Achievement achievement = (Achievement) modelItem;
                new ModifyAchievement(achievement).showAndWait();
            } else if(modelItem instanceof  Solution) {
                Solution solution = (Solution) modelItem;
                new ModifySolution(solution).showAndWait();
            }
        }
    }
}
