import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.*;
import java.math.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    private static Converter con;

    @BeforeAll
    private static void initialize() {
        con = new Converter();
    }

   @Test
    public void testConvertToXML() {
        assertTrue(con.convertToXML("sample.csv","finalOutput.xml"));

        File file = new File("finalOutput.xml");
        if(!(file.exists())){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            assertTrue(compareFiles("testXML.xml", "finalOutput.xml"));
        }

        File file2 = new File("file2.xml");

        if(!(file2.exists())){
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("File2 exists");
            assertFalse(compareFiles("src/testingFiles/file2.xml","finalOutput.xml"));
        }


    }

    @Test
    public void testFileFormatWrong(){
        assertFalse(con.convertToXML("testingFiles/textFile.txt","testingFiles/txtToXML.xml"));
    }

    @Test
    public void testFileIsEmpty(){
        assertFalse(con.convertToXML("testingFiles/emptyCSV.csv","testingFiles/emptyCSVtoXML.xml"));
    }

    @Test
    public void testFileDoesNotExist(){
        try{
            con.convertToXML("abc.csv","abc.xml");
        } catch(Exception e){
            if( !(e instanceof FileNotFoundException)){
                fail("File Not found exception should be thrown");
            }
        }
    }

    @Test
    public void testEmptyFieldInRecord(){
        assertTrue(con.convertToXML("testingFiles/file2.csv","src/testingFiles/file2.xml"));
    }

    @Test
    public void testHeaderNoValue(){
        assertTrue(con.convertToXML("testingFiles/file3.csv","src/testingFiles/file3.xml"));
    }


    private boolean compareFiles(String expectedFile, String actualFile) {
        return (calculateHash(convertToString(expectedFile)).equals(calculateHash(convertToString(actualFile))));
    }

    private String convertToString(String fileName) {

        StringBuilder sb = new StringBuilder("");
        try {
            File f = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            String line = br.readLine();
            while (line != null) {
                sb.append(line + "/n");
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String calculateHash(String a){
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(a.getBytes(),0,a.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1,m.digest()).toString(16);
    }

}
