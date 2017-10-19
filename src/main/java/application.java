import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class application {
    public static File folder = new File("C:\\Java\\GitProjetos\\tasy-backend");
    public static File views = new File("C:\\Users\\romaia\\Desktop\\testes");

    static String temp = "";




    public static void main(String[] args) {

        System.out.println("Reading files under the folder "+ folder.getAbsolutePath());
        ArrayList<String> list = doviewlist();
        listFilesForFolder(folder, list);
    }
    public static ArrayList doviewlist(){
        ArrayList<String> list = new ArrayList();
        for (final File fileEntry : views.listFiles()) {
            if (fileEntry.isFile()) {
                File fileToRead = new File(views.getAbsolutePath() + "\\" + fileEntry.getName());
                try(FileReader fileStream = new FileReader( fileToRead );
                    BufferedReader bufferedReader = new BufferedReader( fileStream ) ) {
                    String line = null;
                    while( (line = bufferedReader.readLine()) != null ) {
                        list.add(line.trim());
                    }
                } catch ( FileNotFoundException ex ) {
                    //exception Handling
                } catch ( IOException ex ) {
                    //exception Handling
                }
            }
        }
        return list;

    }

    public static void readfilebyname(String path, ArrayList list){
        File fileToRead = new File(path);

        try(FileReader fileStream = new FileReader( fileToRead );
            BufferedReader bufferedReader = new BufferedReader( fileStream ) ) {

            String line = null;
            String pattern = "(.*)(\\d+)(.*)";
            Pattern r = Pattern.compile(pattern);
            while( (line = bufferedReader.readLine()) != null ) {
                String finalLine = line;

                if((finalLine.contains("NewRecordHandler"))
                        || finalLine.contains("InsertHandler")
                        || finalLine.contains("UpdateHandler")
                        || finalLine.contains("DeleteHandler")
                        || finalLine.contains("private final int")
                        || finalLine.contains("NR_SEQ_VISAO")

                        )
                {
                    list.stream()
                            .forEach((string) -> {
                                if((finalLine.contains(string.toString()))){
                                    System.out.println("View Found in "+path + "// View: "+string);
                                    System.out.println(finalLine);
                                }
                            });
                }




            }

        } catch ( FileNotFoundException ex ) {
            //exception Handling
        } catch ( IOException ex ) {
            //exception Handling
        }
    }

    public static void listFilesForFolder(final File folder, ArrayList list) {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, list);
            } else {
                if (fileEntry.isFile()) {
                    temp = fileEntry.getName();
                    if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("java"))
                    {
                        //System.out.println("Reading File  " + folder.getAbsolutePath() + "\\" + fileEntry.getName());
                        readfilebyname(folder.getAbsolutePath() + "\\" + fileEntry.getName(), list);

                    }
                }

            }
        }

    }
}
