package application;
import application.TextTab;
import application.FilePath;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TextFinder {
	private TreeView<FilePath> treeView;
    private TreeItem<FilePath> rootFolder;
    private TextField textfield;
    private VBox vBox;
    TabPane tabPane;
    public VBox init() {
    	tabPane=new TabPane();
    	vBox=new VBox();
    	treeView= new TreeView<>();
    	textfield = new TextField();

    	textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            treeWithText(newValue);
        });
    	vBox.getChildren().addAll(treeView,textfield);
    	
        MultipleSelectionModel<TreeItem<FilePath>> selectionModel = treeView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<FilePath>>(){
            
            public void changed(ObservableValue<? extends TreeItem<FilePath>> changed, 
                                TreeItem<FilePath> oldValue, TreeItem<FilePath> newValue){
                  
            	openFiles(newValue);
            }
        });
        
        return vBox;
    }			//модель выбора элементов // вначале надо получить прослушиваемое свойство 
    
    void getTree() {
    	rootFolder = new TreeItem<>(new FilePath(Paths.get((Installer.ROOTFOLDER))));
    	getTree(rootFolder);
        treeView.setRoot(rootFolder);
    }
    void getTree(TreeItem<FilePath> rootFolder) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootFolder.getValue().getPath())) {

            for (Path path : directoryStream) {//заход в каждую папку 
            	TreeItem<FilePath> newItem = new TreeItem<>(new FilePath(path));//каждый новый файл добавляем как потомка
                newItem.setExpanded(true);
                rootFolder.getChildren().add(newItem);//каждый новый файл добавляем как потомка

                if (Files.isDirectory(path)) {//если папка- рекурсия 
                	getTree(newItem);
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }
    
    void treeWithText(String text) {
    	
        TreeItem<FilePath> newRoot = new TreeItem<>(new FilePath(Paths.get(Installer.ROOTFOLDER)));//ikj
       
        CreateTreeWithText newTree = new  CreateTreeWithText();
        
        
        Runnable r = ()->{
            if (text.isEmpty()) {
            	Platform.runLater(() -> treeView.setRoot(rootFolder));
            }
            else{
               try {
            	   newTree.createNewRoot(rootFolder,text,newRoot);
            	   Platform.runLater(() -> treeView.setRoot(newRoot));
				} 
               catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
                
            
        };
        Thread myThread = new Thread(r);
        myThread.start();
        
    }
   
   void openFiles(TreeItem<FilePath> path) {
    	
    	if (path != null) {
            if (path.getValue().getPath().toFile().isFile()) {
            	//System.out.println(textfield.getText());
            	TextTab tab = new TextTab(path.getValue(), textfield.getText());
                tabPane.getTabs().add(tab.initialize());
            }
        }
    }

    
}
