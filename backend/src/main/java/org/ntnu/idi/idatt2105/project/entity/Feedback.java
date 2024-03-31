package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Class representing a feedback in the application. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
