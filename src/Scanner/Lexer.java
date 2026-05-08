package Scanner;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private String input;
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }


    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);


            if (Character.isWhitespace(c)) {
                pos++;


            } else if (c == '(') {
                tokens.add(new Token(Token.Type.LPAREN, "("));
                pos++;


            } else if (c == ')') {
                tokens.add(new Token(Token.Type.RPAREN, ")"));
                pos++;

            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                tokens.add(new Token(Token.Type.OPERATOR, String.valueOf(c)));
                pos++;


            } else if (Character.isDigit(c)) {
                tokens.add(readInteger());


            } else if (Character.isLetter(c)) {
                tokens.add(readWord());


            } else {
                throw new RuntimeException("Lexer Error: Unknown character '" + c + "' at position " + pos);
            }
        }

        // إضافة علامة نهاية الـ input
        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    private Token readInteger() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return new Token(Token.Type.INTEGER, sb.toString());
    }

    private Token readWord() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        String word = sb.toString();

        if (word.equals("let")) {
            return new Token(Token.Type.KEYWORD, word);
        }

        return new Token(Token.Type.IDENTIFIER, word);
    }
}