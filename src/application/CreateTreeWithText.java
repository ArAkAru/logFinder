package application;
import application.FilePath;
import javafx.scene.control.TreeItem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  CreateTreeWithText {
	
    void createNewRoot(TreeItem<FilePath> root, String text, TreeItem<FilePath> newRoot) throws IOException {
		
    	for (TreeItem<FilePath> child : root.getChildren()) {
    		
            TreeItem<FilePath> newChild = new TreeItem<>(child.getValue());
           
            newChild.setExpanded(true);

            createNewRoot(child, text, newChild);
            if (!newChild.getChildren().isEmpty() || check(newChild.getValue(), text)) {
            	newRoot.getChildren().add(newChild);
            }
        }
//    	for (TreeItem<FilePath> child : root.getChildren()) {
//    		//System.out.println(child);
//            //TreeItem<FilePath> newChild = new TreeItem<>(child.getValue());
//            //System.out.println(newChild);
//            //filteredChild.setExpanded(true);
//
//            filter(child, newChild);
//            //System.out.println("zzzzz");
//            //System.out.println(!newChild.getChildren().isEmpty());
//            if (isMatch(child.getValue(), filter)) {
//                newChild.getChildren().add(child);
//            }
//        }
    	//System.out.println(root.getChildren());
    	//System.out.println(filteredRoot.getChildren());
    }

   boolean check(FilePath value, String text) throws IOException {
    	boolean find=false;
    	if (value.getPath().toFile().isFile()) {
        	if (value.getPath().toFile()!=null) {
        		String name = value.getPath().toFile().getName();
                int index = name.lastIndexOf('.');
                name=name.substring(index + 1);
                if(name.equals(Installer.ROOTEXPANSION)) {
//                	List<String> lines = Files.readAllLines(value.getPath());
//                	Pattern word = Pattern.compile(filter.toLowerCase());
//                	Matcher matcher = word.matcher(lines.toString());
//                	find=matcher.find();
                	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(value.getPath().toFile())));
                    Pattern word = Pattern.compile(text.toLowerCase());
                     find=br.lines().map(String::toLowerCase).anyMatch(s -> {
                       Matcher match = word.matcher(s);
                       boolean varcheck=match.find();
                       return varcheck;
                    });
                	
                }
        	}
        }
        return find;
    }


    public void getFindingTextPositions(FilePath file, String findingText, ArrayList<Integer> positions) {
      
    	String text = getText(file).toLowerCase();//считывание тексат из файла
    	Pattern word = Pattern.compile(findingText.toLowerCase());
        Matcher match = word.matcher(text);
        while (match.find()) {
            positions.add(match.start());
        }
       
    }

    public String getText(FilePath path) {
        String outString = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path.getPath().toFile()))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            outString = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outString;
    }
}


