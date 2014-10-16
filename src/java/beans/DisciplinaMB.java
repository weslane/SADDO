/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private List<Disciplina> listaOptativas = new ArrayList<Disciplina>();


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
    
      public List<Disciplina> getListaOptativas() {
        return listaOptativas;
    }

    public void setListaOptativas(List<Disciplina> listaOptativas) {
        this.listaOptativas = listaOptativas;
    }
    
     public void cadastrar() {
        try {
            dao.create(disciplina);
            disciplina= new Disciplina();
              FacesUtil.adicionarMensagem("formCadastroDisc", "Cadastrado");
        } catch (EntityExistsException e) {
            FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Já está cadastrado");
        } catch (RollbackException e) {
             FacesUtil.adicionarMensagem("formCadastroDisc", "Erro: Algo deu errado no cadastro");
        }
        pesquisarTudo();
    }
    
     public void alterar() {
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
        selecaoOptativas();

    }
       public void selecaoOptativas(){
           
           listaOptativas = new ArrayList<Disciplina>();
           
           for (Disciplina d : dao.findDisciplinaEntities()){
               
               if("Optativa".equals(d.getTipo()))
                   
                   listaOptativas.add(d);
            }
       }
       
}
