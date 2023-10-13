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

    public void ajouteEquation(String newEquation) {
        try {
            Equation equation = parseEquation(newEquation);
            equationMap.put(equation.getNom(), new Expression(equation.getExpression()));
            addVariablesFromEquation(equation);
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Équation non valide");
        }
    }

    private Equation parseEquation(String equationString) {
        String[] equationSplit = equationString.split("=");

        return new Equation(equationSplit[0], equationSplit[1]);
    }

    private void addVariablesFromEquation(Equation equation) {
        String[] variableNames = equation.getExpression().split("[+\\-*/]");
        for (String variableName : variableNames) {
            variableMap.put(variableName, new Constant(variableName, Double.NaN));
        }
    }

    public void effaceEquation(String nomEquation) {
        if (equationMap.containsKey(nomEquation)) {
            Expression associatedExpression = (Expression) equationMap.get(nomEquation);
            equationMap.remove(nomEquation);
            if (associatedExpression != null) {
                variableMap.replace(associatedExpression.getExpressionString(), 0.0);
            }
            variableMap.keySet().removeIf(variable -> !equationMap.containsKey(variable));
        }
    }

    public double calcule(String nomEquation) {
        Expression expression = (Expression) equationMap.get(nomEquation);
        if (expression != null) {
            return expression.calculate();
        }
        return Double.NaN;
    }

    public Set<Object> getAllVariables() {
        return variableMap.keySet();
    }

    public Collection<Object> getAllEquations() {
        return equationMap.values();
    }

    public HashMap<Object, Object> getVariableValues() {
        return variableMap;
    }

    public HashMap<Object, Object> getEquationExpressions() {
        return equationMap;
    }


    public double calcule(Equation equation) {
//        equation.getElementsRequis();
//        equation.getExpression();
//
//        Expression expression = new Expression(equation.getExpression(), (PrimitiveElement) equation.getElementsRequis());
//        expression.calculate();
//        essayé de commencer la méthode sans succès

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
