// package com.recommendation.recommendation.Services;

// import java.util.*;
// import com.recommendation.recommendation.Algorithm.LCSAlgorithm;
// import com.recommendation.recommendation.Models.Products;

// public class RecommendationEngine {
//     private static final double THRESHOLD = 0.42; 

//     public static List<Map<String, Object>> recommend(Products target, List<Products> products) {
//         if (target == null || target.getDescription() == null || products.isEmpty()) {
//             System.out.println("Invalid target product or empty product list.");
//             return Collections.emptyList();
//         }

//         Map<Integer, Double> similarityScores = new HashMap<>();
//         Map<Integer, Products> productMap = new HashMap<>();
//         int totalProducts = products.size();
//         int selectedCount = 0;

//         System.out.println("\nSimilarity Scores:");

//         for (Products p : products) {
//             if (p.getId() == target.getId() || p.getDescription() == null) continue;

//             int lcsLength = LCSAlgorithm.findLCS(target.getDescription(), p.getDescription());
//             int maxLength = Math.max(target.getDescription().length(), p.getDescription().length());

//             double normalizedScore = (double) lcsLength / maxLength; 

//             System.out.println("- " + target.getName() + " & " + p.getName() + " => LCS: " + lcsLength + ", Normalized: " + normalizedScore);

//             if (normalizedScore >= THRESHOLD) {
//                 similarityScores.put(p.getId(), normalizedScore);
//                 productMap.put(p.getId(), p);
//                 selectedCount++;
//             }
//         }

//         System.out.println("\nSelected " + selectedCount + " out of " + (totalProducts - 1) + " products based on the threshold.");

//         // Prepare final list with product details + LCS score
//         List<Map<String, Object>> recommendedProducts = new ArrayList<>();

//         similarityScores.entrySet()
//                 .stream()
//                 .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) 
//                 .forEach(entry -> {
//                     Map<String, Object> productInfo = new HashMap<>();
//                     Products product = productMap.get(entry.getKey());
//                     productInfo.put("id", product.getId());
//                     productInfo.put("name", product.getName());
//                     productInfo.put("description", product.getDescription());
//                     productInfo.put("lcs_score", entry.getValue());
//                     recommendedProducts.add(productInfo);
//                 });

//         return recommendedProducts;
//     }
// }


package com.recommendation.recommendation.Services;

import java.util.*;
import com.recommendation.recommendation.Algorithm.LCSAlgorithm;
import com.recommendation.recommendation.Models.Products;

public class RecommendationEngine {
    private static final double THRESHOLD = 0.42; 

    public static List<Map<String, Object>> recommend(Products target, List<Products> products) {
        if (target == null || target.getDescription() == null || products.isEmpty()) {
            System.out.println("Invalid target product or empty product list.");
            return Collections.emptyList();
        }

        Map<Integer, Double> similarityScores = new HashMap<>();
        Map<Integer, Products> productMap = new HashMap<>();
        int totalProducts = products.size();
        int selectedCount = 0;

        System.out.println("\nSimilarity Scores:");

        for (Products p : products) {
            if (p.getId() == target.getId() || p.getDescription() == null) continue;

            int lcsLength = LCSAlgorithm.findLCS(target.getDescription(), p.getDescription());
            int maxLength = Math.max(target.getDescription().length(), p.getDescription().length());

            double normalizedScore = (double) lcsLength / maxLength; 

            System.out.println("- " + target.getName() + " & " + p.getName() + " => LCS: " + lcsLength + ", Normalized: " + normalizedScore);

            if (normalizedScore >= THRESHOLD) {
                similarityScores.put(p.getId(), normalizedScore);
                productMap.put(p.getId(), p);
                selectedCount++;
            }
        }

        System.out.println("\nSelected " + selectedCount + " out of " + (totalProducts - 1) + " products based on the threshold.");

        // Prepare final list with product details + LCS score
        List<Map<String, Object>> recommendedProducts = new ArrayList<>();

        similarityScores.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) 
                .forEach(entry -> {
                    Map<String, Object> productInfo = new HashMap<>();
                    Products product = productMap.get(entry.getKey());
                    productInfo.put("id", product.getId());
                    productInfo.put("name", product.getName());
                    productInfo.put("description", product.getDescription());
                    productInfo.put("lcs_score", entry.getValue());
                    recommendedProducts.add(productInfo);
                });

        return recommendedProducts;
    }
}
