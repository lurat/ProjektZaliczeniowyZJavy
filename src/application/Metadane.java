package application;
import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class Metadane {

	public static String pobierz_metadane(File file) {

		String tags = "";

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {


                for (Tag tag : directory.getTags())
                {
                	tags = tags + tag + "\n";
                }
                return tags;
            }
        }
        catch (ImageProcessingException e)
        {
        	//System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
        	//System.out.println(e.getMessage());
        }
        return tags;
	}


	//------------------------------------------------------------------------------------------


	public void dodaj_tag(File file); {


	}



}