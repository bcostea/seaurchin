package ro.seaurchin.websurvey;

import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import ro.seaurchin.websurvey.securitate.UtilizatorSistem;
import ro.seaurchin.websurvey.securitate.dao.UtilizatorSistemDao;
import ro.seaurchin.websurvey.support.dao.ChestionarDao;
import ro.seaurchin.websurvey.support.dao.UnitateInvatamantDao;
import ro.seaurchin.websurvey.support.dao.impl.SetRezultatDao;
import ro.seaurchin.websurvey.support.util.FiltruLista;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 1:21:00 PM
 */
public class Controller extends MultiActionController
{
    ChestionarDao chestionarDao;
    UnitateInvatamantDao unitateInvatamantDao;
    SetRezultatDao setRezultatDao;
    UtilizatorSistemDao utilizatorSistemDao;

    public Map addUserAndRole(HttpServletRequest request, Map model)
    {
        String user = request.getRemoteUser();
        boolean esteAdmin = request.isUserInRole("Administrator");
        model.put("user", user);
        model.put("admin", Boolean.valueOf(esteAdmin));
        return model;
    }

    public ModelAndView doLogout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {
        httpServletRequest.getSession().invalidate();
        httpServletResponse.sendRedirect("index.html");
        return null;
    }

    public ModelAndView showIndex(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {
        Map myModel = new HashMap();
        addUserAndRole(httpServletRequest, myModel);
        myModel.put("pagina","index-content");
        return new ModelAndView("index", myModel);
    }

    public ModelAndView showChestionar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {

        Map myModel = new HashMap();
        addUserAndRole(httpServletRequest, myModel);
        myModel.put("chestionare", chestionarDao.getChestionare().toArray());
        myModel.put("pagina","chestionar-content");

        return new ModelAndView("index", myModel);
    }

    public ModelAndView showListaRezultate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {
        Map myModel = new HashMap();
        addUserAndRole(httpServletRequest, myModel);
        Long chestionar = new Long(RequestUtils.getLongParameter(httpServletRequest,"chestionar",0));
        Long areFiltru = new Long(RequestUtils.getLongParameter(httpServletRequest,"filtru",0));

        if(areFiltru.equals(new Long(0))){
            myModel.put("setRezultat", setRezultatDao.getLista(chestionar));
        }
        else
        {
            myModel.put("setRezultat", setRezultatDao.getLista(chestionar, BuildFiltru(httpServletRequest)));
            myModel.put("existaFiltru","da");
        }
        myModel.put("chestionar", chestionarDao.getChestionar(chestionar));
        myModel.put("pagina","lista-rezultate");
        return new ModelAndView("index", myModel);
    }

    private FiltruLista BuildFiltru(HttpServletRequest httpServletRequest)
    {
        Long tipFiltru = new Long(RequestUtils.getLongParameter(httpServletRequest,"filtru",0));
        if(tipFiltru.equals(new Long(0))){
            return new FiltruLista("",7);
        } else {
            String entitate = RequestUtils.getStringParameter(httpServletRequest, "entitate", "NOPE ENTITATE");
            String valoare = RequestUtils.getStringParameter(httpServletRequest, "valoare", "NOPE VALOARE");
            return new FiltruLista(entitate,tipFiltru.intValue(),valoare);
        }
    }

    public ModelAndView processChestionar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {

        String actiune=RequestUtils.getStringParameter(httpServletRequest, "actiune", "1");
        Long id = new Long(RequestUtils.getLongParameter(httpServletRequest,"c",-1));
        Long sr = new Long(RequestUtils.getLongParameter(httpServletRequest,"sr",-1));

        if(actiune.equals("1")){
            return adaugaChestionar(httpServletRequest,httpServletResponse);
        }

        if(actiune.equals("3")){
            setRezultatDao.stergeSetRezultate(sr);
            return showChestionar(httpServletRequest,httpServletResponse);
        }

        return showChestionar(httpServletRequest,httpServletResponse);
    }

    public ModelAndView userDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
    {
        if(RequestUtils.getStringParameter(httpServletRequest, "action","vizualizare").equals("Salveaza"))
        {
            Map myModel = new HashMap();
            addUserAndRole(httpServletRequest, myModel);

            utilizatorSistemDao.updateUser(
                                        RequestUtils.getStringParameter(httpServletRequest,"idUtilizator","-1"),
                                        RequestUtils.getStringParameter(httpServletRequest,"email","n/a"),
                                        RequestUtils.getStringParameter(httpServletRequest,"parola","n/a")
            );


            myModel.put("pagina","index-content");
            return new ModelAndView("index", myModel);
        } else {
            Map myModel = new HashMap();
            addUserAndRole(httpServletRequest, myModel);

            String userName = RequestUtils.getStringParameter(httpServletRequest,"utilizatorSistem", httpServletRequest.getRemoteUser());
            UtilizatorSistem us = utilizatorSistemDao.getUtilizator(userName);

            myModel.put("utilizator", us);
            myModel.put("pagina","user-detail");
            return new ModelAndView("index", myModel);
        }
    }

    private ModelAndView adaugaChestionar(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map myModel = new HashMap();
        addUserAndRole(httpServletRequest, myModel);
        Long id = new Long(RequestUtils.getLongParameter(httpServletRequest,"c",-1));
        Long stage = new Long(RequestUtils.getLongParameter(httpServletRequest,"stage",1));
        Long sr = new Long(RequestUtils.getLongParameter(httpServletRequest,"sr",-1));

        String judetSelectat = RequestUtils.getStringParameter(httpServletRequest, "judetSelectat", "nimic");
        String localitateSelectata = RequestUtils.getStringParameter(httpServletRequest, "localitateSelectata", "nimic");
        String unitateSelectata = RequestUtils.getStringParameter(httpServletRequest, "unitateSelectata", "nimic");


        myModel.put("judete",unitateInvatamantDao.getJudete());

        switch(stage.intValue())
        {
            case 1:
                myModel.put("stage","1");
                break;
            case 2:
                myModel.put("judetSelectat",judetSelectat);
                myModel.put("stage","2");
                myModel.put("localitati",unitateInvatamantDao.getLocalitatiForJudet(judetSelectat));
                break;
            case 3:
                myModel.put("judetSelectat",judetSelectat);
                myModel.put("stage","3");
                myModel.put("localitati",unitateInvatamantDao.getLocalitatiForJudet(judetSelectat));
                myModel.put("localitateSelectata",localitateSelectata);
                myModel.put("unitati",unitateInvatamantDao.getUnitatiForJudetAndLocalitate(judetSelectat,localitateSelectata));
                break;
            case 4:
                myModel.put("judetSelectat",judetSelectat);
                myModel.put("stage","4");
                myModel.put("localitati",unitateInvatamantDao.getLocalitatiForJudet(judetSelectat));
                myModel.put("localitateSelectata",localitateSelectata);
                myModel.put("unitati",unitateInvatamantDao.getUnitatiForJudetAndLocalitate(judetSelectat,localitateSelectata));
                myModel.put("unitateSelectata",unitateSelectata);
                myModel.put("serie",unitateInvatamantDao.getSerieForUnitateDinJudetSiLocalitate(judetSelectat,localitateSelectata,unitateSelectata));
                myModel.put("etapa",unitateInvatamantDao.getEtapaForUnitateDinJudetSiLocalitate(judetSelectat,localitateSelectata,unitateSelectata));
                myModel.put("setRezultat", ""+ unitateInvatamantDao.checkForDateGetId(judetSelectat,localitateSelectata,unitateSelectata, id));
                break;

            case 5:
                myModel.put("judetSelectat",setRezultatDao.getJudet(sr));
                myModel.put("stage","5");
                myModel.put("localitateSelectata",setRezultatDao.getLocalitate(sr));
                myModel.put("unitateSelectata",setRezultatDao.getUnitate(sr));
                myModel.put("serie",setRezultatDao.getSerie(sr));
                myModel.put("etapa",setRezultatDao.getEtapa(sr));
                myModel.put("setRezultat", sr);
                break;
        }

        myModel.put("idChestionar",""+id);
        myModel.put("chestionar",chestionarDao.getChestionar(id));
        myModel.put("rezultateMap", chestionarDao.getRezultateMap(id,sr));
        myModel.put("pagina","chestionar");

        return new ModelAndView("index", myModel);
    }

    public void setChestionarDao(ChestionarDao chestionarDao) {
        this.chestionarDao = chestionarDao;
    }

    public void setUnitateInvatamantDao(UnitateInvatamantDao unitateInvatamantDao) {
        this.unitateInvatamantDao = unitateInvatamantDao;
    }


    public void setSetRezultatDao(SetRezultatDao setRezultatDao) {
        this.setRezultatDao = setRezultatDao;
    }

    public void setUtilizatorSistemDao(UtilizatorSistemDao utilizatorSistemDao) {
        this.utilizatorSistemDao = utilizatorSistemDao;
    }

}
