import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Output a glossary into a group of HTML files.
 *
 * @author Henrique Painhas
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {

        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        out.print("Enter the name of the input file: ");
        SimpleReader input = new SimpleReader1L(in.nextLine());

        out.print("Enter the name of the output folder: ");
        String folderName = in.nextLine();

        SimpleWriter html = new SimpleWriter1L(folderName + "/index.html");

        Set<String> allTerms = new Set1L<String>();

        html.println("<html>");
        html.println("<head>");
        html.println("<title>Glossary</title>");
        html.println("</head>");
        html.println("<body>");
        html.println("<h2>Glossary</h2>");
        html.println("<hr />");
        html.println("<h3>Index</h3>");
        html.println("<ul>");

        while (!input.atEOS()) {
            String term = input.nextLine();

            if (!allTerms.contains(term)) {
                allTerms.add(term);
            }

            String definition = input.nextLine();
            String stillDefinition = input.nextLine();

            while (!stillDefinition.equals("")) {
                definition = definition.concat(stillDefinition);
                stillDefinition = input.nextLine();
            }

            html.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");

            outputTermsWithDefinitions(allTerms, term, folderName, definition);

        }

        html.println("</ul>");
        html.println("</body>");
        html.println("</html>");

        in.close();
        out.close();
        html.close();
        input.close();
    }

    /**
     * Output separate pages for each of the terms with their respective
     * definitions.
     *
     * @param allTerms
     *            set of String containing all terms from the input
     * @param term
     *            the word to assign the definition
     * @param definition
     *            the definition of the term
     * @param folderName
     *            the folder where all the output files will be saved
     *
     * @requires term = "" and definition = "" and allTerms = <>
     *
     * @ensures html.contents = html file page of term with its definition
     */
    public static void outputTermsWithDefinitions(Set<String> allTerms,
            String term, String folderName, String definition) {

        SimpleWriter html = new SimpleWriter1L(
                folderName + "/" + term + ".html");

        html.println("<html>");
        html.println("<head>");
        html.println("<title>" + term + "</title>");
        html.println("</head>");
        html.println("<body>");
        html.println("<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>");

        String result = "";
        String comparison = allTerms.removeAny();

        while (allTerms.size() > 0) {
            if (definition.contains(comparison)) {
                result = definition.replace(comparison,
                        "<a href=\"" + comparison + ".html\"></a>");
            }
            comparison = allTerms.removeAny();
        }

        html.println("<blockquote>" + result + "</blockquote>");
        html.println("<hr />");
        html.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        html.println("</body>");
        html.println("</html>");
        html.close();
    }
}
