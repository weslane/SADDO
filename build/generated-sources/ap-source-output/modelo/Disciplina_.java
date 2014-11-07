package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Professor;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-06T18:06:55")
@StaticMetamodel(Disciplina.class)
public class Disciplina_ { 

    public static volatile SingularAttribute<Disciplina, Long> id;
    public static volatile SingularAttribute<Disciplina, String> codigo;
    public static volatile SingularAttribute<Disciplina, Integer> cargaHoraria;
    public static volatile SingularAttribute<Disciplina, String> credito;
    public static volatile SingularAttribute<Disciplina, String> tipo;
    public static volatile SingularAttribute<Disciplina, Professor> professor;
    public static volatile SingularAttribute<Disciplina, String> nome;
    public static volatile SingularAttribute<Disciplina, Integer> nivel;

}