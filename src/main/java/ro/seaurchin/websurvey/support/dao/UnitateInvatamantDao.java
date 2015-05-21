package ro.seaurchin.websurvey.support.dao;

import java.util.List;

/**
 * User: BogdanCo
 * Date: Sep 21, 2006
 * Time: 8:34:03 PM
 */
public interface UnitateInvatamantDao
{
    public List getJudete();
    public List getLocalitatiForJudet(String judet);
    public List getUnitatiForJudetAndLocalitate(String judet, String Localitate);

    public String getSerieForUnitateDinJudetSiLocalitate(String judet, String localitate, String unitate);
    public String getEtapaForUnitateDinJudetSiLocalitate(String judet, String localitate, String unitate);

    public Long checkForDateGetId(String judetSelectat, String localitateSelectata, String unitateSelectata, Long id);
}
