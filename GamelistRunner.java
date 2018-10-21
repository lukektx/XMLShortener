import java.util.Scanner;
class GamelistRunner{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		System.out.println("Input the file path of the collection folder (use \\\\ for a \\)");
		String path = in.nextLine();
		System.out.println("Input name of the current master gamelist for this collection");
		String masterFileName = in.nextLine();
		System.out.println("Input the desired output file name");
		String outFile = in.nextLine();
		System.out.println("Do you want to input the files from a list file, the collection list file, or from scanning the directory? (1, 2, or 3)");
		int inpType = in.nextInt();
		String fileName = "";
		Gamelist gamelist = new Gamelist(path, masterFileName, outFile);
		switch(inpType){
			case 1:
				System.out.println("Input the desired list file name");
				in.nextLine();
				fileName = in.nextLine();
				gamelist.setFileName(fileName);
				gamelist.fromListFile();
				break;
			case 2:
				System.out.println("Input the desired collection list file name");
				in.nextLine();
				fileName = in.nextLine();
				gamelist.setFileName(fileName);
				gamelist.fromCollectionFile();
				break;
			case 3:
				gamelist.fromDirectory();
				break;
			default:
				System.out.println("Please restart the program and input 1, 2, or 3 for the input type");
				break;
		}
		gamelist.readMasterGamelist();
		gamelist.findSections();
		gamelist.writeOut();
		System.out.println("The gamelist has been optimized");
	}
}