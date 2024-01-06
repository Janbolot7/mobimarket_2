package kg.neo.mobimarket_2.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
