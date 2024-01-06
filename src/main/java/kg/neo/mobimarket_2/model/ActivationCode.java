package kg.neo.mobimarket_2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activation_token")
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
    private String code;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ActivationCode(User user) {
        this.code = UUID.randomUUID().toString();
        this.user = user;
    }

    @Override
    public String toString() {
        return "ActivationToken{" +
                "id=" + id +
                ", token='" + code + '\'' +
                ", user=" + user +
                '}';
    }
}
