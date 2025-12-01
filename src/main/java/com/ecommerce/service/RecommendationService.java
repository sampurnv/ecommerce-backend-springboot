package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    
    /**
     * Get personalized product recommendations for a user
     * Based on purchase history and browsing patterns
     */
    public List<Product> getPersonalizedRecommendations(Long userId, int limit) {
        try {
            // Get user's order history
            var userOrders = orderRepository.findByUserId(userId);
            
            if (userOrders.isEmpty()) {
                // New user - return popular products
                return getPopularProducts(limit);
            }
            
            // Extract categories from user's purchase history
            Set<String> userCategories = userOrders.stream()
                    .flatMap(order -> order.getItems().stream())
                    .map(item -> item.getProduct().getCategory())
                    .collect(Collectors.toSet());
            
            // Get products from user's preferred categories
            List<Product> recommendations = new ArrayList<>();
            for (String category : userCategories) {
                recommendations.addAll(productRepository.findByCategory(category));
            }
            
            // Remove duplicates and limit results
            return recommendations.stream()
                    .distinct()
                    .limit(limit)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            log.error("Error generating recommendations for user {}", userId, e);
            return getPopularProducts(limit);
        }
    }
    
    /**
     * Get similar products based on category and price range
     */
    public List<Product> getSimilarProducts(Long productId, int limit) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            // Find products in same category
            List<Product> similarProducts = productRepository.findByCategory(product.getCategory());
            
            // Filter by similar price range (±30%)
            double minPrice = product.getPrice() * 0.7;
            double maxPrice = product.getPrice() * 1.3;
            
            return similarProducts.stream()
                    .filter(p -> !p.getId().equals(productId))
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .limit(limit)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            log.error("Error finding similar products for product {}", productId, e);
            return Collections.emptyList();
        }
    }
    
    /**
     * Get trending/popular products
     */
    public List<Product> getPopularProducts(int limit) {
        // In a real implementation, this would be based on sales data, views, etc.
        List<Product> allProducts = productRepository.findAll();
        Collections.shuffle(allProducts);
        return allProducts.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Get frequently bought together products
     */
    public List<Product> getFrequentlyBoughtTogether(Long productId, int limit) {
        try {
            // Find orders containing this product
            var orders = orderRepository.findAll().stream()
                    .filter(order -> order.getItems().stream()
                            .anyMatch(item -> item.getProduct().getId().equals(productId)))
                    .collect(Collectors.toList());
            
            // Count product co-occurrences
            Map<Long, Integer> productCounts = new HashMap<>();
            for (var order : orders) {
                order.getItems().stream()
                        .map(item -> item.getProduct().getId())
                        .filter(id -> !id.equals(productId))
                        .forEach(id -> productCounts.merge(id, 1, Integer::sum));
            }
            
            // Get top co-occurring products
            return productCounts.entrySet().stream()
                    .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                    .limit(limit)
                    .map(entry -> productRepository.findById(entry.getKey()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            log.error("Error finding frequently bought together for product {}", productId, e);
            return Collections.emptyList();
        }
    }
}