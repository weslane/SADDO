
package beans;

import dao.TurmaJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import modelo.Aluno;
import modelo.Disciplina;
import modelo.Professor;
import modelo.Turma;
import util.FacesUtil;

/**
 *
 * @author Monnalisa Medeiros
 */
@ManagedBean
@RequestScoped
public class TurmaMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    TurmaJpaController dao = new TurmaJpaController(emf);
    private List<Turma> listaTurmas = new ArrayList<Turma>();
    private Disciplina disciplina = new Disciplina();
    private Professor professor = new Professor();
    private Aluno aluno = new Aluno();
    private Turma turma = new Turma();

    public TurmaMB() {
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Turma> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(List<Turma> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }
    
     public void cadastrar() {
         
         turma.setDisciplina(disciplina);
         turma.setProfessor(professor);
        try {
            dao.create(turma);
            turma = new Turma();
              FacesUtil.adicionarMensagem("formularioTurma", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formularioTurma", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
             FacesUtil.adicionarMensagem("formularioTurma", "Erro: Algo deu errado no cadastro");
        }
        pesquisarTudo();

    }
    
     public void alterar() {
        try {
            dao.edit(turma);
             turma = new Turma();
            FacesUtil.adicionarMensagem("formularioTurma", "Alterado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioTurma", "Erro: Já está cadastrado");
        } catch (Exception ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioTurma", "Erro: Algo deu errado na alteração");
        }
        pesquisarTudo();
    }
     
      public void limpar() {
        setTurma(new Turma());

    }

      public void excluir(Long id) {
        try {
            dao.destroy(id);
            setTurma(new Turma());
            FacesUtil.adicionarMensagem("formularioTurma", "Excluido");
        } catch (NonexistentEntityException ex) {
           FacesUtil.adicionarMensagem("formularioTurma", "Erro: Algo deu errado na exclusão");
            Logger.getLogger(TurmaMB.class.getName()).log(Level.SEVERE, null, ex);
        } 
        pesquisarTudo();
    }
      
      public void pesquisarTudo() {
        listaTurmas = dao.findTurmaEntities();

    }
    
}
