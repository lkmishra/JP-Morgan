/**
 * 
 */
package com.jpm.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jpm.test.rec.CurrencyRateRec;
import com.jpm.test.rec.CurrencyRec;
import com.jpm.test.rec.TradeInstructionRec;
import com.jpm.test.rec.WeekTypeRec;

/**
 * @author Lalit Mishra
 * This class sets up static data in the memory to run the report.
 * Please feel free to change and add more data to test. Be aware you may have to
 * adjust the values of some final variables in the top of the SetUpDataTest class
 * or it may fail some tests.
 */
public class SetUpData {
  private WeekTypeRec[] weekTypes = new WeekTypeRec[2];
  private CurrencyRec[] currency = new CurrencyRec[10];
  private CurrencyRateRec[] currencyRate = new CurrencyRateRec[10];
  private TradeInstructionRec[] tradeInstruction = new TradeInstructionRec[40];
  
  /**
   * Set up Week Type Data.
   * D - default
   * A - Arab 
   */
  public void setWeekType() {
    weekTypes[0] = new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY);
    weekTypes[1] = new WeekTypeRec("A", Calendar.SUNDAY, Calendar.THURSDAY);
  }
  
  /**
   * @param weekType
   * Returns the WeekTypeRec class for a weekType parameter. 
   */
  private WeekTypeRec getWeekType(String weekType) {
    WeekTypeRec[] wt = Arrays.stream(weekTypes).filter(x -> x.getWeekType().equals(weekType)).toArray(WeekTypeRec[]::new);
    if (wt.length > 0)
      return wt[0];
    else
      return null;
  }
  
  /**
   * @param curr - Currency code
   * Returns the WeekTypeRec class for a Currency code. 
   */
  public WeekTypeRec getCurrencyWeekType(String curr) {
    return getWeekType(getCurrencyType(curr));
  }
  
  /**
   * Sets Currency code and WeekType for a Currency. 
   */
  public void setCurrency() {
    currency[0] = new CurrencyRec("AED", "A");
    currency[1] = new CurrencyRec("SAR", "A");
    currency[2] = new CurrencyRec("GBP", "D");
    currency[3] = new CurrencyRec("SGP", "D");
    currency[4] = new CurrencyRec("USD", "D");
    currency[5] = new CurrencyRec("JPY", "D");
    currency[6] = new CurrencyRec("INR", "D");
    currency[7] = new CurrencyRec("EUR", "D");
    currency[8] = new CurrencyRec("CNY", "D");
    currency[9] = new CurrencyRec("AUD", "D");
  }
  
  /**
   * @param curr - Currency code
   * Get Currency WeekType for a Currency code parameter. 
   */
  public String getCurrencyType(String curr) {
    if (curr.isEmpty()) return "";
    
    CurrencyRec[] ct = Arrays.stream(currency).filter(x -> x.getCurrency().equals(curr)).toArray(CurrencyRec[]::new);
    if (ct.length > 0)
      return ct[0].getWeekType();
    else
      return "";
  }
  
  
  /**
   * Sets default exchange rate for a Currency code. 
   */
  public void setCurrencyRate() {
    currencyRate[0] = new CurrencyRateRec("AED", 0.22);
    currencyRate[1] = new CurrencyRateRec("SAR", 0.27);
    currencyRate[2] = new CurrencyRateRec("GBP", 1.30);
    currencyRate[3] = new CurrencyRateRec("SGP", 0.50);
    currencyRate[4] = new CurrencyRateRec("USD", 1.0);
    currencyRate[5] = new CurrencyRateRec("JPY", 0.009);
    currencyRate[6] = new CurrencyRateRec("INR", 0.016);
    currencyRate[7] = new CurrencyRateRec("EUR", 1.18);
    currencyRate[8] = new CurrencyRateRec("CNY", 0.15);
    currencyRate[9] = new CurrencyRateRec("AUD", 0.79);
  }
  
  /**
   * Get all the Currency Rates defined in the application. 
   */
  public CurrencyRateRec[] getCurrencyRates() {
    return currencyRate;
  }
  
  /**
   * @param curr - Currency code
   * Get Currency Rates for a currency parameter. 
   */
  public Double getFx(String curr) {
    CurrencyRateRec[] cr = Arrays.stream(currencyRate).filter(x -> x.getCurrency().equals(curr)).toArray(CurrencyRateRec[]::new);
    if (cr.length > 0)
      return cr[0].getRate();
    else
      return 0.0;
  }
  
  /**
   * @param curr - Currency code
   * @param settlementDate
   * Fix the settlement date for a settlement date.
   * If the date is in a weekend, it will change to the next working day. 
   */
  public Date fixSettlementDate(String curr, Date settlementDate) {
    if (curr.isEmpty())  return null;
    
    WeekTypeRec wt = getCurrencyWeekType(curr);
    if (wt == null) return null;
    
    Calendar c = Calendar.getInstance();
    c.setTime(settlementDate);
    
    if (wt.getStartDay() <= c.get(Calendar.DAY_OF_WEEK) && wt.getEndDay() >= c.get(Calendar.DAY_OF_WEEK))
      return settlementDate;
    else
      if (c.get(Calendar.DAY_OF_WEEK) < wt.getStartDay()) {
        c.add(Calendar.DATE, wt.getStartDay() - c.get(Calendar.DAY_OF_WEEK));
      } else {
        c.add(Calendar.DATE, Calendar.DAY_OF_WEEK + wt.getStartDay() - c.get(Calendar.DAY_OF_WEEK));
      }
      return  c.getTime();
  }
  
  /**
   * Set up Trading Instructions required for printing the report.
   */
  public void setTradeInstruction() {
    String curr;
    curr = "SGP";
    tradeInstruction[0] = new TradeInstructionRec("foo", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 1).getTime(), new GregorianCalendar(2016, 0, 2).getTime(), 200, 100.25);
    curr = "AED";
    tradeInstruction[1] = new TradeInstructionRec("bar", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 450, 150.5);
    curr = "SAR";
    tradeInstruction[2] = new TradeInstructionRec("anz", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 1).getTime(), new GregorianCalendar(2016, 0, 2).getTime(), 100, 100.0);
    curr = "GBP";
    tradeInstruction[3] = new TradeInstructionRec("tar", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 500, 50.5);
    curr = "USD";
    tradeInstruction[4] = new TradeInstructionRec("loo", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 2).getTime(), new GregorianCalendar(2016, 0, 4).getTime(), 270, 180.0);
    curr = "JPY";
    tradeInstruction[5] = new TradeInstructionRec("car", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 350, 10.5);
    curr = "INR";
    tradeInstruction[6] = new TradeInstructionRec("sco", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 3).getTime(), new GregorianCalendar(2016, 0, 5).getTime(), 500, 410.5);
    curr = "EUR";
    tradeInstruction[7] = new TradeInstructionRec("far", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 1000, 50.5);
    curr = "CNY";
    tradeInstruction[8] = new TradeInstructionRec("fox", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 4).getTime(), new GregorianCalendar(2016, 0, 5).getTime(), 230, 500.25);
    curr = "AUD";
    tradeInstruction[9] = new TradeInstructionRec("nat", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 620, 550.5);
    curr = "SGP";
    tradeInstruction[10] = new TradeInstructionRec("sat", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 6).getTime(), new GregorianCalendar(2016, 0, 9).getTime(), 300, 800.25);
    curr = "AED";
    tradeInstruction[11] = new TradeInstructionRec("sar", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 500, 450.5);
    curr = "SAR";
    tradeInstruction[12] = new TradeInstructionRec("dor", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 7).getTime(), new GregorianCalendar(2016, 0, 9).getTime(), 100, 600.25);
    curr = "GBP";
    tradeInstruction[13] = new TradeInstructionRec("mar", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 400, 650.5);
    curr = "USD";
    tradeInstruction[14] = new TradeInstructionRec("too", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 8).getTime(), new GregorianCalendar(2016, 0, 9).getTime(), 260, 700.25);
    curr = "JPY";
    tradeInstruction[15] = new TradeInstructionRec("par", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 950, 20.5);
    curr = "INR";
    tradeInstruction[16] = new TradeInstructionRec("fsp", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 9).getTime(), new GregorianCalendar(2016, 0, 11).getTime(), 750, 600.25);
    curr = "EUR";
    tradeInstruction[17] = new TradeInstructionRec("mer", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 100, 505.0);
    curr = "CNY";
    tradeInstruction[18] = new TradeInstructionRec("bmw", "B", getFx(curr), curr, new GregorianCalendar(2016, 0, 10).getTime(), new GregorianCalendar(2016, 0, 12).getTime(), 50, 10.0);
    curr = "AUD";
    tradeInstruction[19] = new TradeInstructionRec("ofl", "S", getFx(curr), curr, new GregorianCalendar(2016, 0, 5).getTime(), new GregorianCalendar(2016, 0, 7).getTime(), 280, 50.15);

    curr = "SGP";
    tradeInstruction[20] = new TradeInstructionRec("foo", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 1).getTime(), new GregorianCalendar(2017, 0, 2).getTime(), 200, 100.25);
    curr = "AED";
    tradeInstruction[21] = new TradeInstructionRec("bar", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 450, 150.5);
    curr = "SAR";
    tradeInstruction[22] = new TradeInstructionRec("anz", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 100, 100.0);
    curr = "GBP";
    tradeInstruction[23] = new TradeInstructionRec("tar", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 500, 50.5);
    curr = "USD";
    tradeInstruction[24] = new TradeInstructionRec("loo", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 270, 180.0);
    curr = "JPY";
    tradeInstruction[25] = new TradeInstructionRec("car", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 350, 10.5);
    curr = "INR";
    tradeInstruction[26] = new TradeInstructionRec("sco", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 500, 410.5);
    curr = "EUR";
    tradeInstruction[27] = new TradeInstructionRec("far", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 1000, 50.5);
    curr = "CNY";
    tradeInstruction[28] = new TradeInstructionRec("fox", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 230, 500.25);
    curr = "AUD";
    tradeInstruction[29] = new TradeInstructionRec("nat", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 620, 550.5);
    curr = "SGP";
    tradeInstruction[30] = new TradeInstructionRec("sat", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 300, 800.25);
    curr = "AED";
    tradeInstruction[31] = new TradeInstructionRec("sar", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 500, 450.5);
    curr = "SAR";
    tradeInstruction[32] = new TradeInstructionRec("dor", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 100, 600.25);
    curr = "GBP";
    tradeInstruction[33] = new TradeInstructionRec("mar", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 400, 650.5);
    curr = "USD";
    tradeInstruction[34] = new TradeInstructionRec("too", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 260, 700.25);
    curr = "JPY";
    tradeInstruction[35] = new TradeInstructionRec("par", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 950, 20.5);
    curr = "INR";
    tradeInstruction[36] = new TradeInstructionRec("fsp", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 750, 600.25);
    curr = "EUR";
    tradeInstruction[37] = new TradeInstructionRec("mer", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 100, 505.0);
    curr = "CNY";
    tradeInstruction[38] = new TradeInstructionRec("bmw", "B", getFx(curr), curr, new GregorianCalendar(2017, 0, 10).getTime(), new GregorianCalendar(2017, 0, 12).getTime(), 50, 10.0);
    curr = "AUD";
    tradeInstruction[39] = new TradeInstructionRec("ofl", "S", getFx(curr), curr, new GregorianCalendar(2017, 0, 5).getTime(), new GregorianCalendar(2017, 0, 7).getTime(), 280, 50.15);
  
  }
  
  /**
   * Set up all the data.
   */
  public void setup() {
    setCurrency();
    setCurrencyRate();
    setWeekType();
    setTradeInstruction();
  }
  
  /**
   * Get all the Trading Instructions.
   */
  public TradeInstructionRec[] getTradeInstructions() {
    return tradeInstruction;
  }
  
  /**
   * @param dt - Actual Settlement Date
   * Get Trading Instructions for a date.
   */
  public TradeInstructionRec[] getTradeInstructions(Date dt) {
    return Arrays.stream(tradeInstruction).filter(x -> fixSettlementDate(x.getCurrency(), x.getSettlementDate()).equals(dt)).toArray(TradeInstructionRec[]::new);
  }
  
  /**
   * @param dt - Actual Settlement Date
   * @param buySell - 'B' for Buy, 'S' for Sell
   * Get Trading Instructions for a date and Buy or Sell.
   */
  public TradeInstructionRec[] getTradeInstructions(Date dt, String buySell) {
    return Arrays.stream(tradeInstruction).filter(x -> x.getBuySell().equals(buySell) && fixSettlementDate(x.getCurrency(), x.getSettlementDate()).equals(dt)).toArray(TradeInstructionRec[]::new);
  }
  
  /**
   * @param buySell - 'B' for Buy, 'S' for Sell
   * Get Trading Instructions for Buy or Sell.
   */
  public TradeInstructionRec[] getTradeInstructions(String buySell) {
    return Arrays.stream(tradeInstruction).filter(x -> x.getBuySell().equals(buySell)).toArray(TradeInstructionRec[]::new);
  }
}
