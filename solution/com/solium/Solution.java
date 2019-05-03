package com.solium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * @author 	Roxanne Montes <roxanne.montes@yahoo.com>
 * @since	January 19, 2015
 */
public class Solution {
    /**
     * list that will contain Employee objects that have already vested, granted bonus or sold units
     */
    private ArrayList<Employee> employeeList = new ArrayList<Employee>();

    public static void main(String[] args) {
        Solution sol = new Solution();

        sol.processFile();
    }

    /**
     * 
     * Reads the input file and processes data and displays output
     * 
     */
    private void processFile() {
        // read the input file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DateFormat df = new SimpleDateFormat("yyyyMMdd");	// sets the date format
        Date runDate = null;								// date at the final input line
        double marketPrice = 0;								// stock market price

        try {
            String line;									// line read from file
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");          // break each line with commas and store each field in an array

                switch (fields.length) {					// evaluate each array's length
                    case 2:									// final input line
                        runDate = df.parse(fields[0]);		
                        marketPrice = Double.parseDouble(fields[1]);
                        break;
                    case 4:									// VEST, PERF or SALE lines
                    case 5:
                        Employee ee;				
                        String transCode = fields[0];		// values could either be VEST, PERF or SALE
                        String employeeID = fields[1];		
                        Date transDate = df.parse(fields[2]);	// values could either be vest date, bonus date or sale date

                        // checks if employee is on the array list (employeeList)
                        boolean empExists = containsEmpID(employeeID);	

                        if (empExists) {
                        	// retrieve the Employee object for the given employee ID if employee exists in the array list
                            ee = getEmployee(employeeID);		 
                        }
                        else {
                        	// create new Employee object if employee does not exist in the array list
                            ee = new Employee(employeeID);		
                            employeeList.add(ee);			// add new employee to the array list
                        }

                        double bonus;						// bonus multiplier
                        double transAmount;					// amount of units vested, sold or awarded as a bonus
                        double transPrice;					// grant price for the units vested or market price for the units sold
                        
                        if (fields.length == 4) {			// PERF
                            bonus = Double.parseDouble(fields[3]);
                            
                            // create stock item/option for the performance bonus of the current employee
                            // and add it to the employee stock items list (eeStockItems)
                            ee.addStockItem(new EmployeeStockItem(transCode, transDate, bonus));
                        }
                        else {								// VEST or SALE
                            transAmount = Double.parseDouble(fields[3]);		
                            transPrice = Double.parseDouble(fields[4]);
                            
                            // create stock item/option for the vest or sale of units of the current employee
                            // and add it to the employee stock items list (eeStockItems)
                            ee.addStockItem(new EmployeeStockItem(transCode, transDate, transAmount, transPrice));
                        }
                        break;
                }
            }

            // sort the employee array list (employeeList) by employeeID
            Collections.sort(employeeList, Employee.EmployeeIDComparator);

            System.out.println("Output:");
            calculateTotalGain(runDate, marketPrice);		// calculate and display the employees' total gain

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates and displays each employee's total gain
     * 
     * @param 	runDate			date at the final line of the input file
     * @param 	marketPrice		price for the stocks at the given date
     */
    private void calculateTotalGain(Date runDate, double marketPrice) {
        boolean soldUnits = false;				// tracks if there is a SALE option in the input file

    	// iterate through each employee in the list
        for (Employee employee : employeeList) {
            double totalGain = 0;				// employee's total gain
            double vestAmount = 0;				// amount of units vested to the employee
            double grantPrice = 0;				// grant price when the units were vested
            double totalGainThruSale = 0;		// total gain when a sale was made

            // iterate through each of the employee's stock items
            for (EmployeeStockItem item : employee.getStockItems()) {

            	// the employee has vested and the transDate ("vest date") is on or before the runDate (given date)
                if (item.getTransCode().equals("VEST") && item.getTransDate().compareTo(runDate) <= 0) {
                    vestAmount += item.getTransAmount();
                    grantPrice = item.getTransPrice();
                    totalGain += item.getTransAmount() * (marketPrice - item.getTransPrice());
                } 
                // the employee was given a performance bonus and transDate ("bonus date") is on or before the runDate (given date)
                else if (item.getTransCode().equals("PERF") && item.getTransDate().compareTo(runDate) <= 0) {
                    totalGain *= item.getBonus();
                } 
                
                // the employee has sold units and transDate ("sale date") is on or before the runDate (given date)
                else if (item.getTransCode().equals("SALE") && item.getTransDate().compareTo(runDate) <= 0) {
                    totalGainThruSale = (vestAmount - item.getTransAmount()) * (item.getTransPrice() - grantPrice);
                    totalGain = totalGainThruSale;
                    soldUnits = true;
                }
            }

            // display employee ID and total cash gain
            System.out.print(employee.getEmpID() + "," + String.format("%.2f", totalGain));

            if (soldUnits) {			
            	// add total cash gain through sale to the output if a sale was made
                System.out.println("," + String.format("%.2f", totalGainThruSale));
            }
            else {
            	// terminate the line
                System.out.println();
            }
        }
    }

    /**
     * Retrieves the Employee object associated with the given employee ID from the array list (employeeList)
     * 
     * @param 	employeeID	ID of the employee
     * @return 				Employee
     */
    private Employee getEmployee(String employeeID) {
    	
    	// iterate through the array list to search for the employee
        for (Employee e : employeeList) {
            if (e.getEmpID().equals(employeeID)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Checks if the array list (employeeList) contains the Employee object for the given employee ID
     * 
     * @param 	employeeID	ID of the employee
     * @return 				true if Employee object is found, otherwise false
     */
    private boolean containsEmpID(String employeeID) {

    	// iterate through the array list to search for the employee
    	for (Employee e : employeeList) {
            if (e.getEmpID().equals(employeeID)) {
                return true;
            }
        }
        return false;
    }
}
