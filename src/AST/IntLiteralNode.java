package AST;

public class IntLiteralNode extends ASTNode {
    public int value;

    public IntLiteralNode(int value) {
        this.value = value;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "IntLiteral(" + value + ")";
    }
}

