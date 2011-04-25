package com.hsforum.correlation;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Set;

/**
 * @author berinle
 */
@Entity
@Table(name="ARTIST")
@Indexed
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @Column(name="ARTIST_ID")
    private Long id;

    @Field
    @Column(name = "NAME")
    private String name;

    @IndexedEmbedded
    @OneToMany(mappedBy = "artist")
    private Set<Album> albums;

    @IndexedEmbedded
    @OneToMany(mappedBy = "artist")
    private Set<House> houses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }
}
