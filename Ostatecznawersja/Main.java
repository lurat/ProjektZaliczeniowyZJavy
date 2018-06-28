package application;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;//KAMIL biblioteki
import java.io.PrintWriter;
import java.nio.file.Files;
import java.io.InputStream;import javax.swing.JOptionPane;//KAMIL biblioteki
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
import javafx.stage.DirectoryChooser;
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
	public static final ObservableList datanames = FXCollections.observableArrayList();

	//....................................-------------------------------------------------------------------------
	String sciezka_jako_nazwa_pliku;
	String temp_nazwa;
	String temp_nazwa_txt;
	final String[] tags = {""};
	TextArea metadane = new TextArea(tags[0]);
	
	String sciezka = "D:\\Image\\"; // sztywna poczatkowa
	String test="off";
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

        File file = new File(sciezka);

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
            datanames.add(f.getName());
        }

        listView.setItems(datanames);
        label.setText(list.getSelectionModel().getSelectedItem());
        System.out.println(listView.getSelectionModel().getSelectedIndex());
        System.out.println(2);


        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        final Button openButton = new Button("Wy�wietl Obraz");
  		openButton.setLayoutX(230);
  		openButton.setLayoutY(236);

        final Button nowykatalog = new Button("Utw�rz katalog");
        nowykatalog.setLayoutX(230);
        nowykatalog.setLayoutY(200);

        final Button usun = new Button("Usu�");
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

        final Button otworz1 = new Button("Wczytaj nowe zdjecie!");
        otworz1.setLayoutX(840);
        otworz1.setLayoutY(200);


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
        		     textField1.setText((String)data.get(listView.getSelectionModel().getSelectedIndex()));
        		     temp_nazwa = (String)data.get(listView.getSelectionModel().getSelectedIndex());
        		     temp_nazwa_txt = temp_nazwa + ".txt";
        		     System.out.println(temp_nazwa);
        		     System.out.println(temp_nazwa_txt);
         			 File f = new File(temp_nazwa);
         			 System.out.println(f.getName());
         			 sciezka_jako_nazwa_pliku = f.getName();
         			 System.out.println(sciezka_jako_nazwa_pliku);

         			 File fff = new File(temp_nazwa_txt);
        			 if(fff.exists() && !fff.isDirectory())
        			 {
        				try
        				{
        					System.out.println(readFile(temp_nazwa_txt));
        					metadane.setText(readFile(temp_nazwa_txt));
                        }
        				catch (IOException e33)
        				{
        					// TODO Auto-generated catch block
                            e33.printStackTrace();
                        }
        			 }
        		}
        	});
        otworz0.setOnAction(
          new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {

                    File file2 = directoryChooser.showDialog(primaryStage);
                    System.out.println(file2);
                    try {
						sciezka=file2.getCanonicalPath();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    File file21 = new File(sciezka);
                    datanames.removeAll(datanames);
                    data.removeAll(data);
            		// check if the specified pathname is directory first
            		if(file21.isDirectory())
            		{
            			//list all files on directory
            			File[] files = file21.listFiles();
            			for(File f:files)
            			{
            				try
            				{
            					System.out.println(f.getCanonicalPath());
            				}
            				catch(IOException e1)
            				{
            					e1.printStackTrace();
            				}
            			}
            		}
            		File[] files = file21.listFiles();
                    for (File f:files)
                    {
                        try {
							data.add(f.getCanonicalPath());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        datanames.add(f.getName());
                    }
                    textField0.setText(sciezka);

                    listView.setItems(datanames);
                    label.setText(list.getSelectionModel().getSelectedItem());
                    System.out.println((String)data.get(listView.getSelectionModel().getSelectedIndex()));
                    System.out.println(2);

                    }
                }
            );

        nowykatalog.setOnAction(
        		new EventHandler<ActionEvent>(){//KAMIL przycisk
        			@Override
                    public void handle(final ActionEvent e) {
        				String nazwa = JOptionPane.showInputDialog("Podaj nazwe katalogu");
        				boolean success = (new File(sciezka+nazwa)).mkdirs();
        			    if (!success) {
        			        System.out.append("\n Folder nie utworzony bo ju� istnieje :\n");
        			    }
        			    else {
        			        System.out.append("\n Folder utworzony\n");
        			        File file = new File(sciezka);

    			    		File[] files = file.listFiles();
    			    		datanames.removeAll(datanames);
    			            data.removeAll(data);
    			            for (File f:files)
    			            {
    			                try {
    			    				data.add(f.getCanonicalPath());
    			    			} catch (IOException e1) {
    			    				// TODO Auto-generated catch block
    			    				e1.printStackTrace();
    			    			}
    			                datanames.add(f.getName());
    			            }

    			            listView.setItems(datanames);}
        		}
        		}

        		);

        usun.setOnAction(
        		new EventHandler<ActionEvent>(){//KAMIL przycisk
        			@Override
                    public void handle(final ActionEvent e) {
        				try{
                			File filele = new File((String) data.get(listView.getSelectionModel().getSelectedIndex()));
        			        if(filele.delete()){
        			            System.out.println(filele.getName() + " zostal skasowany!");
        			            File file = new File(sciezka);

        			    		File[] files = file.listFiles();
        			    		datanames.removeAll(datanames);
        			            data.removeAll(data);
        			            for (File f:files)
        			            {
        			                try {
        			    				data.add(f.getCanonicalPath());
        			    			} catch (IOException e1) {
        			    				// TODO Auto-generated catch block
        			    				e1.printStackTrace();
        			    			}
        			                datanames.add(f.getName());
        			            }

        			            listView.setItems(datanames);
        			        }else{
        			            System.out.println("Operacja kasowania sie nie powiodla.");
        			        }

        			    }catch(Exception e2){

        			        e2.printStackTrace();

        			    }
        		}
        		}

        		);

        kopiuj.setOnAction(
        		new EventHandler<ActionEvent>(){//KAMIL przycisk
        			@Override
                    public void handle(final ActionEvent e) {
        					InputStream inStream = null;
                            OutputStream outStream = null;
            				String sciezka13=null;
                            File afile = new File((String) data.get(listView.getSelectionModel().getSelectedIndex()));
            				File file4 = directoryChooser.showDialog(primaryStage);
            				sciezka13=file4.getPath();
                            System.out.println(sciezka13);
    			            System.out.println(sciezka13+afile.getName());
    			            File bfile=new File(sciezka13+afile.getName());
                            if(!bfile.exists()){
                            	try{
                            	bfile.createNewFile();}
                            	catch(IOException ea){ea.printStackTrace();}
        					}
                         try{
                             inStream = new FileInputStream(afile);
                             outStream = new FileOutputStream(bfile);
                             byte[] buffer = new byte[8192];
                             int length;
                             while ((length = inStream.read(buffer)) > 0){
                                 outStream.write(buffer, 0, length);
                             }
                             inStream.close();
                             outStream.close();
                             System.out.println("Plik zostal skopiowany!");
                         }catch(IOException e3){
                             e3.printStackTrace();
                         }

        		}
        		}

        		);

        wytnij.setOnAction(
        		new EventHandler<ActionEvent>(){//KAMIL przycisk
        			@Override
                    public void handle(final ActionEvent e) {
        				InputStream inStream = null;
                        OutputStream outStream = null;
        				String sciezka13=null;
                        File afile = new File((String) data.get(listView.getSelectionModel().getSelectedIndex()));
        				File file4 = directoryChooser.showDialog(primaryStage);
        				sciezka13=file4.getPath();
                        System.out.println(sciezka13);
			            System.out.println(sciezka13+afile.getName());
			            File bfile=new File(sciezka13+afile.getName());
                        if(!bfile.exists()){
                        	try{
                        	bfile.createNewFile();}
                        	catch(IOException ea){ea.printStackTrace();}
    					}
                     try{
                         inStream = new FileInputStream(afile);
                         outStream = new FileOutputStream(bfile);
                         byte[] buffer = new byte[8192];
                         int length;
                         while ((length = inStream.read(buffer)) > 0){
                             outStream.write(buffer, 0, length);
                         }
                         inStream.close();
                         outStream.close();
                         System.out.println("Plik zostal przeniesiony!");
                     }catch(IOException e3){
                         e3.printStackTrace();
                     }
                     try{
     			        if(afile.delete()){
     			            System.out.println(afile.getName() + " zostal skasowany!");
     			            File file = new File(sciezka);
     			    		File[] files = file.listFiles();
     			    		datanames.removeAll(datanames);
     			            data.removeAll(data);
     			            for (File f:files)
     			            {
     			                try {
     			    				data.add(f.getCanonicalPath());
     			    			} catch (IOException e1) {
     			    				// TODO Auto-generated catch block
     			    				e1.printStackTrace();
     			    			}
     			                datanames.add(f.getName());
     			            }

     			            listView.setItems(datanames);
     			        }else{
     			            System.out.println("Operacja kasowania sie nie powiodla.");
     			        }

     			    }catch(Exception e2){

     			        e2.printStackTrace();

     			    }
        		}
        		}

        		);
        otworz1.setOnAction(
        		new EventHandler<ActionEvent>(){//KAMIL przycisk
        			@Override
                    public void handle(final ActionEvent e) {
        				File file4 = fileChooser.showOpenDialog(primaryStage);
                        System.out.println(file4);
                        try {
    						sciezka=file4.getCanonicalPath();
    					} catch (IOException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
                       File file21 = new File(sciezka);
                     try
           			 {
                    	 test="on";
   						openNewImageWindow(file21,primaryStage,openButton,listView, nowykatalog, usun, kopiuj, wytnij, otworz0, otworz1, hb0, hb1);
           			 }
           			 catch(FileNotFoundException e1)
           			 {
   						e1.printStackTrace();
           			 }

        		}
        		}

        		);






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

    private void setDirectoryChooser(DirectoryChooser chooser)
    {


    }


    private void setExtFilters(FileChooser chooser)
    {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }


	//....................................-------------------------------------------------------------------------
    private static void zapiszPlik(String plik, String tekst){

        try (PrintWriter pw = new PrintWriter(plik)){
            pw.write(tekst);
        } catch (FileNotFoundException ex){
            System.out.println(ex);
        }
    }

public String readFile(String filePath) throws IOException {
    FileReader fileReader = new FileReader(filePath);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    String textLine = bufferedReader.readLine();
    String zwrot=textLine;

    do
    {
      System.out.println(textLine);

      textLine = bufferedReader.readLine();
      zwrot=zwrot + textLine;
    } while(textLine != null);

    bufferedReader.close();
    System.out.println(zwrot);
    return zwrot;
  }

    private void openNewImageWindow(File file, Stage primaryStage,Button openButton, ListView listView,
    		Button nowykatalog, Button usun, Button kopiuj, Button wytnij, Button otworz0, Button otworz1,
    		HBox hb0, HBox hb1) throws FileNotFoundException
    {


        Stage secondStage = new Stage();
      //KAMIL if
        //System.out.println(data.get(listView.getSelectionModel().getSelectedIndex()));
        //System.out.println(listView.getSelectionModel().getSelectedIndex());
        //System.out.println(2);
        File fi=null;
        String tags2=null;
        ImageView imageView=null;
        Image image=null;
        if(test=="on"){
        label.setText(list.getSelectionModel().getSelectedItem());
        image = new Image(new FileInputStream(file));
		imageView = new ImageView(image);
		tags2 ="";
		fi =  file;
		test="off";}
        else{
        	label.setText(list.getSelectionModel().getSelectedItem());
            image = new Image(new FileInputStream((String) data.get(listView.getSelectionModel().getSelectedIndex())));
    		imageView = new ImageView(image);
    		tags2 ="";
    		fi = new File((String) data.get(listView.getSelectionModel().getSelectedIndex()));
        }

        tags[0] = Metadane.pobierz_metadane(fi);

		TextArea metadane = new TextArea(tags[0]);
		metadane.setLayoutX(10);
		metadane.setLayoutY(300);
		metadane.setEditable(false);


		Label tagnazwa = new Label("Podaj nazwe Tagu , kt�ry chcesz dodac do metadanych zdjecia:   ");
        tagnazwa.setTextFill(Color.WHITE);
        TextField textFieldtagnazwa = new TextField ();
        textFieldtagnazwa.setMinWidth(300);
        VBox vbtagnazwa = new VBox();
        vbtagnazwa.getChildren().addAll(tagnazwa, textFieldtagnazwa);
        vbtagnazwa.setSpacing(10);
        vbtagnazwa.setLayoutX(10);
        vbtagnazwa.setLayoutY(500);

		Label tagtresc = new Label("Podaj tre�c Tagu , kt�ry chcesz dodac do metadanych zdjecia:   ");
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


        dodajtag.setOnAction(
        		new EventHandler<ActionEvent>(){
        			@Override
                    public void handle(final ActionEvent e) {
        				String tags_nazwa;
            			String tags_tresc;
            			String tags_suma;
            			String nazwa_pliku_txt;

            			tags_nazwa = textFieldtagnazwa.getText();
            			tags_tresc = textFieldtagtresc.getText();
            			tags_suma = "Dodatkowy tag: " + tags_nazwa + " - " + tags_tresc + "\n";
            			tags[0] =  tags[0] + tags_suma;
            			nazwa_pliku_txt = sciezka_jako_nazwa_pliku + ".txt";

            			zapiszPlik(nazwa_pliku_txt , tags[0]);
            			metadane.setText(tags[0]);
            			textFieldtagnazwa.setText("");
            			textFieldtagtresc.setText("");
        		}
        		}

        		);



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