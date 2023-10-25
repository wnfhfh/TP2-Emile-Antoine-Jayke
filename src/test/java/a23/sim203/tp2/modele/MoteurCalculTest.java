/**
 * La classe MoteurCalculTest est responsable du bon fonctionnement des équations et du calcul
 * des valeurs des variables associées.
 *
 * @author Jayke Gagné, Antoine Houde, Émile Roy
 */

package a23.sim203.tp2.modele;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MoteurCalculTest {

    private MoteurCalcul moteurCalcul;
    JFXPanel jfxPanel = new JFXPanel();

    /**
     * Prépare le moteur de calcul avant chaque test en désactivant la tête graphique AWT.
     */
    @BeforeEach
    public void prepareMoteur() {
        System.setProperty("java.awt.headless", "true");
        moteurCalcul = new MoteurCalcul();
    }

    /**
     * Teste l'ajout d'une variable et vérifie si elle est correctement ajoutée au moteur de calcul.
     */
    @Test
    public void testAjoute1Variable() {
        moteurCalcul.ajouteEquation("a0=x1");
//        Set<Map.Entry<String, Constant>> resultat = moteurCalcul.getVariableValueMap().entrySet();
        Constant resultat = (Constant) moteurCalcul.getVariableValueMap().get("x1");
        assertEquals(Double.NaN, resultat.getConstantValue());
        assertEquals("x1", resultat.getConstantName());
    }

    /**
     * Teste l'ajout d'une équation avec une variable associée et vérifie si la valeur de la variable
     * est correctement mise à jour dans le moteur de calcul.
     */
    @Test
    public void testAjoute1Equation1Variable() {
        moteurCalcul.ajouteEquation("a0=x1");
        moteurCalcul.setValeurVariable("x1", 1);

//        Set<Map.Entry<String, Constant>> resultat = moteurCalcul.getVariableValueMap().entrySet();
        Constant resultat = (Constant) moteurCalcul.getVariableValueMap().get("x1");
        assertEquals(1, resultat.getConstantValue());
        assertEquals("x1", resultat.getConstantName());
    }

    /**
     * Teste l'ajout d'une équation avec trois variables et vérifie si toutes les variables sont
     * correctement ajoutées au moteur de calcul.
     */
    @Test
    public void testAjoute1Equation3Variable() {
        moteurCalcul.ajouteEquation("a0=x0+x1+x3");
        boolean resultat = moteurCalcul.getVariableValueMap().keySet().containsAll(Set.of("x0", "x1", "x3"));
        assertTrue(resultat);
    }

    /**
     * Teste le remplacement d'une variable par une équation et vérifie si le moteur de calcul
     * gère correctement cette transformation.
     */
    @Test
    public void testRemplaceVariableParEquation() {
        moteurCalcul.ajouteEquation("a0=x0+1");// varaible x0 créée
        boolean etape1 = moteurCalcul.getVariableValueMap().containsKey("x0");

        moteurCalcul.ajouteEquation("x0=x1+3");// variable x0 devient équation
        boolean etape2 = !moteurCalcul.getVariableValueMap().containsKey("x0");
        boolean etape3 = moteurCalcul.getVariableValueMap().containsKey("x1");

        assertTrue(etape1 && etape2 && etape3);
    }

    /**
     * Teste la suppression d'une équation simple et vérifie si elle est correctement retirée
     * du moteur de calcul.
     */
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

    /**
     * Teste la suppression d'une équation avec une variable partagée
     * et vérifie si le moteur de calcul gère correctement cette situation.
     */

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

    /**
     * Teste le calcul d'une équation simple et vérifie si le résultat est correct.
     */
    @Test
    public void testCalculSimple() {
        Equation equation = new Equation("test0", "2*(3-6)");
        double resultat = moteurCalcul.calcule(equation);
        assertEquals(-6, resultat);
    }

    /**
     * Teste le calcul d'une équation avec une variable et vérifie si le résultat est correct.
     */
    @Test
    public void testCalcul1Variable() {
        moteurCalcul.ajouteEquation("a0=b0+1");
        moteurCalcul.setValeurVariable("b0", 4);
        double resultat = moteurCalcul.calcule("a0");
        assertEquals(5, resultat);
    }

    /**
     * Teste le calcul d'une équation avec deux variables et vérifie si le résultat est correct.
     */

    @Test
    public void testCalcul2Variable() {
        moteurCalcul.ajouteEquation("a0=b0*c0");
        moteurCalcul.setValeurVariable("b0", 4);
        moteurCalcul.setValeurVariable("c0", 5);

        double resultat = moteurCalcul.calcule("a0");
        assertEquals(20, resultat);
    }

    /**
     * Teste le calcul de deux équations en chaîne et vérifie si le résultat final est correct.
     */
    @Test
    public void testCalcul2Equation() {
        moteurCalcul.ajouteEquation("a0=b0*2");
        moteurCalcul.ajouteEquation("b0=3+v0");
        moteurCalcul.setValeurVariable("v0", 4);

        double resultat = moteurCalcul.calcule("a0");
        assertEquals(14, resultat);
    }

    /**
     * Teste le calcul d'une équation avec deux variables interdépendantes
     * et vérifie si le résultat est correct.
     */
    @Test
    public void testCalcul2Equation2Var() {
        moteurCalcul.ajouteEquation("a0=b0*c2");
        moteurCalcul.ajouteEquation("b0=c2+g7");
        moteurCalcul.setValeurVariable("c2", 4);
        moteurCalcul.setValeurVariable("g7", 5);
        double resultat = moteurCalcul.calcule("a0");
        assertEquals(36, resultat);
    }

    /**
     * Teste la récupération de toutes les variables du moteur de calcul
     * et vérifie si les valeurs sont correctes.
     */
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

    /**
     * Teste la récupération de toutes les équations du moteur de calcul
     * et vérifie si les équations sont correctement ajoutées.
     */

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

    /**
     * Teste la détection d'une équation récursive dans le moteur de calcul
     * en utilisant une exécution différée dans la plateforme JavaFX.
     */
    @Test
    public void testEquationRecursive() {
        Platform.runLater(() -> {
            boolean estRecursive = false;
            moteurCalcul.ajouteEquation("a0=5+a0");
            moteurCalcul.getEquationMap().get("a0");
            estRecursive = moteurCalcul.equationEstRecursive("a0");
            assertTrue(estRecursive);
        });
    }

    /**
     * Teste la séparation d'une équation de chaque coté du symbole égal
     */
    @Test
    public void testParseEquation() {
        Platform.runLater(() -> {
            Equation result = moteurCalcul.parseEquation("c0=6+b0");
            assertEquals("c0", result.getNom());
            assertEquals("6+b0", result.getExpression());
        });
    }


    /**
     * Teste la suppression de variables inutiles et s'assure que le moteur de calcul
     * fonctionne correctement après la suppression.
     */
    @Test

    public void testRetireVariableInutile() {
        Platform.runLater(() -> {
            moteurCalcul.setValeurVariable("a", 1.0);
            moteurCalcul.setValeurVariable("b", 2.0);

            moteurCalcul.ajouteEquation("x=a+b");
            moteurCalcul.ajouteEquation("y = a + b");
            assertEquals(1.0, moteurCalcul.calcule("x"));
            assertEquals(2.0, moteurCalcul.calcule("y"));

            moteurCalcul.retireVariablesInutiles();
            assertFalse(moteurCalcul.getAllVariables().contains("y"));

            assertEquals(1.0, moteurCalcul.calcule("x"));
            assertEquals(Double.NaN, moteurCalcul.calcule("y"));
        });
    }

    /**
     * Teste la suppression de variables inutiles avec une équation récursive et
     * vérifie que le moteur de calcul traite correctement les dépendances.
     */
    @Test
    public void testRetireVariablesInutilesAvecEquationRecursive() {
        Platform.runLater(() -> {
            MoteurCalcul moteurCalcul = new MoteurCalcul();

            moteurCalcul.setValeurVariable("a", 1.0);
            moteurCalcul.setValeurVariable("b", 2.0);

            moteurCalcul.ajouteEquation("x = a + b");
            moteurCalcul.ajouteEquation("y = a * b");
            moteurCalcul.ajouteEquation("z = x + y"); // Equation récursive

            assertEquals(3.0, moteurCalcul.calcule("x"));
            assertEquals(2.0, moteurCalcul.calcule("y"));
            assertEquals(5.0, moteurCalcul.calcule("z"));

            moteurCalcul.retireVariablesInutiles();

            assertFalse(moteurCalcul.getAllVariables().contains("y"));
            assertFalse(moteurCalcul.getAllVariables().contains("z"));

            assertEquals(3.0, moteurCalcul.calcule("x"));
            assertEquals(Double.NaN, moteurCalcul.calcule("y"));
            assertEquals(Double.NaN, moteurCalcul.calcule("z"));
        });
    }
}