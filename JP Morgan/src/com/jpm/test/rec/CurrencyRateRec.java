/**
 * 
 */
package com.jpm.test.rec;

/**
 * @author Lalit Mishra
 * This class keeps information for a currency rate.
 */
public class CurrencyRateRec {
  private String currency;
  private Double rate;

  /**
   * 
   */
  public CurrencyRateRec() {
  }

  /**
   * @param currency
   * @param rate
   */
  public CurrencyRateRec(String currency, Double rate) {
    this.currency = currency;
    this.rate = rate;
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
   * @return the rate
   */
  public Double getRate() {
    return rate;
  }

  /**
   * @param rate the rate to set
   */
  public void setRate(Double rate) {
    this.rate = rate;
  }
  
}
