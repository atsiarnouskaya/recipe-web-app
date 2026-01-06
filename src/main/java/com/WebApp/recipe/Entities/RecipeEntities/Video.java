package com.WebApp.recipe.Entities.RecipeEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "source")
    private String source;

    @Column(name = "status")
    private int status;

    public Video(int status, String url, String title, String source) {
        this.status = status;
        this.url = url;
        this.title = title;
        this.source = source;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", status=" + status +
                '}';
    }

    @OneToOne(mappedBy = "video", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Recipe recipe;
}
