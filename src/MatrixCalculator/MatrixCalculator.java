/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixCalculator;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author UZAIR
 */
public class MatrixCalculator {
    
    static List<Matrix> matrices= new ArrayList<Matrix>();
    
    public static List<String> tokenize(String s) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-');  // Don't parse minus as part of numbers.
        List<String> tokBuf = new ArrayList<String>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokBuf.add(String.valueOf(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    tokBuf.add(tokenizer.sval);
                    break;
                default:  // operator
                    tokBuf.add(String.valueOf((char) tokenizer.ttype));
            }
        }
        return tokBuf; 
    }
    
    public static Matrix getMatrix(List<Matrix> matrices,String name){
        for (Matrix matrix : matrices ) {
            if (name.equals(matrix.getName())) {
                return matrix;
            }
        }
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Scanner read= new Scanner(System.in);
        System.out.println("======== Matrix Computer =========");
        String option;
        String equation;
        Matrix result=new Matrix();
        
        while(true){
            System.out.println("Do want to make a matrix (Y or N)");
            option=read.next();
            
            if(option.equals("Y")){
                String matName;
                int nRow;
                int nCol;
                System.out.println("Enter matrix name");
                matName = read.next();
                
                System.out.println("Enter matrix dimensions (row col)");
                nRow = read.nextInt();
                nCol = read.nextInt();
                
                System.out.println("Enter matrix data (space seperated values)");
                double[] data = new double[nRow*nCol];
                for(int i=0;i<nRow*nCol;i++){
                    data[i]=read.nextDouble();
                }
                
                Matrix mat = new Matrix(nRow, nCol);
                mat.setName(matName);
                mat.setData(data);
                
                matrices.add(mat);

            }
            else{
                break;
            }
        }
        
        System.out.println("Enter the equation you want to calculate");
        equation = read.next();
        
        result = solveEq(equation,matrices);
        result.getData();
        
    }
    
    public static Matrix solveEq(String equation, List<Matrix> matrices) throws IOException{
    
        Matrix result= new Matrix();
        List<String> tknEq=tokenize(equation);
        System.out.println(tknEq);
        
        String firstTkn=tknEq.get(0);
        for (Matrix matrix : matrices ) {
            if (firstTkn.equals(matrix.getName())) {
                result = matrix;
                break;
            }
        }
        
        //for checking doubles
        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =("[\\x00-\\x20]*"+     // Optional leading "whitespace"
                                "[+-]?(" +              // Optional sign character
                                "NaN|" +                // "NaN" string
                                "Infinity|" +           // "Infinity" string
                                "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+     // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                                "(\\.("+Digits+")("+Exp+")?)|"+     // . Digits ExponentPart_opt FloatTypeSuffix_opt
                                "((" +                  // Hexadecimal strings
                                "(0[xX]" + HexDigits + "(\\.)?)|" +     // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                                "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                                ")[pP][+-]?" + Digits + "))" +
                                "[fFdD]?))" +
                                "[\\x00-\\x20]*");// Optional trailing "whitespace"
        
        for (int i = 0; i < tknEq.size(); i++) {
            String tokens=tknEq.get(i);
            if(tokens.equals("+") || tokens.equals("-") || tokens.equals("*") || tokens.equals("^") || tokens.equals("~")){
                
                if(tokens.equals("+")){
                    i++;
                    result = result.Add(getMatrix(matrices,tknEq.get(i)));
                    return result;
                }
                else if(tokens.equals("*")){
                    i++;
                    if(Pattern.matches(fpRegex, tknEq.get(i))){
                        result.scalMul(Double.valueOf(tknEq.get(i)));
                    }
                    else{
                       result = result.vecMul(getMatrix(matrices,tknEq.get(i)));
                    }
                }
                else if(tokens.equals("-")){
                    i++;
                    result = result.Sub(getMatrix(matrices,tknEq.get(i)));
                    
                }
                else if(tokens.equals("^")){
                    System.out.println("tr detected");
                    result = result.transpose();
                }
                else if(tokens.equals("~")){
                    result = result.inverse();
                }
                
            }
        }
        return result;
            
            
        
    }
    
    
    
}


