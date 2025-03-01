package com.example.dynamic_website.author;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class AuthorDTO implements Comparable<AuthorDTO> {

    private int id;
    private String firstName;
    private String lastName;
    private String info;
    private String photoUrl;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public int compareTo(AuthorDTO o) {
        return Integer.compare(id, o.id);
    }

    public static AuthorDTO fromAuthor(Author author) {
        var authorDTO = new AuthorDTO();
        BeanUtils.copyProperties(author, authorDTO);
        return authorDTO;
    }
}
