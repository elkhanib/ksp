package com.iodigital.ksp.domain;

import java.time.LocalDate;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Table(name = "TED_TALK")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TedTalk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @Column(name = "TALK_DATE")
    private LocalDate talkDate;

    @Column(name = "VIEWS")
    private Long viewCount;

    @Column(name = "LIKES")
    private Long likeCount;
    private String link;
}
