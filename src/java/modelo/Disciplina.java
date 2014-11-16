
package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Weslane
 */
@Entity
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    private String nome;
    private Integer cargaHoraria;
    private String natureza;
    private Integer nivel;
    private String credito;
    @OneToOne
    @JoinColumn(name = "IDdisciplina", referencedColumnName = "id")
    private Disciplina  preRequisito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
     /**
     * @return the natureza
     */
    public String getNatureza() {
        return natureza;
    }

    /**
     * @param natureza the natureza to set
     */
    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }
    
     public Disciplina getPreRequisito() {
        return preRequisito;
    }

    public void setPreRequisito(Disciplina preRequisito) {
        this.preRequisito = preRequisito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Disciplina[ id=" + id + " ]";
    }
}
