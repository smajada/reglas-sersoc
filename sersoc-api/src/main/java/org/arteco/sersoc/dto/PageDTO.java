package org.arteco.sersoc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.domain.Page;

import javax.swing.text.html.ListView;
import java.util.List;

@Data
public class PageDTO<ENTITY> {

    @JsonView(ListView.class)
    private final int totalPages;

    @JsonView(ListView.class)
    private final long totalElements;

    @JsonView(ListView.class)
    private final List<ENTITY> content;

    @JsonView(ListView.class)
    private final int size;

    @JsonView(ListView.class)
    private final int numberOfElements;

    @JsonView(ListView.class)
    private final int number;

    public PageDTO(Page<ENTITY> page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
    }
}
