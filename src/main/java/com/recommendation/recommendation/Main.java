package com.recommendation.recommendation;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.recommendation.recommendation.Database.ProductRepository;
import com.recommendation.recommendation.Models.Products;
import com.recommendation.recommendation.Services.RecommendationEngine;

public class Main {
    public static void main(String[] args) {
        List<Products> products = ProductRepository.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products found in the database. Please add some products first.");
            return;
        }

        System.out.println("\n Available Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getName() + " - " + products.get(i).getDescription());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("\n Enter the number of the product to get recommendations: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= products.size()) {
                    break;
                } else {
                    System.out.println(" Invalid choice. Please enter a number between 1 and " + products.size());
                }
            } else {
                System.out.println(" Invalid input. Please enter a valid number.");
                scanner.next(); 
            }
        }
        
        Products targetProduct = products.get(choice - 1);
        System.out.println("\n Recommendations for: " + targetProduct.getName());

        List<Map<String, Object>> recommendations = RecommendationEngine.recommend(targetProduct, products);
        
        if (recommendations.isEmpty()) {
            System.out.println(" No recommendations found.");
        } else {
            System.out.println("\n Recommended Products:");
            for (Map<String, Object> rec : recommendations) {
                System.out.println("- " + rec.get("name") + " (" + rec.get("description") + ") [LCS Score: " + rec.get("lcs_score") + "]");
            }
        }

        scanner.close(); 
    }
}
