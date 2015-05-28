package ro.seaurchin.websurvey;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import ro.seaurchin.websurvey.securitate.UtilizatorSistem;
import ro.seaurchin.websurvey.securitate.dao.UtilizatorSistemDao;
import ro.seaurchin.websurvey.support.Chestionar;
import ro.seaurchin.websurvey.support.dao.ChestionarDao;
import ro.seaurchin.websurvey.support.dao.UnitateInvatamantDao;
import ro.seaurchin.websurvey.support.dao.impl.SetRezultatDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ChestionarDao chestionarDao;

    @Mock
    UnitateInvatamantDao unitateInvatamantDao;

    @Mock
    SetRezultatDao setRezultatDao;

    Controller controller;

    private final Long idChestionar = 1234L;
    private final Long idSetRezultat = 342535L;
    private ArrayList judete;
    private List localitati;
    private List results;
    private HashMap rezultateMap;

    private Chestionar chestionar;
    private final String addAction = "1";
    private final String deleteAction = "3";
    private final String showAction = "5"; // can be anything other than 1 or 3

    private final String judet = "un judet";
    private List unitati;
    private final String localitate = "localitate";
    private final String unitate = "unitate";
    private final String serie = "serie";
    private final String etapa = "etapa";
    String idUser = "7";
    String email = "a@b.com";
    String password = "a big secret";
    String username = "the user name";
    UtilizatorSistem user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new Controller();
        createJudete();
        createLocalitati();
        createResults();
        createRezultateMap();
        createChestionar();
        createUnitati();
        createUser();
    }

    @Test
    public void addNonAdminUserAndRole() throws Exception {
        when(request.getRemoteUser()).thenReturn("aUser");
        when(request.isUserInRole("Administrator")).thenReturn(false);
        Map model = new HashMap();

        model = controller.addUserAndRole(request, model);

        assertEquals("aUser", model.get("user"));
        assertFalse((Boolean) model.get("admin"));
    }

    @Test
    public void addAdminUserAndRole() throws Exception {
        when(request.getRemoteUser()).thenReturn("aUser");
        when(request.isUserInRole("Administrator")).thenReturn(true);
        Map model = new HashMap();

        model = controller.addUserAndRole(request, model);

        assertEquals("aUser", model.get("user"));
        assertTrue((Boolean) model.get("admin"));
    }

    @Test
    public void testShowIndex() throws Exception {
        ModelAndView modelAndView = controller.showIndex(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals("index-content", model.get("pagina"));
    }

    @Test
    public void testShowChestionar() throws Exception {
        ArrayList<Chestionar> chestionare = twoChestionare();
        when(chestionarDao.getChestionare()).thenReturn(chestionare);
        controller.setChestionarDao(chestionarDao);

        ModelAndView modelAndView = controller.showChestionar(request, response);
        Map model = modelAndView.getModel();

        assertViewIsIndex(modelAndView);
        assertArrayEquals(chestionare.toArray(), (Object[]) model.get("chestionare"));
        assertEquals("chestionar-content", model.get("pagina"));
    }

    @Test
    public void testShowListaRezultateWithoutFilter() throws Exception {
        mockGetListFromRezultatDao();
        mockGetChestionarDao();
        when(request.getParameter("chestionar")).thenReturn(idChestionar.toString());

        ModelAndView modelAndView = controller.showListaRezultate(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals("lista-rezultate", model.get("pagina"));
        assertEquals(results, model.get("setRezultat"));
        assertEquals(null, model.get("existaFiltru"));
        assertEquals(chestionar, model.get("chestionar"));
    }

    @Test
    public void testShowListaRezultateWithFilter() throws Exception {
        when(request.getParameter("filtru")).thenReturn(String.valueOf(1L));
        mockGetListFromRezultatDao();
        mockGetChestionarDao();
        when(request.getParameter("chestionar")).thenReturn(idChestionar.toString());

        ModelAndView modelAndView = controller.showListaRezultate(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals("lista-rezultate", model.get("pagina"));
        assertEquals(results, model.get("setRezultat"));
        assertEquals("da", model.get("existaFiltru"));
        assertEquals(chestionar, model.get("chestionar"));
    }

    @Test
    public void testProcessChestionarAddAndStageOne() throws Exception {
        String stage = "1";
        mockGetJudete();
        mockGetChestionarDao();
        mockGetRezultateMap();
        mockRequest(stage);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals(idChestionar.toString(), model.get("idChestionar"));
        assertEquals(chestionar, model.get("chestionar"));
        assertEquals(rezultateMap, model.get("rezultateMap"));
        assertEquals("chestionar", model.get("pagina"));
        assertEquals(stage, model.get("stage"));
    }

    @Test
    public void testProcessChestionarAddAndStageTwo() throws Exception {
        String stage = "2";
        mockGetJudete();
        mockGetChestionarDao();
        mockGetRezultateMap();
        mockRequest(stage);
        when(request.getParameter("judetSelectat")).thenReturn(judet);
        when(unitateInvatamantDao.getLocalitatiForJudet(judet)).thenReturn(localitati);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals(idChestionar.toString(), model.get("idChestionar"));
        assertEquals(chestionar, model.get("chestionar"));
        assertEquals(rezultateMap, model.get("rezultateMap"));
        assertEquals("chestionar", model.get("pagina"));
        assertEquals(stage, model.get("stage"));
        assertEquals(judet, model.get("judetSelectat"));
        assertEquals(localitati, model.get("localitati"));
    }

    @Test
    public void testProcessChestionarAddAndStageThree() throws Exception {
        String stage = "3";
        mockGetJudete();
        mockGetChestionarDao();
        mockGetRezultateMap();
        mockRequest(stage);
        when(request.getParameter("judetSelectat")).thenReturn(judet);
        when(request.getParameter("localitateSelectata")).thenReturn(localitate);
        when(unitateInvatamantDao.getLocalitatiForJudet(judet)).thenReturn(localitati);
        when(unitateInvatamantDao.getUnitatiForJudetAndLocalitate(judet, localitate)).thenReturn(unitati);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals(idChestionar.toString(), model.get("idChestionar"));
        assertEquals(chestionar, model.get("chestionar"));
        assertEquals(rezultateMap, model.get("rezultateMap"));
        assertEquals("chestionar", model.get("pagina"));
        assertEquals(stage, model.get("stage"));
        assertEquals(judet, model.get("judetSelectat"));
        assertEquals(localitati, model.get("localitati"));
        assertEquals(localitate, model.get("localitateSelectata"));
        assertEquals(unitati, model.get("unitati"));
    }

    @Test
    public void testProcessChestionarAddAndStageFour() throws Exception {
        String stage = "4";
        mockGetJudete();
        mockGetChestionarDao();
        mockGetRezultateMap();
        mockRequest(stage);
        when(request.getParameter("judetSelectat")).thenReturn(judet);
        when(request.getParameter("localitateSelectata")).thenReturn(localitate);
        when(request.getParameter("c")).thenReturn(idChestionar.toString());
        when(unitateInvatamantDao.getLocalitatiForJudet(judet)).thenReturn(localitati);
        when(unitateInvatamantDao.getUnitatiForJudetAndLocalitate(judet, localitate)).thenReturn(unitati);
        when(unitateInvatamantDao.getSerieForUnitateDinJudetSiLocalitate(judet, localitate, unitate)).thenReturn(serie);
        when(unitateInvatamantDao.getEtapaForUnitateDinJudetSiLocalitate(judet, localitate, unitate)).thenReturn(etapa);
        when(unitateInvatamantDao.checkForDateGetId(judet, localitate, unitate, idChestionar)).thenReturn(idSetRezultat);
        when(request.getParameter("unitateSelectata")).thenReturn(unitate);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals(idChestionar.toString(), model.get("idChestionar"));
        assertEquals(chestionar, model.get("chestionar"));
        assertEquals(rezultateMap, model.get("rezultateMap"));
        assertEquals("chestionar", model.get("pagina"));
        assertEquals(stage, model.get("stage"));
        assertEquals(judet, model.get("judetSelectat"));
        assertEquals(localitati, model.get("localitati"));
        assertEquals(localitate, model.get("localitateSelectata"));
        assertEquals(unitati, model.get("unitati"));
        assertEquals(unitate, model.get("unitateSelectata"));
        assertEquals(serie, model.get("serie"));
        assertEquals(etapa, model.get("etapa"));
        assertEquals(idSetRezultat.toString(), model.get("setRezultat"));
    }

    @Test
    public void testProcessChestionarAddAndStageFive() throws Exception {
        String stage = "5";
        mockGetJudete();
        mockRequest(stage);
        mockGetChestionarDao();
        mockGetRezultateMap();
        mockGetListFromRezultatDao();
        when(request.getParameter("sr")).thenReturn(idSetRezultat.toString());
        when(setRezultatDao.getJudet(idSetRezultat)).thenReturn(judet);
        when(setRezultatDao.getLocalitate(idSetRezultat)).thenReturn(localitate);
        when(setRezultatDao.getUnitate(idSetRezultat)).thenReturn(unitate);
        when(setRezultatDao.getSerie(idSetRezultat)).thenReturn(serie);
        when(setRezultatDao.getEtapa(idSetRezultat)).thenReturn(etapa);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals(judet, model.get("judetSelectat"));
        assertEquals(localitate, model.get("localitateSelectata"));
        assertEquals(unitate, model.get("unitateSelectata"));
        assertEquals(serie, model.get("serie"));
        assertEquals(etapa, model.get("etapa"));
        assertEquals(idSetRezultat, model.get("setRezultat"));
        assertEquals(stage, model.get("stage"));
        assertEquals(idChestionar.toString(), model.get("idChestionar"));
        assertEquals(chestionar, model.get("chestionar"));
        assertEquals(rezultateMap, model.get("rezultateMap"));
        assertEquals("chestionar", model.get("pagina"));
    }

    @Test
    public void testProcessChestionarDelete() throws Exception {
        ArrayList<Chestionar> chestionare = twoChestionare();
        when(chestionarDao.getChestionare()).thenReturn(chestionare);
        controller.setChestionarDao(chestionarDao);
        when(request.getParameter("actiune")).thenReturn(deleteAction);
        mockGetJudete();
        mockGetChestionarDao();
        controller.setSetRezultatDao(setRezultatDao);
        when(request.getParameter("sr")).thenReturn(idSetRezultat.toString());

        ModelAndView modelAndView = controller.processChestionar(request, response);
        Map modelMap = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        verify(setRezultatDao).stergeSetRezultate(idSetRezultat);
        assertArrayEquals(chestionare.toArray(), (Object[]) modelMap.get("chestionare"));
        assertEquals("chestionar-content", modelMap.get("pagina"));
    }

    @Test
    public void testProcessChestionarShow() throws Exception {
        ArrayList<Chestionar> chestionare = twoChestionare();
        when(chestionarDao.getChestionare()).thenReturn(chestionare);
        controller.setChestionarDao(chestionarDao);
        when(request.getParameter("actiune")).thenReturn(showAction);

        ModelAndView modelAndView = controller.processChestionar(request, response);
        Map modelMap = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertArrayEquals(chestionare.toArray(), (Object[]) modelMap.get("chestionare"));
        assertEquals("chestionar-content", modelMap.get("pagina"));
    }

    @Test
    public void testDoLogout() throws Exception{
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        ModelAndView modelAndView = controller.doLogout(request, response);

        assertNull(modelAndView);
        verify(session).invalidate();
        verify(response).sendRedirect("index.html");
    }

    @Test
    public void testSaveUserDetail() throws Exception{
        UtilizatorSistemDao utilizatorSistemDao = mock(UtilizatorSistemDao.class);
        controller.setUtilizatorSistemDao(utilizatorSistemDao);
        when(request.getParameter("action")).thenReturn("Salveaza");
        when(request.getParameter("idUtilizator")).thenReturn(idUser);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("parola")).thenReturn(password);

        ModelAndView modelAndView = controller.userDetail(request, response);
        ModelMap modelMap = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals("index-content", modelMap.get("pagina"));
        verify(utilizatorSistemDao).updateUser(idUser, email, password);
    }

    @Test
    public void testViewUserDetail() throws Exception{
        when(request.getParameter("utilizatorSistem")).thenReturn(username);
        UtilizatorSistemDao utilizatorSistemDao = mock(UtilizatorSistemDao.class);
        controller.setUtilizatorSistemDao(utilizatorSistemDao);
        when(utilizatorSistemDao.getUtilizator(username)).thenReturn(user);
        when(request.getParameter("action")).thenReturn("Vizualizeaza");

        ModelAndView modelAndView = controller.userDetail(request, response);
        ModelMap modelMap = modelAndView.getModelMap();

        assertViewIsIndex(modelAndView);
        assertEquals("user-detail", modelMap.get("pagina"));
        assertEquals(user, modelMap.get("utilizator"));

    }

    private void createUnitati() {
        unitati = new ArrayList();
        unitati.add("a");
        unitati.add("b");
    }

    private void assertViewIsIndex(ModelAndView modelAndView) {
        assertEquals("index", modelAndView.getViewName());
    }

    private void mockRequest(String stage) {
        when(request.getParameter("actiune")).thenReturn(addAction);
        when(request.getParameter("c")).thenReturn(idChestionar.toString());
        when(request.getParameter("sr")).thenReturn(idSetRezultat.toString());
        when(request.getParameter("stage")).thenReturn(stage);
    }

    private void mockGetListFromRezultatDao() {
        when(setRezultatDao.getLista(idSetRezultat)).thenReturn(results);
        controller.setSetRezultatDao(setRezultatDao);
    }

    private void mockGetRezultateMap() {
        when(chestionarDao.getRezultateMap(idChestionar, idSetRezultat)).thenReturn(rezultateMap);
    }

    private void mockGetChestionarDao() {
        when(chestionarDao.getChestionar(idChestionar)).thenReturn(chestionar);
        controller.setChestionarDao(chestionarDao);
    }

    private void mockGetJudete() {
        when(unitateInvatamantDao.getJudete()).thenReturn(judete);
        controller.setUnitateInvatamantDao(unitateInvatamantDao);
    }

    private void createChestionar() {
        chestionar = new Chestionar();
    }

    private void createRezultateMap() {
        rezultateMap = new HashMap();
    }

    private void createLocalitati() {
        localitati = new ArrayList();
        localitati.add("a");
        localitati.add("b");
    }

    private ArrayList<Chestionar> twoChestionare() {
        ArrayList<Chestionar> chestionare = new ArrayList<Chestionar>();
        chestionare.add(new Chestionar());
        chestionare.add(new Chestionar());
        return chestionare;
    }

    private void createJudete() {
        judete = new ArrayList();
        judete.add("a");
        judete.add("b");
    }

    private void createResults() {
        results = new ArrayList();
    }

    private void createUser() {
        user = new UtilizatorSistem();
    }
}