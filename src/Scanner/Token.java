package Scanner;

public class Token {

    public enum Type {
        LPAREN,      // (
        RPAREN,      // )
        OPERATOR,    // + - * /
        INTEGER,     // 3, 42, 100
        IDENTIFIER,  // x, y, myVar
        KEYWORD,     // let
        EOF
    }

    public Type type;
    public String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {

        if (value != null && !value.isEmpty()) {
            return type + "(" + value + ")";
        }
        return type.toString();
    }
}