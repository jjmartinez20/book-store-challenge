package com.jjmp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBookDTO {

	private long bookId;

	private List<LocalDate> sales = new ArrayList<>();

	private double totalRevenue = 0;

	private List<String> customers = new ArrayList<>();

}
