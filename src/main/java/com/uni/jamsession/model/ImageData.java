package com.sap.jamsession.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class ImageData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String type;

  @Lob
  @Column(name = "imagedata", columnDefinition = "MEDIUMBLOB")
  private byte[] data;
}
