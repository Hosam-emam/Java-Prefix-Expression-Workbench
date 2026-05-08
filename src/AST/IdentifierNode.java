package AST;

public class IdentifierNode extends ASTNode {
    public String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "Identifier(" + name + ")";
    }
}