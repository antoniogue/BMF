package ie.ucd.clarity.bmf.configurationfile;

import ie.ucd.clarity.bmf.configurationfile.IConfigurationFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ConfigurationFileReader implements IConfigurationFileReader {

	public boolean readConfigurationFile() {

		try {
			File doc = new File("BMFconfiguration.cfg");
			FileInputStream fis = new FileInputStream(doc);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linea = br.readLine();
			while (linea != null) {
				StringTokenizer st = new StringTokenizer(linea, "=");
				String var = st.nextToken();
				if (var.equalsIgnoreCase("WORK_DIRECTORY")) {
					System.setProperty("WORK_DIRECTORY", st.nextToken());
				} else if (var.equalsIgnoreCase("SUNSPOT_BS_PORT")) {
					System.setProperty("SUNSPOT_BS_PORT", st.nextToken());
				} else if (var.equalsIgnoreCase("SUNSPOT_BS_SPEED")) {
					System.setProperty("SUNSPOT_BS_SPEED", st.nextToken());
				} else if (var.equalsIgnoreCase("TELOSB_BS_PORT")) {
					System.setProperty("TELOSB_BS_PORT", st.nextToken());
				} else if (var.equalsIgnoreCase("TELOSB_BS_SPEED")) {
					System.setProperty("TELOSB_BS_SPEED", st.nextToken());
				}else if (var.equalsIgnoreCase("MAX_GROUPS")) {
					System.setProperty("MAX_GROUPS", st.nextToken());
				}
				linea = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CONFIGURATION FILE READING SUCCESFULL");
		return true;
	}

}
