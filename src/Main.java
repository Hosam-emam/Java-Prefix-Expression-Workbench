import Scanner.Lexer;
import Scanner.Token;
import Parser.Parser;
import AST.ASTNode;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        System.out.println("Enter 1 for manual input, 2 to use automated test_cases");

        Scanner scanner = new Scanner(System.in);

        int choice = Integer.parseInt(scanner.nextLine());
        String input2 = scanner.nextLine();
        if (choice == 2) {
            String[] testCases = {
                    // test cases
                    "(+ 3 4)",
                    "(* (+ 1 2) 5)",
                    "(- (* 4 5) (+ 2 3))",
                    "(let x 10)",
                    "(+ x 3)",
                    // Error cases
                    "(+ 3",
                    "(* 2)",
                    "(@ 1 2)"
            };


            for (String input : testCases) {
                run(input);
            }
        }
        else {
            run(input2);
        }
    }

        public static void run (String input){
            System.out.println("=".repeat(45));
            System.out.println("Input: " + input);
            System.out.println("-".repeat(45));

            try {

                Lexer lexer = new Lexer(input);
                List<Token> tokens = lexer.tokenize();

                System.out.println("Tokens:");
                for (Token t : tokens) {
                    if (t.type != Token.Type.EOF) {
                        System.out.println("  " + t);
                    }
                }


                System.out.println("\nAST:");
                Parser parser = new Parser(tokens);
                ASTNode ast = parser.parse();
                System.out.println(ast.prettyPrint(""));

                System.out.println("\n✓ Parsing successful!");

            } catch (RuntimeException e) {
                System.out.println("\n✗ " + e.getMessage());
            }

            System.out.println();
        }
    }