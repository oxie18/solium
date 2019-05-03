package com.solium;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author	Roxanne Montes <roxanne.montes@yahoo.com>
 * @since	January 19, 2015
 */
class Employee {
	/**
	 * employee identifier
	 */
    private String empID;
    
    /**
     * list that will contain the employee's stock options (VEST, PERF, SALE)
     */
    private ArrayList<EmployeeStockItem> eeStockItems;

    /**
     * Employee class constructor
     * 
     * @param 	empID	Employee identifier
     */
    public Employee(String empID) {
        this.empID = empID;
        eeStockItems = new ArrayList<EmployeeStockItem>();
    }

    /**
     * Retrieves the employee identifier
     * 
     * @return	empID	
     */
    public String getEmpID() {
        return empID;
    }

    /**
     * Adds stock option to the list of employee's stock option
     * 
     * @param stockItem		EmployeeStockItem object
     */
    public void addStockItem(EmployeeStockItem stockItem) {
        eeStockItems.add(stockItem);
    }

    /**
     * Retrieves the employee's list of stock options
     * 
     * @return	ArrayList<EmployeeStockItem>	
     */
    public ArrayList<EmployeeStockItem> getStockItems() {
        return eeStockItems;
    }

    /**
     * Used to sort the array list by employee identifier
     */
    public static Comparator<Employee> EmployeeIDComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee e1, Employee e2) {
            String emp1 = e1.getEmpID().toUpperCase();
            String emp2 = e2.getEmpID().toUpperCase();

            return emp1.compareTo(emp2);
        }
    };
}
