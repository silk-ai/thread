package thread;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.*;
import java.lang.Object;

public class Weaver{
    
    private static File file;
    
    public static void weave(String inFilePath, String outFilePath, ArrayList<String> functionNames) throws java.io.IOException{
        String contents = new String(Files.readAllBytes(Paths.get(inFilePath)));
        CompilationUnit ast = StaticJavaParser.parse(contents);
        file = new File(outFilePath);
        Files.write(file.toPath(),"".getBytes(),StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        findOrder(ast, functionNames);
    }
    public static void findOrder(CompilationUnit ast, ArrayList<String> functionNames) throws java.io.IOException{
        List<Node> topLevel = ast.getChildNodes();
        for(Node n: topLevel){
            if(n instanceof ClassOrInterfaceDeclaration){
                sortClass((ClassOrInterfaceDeclaration) n,functionNames);
            }
            else{
                addToFile(n);
            }
        }
    }
    public static void sortClass(ClassOrInterfaceDeclaration cls, ArrayList<String> functionNames) throws java.io.IOException{
        List<Node> clsProps = cls.getChildNodes();
        ArrayList<MethodDeclaration> methodList = new ArrayList<MethodDeclaration>();
        ArrayList<MethodDeclaration> sortedMethodList = new ArrayList<MethodDeclaration>();
        
        for(Node m:clsProps){
            if(m instanceof MethodDeclaration){
                //System.out.println(m);
                methodList.add((MethodDeclaration)m);
            }
        }
        sortedMethodList = sortABasedOnB(methodList, functionNames);
        for(Node m:clsProps){
            if(m instanceof MethodDeclaration){
                addToFile(sortedMethodList.get(0));
                sortedMethodList.remove(0);
            }
            else{
                addToFile(m);
            }
        }

    }
    public static ArrayList<MethodDeclaration> sortABasedOnB(ArrayList<MethodDeclaration> nodes, ArrayList<String> names) throws java.io.IOException{
        ArrayList<MethodDeclaration> sortedMethodList = new ArrayList<MethodDeclaration>();
        for(String s:names){
            //System.out.println(s);
            for(int i=0;i<nodes.size();i++){
                //System.out.println(i);
                if(functionMatch(nodes.get(i),s)){
                    sortedMethodList.add(nodes.get(i));
                    nodes.remove(i);
                    i--;
                }
            }
        }
        return sortedMethodList;
    }
    public static boolean functionMatch(MethodDeclaration n, String function) throws java.io.IOException{
        return n.getName().toString().equals(function);
    }
    public static void addToFile(Node n) throws java.io.IOException{
        Files.write(file.toPath(), n.toString().getBytes(),StandardOpenOption.APPEND);
        //System.out.println(n);
    }
    public static void debug(String a, String b) throws java.io.IOException{
        /*
        get
        set
        increment
        fill
        numBytesPerElement
        resize
        */
        ArrayList<String> names = new ArrayList<String>();
        names.add("increment");
        names.add("set");
        names.add("get");
        names.add("resize");
        names.add("fill");
        names.add("numBytesPerElement");
        weave(a,b,names);   
    }
}