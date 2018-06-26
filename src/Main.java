import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;



//....................................-------------------------------------------------------------------------


public class Main extends Application 
{	
	ListView<String> list = new ListView<String>();
	final Label label = new Label();
	public static final ObservableList names = FXCollections.observableArrayList();
	public static final ObservableList data = FXCollections.observableArrayList();
	
	
	//....................................-------------------------------------------------------------------------
	
	
	@Override
    public void start(Stage primaryStage) throws IOException 
	{
    	list.setItems(data);
    	label.setLayoutX(500);
        label.setLayoutY(150);

        final ListView listView = new ListView(data);
        listView.setPrefSize(200, 250);
        listView.setEditable(true);
        listView.setLayoutX(10);
        listView.setLayoutY(10);
        
        File file = new File("D:\\£ukasz\\Programowanie\\Eclipse\\Image2\\Katalogi\\katalog3\\");

		// check if the specified pathname is directory first
		if(file.isDirectory())
		{
			//list all files on directory
			File[] files = file.listFiles();
			for(File f:files)
			{
				try 
				{
					System.out.println(f.getCanonicalPath());
				} 
				catch(IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		File[] files = file.listFiles();
        for (File f:files) 
        {
            data.add(f.getCanonicalPath());
        }

        listView.setItems(data);
        label.setText(list.getSelectionModel().getSelectedItem());
        System.out.println(listView.getSelectionModel().getSelectedIndex());
        System.out.println(2);


        final FileChooser fileChooser = new FileChooser();
        
        final Button openButton = new Button("Wyœwietl Obraz");
  		openButton.setLayoutX(230);
  		openButton.setLayoutY(236);
        
        final Button nowykatalog = new Button("Utwórz katalog");
        nowykatalog.setLayoutX(230);
        nowykatalog.setLayoutY(200);
  		
        final Button usun = new Button("Usuñ");
        usun.setLayoutX(230);
        usun.setLayoutY(164);
  		
        final Button kopiuj = new Button("Kopiuj");
        kopiuj.setLayoutX(230);
        kopiuj.setLayoutY(126);
  		
        final Button wytnij = new Button("Wytnij");
        wytnij.setLayoutX(230);
        wytnij.setLayoutY(92);
        
        final Button otworz0 = new Button("Podaj!");
        otworz0.setLayoutX(840);
        otworz0.setLayoutY(50);
        
        final Button otworz1 = new Button("Podaj!");
        otworz1.setLayoutX(840);
        otworz1.setLayoutY(100);
        
        
        Label path0 = new Label("Sciezka do katalogu:");
        path0.setTextFill(Color.WHITE);
        TextField textField0 = new TextField ();
        textField0.setMinWidth(300);
        textField0.setEditable(false);
        HBox hb0 = new HBox();
        hb0.getChildren().addAll(path0, textField0);
        hb0.setSpacing(10);
        hb0.setLayoutX(400);
        hb0.setLayoutY(50);
        
        
        Label path1 = new Label("Sciezka do zdjecia:   ");
        path1.setTextFill(Color.WHITE);
        TextField textField1 = new TextField ();
        textField1.setMinWidth(300);
        textField1.setEditable(false);
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(path1, textField1);
        hb1.setSpacing(10);
        hb1.setLayoutX(400);
        hb1.setLayoutY(100);

        

      		Group root = new Group(listView, openButton, nowykatalog, usun, kopiuj, wytnij, otworz0, otworz1, hb0, hb1, label);
      		Scene scene = new Scene(root,1000,750,Color.DIMGREY);

              //StackPane root = new StackPane();
              //root.getChildren().addAll(openButton, listView);

      		
            
              //Scene scene = new Scene(root, 400, 150);
      	

        openButton.setOnAction(
        	new EventHandler<ActionEvent>() 
        	{
        		@Override
        		public void handle(final ActionEvent e) 
        		{
        			 try
        			 {
						openNewImageWindow(file,primaryStage,openButton,listView, nowykatalog, usun, kopiuj, wytnij, otworz0, otworz1, hb0, hb1);
        			 } 
        			 catch(FileNotFoundException e1) 
        			 {
						e1.printStackTrace();
        			 }
        		}
        	});
        	
/*            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    setExtFilters(fileChooser);
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        try {
							openNewImageWindow(file,primaryStage,openButton,listView);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} ///............
                    }
                }
            }); */
        primaryStage.setTitle("JavaImageDisplayer");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


	
	//....................................-------------------------------------------------------------------------
	

    public static void main(String[] args) 
    {
        launch(args);
    }

    
	//....................................-------------------------------------------------------------------------
    
    
    private void setExtFilters(FileChooser chooser)
    {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
    
    
	//....................................-------------------------------------------------------------------------
    

    private void openNewImageWindow(File file, Stage primaryStage,Button openButton, ListView listView, 
    		Button nowykatalog, Button usun, Button kopiuj, Button wytnij, Button otworz0, Button otworz1,
    		HBox hb0, HBox hb1) throws FileNotFoundException
    {
        Stage secondStage = new Stage();
        /*
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItem_Save = new MenuItem("Save Image");
        menuFile.getItems().addAll(menuItem_Save);
        menuBar.getMenus().addAll(menuFile);
       
        Label name = new Label(file.getAbsolutePath());
        Image image = new Image(file.toURI().toString());
       	*/
        //ImageView imageView = new ImageView();

        /*
        menuItem_Save.setOnAction(new EventHandler<ActionEvent>() {
         

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");

                File file = fileChooser.showSaveDialog(secondStage);
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),
                                null), "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(
                            Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });*/

        
        //final VBox vbox = new VBox();
        //vbox.setAlignment(Pos.CENTER);
        //vbox.setSpacing(10);
        //vbox.setPadding(new Insets(0, 10, 0, 10));
        //vbox.getChildren().addAll(name, imageView);
        
        
        System.out.println(data.get(listView.getSelectionModel().getSelectedIndex()));
        Image image = new Image(new FileInputStream((String) data.get(listView.getSelectionModel().getSelectedIndex())));
		ImageView imageView = new ImageView(image);
		
		String tags ="";
		
		//String source = "D:\\£ukasz\\Programowanie\\Eclipse\\Image2\\Katalogi\\katalog3\\5stp";
		
		File fi = new File((String) data.get(listView.getSelectionModel().getSelectedIndex()));

		tags = Metadane.pobierz_metadane(fi);
        
		TextArea metadane = new TextArea(tags);
		metadane.setLayoutX(10);
		metadane.setLayoutY(300);
		metadane.setEditable(false);
		
		
		Label tagnazwa = new Label("Podaj nazwe Tagu , który chcesz dodac do metadanych zdjecia:   ");
        tagnazwa.setTextFill(Color.WHITE);
        TextField textFieldtagnazwa = new TextField ();
        textFieldtagnazwa.setMinWidth(300);
        VBox vbtagnazwa = new VBox();
        vbtagnazwa.getChildren().addAll(tagnazwa, textFieldtagnazwa);
        vbtagnazwa.setSpacing(10);
        vbtagnazwa.setLayoutX(10);
        vbtagnazwa.setLayoutY(500);
        
		Label tagtresc = new Label("Podaj treœc Tagu , który chcesz dodac do metadanych zdjecia:   ");
		tagtresc.setTextFill(Color.WHITE);
        TextField textFieldtagtresc = new TextField ();
        textFieldtagtresc.setMinWidth(300);
        VBox vbtagtresc = new VBox();
        vbtagtresc.getChildren().addAll(tagtresc, textFieldtagtresc);
        vbtagtresc.setSpacing(10);
        vbtagtresc.setLayoutX(10);
        vbtagtresc.setLayoutY(600);
        
        Button dodajtag = new Button("Dodaj Tag do metadanych");
        dodajtag.setLayoutX(10);
        dodajtag.setLayoutY(700);        
      
        
     
		
		/*
		String source = (String) data.get(listView.getSelectionModel().getSelectedIndex());
		InputStream is = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));

		Metadata metadata = ImageMetadataReader.readMetadata(is);
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        System.out.println(tag);
		    }
		}
		
		*/
		
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setLayoutX(550);
        imageView.setLayoutY(300);

        Group root = new Group(imageView,listView,openButton,label, nowykatalog, usun, kopiuj, wytnij, otworz0, otworz1, hb0, hb1, metadane, vbtagnazwa, vbtagtresc, dodajtag);
  		Scene scene = new Scene(root,1000,750,Color.DIMGREY);
        //((VBox)scene.getRoot()).getChildren().addAll(menuBar, vbox);

        //primaryStage.setTitle(file.getName());
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}