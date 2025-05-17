package functional.streaming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.TreeVisitor;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Path;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Utility class to check properties of Java code.
 */
public class CodeTesterUtils {
    private static final Path SOURCES_ROOT = Path.of("./src/main/java");
    private static final String JAVA_FILE_EXTENSION = ".java";

    /**
     * Apply a specific code checker to a specific class.
     *
     * @param clazz The (non-inner) class to be checked
     */
    public static void check(Class<?> clazz, BiConsumer<String, Node> checker) {
        String packageName = clazz.getPackageName();
        String fileName = clazz.getSimpleName() + JAVA_FILE_EXTENSION;
        CompilationUnit compilationUnit = new SourceRoot(SOURCES_ROOT).parse(packageName, fileName);

        new TreeVisitor() {
            @Override
            public void process(Node node) {
                checker.accept(fileName, node);
            }
        }.visitPreOrder(compilationUnit);
    }

    /**
     * Check that no loops are used in a specific class (e.g., because only streams should be used).
     *
     * @param fileName Containing the filename of the checked node
     * @param node     Checked node
     */
    public static void checkNoLoops(String fileName, Node node) {
        if (node instanceof DoStmt ||
            node instanceof ForStmt ||
            node instanceof ForEachStmt ||
            node instanceof WhileStmt) {
            failOnNode("Found a loop", fileName, node);
        }
    }

    /**
     * Check that no hard-coded line separators are used.
     *
     * @param fileName Containing the filename of the checked node
     * @param node     Checked node
     */
    public static void checkNoHardCodedLineSeparators(String fileName, Node node) {
        if (node instanceof CharLiteralExpr) {
            char value = ((CharLiteralExpr) node).asChar();
            if (value == '\n') {
                failOnNode("Found a hard-coded line separator", fileName, node);
            }
        }
        if (node instanceof StringLiteralExpr) {
            String value = ((StringLiteralExpr) node).asString();
            if (value.contains("\n")) {
                failOnNode("Found a hard-coded line separator", fileName, node);
            }
        }
    }

    private static void failOnNode(String message, String filename, Node node) {
        fail("%s in %s%s:%n%s%n".formatted(message, filename, formatNodeRange(node), node));
    }

    private static String formatNodeRange(Node node) {
        return node.getRange().map(value -> " " + value).orElse("");
    }
}
