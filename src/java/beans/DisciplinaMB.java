package beans;

import dao.DisciplinaJpaController;
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
    DisciplinaJpaController dao = new DisciplinaJpaController(emf);
    private List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
    private Disciplina preRequisito;

    public DisciplinaMB() {
        disciplina = new Disciplina();
        pesquisarTudo();

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
    
    public Disciplina getPreRequisito() {
        return preRequisito;
    }

   
    public void setPreRequisito(Disciplina preRequisito) {
        this.preRequisito = preRequisito;
    }

    public void cadastrar() {

        disciplina.setPreRequisito(preRequisito);
        try {
            dao.create(disciplina);
            disciplina = new Disciplina();
            FacesUtil.adicionarMensagem("formularioDisc", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formularioDisc", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
            FacesUtil.adicionarMensagem("formularioDisc", "Erro: Algo deu errado no cadastro");
        }

        pesquisarTudo();

    }

    public void alterar() {

        disciplina.setPreRequisito(preRequisito);
        try {
            dao.edit(disciplina);
            disciplina = new Disciplina();
            FacesUtil.adicionarMensagem("formularioDisc", "Alterado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioDisc", "Erro: Já está cadastrado");
        } catch (Exception ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioDisc", "Erro: Algo deu errado na alteração");
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
            FacesUtil.adicionarMensagem("formularioDisc", "Excluido");
        } catch (NonexistentEntityException ex) {
            FacesUtil.adicionarMensagem("formularioDisc", "Erro: Algo deu errado na exclusão");
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        pesquisarTudo();
    }

    public void pesquisarTudo() {
        listaDisciplinas = dao.findDisciplinaEntities();
    }
}
