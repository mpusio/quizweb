package com.site.michalpusioproject.domains;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor

public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Integer achieved_points;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
