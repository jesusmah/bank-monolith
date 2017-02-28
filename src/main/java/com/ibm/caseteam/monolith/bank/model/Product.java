package com.ibm.caseteam.monolith.bank.model;

import javax.persistence.*;

@Entity
public class Product {

  @Id
  private String name;
  private String description;


  public void setName(String name){
      this.name = name;
  }

  public void setDescription(String description){
      this.description = description;
  }

  public String getName(){
    return this.name;
  }

  public String getDescription(){
    return this.description;
  }

  public String toString(){
    return "Name: " + this.name + ", Description: " + this.description;
  }
}
