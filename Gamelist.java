import java.io.*;
import java.util.*;
public class Gamelist {
	private boolean fromFile;
	private String path, fileName, masterFileName, outFile;
	private List<String> mainLines = new ArrayList<>();
	private List<Integer> goodNums = new ArrayList<>();
	private List<String> files = new ArrayList<>();
	public Gamelist(){
		path = "";
		fileName = "";
		masterFileName = "";
		outFile = "";
		fromFile = false;
	}
	public Gamelist(String path, String masterFileName, String outFile){
		this.path = path;
		this.masterFileName = masterFileName;
		this.outFile = outFile;
	}
	public Gamelist(String path, String fileName, String masterFileName, String outFile){
		this.path = path;
		this.fileName = fileName;
		this.masterFileName = masterFileName;
		this.outFile = outFile;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
		fromFile = true;
	}
	public void fromListFile(){
		BufferedReader wantGames = null;
		try {
			File file = new File(path + "\\" + fileName);
			wantGames = new BufferedReader(new FileReader(file));
			String line;
			while ((line = wantGames.readLine()) != null) {
				files.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wantGames.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void fromCollectionFile(){
		BufferedReader wantGames = null;
		try {
			File file = new File(path + "\\" + fileName);
			wantGames = new BufferedReader(new FileReader(file));
			String line;
			while ((line = wantGames.readLine()) != null) {
				if(line.contains(";arcade")){
					files.add(line.substring(0, line.length() - 7) + ".zip");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wantGames.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < files.size(); i++){
			System.out.println(files.get(i));
		}
	}
	public void fromDirectory(){
		File directory = new File(path);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList){
			if (file.isFile() && file.getName().contains(".zip")){
				System.out.println("Found ROM: " + file.getName());
				files.add(file.getName());
			}
		}
		for(int i = 0; i < files.size(); i++){
			System.out.println(files.get(i));
		}
	}
	public void readMasterGamelist(){
		BufferedReader allGames = null;
		try {
			File file = new File(path + "\\" + masterFileName);
			allGames = new BufferedReader(new FileReader(file));
			String line2;
			while ((line2 = allGames.readLine()) != null) {
				mainLines.add(line2);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				allGames.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void findSections(){
		for (int i = 0; i < mainLines.size() - 1; i++) {
			if (mainLines.get(i).length() > 5) {
				if(mainLines.get(i).substring(2, 6).equals("<pat")) {
					for (int j = 0; j < files.size(); j++) {
						if (mainLines.get(i).contains("./" + files.get(j))) {
							boolean findEnd = false;
							int k = i;
							int l = i;
							while(!mainLines.get(k).contains("<game")){
								k--;
							}
							goodNums.add(k);
							System.out.println("Found opening tag at " + k);
							findEnd = true;
							if(findEnd == true){
								while(!mainLines.get(l).contains("</game")){
									l++;
								}
								goodNums.add(l);
								System.out.println("Found closing tag at " + l);
								findEnd = false;
							}
						}
					}
				}
			}
		}
		System.out.println("Expected number of sections " + files.size() + ", number of found sections " + goodNums.size() / 2);
	}
	public void writeOut(){
		BufferedWriter bw = null;
		int startLine, endLine;
		try {
			String filePath = path + "\\" + outFile;
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				}
				FileWriter fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				for(int i = 0; i < goodNums.size() / 2; i++){
					startLine = goodNums.get(2 * i);
					endLine = goodNums.get(2 * i + 1);
					for(int k = startLine; k < endLine + 1; k++){
						bw.write(mainLines.get(k) + "\n");
					}
				}
				System.out.println("File written Successfully");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally
		{ 
		   try{
			  if(bw!=null)
			 bw.close();
		   }catch(Exception ex){
			   System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
	}
}		