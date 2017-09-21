/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixCalculator;

/**
 *
 * @author UZAIR
 */
public class Matrix {
    
    private String name;
    private int row;
    private int col;
    private double[][] vals;

    public Matrix() {
        this.vals = null;
        this.row = 2;
        this.col = 2;
        vals = new double[row][col];
    }

    public Matrix(int row, int col) {
        
        this.name=null;
        this.row = row;
        this.col = col;
        vals = new double[row][col];
        
    }
    
    public void setData(double[] vals){
        for (int i=0;i<this.getRows();i++) {
            for (int j=0;j<this.getCols();j++) {
                this.vals[i][j]=vals[(i*getCols())+j];
            }
        }   
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public double[][] getData(){
        for (int i=0;i<this.getRows();i++) {
            for (int j=0;j<this.getCols();j++) {
                System.out.print(" " +this.vals[i][j]);
            }
            System.out.println(" ");
        }
        return this.vals;
    }
    
    public void setRows(int row){
        this.row=row;
    }
    
    public void setCols(int col){
        this.col=col;
    }
    
    public int getRows(){
        return row;
    }
    
    public int getCols(){
        return col;
    }
    
    public static void printError(String error){
        System.out.println(error);
    }
    
    public Matrix transpose() {
        Matrix transMat = new Matrix(this.getCols(), this.getRows());
        
        for (int i=0;i<this.getRows();i++) {
            for (int j=0;j<this.getCols();j++) {
                transMat.vals[j][i]=this.vals[i][j];
            }
        }
        return transMat;
    } 
    
    public boolean isSqr(){
        if(this.getCols()==this.getRows()){
           return true;
        }
        else{
           return false;
        }
    }
    
    public boolean checkAddSub(Matrix mat){
        System.out.println(this.getCols());
        if(this.getCols()==mat.getCols() && this.getRows()==mat.getRows()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkMul(Matrix mat){
        if(this.getCols()==mat.getRows()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Matrix Add(Matrix mat) {
        Matrix resMat = new Matrix(this.getRows(), this.getCols());
        if(checkAddSub(mat)){
            for (int i=0;i<this.getRows();i++) {
                for (int j=0;j<this.getCols();j++) {
                    resMat.vals[i][j]=this.vals[i][j]+mat.vals[i][j];
                }
            }
            resMat.getData();
            return resMat;
        }
        else{
            printError("Matrices must be of same order for Addition");
            System.exit(0);
        }
        return null;
    }
    
    public Matrix Sub(Matrix mat) {
        Matrix resMat = new Matrix(this.getRows(), this.getCols());
        if(checkAddSub(mat)){
            for (int i=0;i<this.getRows();i++) {
                for (int j=0;j<this.getCols();j++) {
                    resMat.vals[i][j]=this.vals[i][j]-mat.vals[i][j];
                }
            }
            return resMat;
        }
        else{
            printError("Matrices must be of same order for Subtraction");
            System.exit(0);
        }
        return null;
    }
    
    public Matrix scalMul(double scal) {
        
        Matrix resMat = new Matrix(this.getRows(), this.getCols());
        for (int i=0;i<this.getRows();i++) {
            for (int j=0;j<this.getCols();j++) {
                resMat.vals[i][j]=this.vals[i][j]*scal;
            }
        }
        return resMat;
    }
    
    public Matrix vecMul(Matrix mat) {
        Matrix resMat = new Matrix(this.getRows(), mat.getCols());
        if(checkMul(mat)){
            for (int i = 0; i < this.getRows(); i++) {
                for (int j = 0; j < mat.getCols(); j++) {
                    for (int k = 0; k < this.getCols(); k++) {
                        resMat.vals[i][j] = resMat.vals[i][j] + this.vals[i][k] * mat.vals[k][j];
                    }
                }
            }
            return resMat;
        }
        else{
            printError("These Matrices cannot be multiplied");
            System.exit(0);
        }
        return resMat;
    }
    
    public double det() {
        double myDet = 0;
        if (isSqr()){
            if (this.getCols() == 1) {
                return this.vals[0][0];
            }
            else if (this.getCols()==2) {
                //ad-bc
                return (this.vals[0][0] * this.vals[1][1]) - (this.vals[0][1] * this.vals[1][0]);
            }
            else {
                int sign;
                for (int i=0; i<this.getCols(); i++) {
                    if(i%2==0)  {sign=1;}
                    else {sign=-1;}
                    myDet += (sign * this.vals[0][1]) * this.subMatrix(0, i).det();
                }
            }
        }    
        else{
            printError("Determinanat can only be calculated for a square matrix");
            System.exit(0);
        }
        return myDet;
    }

    public Matrix subMatrix(int remRow, int remCol) {
        Matrix resMat = new Matrix(this.getRows()-1, this.getCols()-1);
        int newR = -1;
        for (int i=0;i<this.getRows();i++) {
            if (i==remRow)
                continue;
            newR++;
            int newC = -1;
            for (int j=0;j<this.getCols();j++) {
                if (j==remCol)
                    continue;
                newC++;
                resMat.vals[newR][newC]=this.vals[i][j];
            }
        }
        return resMat;
    }     
    
    public Matrix adj(){
        Matrix resMat = new Matrix(this.getRows(), this.getCols());
        int signI;int signJ;
        for (int i=0;i<this.getRows();i++) {
            if(i%2==0)  {signI=1;}
            else {signI=-1;}
            for (int j=0; j<this.getCols();j++) {
                if(j%2==0)  {signJ=1;}
                else {signJ=-1;}
                resMat.vals[i][j]= signI * signJ * this.subMatrix(i, j).det();            
            }
        }
        return resMat;
    }
    
    public Matrix inverse() {
        if(this.det()==0){
            printError("Inverse cannot be calculated for a singular matrix");
            System.exit(0);
        }
        return this.adj().transpose().scalMul(1.0/this.det());
    }
}
