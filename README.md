# Prefix Expression Workbench 🛠️

A compiler front-end for a LISP-inspired prefix expression language, built entirely in Java with a handwritten scanner and recursive-descent parser.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Grammar](#grammar)
- [Token Specification](#token-specification)
- [AST Structure](#ast-structure)
- [How to Run](#how-to-run)
- [Sample Input & Output](#sample-input--output)
- [Error Handling](#error-handling)
- [Test Cases](#test-cases)

---

## Overview

This project implements a **compiler front-end** for a prefix expression language inspired by LISP syntax. Instead of writing `3 + 4` (infix), expressions are written as `(+ 3 4)` (prefix).

The pipeline consists of three stages:

```
Source Text  →  [Lexer]  →  Tokens  →  [Parser]  →  AST
```

| Component | Responsibility |
|-----------|---------------|
| **Lexer** | Scans raw input and produces a list of tokens |
| **Parser** | Consumes tokens and builds an Abstract Syntax Tree (AST) |
| **AST Nodes** | Represent the hierarchical structure of the program |

---

## Project Structure

```
src/
├── Main.java                  # Entry point — runs all test cases
├── Scanner/
│   ├── Token.java             # Token types and values
│   └── Lexer.java             # Handwritten scanner
├── Parser/
│   └── Parser.java            # Handwritten recursive-descent parser
└── AST/
    ├── ASTNode.java           # Abstract base class for all nodes
    ├── IntLiteralNode.java    # Integer literals e.g. 42
    ├── IdentifierNode.java    # Variable names e.g. x
    ├── BinaryOpNode.java      # Binary operations e.g. (+ 3 4)
    └── LetExprNode.java       # Variable definitions e.g. (let x 10)
```

---

## Grammar

```
expression  →  '(' operator expression expression ')'
             | '(' 'let' IDENTIFIER expression ')'
             | INTEGER
             | IDENTIFIER

operator    →  '+' | '-' | '*' | '/'
```

---

## Token Specification

| Token Type   | Description               | Examples          |
|--------------|---------------------------|-------------------|
| `LPAREN`     | Left parenthesis          | `(`               |
| `RPAREN`     | Right parenthesis         | `)`               |
| `OPERATOR`   | Arithmetic operator       | `+`, `-`, `*`, `/`|
| `INTEGER`    | Integer literal           | `3`, `42`, `100`  |
| `IDENTIFIER` | Variable name             | `x`, `y`, `myVar` |
| `KEYWORD`    | Reserved word             | `let`             |
| `EOF`        | End of input              | —                 |

---

## AST Structure

Each expression maps to a node in the AST:

### `(+ 3 4)`
```
BinaryOp(+)
├── IntLiteral(3)
└── IntLiteral(4)
```

### `(* (+ 1 2) 5)`
```
BinaryOp(*)
├── BinaryOp(+)
│   ├── IntLiteral(1)
│   └── IntLiteral(2)
└── IntLiteral(5)
```

### `(let x 10)`
```
LetExpr
├── Identifier(x)
└── IntLiteral(10)
```

---

## How to Run

### Requirements
- Java 8 or higher

### Compile

```bash
javac -d out src/Scanner/*.java src/AST/*.java src/Parser/*.java src/Main.java
```

### Run

```bash
java -cp out Main
```

---

## Sample Input & Output

### Input: `(- (* 4 5) (+ 2 3))`

```
=============================================
Input: (- (* 4 5) (+ 2 3))
---------------------------------------------
Tokens:
  LPAREN
  OPERATOR(-)
  LPAREN
  OPERATOR(*)
  INTEGER(4)
  INTEGER(5)
  RPAREN
  LPAREN
  OPERATOR(+)
  INTEGER(2)
  INTEGER(3)
  RPAREN
  RPAREN

AST:
BinaryOp(-)
├── BinaryOp(*)
│   ├── IntLiteral(4)
│   └── IntLiteral(5)
└── BinaryOp(+)
    ├── IntLiteral(2)
    └── IntLiteral(3)

✓ Parsing successful!
```

---

## Error Handling

The parser reports clear syntax errors with the token that caused the problem.

| Invalid Input | Error Message |
|---------------|---------------|
| `(+ 3`        | `Syntax Error: Expected RPAREN but found EOF` |
| `(* 2)`       | `Syntax Error: Expected expression but found RPAREN` |
| `(@ 1 2)`     | `Syntax Error: Expected operator or 'let' after '(' but found OPERATOR(@)` |

---

## Test Cases

### ✅ Valid Inputs

| Input | Description |
|-------|-------------|
| `(+ 3 4)` | Simple addition |
| `(* (+ 1 2) 5)` | Nested expressions |
| `(- (* 4 5) (+ 2 3))` | Deeply nested |
| `(let x 10)` | Variable definition |
| `(+ x 3)` | Identifier as operand |

### ❌ Invalid Inputs

| Input | Error |
|-------|-------|
| `(+ 3` | Missing closing parenthesis |
| `(* 2)` | Missing second operand |
| `(@ 1 2)` | Unknown character `@` |
| `(let 10 x)` | Identifier expected after `let` |

---

## Design Notes

- **No parser generators used** — both the scanner and parser are fully handwritten.
- **Recursive-descent parsing** mirrors the grammar rules directly, making the code easy to follow.
- **AST nodes** use a `prettyPrint()` method to display the tree visually in the console.
- **Error messages** include the unexpected token to help diagnose issues quickly.
