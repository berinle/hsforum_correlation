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
@Table(name="ALBUM")
@Indexed
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @Column(name="ALBUM_ID")
    private Long id;

    @Field
    @Column(name="title")
    private String title;

    @Field
    @Column(name="RELEASE_YR")
    private int year;

    @Field
    @Column(name="NO_OF_SONGS")
    private int noOfSongs;

    @Field
    @Column(name="GENRE")
    private String genre;

    @ContainedIn
    @ManyToOne @JoinColumn(name="ARTIST_ID", insertable = false, updatable = false)
    private Artist artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNoOfSongs() {
        return noOfSongs;
    }

    public void setNoOfSongs(int noOfSongs) {
        this.noOfSongs = noOfSongs;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
