package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Sugestao;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-06T18:06:55")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, Long> id;
    public static volatile SingularAttribute<Evento, Date> dataFinal;
    public static volatile SingularAttribute<Evento, Date> dataInicial;
    public static volatile ListAttribute<Evento, Sugestao> sugestoes;

}