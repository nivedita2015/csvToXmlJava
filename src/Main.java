/**
 * The main method for the CsvConverter program.
 * Purpose: Calls convertToXML function with input and output file name.
 *
 */
public class Main {

    public static void main(String[] args) {
        Converter con = new Converter();
        con.convertToXML("sample.csv","finalXML.xml");
    }
}
