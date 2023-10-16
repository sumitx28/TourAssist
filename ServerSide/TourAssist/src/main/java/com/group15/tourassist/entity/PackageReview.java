package com.group15.tourassist.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "package_review")
public class PackageReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "review_date")
    private Instant reviewDate;

    @Column(name = "ratings")
    private Integer ratings;

    @Column(name = "review_comment")
    private String reviewComment;

}
