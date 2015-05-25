package ro.seaurchin.websurvey.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import ro.seaurchin.websurvey.Controller;
import ro.seaurchin.websurvey.support.Chestionar;
import ro.seaurchin.websurvey.support.dao.ChestionarDao;
import ro.seaurchin.websurvey.support.dao.impl.SetRezultatDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ChestionarDao chestionarDao;

    Controller controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        controller = new Controller();
    }

    @Test
    public void testShowIndex() throws Exception{
        when(request.getRemoteUser()).thenReturn("aUser");
        when(request.isUserInRole("Administrator")).thenReturn(true);

        ModelAndView modelAndView = controller.showIndex(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertEquals("index", modelAndView.getViewName());
        assertEquals("index-content", model.get("pagina"));
        assertEquals("aUser", model.get("user"));
        assertTrue((Boolean) model.get("admin"));

    }

    @Test
    public void testShowListaRezultateWithoutFilter() throws Exception {
        SetRezultatDao setRezultatDao = mock(SetRezultatDao.class);
        List results = new ArrayList();
        when(setRezultatDao.getLista(anyLong())).thenReturn(results);
        controller.setSetRezultatDao(setRezultatDao);
        Chestionar chestionar = new Chestionar();
        ChestionarDao chestionarDao = mock(ChestionarDao.class);
        when(chestionarDao.getChestionar(anyLong())).thenReturn(chestionar);
        controller.setChestionarDao(chestionarDao);

        ModelAndView modelAndView = controller.showListaRezultate(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertEquals("index", modelAndView.getViewName());
        assertEquals("lista-rezultate", model.get("pagina"));
        assertEquals(results, model.get("setRezultat"));
        assertEquals(null, model.get("existaFiltru"));
        assertEquals(chestionar, model.get("chestionar"));
    }

    @Test
    public void testShowListaRezultateWithFilter() throws Exception {
        when(request.getParameter("filtru")).thenReturn(String.valueOf(1L));
        SetRezultatDao setRezultatDao = mock(SetRezultatDao.class);
        List results = new ArrayList();
        when(setRezultatDao.getLista(anyLong())).thenReturn(results);
        controller.setSetRezultatDao(setRezultatDao);
        Chestionar chestionar = new Chestionar();
        ChestionarDao chestionarDao = mock(ChestionarDao.class);
        when(chestionarDao.getChestionar(anyLong())).thenReturn(chestionar);
        controller.setChestionarDao(chestionarDao);

        ModelAndView modelAndView = controller.showListaRezultate(request, response);
        ModelMap model = modelAndView.getModelMap();

        assertEquals("index", modelAndView.getViewName());
        assertEquals("lista-rezultate", model.get("pagina"));
        assertEquals(results, model.get("setRezultat"));
        assertEquals("da", model.get("existaFiltru"));
        assertEquals(chestionar, model.get("chestionar"));
    }

    @Test
    public void testShowChestionar() throws Exception {
        ArrayList<Chestionar> chestionare = twoChestionare();
        when(chestionarDao.getChestionare()).thenReturn(chestionare);
        controller.setChestionarDao(chestionarDao);
        when(request.getRemoteUser()).thenReturn("aUser");
        when(request.isUserInRole("Administrator")).thenReturn(true);

        ModelAndView modelAndView = controller.showChestionar(request, response);
        Map model = modelAndView.getModel();

        assertEquals("index", modelAndView.getViewName());
        assertEquals("aUser", model.get("user"));
        assertTrue((Boolean) model.get("admin"));
        assertArrayEquals(chestionare.toArray(), (Object[]) model.get("chestionare"));
        assertEquals("chestionar-content", model.get("pagina"));
    }

    private ArrayList<Chestionar> twoChestionare() {
        ArrayList<Chestionar> chestionare = new ArrayList<Chestionar>();
        chestionare.add(new Chestionar());
        chestionare.add(new Chestionar());
        return chestionare;
    }
}