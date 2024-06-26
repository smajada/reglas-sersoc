package org.arteco.sersoc.model.base;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.html.ListView;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoutPerPrsId implements Serializable {


    @Column(name = "PER_CON")
    @JsonView(ListView.class)
    private Long perCon;

    @Column(name = "PRS_CON")
    @JsonView(ListView.class)
    private Long prsCon;
}
