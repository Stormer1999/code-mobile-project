package com.example.code_mobile.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(length = 150, nullable = false, unique = false)
  private String name;

  private String image;

  private int price;

  private int stock;

  @Setter(AccessLevel.NONE)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date", nullable = false)
  private Date createDate;

  @Setter(AccessLevel.NONE)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_date", nullable = false)
  private Date updateDate;
}
