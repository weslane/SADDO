package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Disciplina;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-16T15:43:49")
@StaticMetamodel(Aluno.class)
public class Aluno_ { 

    public static volatile SingularAttribute<Aluno, Long> id;
    public static volatile SingularAttribute<Aluno, String> periodoInicial;
    public static volatile SingularAttribute<Aluno, Integer> periodoCorrente;
    public static volatile SingularAttribute<Aluno, String> nome;
    public static volatile SingularAttribute<Aluno, String> login;
    public static volatile ListAttribute<Aluno, Disciplina> disciplinas;
    public static volatile SingularAttribute<Aluno, String> senha;
    public static volatile SingularAttribute<Aluno, String> matricula;

}