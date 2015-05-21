package ro.seaurchin.websurvey.support.dao;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import ro.seaurchin.websurvey.support.Chestionar;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 9:21:45 PM
 */
public interface ChestionarDao
{
    public List getChestionare();
    public void stergeChestionar(Long id);
    public Chestionar getChestionar(Long id);

    public Map getRezultateMap(Long idChestionar, Long idSetRezultat);
}
