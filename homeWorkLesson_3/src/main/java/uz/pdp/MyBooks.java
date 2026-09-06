package uz.pdp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBooks {
    private String tittle;
    private String author;
    private String genre;
    private int pageCount; // kitob band bo'lsa uni olgan user turadi, bo'sh bo'lsa null
    private int year;



    @Override
    public String toString() {
        return "%s, %s, %s, %d, %d, ".formatted(tittle, author, genre, pageCount, year);
    }
}
