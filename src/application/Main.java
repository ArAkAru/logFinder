package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;



public class Main extends Application {
	private BorderPane borderpane;
    private TextFinder textFinder;
    private Installer installer;
   

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
    	installer=new Installer();
    	borderpane= new BorderPane();
        textFinder =new TextFinder();
    	MenuBar menuBar = new MenuBar();
    	Menu fileMenu = new Menu("Файл");
        Menu expansionMenu = new Menu("Расширение");
        MenuItem fileItem = new MenuItem("Открыть файл");
        MenuItem expansionItem = new MenuItem("Сменить расширение");
        fileMenu.getItems().addAll(fileItem);
        expansionMenu.getItems().addAll(expansionItem);
        menuBar.getMenus().addAll(fileMenu,  expansionMenu);
        //////////////////////////////////////////////
        installer.setup(stage, textFinder);
        borderpane.setTop(menuBar);
      
        fileItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	installer.chooseFolder(actionEvent);
            	
            	borderpane.setLeft(installer.vbox);
            	
            	borderpane.setCenter(installer.tabPane);
            }
        });
        expansionItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	installer.chooseExpansion(actionEvent);
            	
            }
        });
        Scene scene = new Scene(borderpane, 700, 700);
        stage.setTitle("JavaFX Menu");
        stage.setScene(scene);
        stage.show();
        

    }
}

