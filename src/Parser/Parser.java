package Parser;

import AST.*;
import Scanner.Token;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    private Token peek() {
        return tokens.get(pos);
    }


    private Token consume() {
        return tokens.get(pos++);
    }

    // بيتحقق إن الـ Token الحالي من النوع المطلوب ويستهلكه، لو لأ بيطلع خطأ
    private Token expect(Token.Type type) {
        Token t = peek();
        if (t.type != type) {
            throw new RuntimeException(
                    "Syntax Error: Expected " + type + " but found " + t.type +
                            (t.value.isEmpty() ? "" : "('" + t.value + "')")
            );
        }
        return consume();
    }


    public ASTNode parse() {
        ASTNode node = parseExpression();
        // بعد الـ parse لازم نكون وصلنا لنهاية الـ input
        expect(Token.Type.EOF);
        return node;
    }

    /*
     * Grammar:
     * expression → '(' operator expression expression ')'
     *            | '(' 'let' IDENTIFIER expression ')'
     *            | INTEGER
     *            | IDENTIFIER
     */
    private ASTNode parseExpression() {
        Token current = peek();

        if (current.type == Token.Type.INTEGER) {
            consume();
            return new IntLiteralNode(Integer.parseInt(current.value));
        }


        if (current.type == Token.Type.IDENTIFIER) {
            consume();
            return new IdentifierNode(current.value);
        }

        if (current.type == Token.Type.LPAREN) {
            consume();
            Token next = peek();

            if (next.type == Token.Type.KEYWORD && next.value.equals("let")) {
                return parseLetExpr();
            }

            if (next.type == Token.Type.OPERATOR) {
                return parseBinaryOp();
            }


            throw new RuntimeException(
                    "Syntax Error: Expected operator or 'let' after '(' but found " + next.type
            );
        }

        throw new RuntimeException(
                "Syntax Error: Unexpected token " + current.type +
                        (current.value.isEmpty() ? "" : "('" + current.value + "')")
        );
    }

    // Parse: operator expression expression ')'
    private ASTNode parseBinaryOp() {
        Token op = expect(Token.Type.OPERATOR);
        ASTNode left  = parseExpression();
        ASTNode right = parseExpression();
        expect(Token.Type.RPAREN);
        return new BinaryOpNode(op.value, left, right);
    }

    // Parse: 'let' IDENTIFIER expression ')'
    private ASTNode parseLetExpr() {
        consume();
        Token id = expect(Token.Type.IDENTIFIER);
        ASTNode value = parseExpression();
        expect(Token.Type.RPAREN);
        return new LetExprNode(id.value, value);
    }
}