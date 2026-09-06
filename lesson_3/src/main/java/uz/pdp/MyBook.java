package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBook {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private User borrowedBy; // kitob band bo'lsa uni olgan user turadi, bo'sh bo'lsa null

    public MyBook(Long id, String title, String author, Integer year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public MyBook(String title, String author, String genre, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String toFileLine() {
        String borrowedId = (borrowedBy != null && borrowedBy.getId() != null) ? String.valueOf(borrowedBy.getId()) : "";
        return String.format("%d, %s, %s, %d, %s",
                id,
                title != null ? title : "",
                author != null ? author : "",
                year != null ? year : 0,
                borrowedId);
    }
}
