
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Monnalisa Medeiros
 */
@Entity
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicial = new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal = new Date();
    
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "listaSugestoes")
    private List<Sugestao> sugestoes;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }
    
    /**
     * @return the sugestoes
     */
    public List<Sugestao> getSugestoes() {
        return sugestoes;
    }

    /**
     * @param sugestoes the sugestoes to set
     */
    public void setSugestoes(List<Sugestao> sugestoes) {
        this.sugestoes = sugestoes;
    }
    
   public void addSugestao(Sugestao item) {
        sugestoes.add(item);
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Evento[ id=" + id + " ]";
    }
    
}
