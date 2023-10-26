/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tableView;

/**
 *
 * @author User
 */
class Department {
     static void add(Department department) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     private String department_name;
     public Department(String department_name){
         this.department_name = department_name;
     }
     public String getDepartmentNames(){
        return department_name;
    }
     public void setDepartmentNames(String department_name){
        this.department_name = department_name;
    }
}
