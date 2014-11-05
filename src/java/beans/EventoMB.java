package beans;

import dao.DisciplinaJpaController;
import dao.EventoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import modelo.Disciplina;
import modelo.Evento;
import util.FacesUtil;

/**
 *
 * @author Monnalisa Medeiros
 */
@ManagedBean
@RequestScoped
public class EventoMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    EventoJpaController daoEvento = new EventoJpaController(emf);
    DisciplinaJpaController dao = new DisciplinaJpaController(emf);
    private Evento evento = new Evento();
    List<Evento> listaEvento = new ArrayList<Evento>();
    List<Disciplina> optativas = new ArrayList<Disciplina>();

    public EventoMB() {
        evento = new Evento();
        
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getListaEvento() {
        return listaEvento;
    }

    public void setListaEvento(List<Evento> listaEvento) {
        this.listaEvento = listaEvento;
    }

    public List<Disciplina> getLista() {
        return optativas;
    }

    public void setLista(List<Disciplina> optativas) {
        this.optativas = optativas;
    }

    public void cadastro() {
        buscarOptativas();
        try {
            daoEvento.create(evento);
            evento = new Evento();
            FacesUtil.adicionarMensagem("formCadastroEven", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formCadastroEven", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
            FacesUtil.adicionarMensagem("formCadastroEven", "Erro: Algo deu errado no cadastro");
        }
        pesquisarTudo();

    }

    public void pesquisarTudo() {

        listaEvento = daoEvento.findEventoEntities();
    }

    public void buscarOptativas() {
      
        optativas();
        evento.setDisciplinas(optativas);
        
    }

    public void optativas() {

        for (Disciplina d : dao.findDisciplinaEntities()) {

            if ("Optativa".equals(d.getTipo())) {
                optativas.add(d);
            }
        }
    }

}
