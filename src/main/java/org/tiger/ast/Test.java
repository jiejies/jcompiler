package org.tiger.ast;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Create a simple MiniJava program AST
        // class Factorial {
        //     public static void main(String[] a) {
        //         System.out.println(new Fac().ComputeFac(10));
        //     }
        // }
        // 
        // class Fac {
        //     public int ComputeFac(int num) {
        //         int num_aux;
        //         if (num < 1)
        //             num_aux = 1;
        //         else
        //             num_aux = num * (this.ComputeFac(num-1));
        //         return num_aux;
        //     }
        // }

        // Create main class
        Statement mainStatement = new Print(
            new Call(
                new NewObject("Fac"),
                "ComputeFac",
                List.of(new IntegerLiteral(10))
            )
        );
        MainClass mainClass = new MainClass("Factorial", "a", mainStatement);

        // Create Fac class
        List<VarDecl> fields = new ArrayList<>();
        
        // ComputeFac method
        List<VarDecl> params = List.of(new VarDecl(new IntegerType(), "num"));
        List<VarDecl> locals = List.of(new VarDecl(new IntegerType(), "num_aux"));
        
        // if statement
        Statement ifStatement = new If(
            new LessThan(new IdentifierExp("num"), new IntegerLiteral(1)),
            new Assign("num_aux", new IntegerLiteral(1)),
            new Assign("num_aux",
                new Times(
                    new IdentifierExp("num"),
                    new Call(
                        new This(),
                        "ComputeFac",
                        List.of(
                            new Minus(
                                new IdentifierExp("num"),
                                new IntegerLiteral(1)
                            )
                        )
                    )
                )
            )
        );
        
        List<Statement> statements = List.of(ifStatement);
        Expression returnExp = new IdentifierExp("num_aux");
        
        MethodDecl computeFac = new MethodDecl(
            new IntegerType(),
            "ComputeFac",
            params,
            locals,
            statements,
            returnExp
        );
        
        List<MethodDecl> methods = List.of(computeFac);
        
        ClassDecl facClass = new ClassDecl("Fac", null, fields, methods);
        
        // Create complete program
        Program program = new Program(mainClass, List.of(facClass));
        
        // Print program source code
        System.out.println("Program source code:");
        PrettyPrinter printer = new PrettyPrinter();
        program.accept(printer);
        System.out.println(printer.toString());
        
        // Execute program
        System.out.println("\nProgram output:");
        Interpreter interpreter = new Interpreter();
        interpreter.execute(program);
    }
} 