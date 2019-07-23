package com.site.michalpusioproject.domains;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

}
