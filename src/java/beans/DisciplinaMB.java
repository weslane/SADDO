package beans;

import dao.DisciplinaJpaController;
import dao.ProfessorJpaController;
import dao.SugestaoJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import modelo.Disciplina;
import modelo.Professor;
import modelo.Sugestao;
import util.FacesUtil;

/**
 *
 * @author Monnalisa Medeiros
 */
@ManagedBean
@SessionScoped
public class DisciplinaMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    private Disciplina disciplina = new Disciplina();
    ProfessorJpaController daoProf = new ProfessorJpaController(emf);
    DisciplinaJpaController dao = new DisciplinaJpaController(emf);
    SugestaoJpaController daoSugestao = new SugestaoJpaController(emf);
    private List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
    private List<Sugestao> listaOptativas = new ArrayList<Sugestao>();
    
    private Professor prof = new Professor();
    private Sugestao sugestao = new Sugestao();

    public DisciplinaMB() {
        disciplina = new Disciplina();
        prof = new Professor();
        pesquisarTudo();
       
    }

    public Sugestao getSugestao() {
        return sugestao;
    }

    public void setSugestao(Sugestao sugestao) {
        this.sugestao = sugestao;
    }

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public List<Sugestao> getListaOptativas() {
        return listaOptativas;
    }

    public void setListaOptativas(List<Sugestao> listaOptativas) {
        this.listaOptativas = listaOptativas;
    }
    
    public void cadastrar() {

        disciplina.setProfessor(prof);
        selecaoOptativas(disciplina);
        try {
            dao.create(disciplina);
            disciplina = new Disciplina();
            FacesUtil.adicionarMensagem("formCadastroDisc", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Algo deu errado no cadastro");
        }
        disciplina = new Disciplina();
        pesquisarTudo();
        
    }

    public void alterar() {

        disciplina.setProfessor(prof);
        try {
            dao.edit(disciplina);
            disciplina = new Disciplina();
            FacesUtil.adicionarMensagem("formCadastroDisc", "Alterado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Já está cadastrado");
        } catch (Exception ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Algo deu errado na alteração");
        }
        pesquisarTudo();
    }

    public void limpar() {
        setDisciplina(new Disciplina());

    }

    public void excluir(Long id) {
        try {
            dao.destroy(id);
            setDisciplina(new Disciplina());
            FacesUtil.adicionarMensagem("formCadastroDisc", "Excluido");
        } catch (NonexistentEntityException ex) {
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Algo deu errado na exclusão");
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        pesquisarTudo();
    }

    public void pesquisarTudo() {
        listaDisciplinas = dao.findDisciplinaEntities();
        listaOptativas = daoSugestao.findSugestaoEntities();

    }

    public void selecaoOptativas(Disciplina d) {

        if ("Optativa".equals(d.getTipo())) {
            cadastroSugestao(d);
        }
    }

    public void cadastroSugestao(Disciplina d) {

        sugestao = new Sugestao();
        sugestao.setDisciplina(d);
        sugestao.setAlunos(null);
        daoSugestao.create(sugestao);
    }
    
}
