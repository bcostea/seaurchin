package ro.seaurchin.websurvey.support.util;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * User: BogdanCo
 * Date: Oct 17, 2006
 * Time: 6:35:39 PM
 */
public class FiltruLista
{

    Logger logger = Logger.global;
    private int OPERATIE;
    private String entitate;
    private String valoare;

    public final int EQUALS = 1;
    public final int NOT_EQUALS = 2;
    public final int LIKE = 3;
    public final int NOT_LIKE = 4;
    public final int IS_NULL = 5;
    public final int IS_NOT_NULL = 6;
    public final int NO_FILTER = 7;


    public FiltruLista(String entitate, int OPERATIE, String valoare) {
        this.entitate = entitate;
        this.OPERATIE = OPERATIE;
        this.valoare = valoare;
        if ( valoare.length()<1 )
        {
            logger.warning("Nu are valoare, operatia poate fi numai IS_NULL sau IS_NOT_NULL");
        }

        this.valoare=this.valoare.replaceAll("'","''");
    }

    public FiltruLista(String entitate, int OPERATIE)
    {
            this.entitate = entitate;
            this.OPERATIE = OPERATIE;
    }



    public String getSQL()
    {
        StringBuffer SQL = new StringBuffer(" AND ");
        switch(OPERATIE)
        {
            case EQUALS:
                SQL.append( this.entitate).append("='").append(this.valoare).append("'");
                break;
            case NOT_EQUALS:
                SQL.append( this.entitate).append("!='").append(this.valoare).append("'");
                break;
            case LIKE:
                SQL.append( this.entitate).append(" LIKE '%").append(this.valoare).append("%'");
                break;
            case NOT_LIKE:
                SQL.append( this.entitate).append(" NOT LIKE '%").append(this.valoare).append("%'");
                break;
            case IS_NULL:
                SQL.append( this.entitate).append(" IS NULL");
                break;
            case IS_NOT_NULL:
                SQL.append( this.entitate).append(" IS NOT NULL");
                break;
            case NO_FILTER:
                //in cazul in care ajunge aici ( eroare in stratul de controller)
                SQL.append("1=1");
                logger.warning("UPS, nu a ajuns requestul bine din controller in BuildFiltru!");
                break;
        }

        return SQL.toString();
    }
}
