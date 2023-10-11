package a23.sim203.tp2.modele;

import javafx.scene.control.Alert;
import org.mariuszgromada.math.mxparser.*;

import java.util.*;

public class MoteurCalcul {

    // ajoutez les attributs pour stocker les équations et les variables
    private HashMap<Object, Object> variableMap;
    private HashMap<Object, Object> equationMap;

    public MoteurCalcul() {
        License.iConfirmNonCommercialUse("Cegep Limoilou");
        variableMap = new HashMap<>();
        equationMap = new HashMap<>();

    }

    private Set<String> determineToutesVariablesRequises() {
        return (Set<String>) getToutesLesVariables();
    }

    private void ajouteVariable(String variable, double valeur) {
        variableMap.put(variable, new Constant(variable, valeur));
    }

    public void setValeurVariable(String nomVariable, double valeur) {
        variableMap.put(nomVariable, new Constant(nomVariable, valeur));
    }

    public void ajouteEquation(String nouvelleEquation) {
        String[] nouvelleEquationSplit = nouvelleEquation.split("=");
        ArrayList<String> nomsVariables = new ArrayList<>();
        try {
            Equation equation = new Equation(nouvelleEquationSplit[0], nouvelleEquationSplit[1]);
            equationMap.put(equation.getNom(), new Expression(equation.getExpression()));
            Collections.addAll(nomsVariables, equation.getExpression().split("[+]"));
            Collections.addAll(nomsVariables, equation.getExpression().split("[-]"));
            Collections.addAll(nomsVariables, equation.getExpression().split("[/]"));
            Collections.addAll(nomsVariables, equation.getExpression().split("[*]"));
            //TODO faut ajouter nomsVariables a variableMap mais je sais pas sous quelle forme
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Équation non valide");
        }
    }


    public void effaceEquation(String nomEquation) {

    }

    public double calcule(String nomEquation) {

        return 0; // à changer
    }

    public double calcule(Equation equation) {

        return 0; // à changer

    }

    public Collection<String> getToutesLesVariables() {
        HashSet<String> toutesLesVariables = new HashSet<String>();

        Iterator iterator = variableMap.values().iterator();
        while (iterator.hasNext()) {
            Expression expressionTemp = (Expression) iterator.next();
        }

        return toutesLesVariables; // à changer
    }

    public Equation getToutesLesEquations() {
        return null; // à changer

    }


    public Map<Object, Object> getVariableValueMap() {
        return variableMap; // à changer
    }

    public Map<Object, Object> getEquationMap() {
        return variableMap;
    }

    public static void main(String[] args) {

        // Comment utiliser les expression avec plusieurs variables

        License.iConfirmNonCommercialUse("Cegep Limoilou");

        Constant A0 = new Constant("a0", 3);
        Constant B0 = new Constant("b0", 3);


        Expression e1 = new Expression("3+4+a0+b0", A0, B0);
        Expression e2 = new Expression("3+4+a0+b0", new Constant[]{A0, B0});// alternative avec tableau

        System.out.println("e1=" + e1.calculate());
        System.out.println("e2=" + e2.calculate());

    }
}
