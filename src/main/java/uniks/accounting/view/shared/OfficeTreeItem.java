package uniks.accounting.view.shared;

import javafx.scene.control.TreeItem;

import java.util.UUID;

public class OfficeTreeItem extends TreeItem<String> {
    
    private String id;
    
    public OfficeTreeItem(String value) {
        super(value);
        this.id = UUID.randomUUID().toString();
    }
    
    public String getId() {
        return id;
    }
}
