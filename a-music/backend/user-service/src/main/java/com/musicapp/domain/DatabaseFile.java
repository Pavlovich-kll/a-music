package com.musicapp.domain;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.http.MediaType;

import javax.persistence.*;

/**
 * Сущность файла, который сохраняется в базу данных
 */
@Entity
@Table(name = "files")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class DatabaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "data")
    private byte[] bytes;
    @Column(name = "file_extension")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private FileExtension fileExtension;
    @Column(name = "name")
    private String name;

    public MediaType getMediaType() {
        return MediaType.parseMediaType(fileExtension.getMediaTypeString());
    }
}
