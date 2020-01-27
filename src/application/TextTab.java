package application;

import application.FilePath;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;




class TextTab extends Tab {

    private FilePath filePath;
    private ArrayList<Integer> positions;
    private int currentPosition;
    private TextArea area;
    private String findingText;
    private AnchorPane anchorPane;
    VBox vboxTextPane;
    TextArea area2;
    TextArea area3;

    TextTab(FilePath filePath, String findingText) {
    	//System.out.println(filePath);
        super.setText(filePath.toString());//имя вкладки
        
        this.filePath = filePath;//название файла
        this.positions = new ArrayList<>();
        this.currentPosition = 0;
        this.findingText = findingText;
    }

    TextTab initialize() {
    	
        new Thread(() -> new CreateTreeWithText().getFindingTextPositions(filePath, findingText, positions)).start();

        vboxTextPane = new VBox();

        anchorPane = new AnchorPane();
        createAndSetSwingContent(anchorPane);

        VBox.setVgrow(anchorPane, Priority.ALWAYS);
        Button nextButton = new Button("Cледующий");
        Button prevButton = new Button("Предыдущий");
        Button selectButton = new Button("Выбрать все");
        
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> nextPos());
        prevButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> prevPos());
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectText();
            area.setFocusTraversable(true);
        });

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(prevButton,nextButton,selectButton);

        vboxTextPane.getChildren().addAll(toolBar, anchorPane);
        this.setContent(vboxTextPane);
        return this;
    }
    private void selectText() {
    	area3=new TextArea();
    	area3.setText(area.getText());
//    	anchorPane.setFocusTraversable(true);
//    	anchorPane.requestFocus();
//      area3.requestFocus();
        //area.grabFocus();
        area3.selectAll();
        area3.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: gray; -fx-font-size: 12px;");
        anchorPane.getChildren().add(area3);
        AnchorPane.setTopAnchor(area3, 0.0);
        AnchorPane.setLeftAnchor(area3, 0.0);
        AnchorPane.setRightAnchor(area3, 0.0);
        AnchorPane.setBottomAnchor(area3, 0.0);
    }
    private void nextPos() {
        this.area.positionCaret(getNextPos());
    }
    private void prevPos() {
        this.area.positionCaret(getPrevPos());
    }
    private int getNextPos() {
        if (this.currentPosition == this.positions.size() - 1) {
            this.currentPosition = 0;
        } else {
            this.currentPosition++;
        }
        highLightWord();
        
        //System.out.println(positions.get(this.currentPosition));
        return this.positions.get(this.currentPosition);
    }
    private int getCurrPos() {
        return this.positions.get(this.currentPosition);
    }
    private void highLightWord() {
    	area2=new TextArea();
    	area2.setText(area.getText());
    	int p0 = getCurrPos();
        int p1 = p0 + this.findingText.length();
        //final TextArea text = new TextArea("Here is some textz to highlight");
        area2.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: green; -fx-font-size: 12px;");
        area2.setEditable(false);
        area2.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent t) { t.consume(); }
        });
        area2.selectRange(p0, p1);
        anchorPane.getChildren().add(area2);
        AnchorPane.setTopAnchor(area2, 0.0);
        AnchorPane.setLeftAnchor(area2, 0.0);
        AnchorPane.setRightAnchor(area2, 0.0);
        AnchorPane.setBottomAnchor(area2, 0.0);
        System.out.println(p0+" "+p1);
        
    }
    private int getPrevPos() {
        if (this.currentPosition == 0) {
            this.currentPosition = this.positions.size() - 1;
        } else {
            this.currentPosition--;
        }
        highLightWord();
        //System.out.println(positions.get(this.currentPosition));
        return this.positions.get(this.currentPosition);
    }
    private void createAndSetSwingContent(final AnchorPane  anchorPane ) {
        Platform.runLater(()->{
        	area = new TextArea();
            area.setEditable(false);
            area.setText(new String(new CreateTreeWithText().getText(filePath).getBytes(), StandardCharsets.UTF_8));
            anchorPane.getChildren().add(area);
            AnchorPane.setTopAnchor(area, 0.0);
            AnchorPane.setLeftAnchor(area, 0.0);
            AnchorPane.setRightAnchor(area, 0.0);
            AnchorPane.setBottomAnchor(area, 0.0);
            highLightWord();
        });
    	
    
    }
}
