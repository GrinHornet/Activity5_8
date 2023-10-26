/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableView;

/**
 *
 * @author User
 */
public class Person {

    static void add(Person person) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private String fname;
    private String mname;
    private int id;
    private String lname;
    private int cntct;
    private int status;
    
    public Person(int id, String fname, String mname, String lname, int cntct, int status){
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.cntct = cntct;
        this.status = status;
    }
    //Get constructors
    public int getId(){
        return id;
    }
    public String getFname(){
        return fname;
    }
    public String getMname(){
        return mname;
    }
    public String getLname(){
        return lname;
    }
    public int getCntct(){
        return cntct;
    }
    public int getStatus(){
        return status;
    }
    // Set Constructors
    public void setId(int id) {
        this.id = id;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setCntct(int cntct) {
        this.cntct = cntct;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
