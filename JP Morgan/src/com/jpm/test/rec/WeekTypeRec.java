/**
 * 
 */
package com.jpm.test.rec;

/**
 * @author Lalit Mishra
 *
 */
public class WeekTypeRec {
  private String weekType;
  private int startDay;
  private int endDay;

  /**
   * 
   */
  public WeekTypeRec() {
  }
 
  /**
   * @param weekType
   * @param startDay
   * @param endDay
   */
  public WeekTypeRec(String weekType, int startDay, int endDay) {
    this.weekType = weekType;
    this.startDay = startDay;
    this.endDay = endDay;
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
  
  /**
   * @return the startDay
   */
  public int getStartDay() {
    return startDay;
  }
  
  /**
   * @param startDay the startDay to set
   */
  public void setStartDay(int startDay) {
    this.startDay = startDay;
  }
  
  /**
   * @return the endDay
   */
  public int getEndDay() {
    return endDay;
  }
  
  /**
   * @param endDay the endDay to set
   */
  public void setEndDay(int endDay) {
    this.endDay = endDay;
  }
  
}
