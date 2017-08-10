package com.jpm.junit;

/**
 * @author Lalit Mishra
 * JUnit tests to test the SetUpData class.
 */

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.jpm.test.SetUpData;
import com.jpm.test.rec.TradeInstructionRec;
import com.jpm.test.rec.WeekTypeRec;

public class SetUpDataTest {
  private final int totTradeInstructionRows = 40;
  private final int tradeRows4116 = 2;  // Total TradeInstruction rows for date 04/01/2016.
  private final int totTradeRowsBuy = 20;  // Total TradeInstruction rows for Buy.
  private final int totTradeRowsSell = 20;  // Total TradeInstruction rows for Sell.
  
  SetUpData sd = new SetUpData();

  @Test
  public void testCurrencyRate() {
    sd.setCurrencyRate();
    
    assertTrue(sd.getFx("XXX") == 0.0);
    assertTrue(sd.getFx("AED") == 0.22);
    assertTrue(sd.getFx("SAR") == 0.27);
    assertTrue(sd.getFx("GBP") == 1.30);
    assertTrue(sd.getFx("SGP") == 0.50);
    assertTrue(sd.getFx("USD") == 1.0);
    assertTrue(sd.getFx("JPY") == 0.009);
    assertTrue(sd.getFx("INR") == 0.016);
    assertTrue(sd.getFx("EUR") == 1.18);
    assertTrue(sd.getFx("CNY") == 0.15);
    assertTrue(sd.getFx("AUD") == 0.79);
  }
  
  private boolean compareWeekType(WeekTypeRec wt1, WeekTypeRec wt2) {
    if (wt1.getWeekType().equals(wt2.getWeekType())
        && wt1.getStartDay() == wt2.getStartDay()
        && wt1.getEndDay() == wt2.getEndDay()) {
      return true;
    }
    
    return false;
  }
  
