package beans;

import dao.AlunoJpaController;
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
import modelo.Aluno;
import util.FacesUtil;

/**
 * 
 * @author Monnalisa Medeiros
 */
@ManagedBean
@SessionScoped
public class AlunoMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    private Aluno aluno = new Aluno();
    AlunoJpaController dao = new AlunoJpaController(emf);
    private List<Aluno> listaAlunos = new ArrayList<Aluno>();

    public AlunoMB() {
        aluno = new Aluno();
        pesquisarTudo();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(List<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public void cadastrar() {
        try {
            dao.create(aluno);
            aluno = new Aluno();
              FacesUtil.adicionarMensagem("formularioAluno", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formularioAluno", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
             FacesUtil.adicionarMensagem("formularioAluno", "Erro: Algo deu errado no cadastro");
        }
        pesquisarTudo();

    }
    
     public void alterar() {
        try {
            dao.edit(aluno);
            aluno = new Aluno();
            FacesUtil.adicionarMensagem("formularioAluno", "Alterado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioAluno", "Erro: Já está cadastrado");
        } catch (Exception ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formularioAluno", "Erro: Algo deu errado na alteração");
        }
        pesquisarTudo();
    }
     
      public void limpar() {
        setAluno(new Aluno());

    }

      public void excluir(Long id) {
        try {
            dao.destroy(id);
            setAluno(new Aluno());
            FacesUtil.adicionarMensagem("formularioAluno", "Excluido");
        } catch (NonexistentEntityException ex) {
           FacesUtil.adicionarMensagem("formularioAluno", "Erro: Algo deu errado na exclusão");
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        } 
        pesquisarTudo();
    }
      
      public void pesquisarTudo() {
        listaAlunos = dao.findAlunoEntities();

    }
}
