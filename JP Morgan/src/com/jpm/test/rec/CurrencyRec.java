/**
 * 
 */
package com.jpm.test.rec;

/**
 * @author Lalit Mishra
 *
 */
public class CurrencyRec {
  private String currency;
  private String weekType;
 
  /**
   * @param currency
   * @param weekType
   */
  public CurrencyRec(String currency, String weekType) {
    this.currency = currency;
    this.weekType = weekType;
  }

  /**
   * 
   */
  public CurrencyRec() {
  }

  /**
   * @return the currency
   */
  public String getCurrency() {
    return currency;
  }
  
  /**
   * @param currency the currency to set
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  /**
   * @return the weekType
   */
  public String getWeekType() {
    return weekType;
  }
  
  /**
   * @param weekType the weekType to set
   */
  public void setWeekType(String weekType) {
    this.weekType = weekType;
  }
  
  
}
