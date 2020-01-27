package application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;




public class Installer {
	static String ROOTFOLDER = "";
    static String ROOTEXPANSION= "log";
    TextFinder textFinder;
    VBox vbox;
    Stage mystage;
    TabPane tabPane;
    TextField textField;
    public void setup(Stage stage,TextFinder textFinder ) {
    	mystage=stage;
    	this.textFinder=textFinder;
    }
   
    public void chooseFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(mystage);
        if (dir != null) {
        	ROOTFOLDER=dir.getPath();
        	vbox=textFinder.init();
        	textFinder.getTree();
        	tabPane= textFinder.tabPane;
           }
    }

   
    public void chooseExpansion(ActionEvent actionEvent){
        
    	Label lbl = new Label();
    	textField = new TextField();
    	Button btn = new Button("Сменить");
    	Stage mystage2=new Stage();
    	btn.setOnAction(event -> {
    		System.out.println(ROOTEXPANSION);
    		ROOTEXPANSION=textField.getText();System.out.println(ROOTEXPANSION);
    		mystage2.close();
    		
        	//textFinder.getTree();
        	});
    	FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, textField, btn, lbl);
        Scene scene = new Scene(root, 250, 200);
      
        mystage2.setScene(scene);
        mystage2.setTitle("Выбор Расширения");
        mystage2.show();
        
     }
}

