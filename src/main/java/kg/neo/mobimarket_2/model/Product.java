package kg.neo.mobimarket_2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String productName;
    private int price;
    private String shortDescription;
    private String fullDescription;
    private int numberOfLikes;

//    @Lob
//    @Column(columnDefinition = "bytea")
//    private byte[] image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @ManyToMany(mappedBy = "favoriteProducts")
    private Set<User> favoriteByUsers = new HashSet<>();


    public void incrementLikeCount() {
        numberOfLikes++;
    }
    public void decrementLikeCount() {
        if (numberOfLikes > 0) {
            numberOfLikes--;
        }
    }
}
