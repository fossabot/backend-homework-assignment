package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.issuer.Issuer;
import com.lazar.andric.homework.offer.Offer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Tender {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuer_id")
    private Issuer issuer;

    @OneToMany(mappedBy = "tender", fetch = FetchType.LAZY)
    private List<Offer> offer;
}
