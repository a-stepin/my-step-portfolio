package com.google.sps.data;

/** Represents data regarding power generation in India. */
public class IndianPowerData {
  private String name;
  private int area;
  private String region;
  private double percent;
 
  public IndianPowerData(String name, int area, double percent, String region) {
    this.name = name;
    this.area = area;
    this.region = region;
    this.percent = percent;
  }
}