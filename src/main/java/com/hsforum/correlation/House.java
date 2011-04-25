package com.hsforum.correlation;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

/**
 * @author berinle
 */
@Entity
@Table(name="HOUSE")
@Indexed
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @Column(name="HOUSE_ID")
    private Long id;

    @Field
    @Column(name="STREET")
    private String street;

    @Field
    @Column(name="CITY")
    private String city;

    @Field
    @Column(name="ZIP")
    private String zip;

    @ContainedIn
    @ManyToOne
    @JoinColumn(name="ARTIST_ID", insertable = false, updatable = false)
    private Artist artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
