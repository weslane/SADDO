/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import beans.AlunoMB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Esta classe é uma fachada para o JavaServer Faces. Ela possui métodos que
 * facilitam, por exemplo, colocar mensagens para o usuário.
 *
 */
public class FacesUtil {

    /**
     * Adiciona uma mensagem ao FacesContext para o id especificado.
     *
     * @param id o identificador da tag que receberá a mensagem. Exemplo:
     * "formCadastro:nome"
     * @param mensagem a mensagem a ser adicionada.
     */
    public static void adicionarMensagem(String id, String mensagem) {
        FacesMessage message = new FacesMessage(mensagem);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(id, message);
    }

    /**
     * Adiciona uma mensagem ao FacesContext.
     *
     * @param mensagem a mensagem a ser adicionada.
     */
    public static void adicionarMensagem(String mensagem) {
        adicionarMensagem(null, mensagem);
    }

    public static AlunoMB getAlunoMB() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        AlunoMB alunoMB = (AlunoMB) app.evaluateExpressionGet(facesContext, "#{alunoMB}", AlunoMB.class);
        return alunoMB;
    }
}
