package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Aluno;
import modelo.Disciplina;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-06T18:06:55")
@StaticMetamodel(Sugestao.class)
public class Sugestao_ { 

    public static volatile SingularAttribute<Sugestao, Long> id;
    public static volatile SingularAttribute<Sugestao, Disciplina> disciplina;
    public static volatile ListAttribute<Sugestao, Aluno> alunos;

}