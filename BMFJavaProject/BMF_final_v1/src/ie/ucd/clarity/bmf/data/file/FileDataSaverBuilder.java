package ie.ucd.clarity.bmf.data.file;

import ie.ucd.clarity.bmf.data.IDataSaver;

public class FileDataSaverBuilder implements IFileDataSaverBuilder {

	@Override
	public IDataSaver getIDataSaverInstance() {
		return new FileDataSaver();
	}

}