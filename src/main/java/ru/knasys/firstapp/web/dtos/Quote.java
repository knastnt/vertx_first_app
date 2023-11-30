package ru.knasys.firstapp.web.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quote {
  private Asset asset;
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal lastPrice;
  private BigDecimal volume;
}