  @Test
  public void testWeekType() {
    sd.setCurrency();
    sd.setWeekType();

    assertTrue(compareWeekType(sd.getCurrencyWeekType("AED"), new WeekTypeRec("A", Calendar.SUNDAY, Calendar.THURSDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("SAR"), new WeekTypeRec("A", Calendar.SUNDAY, Calendar.THURSDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("GBP"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("SGP"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("USD"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("JPY"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("INR"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("EUR"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("CNY"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(compareWeekType(sd.getCurrencyWeekType("AUD"), new WeekTypeRec("D", Calendar.MONDAY, Calendar.FRIDAY)));
    assertTrue(sd.getCurrencyWeekType("XXX") == null);
  }

  @Test
  public void testCurrencyType() {
    sd.setCurrency();
    
    assertTrue(sd.getCurrencyType("XXX").equals(""));
    assertTrue(sd.getCurrencyType("AED").equals("A"));
    assertTrue(sd.getCurrencyType("SAR").equals("A"));
    assertTrue(sd.getCurrencyType("GBP").equals("D"));
    assertTrue(sd.getCurrencyType("SGP").equals("D"));
    assertTrue(sd.getCurrencyType("USD").equals("D"));
    assertTrue(sd.getCurrencyType("JPY").equals("D"));
    assertTrue(sd.getCurrencyType("INR").equals("D"));
    assertTrue(sd.getCurrencyType("EUR").equals("D"));
    assertTrue(sd.getCurrencyType("CNY").equals("D"));
    assertTrue(sd.getCurrencyType("AUD").equals("D"));
  }
  
  private Date getDate(int d, int m, int y) {
    return new GregorianCalendar(y, m - 1, d).getTime();
  }
  
  @Test
  public void testfixSettlementDate() {
    sd.setCurrency();
    sd.setWeekType();

    assertTrue(sd.fixSettlementDate("XXX", getDate(1, 1, 2016)) == null);
    assertTrue(sd.fixSettlementDate("AED", getDate(1, 1, 2016)).compareTo(getDate(3, 1, 2016)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(6, 8, 2017)).compareTo(getDate(6, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(7, 8, 2017)).compareTo(getDate(7, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(8, 8, 2017)).compareTo(getDate(8, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(9, 8, 2017)).compareTo(getDate(9, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(10, 8, 2017)).compareTo(getDate(10, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(11, 8, 2017)).compareTo(getDate(13, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(12, 8, 2017)).compareTo(getDate(13, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AED", getDate(13, 8, 2017)).compareTo(getDate(13, 8, 2017)) == 0);
    
    assertTrue(sd.fixSettlementDate("SAR", getDate(1, 1, 2016)).compareTo(getDate(3, 1, 2016)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(16, 8, 2017)).compareTo(getDate(16, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(17, 8, 2017)).compareTo(getDate(17, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(18, 8, 2017)).compareTo(getDate(20, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(19, 8, 2017)).compareTo(getDate(20, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(20, 8, 2017)).compareTo(getDate(20, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(21, 8, 2017)).compareTo(getDate(21, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(22, 8, 2017)).compareTo(getDate(22, 8, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("SAR", getDate(24, 8, 2017)).compareTo(getDate(24, 8, 2017)) == 0);
    
    assertTrue(sd.fixSettlementDate("GBP", getDate(1, 1, 2016)).compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(16, 7, 2017)).compareTo(getDate(17, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(17, 7, 2017)).compareTo(getDate(17, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(18, 7, 2017)).compareTo(getDate(18, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(19, 7, 2017)).compareTo(getDate(19, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(20, 7, 2017)).compareTo(getDate(20, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(21, 7, 2017)).compareTo(getDate(21, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(22, 7, 2017)).compareTo(getDate(24, 7, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("GBP", getDate(23, 7, 2017)).compareTo(getDate(24, 7, 2017)) == 0);
    
    assertTrue(sd.fixSettlementDate("USD", getDate(1, 1, 2016)).compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(16, 6, 2017)).compareTo(getDate(16, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(17, 6, 2017)).compareTo(getDate(19, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(18, 6, 2017)).compareTo(getDate(19, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(19, 6, 2017)).compareTo(getDate(19, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(20, 6, 2017)).compareTo(getDate(20, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(21, 6, 2017)).compareTo(getDate(21, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(22, 6, 2017)).compareTo(getDate(22, 6, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("USD", getDate(24, 6, 2017)).compareTo(getDate(26, 6, 2017)) == 0);
    
    assertTrue(sd.fixSettlementDate("SGP", getDate(1, 1, 2016)).compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(sd.fixSettlementDate("JPY", getDate(16, 5, 2017)).compareTo(getDate(16, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("INR", getDate(17, 5, 2017)).compareTo(getDate(17, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("EUR", getDate(18, 5, 2017)).compareTo(getDate(18, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("CNY", getDate(19, 5, 2017)).compareTo(getDate(19, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("CNY", getDate(20, 5, 2017)).compareTo(getDate(22, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AUD", getDate(21, 5, 2017)).compareTo(getDate(22, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AUD", getDate(22, 5, 2017)).compareTo(getDate(22, 5, 2017)) == 0);
    assertTrue(sd.fixSettlementDate("AUD", getDate(24, 5, 2017)).compareTo(getDate(24, 5, 2017)) == 0);
  }
  
  @Test
  public void testgetTradeInstructions() {
    sd.setup();
    
    TradeInstructionRec[] trd = sd.getTradeInstructions();
    assertTrue(trd.length == totTradeInstructionRows);
    
    assertTrue(trd[0].getEntity() == "foo");
    assertTrue(trd[0].getBuySell() == "B");
    assertTrue(trd[0].getAgreedFx() == 0.5);
    assertTrue(trd[0].getCurrency() == "SGP");
    assertTrue(trd[0].getInstructionDate().compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(trd[0].getSettlementDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[0].getUnits() == 200);
    assertTrue(trd[0].getUnitPrice() == 100.25);
    assertTrue(trd[0].getTradeAmount() == trd[0].getUnits() * trd[0].getUnitPrice());
    assertTrue(trd[0].getTradeBaseAmount() == trd[0].getUnits() * trd[0].getUnitPrice() * trd[0].getAgreedFx());
    
    assertTrue(trd[1].getEntity() == "bar");
    assertTrue(trd[1].getBuySell() == "S");
    assertTrue(trd[1].getAgreedFx() == 0.22);
    assertTrue(trd[1].getCurrency() == "AED");
    assertTrue(trd[1].getInstructionDate().compareTo(getDate(5, 1, 2016)) == 0);
    assertTrue(trd[1].getSettlementDate().compareTo(getDate(7, 1, 2016)) == 0);
    assertTrue(trd[1].getUnits() == 450);
    assertTrue(trd[1].getUnitPrice() == 150.5);
    assertTrue(trd[1].getTradeAmount() == trd[1].getUnits() * trd[1].getUnitPrice());
    assertTrue(trd[1].getTradeBaseAmount() == trd[1].getUnits() * trd[1].getUnitPrice() * trd[1].getAgreedFx());
  }
  
  @Test
  public void testgetTradeInstructions1() {
    sd.setup();
    
    TradeInstructionRec[] trd = sd.getTradeInstructions(getDate(4, 1, 2016));
    assertTrue(trd.length == tradeRows4116);
    
    assertTrue(trd[0].getEntity() == "foo");
    assertTrue(trd[0].getBuySell() == "B");
    assertTrue(trd[0].getAgreedFx() == 0.5);
    assertTrue(trd[0].getCurrency() == "SGP");
    assertTrue(trd[0].getInstructionDate().compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(trd[0].getSettlementDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[0].getUnits() == 200);
    assertTrue(trd[0].getUnitPrice() == 100.25);
    assertTrue(trd[0].getTradeAmount() == trd[0].getUnits() * trd[0].getUnitPrice());
    assertTrue(trd[0].getTradeBaseAmount() == trd[0].getUnits() * trd[0].getUnitPrice() * trd[0].getAgreedFx());
    
    assertTrue(trd[1].getEntity() == "loo");
    assertTrue(trd[1].getBuySell() == "B");
    assertTrue(trd[1].getAgreedFx() == 1);
    assertTrue(trd[1].getCurrency() == "USD");
    assertTrue(trd[1].getInstructionDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[1].getSettlementDate().compareTo(getDate(4, 1, 2016)) == 0);
    assertTrue(trd[1].getUnits() == 270);
    assertTrue(trd[1].getUnitPrice() == 180);
    assertTrue(trd[1].getTradeAmount() == trd[1].getUnits() * trd[1].getUnitPrice());
    assertTrue(trd[1].getTradeBaseAmount() == trd[1].getUnits() * trd[1].getUnitPrice() * trd[1].getAgreedFx());
  }
  
  @Test
  public void testgetTradeInstructions2() {
    sd.setup();
    
    TradeInstructionRec[] trd = sd.getTradeInstructions(getDate(4, 1, 2016), "S");
    assertTrue(trd.length == 0);
    
    trd = sd.getTradeInstructions(getDate(4, 1, 2016), "B");
    assertTrue(trd.length == 2);
    
    assertTrue(trd[0].getEntity() == "foo");
    assertTrue(trd[0].getBuySell() == "B");
    assertTrue(trd[0].getAgreedFx() == 0.5);
    assertTrue(trd[0].getCurrency() == "SGP");
    assertTrue(trd[0].getInstructionDate().compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(trd[0].getSettlementDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[0].getUnits() == 200);
    assertTrue(trd[0].getUnitPrice() == 100.25);
    assertTrue(trd[0].getTradeAmount() == trd[0].getUnits() * trd[0].getUnitPrice());
    assertTrue(trd[0].getTradeBaseAmount() == trd[0].getUnits() * trd[0].getUnitPrice() * trd[0].getAgreedFx());
    
    assertTrue(trd[1].getEntity() == "loo");
    assertTrue(trd[1].getBuySell() == "B");
    assertTrue(trd[1].getAgreedFx() == 1);
    assertTrue(trd[1].getCurrency() == "USD");
    assertTrue(trd[1].getInstructionDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[1].getSettlementDate().compareTo(getDate(4, 1, 2016)) == 0);
    assertTrue(trd[1].getUnits() == 270);
    assertTrue(trd[1].getUnitPrice() == 180);
    assertTrue(trd[1].getTradeAmount() == trd[1].getUnits() * trd[1].getUnitPrice());
    assertTrue(trd[1].getTradeBaseAmount() == trd[1].getUnits() * trd[1].getUnitPrice() * trd[1].getAgreedFx());
  }
  
  @Test
  public void testgetTradeInstructions3() {
    sd.setup();
    
    TradeInstructionRec[] trd = sd.getTradeInstructions("B");
    assertTrue(trd.length == totTradeRowsBuy);
    
    assertTrue(trd[0].getEntity() == "foo");
    assertTrue(trd[0].getBuySell() == "B");
    assertTrue(trd[0].getAgreedFx() == 0.5);
    assertTrue(trd[0].getCurrency() == "SGP");
    assertTrue(trd[0].getInstructionDate().compareTo(getDate(1, 1, 2016)) == 0);
    assertTrue(trd[0].getSettlementDate().compareTo(getDate(2, 1, 2016)) == 0);
    assertTrue(trd[0].getUnits() == 200);
    assertTrue(trd[0].getUnitPrice() == 100.25);
    assertTrue(trd[0].getTradeAmount() == trd[0].getUnits() * trd[0].getUnitPrice());
    assertTrue(trd[0].getTradeBaseAmount() == trd[0].getUnits() * trd[0].getUnitPrice() * trd[0].getAgreedFx());
    
    trd = sd.getTradeInstructions("S");
    assertTrue(trd.length == totTradeRowsSell);
    
    assertTrue(trd[0].getEntity() == "bar");
    assertTrue(trd[0].getBuySell() == "S");
    assertTrue(trd[0].getAgreedFx() == 0.22);
    assertTrue(trd[0].getCurrency() == "AED");
    assertTrue(trd[0].getInstructionDate().compareTo(getDate(5, 1, 2016)) == 0);
    assertTrue(trd[0].getSettlementDate().compareTo(getDate(7, 1, 2016)) == 0);
    assertTrue(trd[0].getUnits() == 450);
    assertTrue(trd[0].getUnitPrice() == 150.5);
    assertTrue(trd[0].getTradeAmount() == trd[0].getUnits() * trd[0].getUnitPrice());
    assertTrue(trd[0].getTradeBaseAmount() == trd[0].getUnits() * trd[0].getUnitPrice() * trd[0].getAgreedFx());
  }
}
