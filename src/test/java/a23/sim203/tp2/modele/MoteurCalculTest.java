package a23.sim203.tp2.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoteurCalculTest {

    private MoteurCalcul moteurCalcul;

    @BeforeEach
    public void prepareMoteur() {
        moteurCalcul = new MoteurCalcul();
    }

    @Test
    public void testAjoute1Variable() {
        moteurCalcul.ajouteEquation("a0=x1");
//        Set<Map.Entry<String, Constant>> resultat = moteurCalcul.getVariableValueMap().entrySet();
        Constant resultat = (Constant) moteurCalcul.getVariableValueMap().get("x1");
        assertEquals(Double.NaN, resultat.getConstantValue());
        assertEquals("x1", resultat.getConstantName());
    }

    @Test
    public void testAjoute1Equation1Variable() {
        moteurCalcul.ajouteEquation("a0=x1");
        moteurCalcul.setValeurVariable("x1", 1);

//        Set<Map.Entry<String, Constant>> resultat = moteurCalcul.getVariableValueMap().entrySet();
        Constant resultat = (Constant) moteurCalcul.getVariableValueMap().get("x1");
        assertEquals(1, resultat.getConstantValue());
        assertEquals("x1", resultat.getConstantName());
    }

    @Test
    public void testAjoute1Equation3Variable() {
        moteurCalcul.ajouteEquation("a0=x0+x1+x3");
        boolean resultat = moteurCalcul.getVariableValueMap().keySet().containsAll(
                Set.of("x0", "x1", "x3"));
        assertTrue(resultat);
    }

    @Test
    public void testRemplaceVariableParEquation() {
        moteurCalcul.ajouteEquation("a0=x0+1");// varaible x0 créée
        boolean etape1 = moteurCalcul.getVariableValueMap().containsKey("x0");

        moteurCalcul.ajouteEquation("x0=x1+3");// variable x0 devient équation
        boolean etape2 = !moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape3 = moteurCalcul.getVariableValueMap().containsKey("x1");

        assertTrue(etape1 && etape2 && etape3);
    }

    @Test
    public void testEffaceEquationSimple() {
        moteurCalcul.ajouteEquation("a0=x0+1");// varaible x0 créée
        boolean etape1 = moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape2 = moteurCalcul.getEquationMap().containsKey("a0");

        moteurCalcul.effaceEquation("a0");// variable x0 devient équation
        boolean etape3 = !moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape4 = !moteurCalcul.getEquationMap().containsKey("a0");

        assertTrue(etape1 && etape2 && etape3 && etape4);
    }


    @Test
    public void testEffaceEquationVariablePartagee() {
        moteurCalcul.ajouteEquation("a0=x0+1");// varaible x0 créée
        moteurCalcul.ajouteEquation("b0=x0+2");// varaible x0 encore utilisé

        boolean etape1 = moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape2 = moteurCalcul.getEquationMap().containsKey("a0");
        boolean etape3 = moteurCalcul.getEquationMap().containsKey("b0");

        moteurCalcul.effaceEquation("a0");// variable x0 doit demeurer
        boolean etape4 = moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape5 = !moteurCalcul.getEquationMap().containsKey("a0");
        boolean etape6 = moteurCalcul.getEquationMap().containsKey("b0");


        assertTrue(etape1 && etape2 && etape3 && etape4 && etape5 && etape6);
    }

    @Test
    public void testCalculSimple() {
        Equation equation = new Equation("test0", "2*(3-6)");
        double resultat = moteurCalcul.calcule(equation);
        assertEquals(-6, resultat);
    }

    @Test
    public void testCalcul1Variable() {
        moteurCalcul.ajouteEquation("a0=b0+1");
        moteurCalcul.setValeurVariable("b0", 4);
        double resultat = moteurCalcul.calcule("a0");
        assertEquals(5, resultat);
    }

    @Test
    public void testCalcul2Variable() {
        moteurCalcul.ajouteEquation("a0=b0*c0");
        moteurCalcul.setValeurVariable("b0", 4);
        moteurCalcul.setValeurVariable("c0", 5);

        double resultat = moteurCalcul.calcule("a0");
        assertEquals(20, resultat);
    }

    @Test
    public void testCalcul2Equation() {
        moteurCalcul.ajouteEquation("a0=b0*2");
        moteurCalcul.ajouteEquation("b0=3+v0");
        moteurCalcul.setValeurVariable("v0", 4);

        double resultat = moteurCalcul.calcule("a0");
        assertEquals(14, resultat);
    }

    @Test
    public void testCalcul2Equation2Var() {
        moteurCalcul.ajouteEquation("a0=b0*c2");
        moteurCalcul.ajouteEquation("b0=c2+g7");
        moteurCalcul.setValeurVariable("c2", 4);
        moteurCalcul.setValeurVariable("g7", 5);
        double resultat = moteurCalcul.calcule("a0");
        assertEquals(36, resultat);
    }

    @Test
    public void testGetToutesLesVariables() {
        moteurCalcul.setValeurVariable("a0", 2.0);
        moteurCalcul.setValeurVariable("a1", 5.5);
        moteurCalcul.setValeurVariable("a2", Double.NaN);

        HashSet<String> setValidation = new HashSet<String>();
        setValidation.add("a0 = 2.0");
        setValidation.add("a1 = 5.5");
        setValidation.add("a2 = NaN");
        assertEquals(moteurCalcul.getToutesLesVariables(), setValidation);

        moteurCalcul.setValeurVariable("a0", 3.0);
        setValidation.remove("a0 = 2.0");
        setValidation.add("a0 = 3.0");
        assertEquals(moteurCalcul.getToutesLesVariables(), setValidation);
    }

    @Test
    public void testGetAllEquations() {
        moteurCalcul.ajouteEquation("a0=2*b0");
        moteurCalcul.ajouteEquation("a1=2+2");
        moteurCalcul.ajouteEquation("a2=a0*b0");

        HashSet<Equation> setValidation = new HashSet<Equation>();
        setValidation.add(new Equation("a0", "2*b0"));
        setValidation.add(new Equation("a1", "2+2"));
        setValidation.add(new Equation("a2", "a0*b0"));
        assertEquals(moteurCalcul.getAllEquations(), setValidation);
    }
}