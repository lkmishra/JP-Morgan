/**
 * 
 */
package com.jpm.test.rec;

import java.util.Date;

/**
 * @author Lalit Mishra
 *
 */
public class TradeInstructionRec implements Comparable<TradeInstructionRec> {
  private String entity;
  private String buySell;
  private Double agreedFx;
  private String currency;
  private Date instructionDate;
  private Date settlementDate;
  private long units;
  private Double unitPrice;

  /**
   * @param entity
   * @param buySell
   * @param agreedFx
   * @param currency
   * @param instructionDate
   * @param settlementDate
   * @param units
   * @param unitPrice
   */
  public TradeInstructionRec(String entity, String buySell, Double agreedFx, String currency, Date instructionDate,
      Date settlementDate, long units, Double unitPrice) {
    this.entity = entity;
    this.buySell = buySell;
    this.agreedFx = agreedFx;
    this.currency = currency;
    this.instructionDate = instructionDate;
    this.settlementDate = settlementDate;
    this.units = units;
    this.unitPrice = unitPrice;
  }

  /**
   * 
   */
  public TradeInstructionRec() {
  }

  /**
   * @return the entity
   */
  public String getEntity() {
    return entity;
  }
  
  /**
   * @param entity the entity to set
   */
  public void setEntity(String entity) {
    this.entity = entity;
  }
  
  /**
   * @return the buySell
   */
  public String getBuySell() {
    return buySell;
  }
  
  /**
   * @param buySell the buySell to set
   */
  public void setBuySell(String buySell) {
    this.buySell = buySell;
  }
  
  /**
   * @return the agreedFx
   */
  public Double getAgreedFx() {
    return agreedFx;
  }
  
  /**
   * @param agreedFx the agreedFx to set
   */
  public void setAgreedFx(Double agreedFx) {
    this.agreedFx = agreedFx;
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
   * @return the instructionDate
   */
  public Date getInstructionDate() {
    return instructionDate;
  }
  
  /**
   * @param instructionDate the instructionDate to set
   */
  public void setInstructionDate(Date instructionDate) {
    this.instructionDate = instructionDate;
  }
  
  /**
   * @return the settlementDate
   */
  public Date getSettlementDate() {
    return settlementDate;
  }
  
  /**
   * @param settlementDate the settlementDate to set
   */
  public void setSettlementDate(Date settlementDate) {
    this.settlementDate = settlementDate;
  }
  
  /**
   * @return the units
   */
  public long getUnits() {
    return units;
  }
  
  /**
   * @param units the units to set
   */
  public void setUnits(long units) {
    this.units = units;
  }
  
  /**
   * @return the unitPrice
   */
  public Double getUnitPrice() {
    return unitPrice;
  }
  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }
  
  /**
   * Get amount of a trade in Base currency.
   */
 
  public Double getTradeBaseAmount() {
    return unitPrice * units * agreedFx;
  }
  
  /**
   * Get amount of a trade in transaction currency.
   */
 
  public Double getTradeAmount() {
    return unitPrice * units;
  }

  @Override
  public int compareTo(TradeInstructionRec tradeInst) {
    return getTradeBaseAmount().compareTo(tradeInst.getTradeBaseAmount());
  }
}
