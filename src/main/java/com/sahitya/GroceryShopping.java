package com.sahitya;

/* I was trying to Build that You book a Bunch of Grocery Items and then I mail you the Receipt but there is a Problem with
 * the Mail ID and All So Sorry About it. Thank You So Much Sir You are a Amazing Teacher. I Really want to Collaborate with 
 * you and make some amazing projects, But Time is the Villan of this Game and Also I am Working on a Startup Community called
 * StartwithSmall, If you seeing this message remember I will approach about this startup in Next sem or this semester only, I don't
 * really Know. But Yes Thank You So Much Sir.*/



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GroceryShopping {

    public static void main(String[] args) {
    	
    	//Defining File Path
        String filePath = "src/main/resources/items.xlsx";
        Map<Integer, Item> items = new HashMap<>();

        // Read items from Excel file
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                try {
                    int itemNumber = (int) row.getCell(0).getNumericCellValue();
                    String itemName = row.getCell(1).getStringCellValue();
                    double price = row.getCell(2).getNumericCellValue();

                    items.put(itemNumber, new Item(itemNumber, itemName, price));
                } catch (Exception e) {
                    System.out.println("Error parsing row " + row.getRowNum() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath + ". Please ensure the file exists and is accessible.");
            return;
        }

        // Display welcome message and available items
        System.out.println("Welcome to Chotta Bazaar!");
        System.out.println("Available items:");
        System.out.printf("%-10s %-20s %-10s%n", "Item No.", "Item Name", "Price");
        for (Item item : items.values()) {
            System.out.printf("%-10d %-20s %-10.2f%n", item.itemNumber, item.itemName, item.price);
        }

        // Shopping logic
        try (Scanner scanner = new Scanner(System.in)) {
            double totalPrice = 0.0;

            while (true) {
                System.out.print("\nEnter item number to add to cart (or 0 to checkout): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid item number.");
                    scanner.next(); // Discard invalid input
                    continue;
                }

                int itemNumber = scanner.nextInt();
                if (itemNumber == 0) break;

                if (!items.containsKey(itemNumber)) {
                    System.out.println("Invalid item number. Try again.");
                    continue;
                }

                System.out.print("Enter quantity: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                    scanner.next(); // Discard invalid input
                    continue;
                }

                int quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than zero. Try again.");
                    continue;
                }

                Item item = items.get(itemNumber);
                double itemTotal = item.price * quantity;
                totalPrice += itemTotal;

                System.out.printf("%d x %s added to cart. Subtotal: %.2f%n", quantity, item.itemName, itemTotal);
            }

            // Print total price
            System.out.printf("Total Price: ", totalPrice);
            System.out.printf("You can Pay at sahityasingh2005@okhdfcbank \nAnytime and Could'nt Get the Items Forever \n");
            System.out.println("Thank you for shopping at Chotta Bazaar!");
        }
    }

    static class Item {
        int itemNumber;
        String itemName;
        double price;

        Item(int itemNumber, String itemName, double price) {
            this.itemNumber = itemNumber;
            this.itemName = itemName;
            this.price = price;
        }
    }
}
