package leonardo.test.venturus.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name="TB_SERVICO")
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SERVICO")
    @SequenceGenerator(name = "SQ_SERVICO", sequenceName = "SQ_SERVICO", allocationSize = 1)
    @Column(name = "ID_SERVICO")
    private Long id;

    @Column(name = "NOME_SERVICO", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO_SERVICO", nullable = false)
    private String descricao;


}
