/**
 * 
 */
package com.jpm.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import com.jpm.test.rec.TradeInstructionRec;
/**
 * @author Lalit Mishra
 * This class prints the report. You need to pass the SetUpData objects.
 * The report titles and date formats can be changed by changing the final variables in the top.
 */
public class Report {
  private final String incomingReportTitle = "Amount in USD settled incoming everyday";
  private final String outgoingReportTitle = "Amount in USD settled outgoing everyday";
  private final int repWidth = 140;
  private final SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM yyyy");
  
  private SetUpData data;

  /**
   * @param data
   */
  public Report(SetUpData data) {
    this.data = data;
  }

  /**
   * 
   */
  public Report() {
  }

  /**
   * @return the data
   */
  public SetUpData getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(SetUpData data) {
    this.data = data;
  }
  
  /**
   * @param str - character to repeat
   * @param times - no of times to repeat the character
   * Repeat the same character <times> no of times and return the string. 
   */
  private String repeat(String str, int times) {
    return String.join("", Collections.nCopies((times <= 0)? 1:times, str));
  }

  /**
   * @param title
   * Prints the title and header columns of the report.
   */
  private void printTitle(String title) {
    System.out.println("┌" + repeat("─", repWidth - 2) + "┐");
    System.out.format("│%-" + (repWidth - 2) + "s│\n", title);
    System.out.println("├────────┬───────────────┬───────────────────────────────┬────────┬────────┬───────┬──────────────┬────────────────────────────────────────┤");
    System.out.println("│        │               │ Settlement Date               │        │        │       │              │Trading amount in                       │");
    System.out.println("│        │ Instruction   ├───────────────┬───────────────┤        │        │       ├──────────────┼─────────────────────┬──────────────────┤");
    System.out.println("│ Entity │ Date          │ Requested     │ Actual        │Currency│AgreedFx│ Units │Price per Unit│Transactinal Currency│Base Currency(USD)│");
  }
  
  /**
   * Draws lines between the rows of the report.
   */
  private String getRowLine() {
    return "├────────┼───────────────┼───────────────┼───────────────┼────────┼────────┼───────┼──────────────┼─────────────────────┼──────────────────┤";
  }
  
  /**
   * Draws the last line of the report.
   */
  private String getLastLine() {
    return "└────────┴───────────────┴───────────────┴───────────────┴────────┴────────┴───────┴──────────────┴─────────────────────┴──────────────────┘";
  }
  
  
  /**
   * @param tr - TradingInstructionRec
   * Draws the row of the report from the class parameter.
   */
  private String getFormattedRow(TradeInstructionRec tr) {
    return String.format("│%-8s│%-15s│%-15s│%-15s│%-8s│%,8.2f│%,7d│%,14.2f│%,21.2f│%,18.2f│", tr.getEntity(), df.format(tr.getInstructionDate()),df.format(tr.getSettlementDate()),df.format(data.fixSettlementDate(tr.getCurrency(), tr.getSettlementDate())),
              tr.getCurrency(), tr.getAgreedFx(), tr.getUnits(), tr.getUnitPrice(), tr.getTradeAmount(), tr.getTradeBaseAmount());
  }
  
  /**
   * @param reportDate
   * @param buySell
   * Prints the incoming or outgoing report in a tabular format depending on the parameters.
   */
  private void printReport(Date reportDate, String buySell) {
    String title = (buySell.equals("B"))? outgoingReportTitle: incomingReportTitle;
    TradeInstructionRec[] tradeInst = data.getTradeInstructions(reportDate, buySell);
    
    if (tradeInst.length <= 0) {
      System.out.println(" *** No data to display for " + title + " ***");
      return;
    }
    Arrays.sort(tradeInst, Collections.reverseOrder());
    
    printTitle(title);
    
    for (TradeInstructionRec tr: tradeInst) {
      System.out.println(getRowLine());
      System.out.println(getFormattedRow(tr));
    }
    System.out.println(getLastLine());
  }
  
  /**
   * @param reportDate
   * Prints the incoming report in a tabular format for a date.
   */
  public void printBuyReport(Date reportDate) {
    printReport(reportDate, "B");
  }
  
  
  /**
   * @param reportDate
   * Prints the outgoing report in a tabular format for a date.
   */
  public void printSellReport(Date reportDate) {
    printReport(reportDate, "S");
  }
  
  /**
   * @param reportDate
   * Prints the all the reports in a tabular format for a date.
   */
  public void print(Date reportDate) {
    printSellReport(reportDate);
    System.out.println();
    System.out.println();
    printBuyReport(reportDate);
  }

  /**
   * Prints the reports in a tabular format for all the data in TradeInstructions.
   */
  public void printAllReport() {
    TradeInstructionRec[] tradeInst = data.getTradeInstructions();
    printTitle(incomingReportTitle);
    for (TradeInstructionRec tr: tradeInst) {
      System.out.println(getRowLine());
      System.out.println(getFormattedRow(tr) + tr.getBuySell());
    }
    System.out.println(getLastLine());
  }

  /**
   * Prints all the data in TradeInstructions in CSV format.
   */
  public void printCSVData() {
    TradeInstructionRec[] tradeInst = data.getTradeInstructions();
    System.out.println("Entity, Buy/Sell, AgreedFx, Currency, Instruction Date, Settlement Date, Actual Settlement Date,  Units, Price per unit, Trade Amount, Trade Base Amount(USD)");
    for (TradeInstructionRec tr: tradeInst) {
      System.out.println(tr.getEntity() + ", " + tr.getBuySell() + ", " + tr.getAgreedFx() + ", " + tr.getCurrency() + ", " + df.format(tr.getInstructionDate()) + ", "
          + df.format(tr.getSettlementDate()) + ", " + df.format(data.fixSettlementDate(tr.getCurrency(), tr.getSettlementDate())) + ", " + tr.getUnits() + ", " + tr.getUnitPrice() 
          + ", " + tr.getTradeAmount() + ", " + tr.getTradeBaseAmount());
    }
  }
}
