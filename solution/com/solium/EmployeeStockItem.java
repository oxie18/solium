package com.solium;

import java.util.Date;

/**
 * @author	Roxanne Montes <roxanne.montes@yahoo.com>
 * @since	January 19, 2015
 */
class EmployeeStockItem {
	/**
	 * refers to the employee stock option either VEST, PERF or SALE
	 */
    private String transCode;
    
    /**
     * date when the employee stocks are vested or sold or date when bonus took effect
     */
    private Date transDate;
    
    /**
     * amount of units vested or sold
     */
    private double transAmount;
    
    /**
     * grant price for the stocks vested or market price for the stocks sold
     */
    private double transPrice;
    
    /**
     * performance bonus multiplier
     */
    private double bonus;

    /**
     * EmployeeStockItem class constructor
     * 
     * @param transCode		employee stock option (VEST, PERF or SALE)
     * @param transDate		date when a stock is vested or sold or when a bonus took effect
     * @param transAmount	amount of stock units vested or sold
     * @param transPrice	grant price for the stocks vested or market price for the stocks sold
     */
    public EmployeeStockItem(String transCode, Date transDate, double transAmount, double transPrice) {
        this.transCode = transCode;
        this.transDate = transDate;
        this.transAmount = transAmount;
        this.transPrice = transPrice;
    }

    /**
     * EmployeeStockItem class constructor
     * 
     * @param transCode		employee stock option (VEST, PERF or SALE)
     * @param transDate		date when a stock is vested or sold or when a bonus took effect
     * @param bonus			performance bonus multiplier
     */
    public EmployeeStockItem(String transCode, Date transDate, double bonus) {
        this.transCode = transCode;
        this.transDate = transDate;
        this.bonus = bonus;
    }

    /**
     * Retrieves the employee stock option
     * @return	String	VEST, PERF or SALE
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     * Retrieves the date when stocks were vested, a bonus took effect or stocks were sold
     * 
     * @return	Date
     */
    public Date getTransDate() {
        return transDate;
    }

    /**
     * Retrieves the amount of units vested or sold
     * 
     * @return 	amount of units vested or sold
     */
    public double getTransAmount() {
        return transAmount;
    }

    /**
     * Retrieves the grant price for units vested or market price for units sold
     * 
     * @return	grant price for units vested or market price for units sold
     */
    public double getTransPrice() {
        return transPrice;
    }

    /**
     * Retrieves the performance bonus multiplier
     * 
     * @return	performance bonus multiplier
     */
    public double getBonus() {
        return bonus;
    }
}
