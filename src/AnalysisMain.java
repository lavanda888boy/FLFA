import analysis.parser.Parser;

public class AnalysisMain {
    public static void main(String[] args) {
        
        Parser p = new Parser();
        p.fillParseList("resources/code.txt");
        p.printParserOutput();
    }
}
