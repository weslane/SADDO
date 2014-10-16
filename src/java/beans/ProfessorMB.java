/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.ProfessorJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import modelo.Professor;
import util.FacesUtil;

/**
 *
 * @author Monnalisa Medeiros
 */
public class ProfessorMB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    private Professor professor = new Professor();
    ProfessorJpaController dao = new ProfessorJpaController(emf);
    private List<Professor> listaProfessores = new ArrayList<Professor>();

     public ProfessorMB() {
        professor = new Professor();
        pesquisarTudo();
    }
     
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Professor> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Professor> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }
    
     public void cadastrar() {
        try {
            dao.create(professor);
            professor = new Professor();
              FacesUtil.adicionarMensagem("formCadastroProf", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formCadastroProf", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
             FacesUtil.adicionarMensagem("formCadastroProf", "Erro: Algo deu errado no cadastro");
        }
        pesquisarTudo();

    }
    
     public void alterar() {
        try {
            dao.edit(professor);
            professor= new Professor();
            FacesUtil.adicionarMensagem("formCadastroProf", "Alterado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formCadastroProf", "Erro: Já está cadastrado");
        } catch (Exception ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.adicionarMensagem("formCadastroProf", "Erro: Algo deu errado na alteração");
        }
        pesquisarTudo();
    }
     
      public void limpar() { 
       professor = new Professor();
    }

      public void excluir(Long id) {
        try {
            dao.destroy(id);
            setProfessor(new Professor());
            FacesUtil.adicionarMensagem("formCadastroProf", "Excluido");
        } catch (NonexistentEntityException ex) {
           FacesUtil.adicionarMensagem("formCadastroProf", "Erro: Algo deu errado na exclusão");
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        pesquisarTudo();
    }
      
      public void pesquisarTudo() {
        listaProfessores = dao.findProfessorEntities();

    }
    
}
