package ie.ucd.clarity.bmf.data.file;

import java.io.IOException;

import ie.ucd.clarity.bmf.data.IDataSaver;

public interface IFileDataSaver extends IDataSaver {

	public boolean openConnection(String fileNamePath) throws IOException;
	
}
