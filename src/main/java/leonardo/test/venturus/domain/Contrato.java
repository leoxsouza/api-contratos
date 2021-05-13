package leonardo.test.venturus.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name="TB_CONTRATO")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CONTRATO")
    @SequenceGenerator(name = "SQ_CONTRATO", sequenceName = "SQ_CONTRATO", allocationSize = 1)
    @Column(name = "NU_CONTRATO")
    private Long id;

    @Column(name = "DT_VIGENCIA", nullable = false)
    private LocalDate vigencia;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID_CLIENTE", name = "ID_CLIENTE")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID_SERVICO", name = "ID_SERVICO")
    private Servico servico;

}
