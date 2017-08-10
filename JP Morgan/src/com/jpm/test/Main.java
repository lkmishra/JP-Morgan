/**
 * 
 */
package com.jpm.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lalit Mishra
 * Main class. Setup the data and runs the report.
 */
public class Main {

  /**
   * @param args
   * pass parameter <date> to run the report for that date.
   * pass -CSV parameter to get the trading instruction datas in the csv format.
   */
  public static void main(String[] args) {
    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    
    SetUpData sd = new SetUpData();
    sd.setup();
    Report rpt = new Report();
    rpt.setData(sd);
    
    if (args.length < 1) {
      System.out.println(Setting.usageText);
      System.out.println("Defaulted to date : " + Setting.defaultDate);
      try {
        date = fmt.parse(Setting.defaultDate);
      } catch (ParseException e) {
        System.out.println("Error in default date. " + e.getMessage());
        System.out.println("Exited.");
        System.exit(2);
      }
    } else {
      if (args[0].equalsIgnoreCase("-CSV")) {
        rpt.printCSVData();
        System.exit(0);
      } else {
        if (args[0].matches("\\d{1,2}/\\d{1,2}/\\d{2,4}")) {
          try {
            date = fmt.parse(args[0]);
          } catch (ParseException e) {
            System.out.println("Invalid date." + e.getMessage());
            System.out.println("Please Enteer a valid Date in dd/MM/yyyy format.");
            System.out.println("Exited.");
            System.exit(2);
          }
        } else {
          System.out.println("Invalid parameter or wrong date format.");
          System.out.println(Setting.usageText);
          System.exit(2);
        }
      }
    }
    
    System.out.println("Parameter Date: " + fmt.format(date));
    System.out.println("");
    rpt.print(date);
    
  }

}
