package beans;

import dao.EventoJpaController;
import dao.SugestaoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import modelo.Aluno;
import modelo.Evento;
import modelo.Sugestao;
import util.FacesUtil;

/**
 *
 * @author Monnalisa Medeiros
 */
@ManagedBean
@SessionScoped
public class EventoMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    EventoJpaController daoEvento = new EventoJpaController(emf);
    SugestaoJpaController daoSugestao = new SugestaoJpaController(emf);
    private Evento evento = new Evento();
    private Aluno aluno = new Aluno();
    private Sugestao sugestao =new Sugestao();
    
    private List<Evento> listaEvento = new ArrayList<Evento>();

    public EventoMB() {
        evento = new Evento();
        pesquisarTudo();
        pesquisarOptativas();
        
    }

    public Sugestao getSugestao() {
        return sugestao;
    }

    public void setSugestao(Sugestao sugestao) {
        this.sugestao = sugestao;
    }
    
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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

    public void cadastro() {
        
        pesquisarOptativas();
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

     public void pesquisarOptativas() {
        
         for(Sugestao s: daoSugestao.findSugestaoEntities()){
         
             evento.addSugestao(s);
         }
    }
}
