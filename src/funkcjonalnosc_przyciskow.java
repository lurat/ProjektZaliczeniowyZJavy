package application;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;import java.io.OutputStream;import java.io.InputStream; //nowy input
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



public class funkcjonalnosc_przyciskow {
	public  String setButtons(Button openButton, Button otworz0,Button nowykatalog,Button usun,Button kopiuj,Button wytnij, Button otworz1,
							Stage primaryStage, ListView listView, File file,HBox hb0, HBox hb1
							,TextField textField0,TextField textField1,ObservableList data, FileChooser fileChooser,
							DirectoryChooser directoryChooser, String sciezka
							,ObservableList datanames, Label label,ListView list)//
	{
		String Sp=sciezka;
		final String test="off";
	openButton.setOnAction(
        	new EventHandler<ActionEvent>()
        	{
        		@Override
        		public void handle(final ActionEvent e)
        		{

        			 try
        			 {
						Main.openNewImageWindow(file,primaryStage,openButton,listView, nowykatalog, usun, kopiuj, wytnij, otworz0, otworz1, hb0, hb1);
        			 }
        			 catch(FileNotFoundException e1)
        			 {
						e1.printStackTrace();
        			 }
        		     textField1.setText((String)data.get(listView.getSelectionModel().getSelectedIndex()));

        		}
        	});
        otworz0.setOnAction(
          new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {

                    File file2 = directoryChooser.showDialog(primaryStage);
                    //String sciezkaP = null;
                    System.out.println(file2);
                    String sciezkaP = null;
					try {
						sciezkaP = file2.getCanonicalPath();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//System.out.println( (String) data.get(listView.getSelectionModel().getSelectedIndex()));
                    File file21 = new File(sciezkaP);
                    System.out.println(file21);
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
					System.out.println(files);

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
                    textField0.setText(sciezkaP);

                    listView.setItems(datanames);
                    label.setText((String) list.getSelectionModel().getSelectedItem());
                    //System.out.println((String)data.get(listView.getSelectionModel().getSelectedIndex()));
                    //System.out.println(2);

                    }
                }
            );

        nowykatalog.setOnAction(
        		new EventHandler<ActionEvent>(){
        			@Override
                    public void handle(final ActionEvent e) {

        				/*boolean success = (new File(sciezka+"XD")).mkdirs();
        			    if (!success) {
        			        System.out.append("\n Folder nie utworzony bo ju� istnieje :\n");
        			    }
        			    else {
        			        System.out.append("\n Folder utworzony\n");}*/
        				//TODO edycja filechosera
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
        			            File file = new File(Sp);

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
        		new EventHandler<ActionEvent>(){
        			@Override
                    public void handle(final ActionEvent e) {
        					InputStream inStream = null;
                            OutputStream outStream = null;
                            File afile = new File( (String) data.get(listView.getSelectionModel().getSelectedIndex()));
            				File file4 = fileChooser.showOpenDialog(primaryStage);
                            System.out.println(file4);
                            String sciezka13=null;
                            File bfile=null;System.out.println(!file4.exists());
                            if(!file4.exists()){
                            	try{
                            	file4.createNewFile();}
                            	catch(IOException ea){ea.printStackTrace();}
        					}
                         try{
                        	 sciezka13=file4.getCanonicalPath();
     						bfile = new File(sciezka13);
                             inStream = new FileInputStream(afile);
                             outStream = new FileOutputStream(bfile);
                             byte[] buffer = new byte[1024];
                             //int length;
                             //length=
                             //copy the file content in bytes while ((length = inStream.read(buffer)) > 0){
                              outStream.write(buffer);
                             inStream.close();
                             outStream.close();
                             System.out.println("Plik zostal skopiowany!");
                         }catch(IOException e3){
                             e3.printStackTrace();
                         }
        			    //TODO eddycja filechoosera

        		}
        		}

        		);

        wytnij.setOnAction(
        		new EventHandler<ActionEvent>(){
        			@Override
                    public void handle(final ActionEvent e) {
        			// YOUR CODE HERE KAMIL
        			//TODO ctr+c, ctr+v kopiowanie + usuwanie
        		}
        		}

        		);
        otworz1.setOnAction(
        		new EventHandler<ActionEvent>(){
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

        System.out.println(sciezka);

        return Sp;
	}
}
