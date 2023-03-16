package me.plurg.creator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "CREATORS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatorEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED")
    private Instant created;
}
