package beans;

import dao.DisciplinaJpaController;
import dao.EventoJpaController;
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
import modelo.Disciplina;
import modelo.Evento;
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
    DisciplinaJpaController daoDisciplina = new DisciplinaJpaController(emf);
    private Evento evento = new Evento();
    private Aluno aluno = new Aluno();
    private Disciplina disciplina = new Disciplina();
    private List<Evento> listaEvento = new ArrayList<Evento>();
    private List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();


    public EventoMB() {
        evento = new Evento();
        pesquisarTudo();

    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Evento> getListaEvento() {
        return listaEvento;
    }

    public void setListaEvento(List<Evento> listaEvento) {
        this.listaEvento = listaEvento;
    }

    public List<Disciplina> getListaDisciplina() {
        return listaDisciplina;
    }

    public void setListaDisciplina(List<Disciplina> listaDisciplina) {
        this.listaDisciplina = listaDisciplina;
    }

    public void adicionar(Disciplina d) {

        
        aluno.setNome("Monnalisa");
        aluno.setLogin("monna");
        aluno.setMatricula("123456");
        aluno.setSenha("monna");
        aluno.setPeriodoCorrente(8);
        aluno.setPeriodoInicial("2011.1");

    }

    public void selecaoOptativas() {
        
        for(Disciplina d: daoDisciplina.findDisciplinaEntities()){

            if ("Optativa".equals(d.getNatureza())) {
                adicionar(d);
            }
        }
    }

    public void cadastro() {
        
        selecaoOptativas();
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
}
